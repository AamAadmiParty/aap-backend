package com.next.aap.task.util;

import java.io.IOException;
import java.io.InputStream;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.auth.PropertiesCredentials;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClient;
import com.amazonaws.services.sqs.model.DeleteMessageRequest;
import com.amazonaws.services.sqs.model.Message;
import com.amazonaws.services.sqs.model.ReceiveMessageRequest;
import com.amazonaws.services.sqs.model.ReceiveMessageResult;

public abstract class AwsQueueListener {

	private String queueName;
	private int defaultWaitTime = 20;
	private AmazonSQS sqs;

	public AwsQueueListener(String queueName, String acccessKey, String secretKey) {
		this(Regions.US_WEST_2, queueName, acccessKey, secretKey);
	}

	public AwsQueueListener(Regions regions, String queueName, String accessKey, String secretKey) {
		this.queueName = queueName;
		System.out.println("queueName = "+queueName);
		System.out.println("accessKey = "+accessKey);
		System.out.println("secretKey = "+secretKey);
		AWSCredentials awsCredentials;
		awsCredentials = new BasicAWSCredentials(accessKey, secretKey);
		sqs = new AmazonSQSClient(awsCredentials);
		Region usWest2 = Region.getRegion(regions);
		sqs.setRegion(usWest2);
		stateListenerThread();

	}

	public abstract void onQueueMessage(String jsonMessage);

	private void stateListenerThread() {
		Runnable runnable = new Runnable() {

			@Override
			public void run() {
				ReceiveMessageRequest receiveMessageRequest = new ReceiveMessageRequest(queueName);
				receiveMessageRequest.setWaitTimeSeconds(defaultWaitTime);
				// Read only one message ata time
				receiveMessageRequest.setMaxNumberOfMessages(1);
				while (true) {
					try {
						System.out.println("Reading Message from Queue");
						ReceiveMessageResult rmResult = sqs.receiveMessage(receiveMessageRequest);
						// System.out.println("Got Result "+ rmResult);
						if (rmResult.getMessages().size() > 0) {
							// A message has been received
							for (Message message : rmResult.getMessages()) {
								try {
									onQueueMessage(message.getBody());
								} catch (Exception ex) {

								} finally {
									// Doesnt matter what happened, remove it
									// from AWS, its App's responsbility to make
									// sure that persist this message
									// in case of failure/exception
									try {
										// sqs.deleteMessage(new
										// DeleteMessageRequest(queueName,message.getReceiptHandle()));
									} catch (Exception ex) {
										// In case some error occurs
									}

								}

							}
						} else {
							System.out.println("No messages available, attempt ");
							// Wait for few second before trying again
						}

					} catch (Exception ex) {
						ex.printStackTrace();
					}

					try {
						Thread.sleep(10000);
					} catch (Exception ex) {

					}
				}

			}
		};

		new Thread(runnable).start();
	}

}
