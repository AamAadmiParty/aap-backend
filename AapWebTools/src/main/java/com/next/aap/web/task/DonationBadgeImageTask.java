package com.next.aap.web.task;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.concurrent.Callable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ListObjectsRequest;
import com.amazonaws.services.s3.model.ObjectListing;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.PutObjectResult;
import com.amazonaws.services.s3.model.S3ObjectSummary;
import com.next.aap.web.controller.DonationTemplateEnum;
import com.next.aap.web.dto.DonationDto;
import com.next.aap.web.util.BadgeImageGenerator;

@Component
public class DonationBadgeImageTask implements Callable<Boolean> {

	private static Logger logger = LoggerFactory.getLogger(DonationBadgeImageTask.class);
	private DonationDto donationDto;
	private String filePath;
	private String id;
	private DonationTemplateEnum donationTemplateEnum;
	String accessKey;
	String accessSecret;
	String bucketName;
	String remoteFileNameAndPath;

	public static void main(String[] args) throws IOException {
		try {
			AmazonS3 s3client = new AmazonS3Client(new BasicAWSCredentials("AKIAJMHWV3JFASLQYCDA", "3rxh5mJYQegMdYTbk3epdk2HGB+KMiU+aekQk0KX"));
			ListObjectsRequest listObjectsRequest = new ListObjectsRequest().withBucketName("myaap");
			ObjectListing objectListing;

			do {
				objectListing = s3client.listObjects(listObjectsRequest);
				for (S3ObjectSummary objectSummary : objectListing.getObjectSummaries()) {
					System.out.println(" - " + objectSummary.getKey() + "  " + "(size = " + objectSummary.getSize() + ")");
				}
				listObjectsRequest.setMarker(objectListing.getNextMarker());
			} while (objectListing.isTruncated());

			System.out.println("Uploading a new object to S3 from a file\n");
			FileInputStream file = new FileInputStream("/Users/ravi/Desktop/saved00.png");
			ObjectMetadata objectMetadata = new ObjectMetadata();
			objectMetadata.setCacheControl("max-age=2592000");
			objectMetadata.addUserMetadata("x-amz-storage-class", "RRS");
			PutObjectResult putObjectResult = s3client.putObject(new PutObjectRequest("myaap", "test/saved00.png", file, objectMetadata));
			System.out.println("getETag : " + putObjectResult.getETag());
			System.out.println("getContentMd5 : " + putObjectResult.getContentMd5());
			System.out.println("getExpirationTimeRuleId : " + putObjectResult.getExpirationTimeRuleId());
			System.out.println("getServerSideEncryption : " + putObjectResult.getServerSideEncryption());
			System.out.println("getVersionId : " + putObjectResult.getVersionId());
			System.out.println("getExpirationTime : " + putObjectResult.getExpirationTime());
			System.out.println("File Uploaded");
		} catch (AmazonServiceException ase) {
			System.out.println("Caught an AmazonServiceException, which " + "means your request made it "
					+ "to Amazon S3, but was rejected with an error response" + " for some reason.");
			System.out.println("Error Message:    " + ase.getMessage());
			System.out.println("HTTP Status Code: " + ase.getStatusCode());
			System.out.println("AWS Error Code:   " + ase.getErrorCode());
			System.out.println("Error Type:       " + ase.getErrorType());
			System.out.println("Request ID:       " + ase.getRequestId());
		} catch (AmazonClientException ace) {
			System.out.println("Caught an AmazonClientException, which " + "means the client encountered " + "an internal error while trying to "
					+ "communicate with S3, " + "such as not being able to access the network.");
			System.out.println("Error Message: " + ace.getMessage());
		}
	}

	public DonationBadgeImageTask() {
	}

	public DonationBadgeImageTask(DonationDto donationDto, String filePath, DonationTemplateEnum donationTemplateEnum, String accessKey, 
			String accessSecret,String bucketName, String remoteFileNameAndPath) {
		this.donationDto = donationDto;
		this.filePath = filePath;
		this.donationTemplateEnum = donationTemplateEnum;
		this.accessKey = accessKey;
		this.accessSecret = accessSecret;
		this.bucketName = bucketName;
		this.remoteFileNameAndPath = remoteFileNameAndPath;
	}

	public static void mainw(String[] args) throws Exception {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MMM-yyyy HH:mm");
		System.out.println(simpleDateFormat.format(Calendar.getInstance().getTime()));
	}

