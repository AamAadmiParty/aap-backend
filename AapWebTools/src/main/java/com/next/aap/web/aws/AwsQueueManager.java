package com.next.aap.web.aws;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClient;
import com.amazonaws.services.sqs.model.SendMessageRequest;
import com.google.gson.JsonObject;

@Component
public class AwsQueueManager {
	@Value("${poll_vote_queue_name}") 
	String pollVoteQueueName;
	
	@Value("${aws_access_key}") 
	String accessKey;
	
	@Value("${aws_access_secret}") 
	String accessSecret;
	
	@Value("${donation_queue_region}") 
	String donationQueueRegion;
	
	private AmazonSQS sqs;
	
	@PostConstruct
	public void init(){
		AWSCredentials awsCredentials;
		awsCredentials = new BasicAWSCredentials(accessKey, accessSecret);
		sqs = new AmazonSQSClient(awsCredentials);
		Region usWest2 = Region.getRegion(Regions.valueOf(donationQueueRegion));
		sqs.setRegion(usWest2);
	}
	
	public void sendPollVoteMessage(Long userId, Long pollQuestionId, Long answerId){
		JsonObject jsonObject = new JsonObject();
		jsonObject.addProperty("userId", userId);
		jsonObject.addProperty("pollQuestionId", pollQuestionId);
		jsonObject.addProperty("answerId", answerId);
		
		SendMessageRequest sendMessageRequest = new SendMessageRequest();
		sendMessageRequest.setQueueUrl(pollVoteQueueName);
		sendMessageRequest.setMessageBody(jsonObject.toString());
		System.out.println("Sending Message "+ jsonObject.toString());
		sqs.sendMessage(sendMessageRequest);
	}
}
