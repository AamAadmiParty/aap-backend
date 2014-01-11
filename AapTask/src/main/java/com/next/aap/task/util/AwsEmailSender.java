package com.next.aap.task.util;

import java.util.LinkedList;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailServiceAsync;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailServiceAsyncClient;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailServiceClient;
import com.amazonaws.services.simpleemail.model.Body;
import com.amazonaws.services.simpleemail.model.Content;
import com.amazonaws.services.simpleemail.model.Destination;
import com.amazonaws.services.simpleemail.model.Message;
import com.amazonaws.services.simpleemail.model.SendEmailRequest;
import com.amazonaws.services.simpleemail.model.SendEmailResult;

public class AwsEmailSender {

	public static void main(String[] args) {
		// TODO Auto-generated constructor stub
		LinkedList<String> recipients = new LinkedList<>();
		recipients.add("ping2ravi@gmail.com");
		sendMail("info@aapuk.org", recipients, "Test Email", "Hi Ravi");
	}
	public static void sendMail(String sender, LinkedList<String> recipients, String subject, String body) {
	    Destination destination = new Destination(recipients);
	    try {

	        Content subjectContent = new Content(subject);
	        //Content bodyContent = new Content(body);
	        //Body msgBody = new Body(bodyContent);
	        
	        // Include a body in both text and HTML formats
	        Content htmlContent = new Content().withData(body);
	        Body msgBody = new Body().withHtml(htmlContent);
	        Message msg = new Message(subjectContent, msgBody);

	        SendEmailRequest request = new SendEmailRequest(sender, destination, msg);

	        AWSCredentials credentials = new BasicAWSCredentials("AKIAJMHWV3JFASLQYCDA", "3rxh5mJYQegMdYTbk3epdk2HGB+KMiU+aekQk0KX");
	        AmazonSimpleEmailServiceAsync sesClient = new AmazonSimpleEmailServiceAsyncClient(credentials);
	        SendEmailResult result = sesClient.sendEmail(request);

	        System.out.println(result + "Email sent");  
	    }catch(Exception e) {
	        System.out.println("Exception from EmailSender.java. Email not send");
	    }
	}

}