	@Override
	public Boolean call() throws Exception {
		logger.info("DonationSummaryTask:Generating Image for " + id);
		if (donationDto == null) {
			return false;
		}
		FileOutputStream fileOutputStream;
		try {

			fileOutputStream = new FileOutputStream(filePath);
			logger.info("filePath {}", filePath);
			String amount = String.format("%8.2f", donationDto.getAmount());
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MMM-yyyy HH:mm");
			String donationDate = simpleDateFormat.format(donationDto.getDonationDate());
			switch (donationTemplateEnum) {
			case template01:
				BadgeImageGenerator.createImageTemplate01(fileOutputStream, amount, donationDto.getDonorName(), donationDate, donationDto.getTransactionId());
				break;
			case template02:
				BadgeImageGenerator.createImageTemplate02(fileOutputStream, amount, donationDto.getDonorName(), donationDate, donationDto.getTransactionId());
				break;
			case template03:
				BadgeImageGenerator.createImageTemplate03(fileOutputStream, amount, donationDto.getDonorName(), donationDate, donationDto.getTransactionId());
				break;
			case template04:
				BadgeImageGenerator.createImageTemplate04(fileOutputStream, amount, donationDto.getDonorName(), donationDate, donationDto.getTransactionId());
				break;
			case template05:
				BadgeImageGenerator.createImageTemplate05(fileOutputStream, amount, donationDto.getDonorName(), donationDate, donationDto.getTransactionId());
				break;
			case template06:
				BadgeImageGenerator.createImageTemplate06(fileOutputStream, amount, donationDto.getDonorName(), donationDate, donationDto.getTransactionId());
				break;
			case template07:
				BadgeImageGenerator.createImageTemplate07(fileOutputStream, amount, donationDto.getDonorName(), donationDate, donationDto.getTransactionId());
				break;
            case template08:
                BadgeImageGenerator.createImageTemplate08(fileOutputStream, amount, donationDto.getDonorName(), donationDate, donationDto.getTransactionId());
                break;
			default:
				BadgeImageGenerator.createImageTemplate03(fileOutputStream, amount, donationDto.getDonorName(), donationDate, donationDto.getTransactionId());
				break;
			}
			fileOutputStream.close();
			uploadFileToS3(accessKey, accessSecret, bucketName, remoteFileNameAndPath, filePath);
			//delete file now
			File file = new File(filePath);
			file.delete();
			/*
			 * AmazonS3 s3client = new AmazonS3Client(new
			 * BasicAWSCredentials(accessKey, accessSecret)); try {
			 * System.out.println("Uploading a new object to S3 from a file\n");
			 * File file = new File(filePath); s3client.putObject(new
			 * PutObjectRequest("myaap", "", file));
			 */
		} catch (FileNotFoundException e) {
			logger.error("File Not found", e);
		} catch (IOException e) {
			logger.error("Error ", e);
		} catch (Exception e) {
			logger.error("Some Error ", e);
		}
		return true;
	}

	/**
	 * 
	 * @param awsKey
	 * @param awsSecret
	 * @param bucketName
	 * @param remoteFileNameAndPath its a complete oath of file like test/dc/abc.png, note there is no leading /
	 * @param localFilePathToUpload
	 * @throws FileNotFoundException
	 */
	private void uploadFileToS3(String awsKey, String awsSecret, String bucketName, String remoteFileNameAndPath, String localFilePathToUpload) throws FileNotFoundException {

		AmazonS3 s3client = new AmazonS3Client(new BasicAWSCredentials(awsKey, awsSecret));

		logger.info("Uploading a new object to S3 from "+localFilePathToUpload+" to remote file "+bucketName+"/"+remoteFileNameAndPath);
		FileInputStream file = new FileInputStream(localFilePathToUpload);
		ObjectMetadata objectMetadata = new ObjectMetadata();
		objectMetadata.setCacheControl("max-age=2592000");
		objectMetadata.addUserMetadata("x-amz-storage-class", "RRS");
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DAY_OF_MONTH, 2);
		objectMetadata.setExpirationTime(calendar.getTime());
		PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, remoteFileNameAndPath, file, objectMetadata);
		putObjectRequest.setCannedAcl(CannedAccessControlList.PublicRead);
		PutObjectResult putObjectResult = s3client.putObject(putObjectRequest);
		logger.info("File Uploaded");
	}

}
