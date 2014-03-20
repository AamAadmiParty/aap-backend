package com.next.aap.web.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Calendar;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.PutObjectResult;

@Component("awsImageUtil")
public class AwsImageUtil {

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	//
	@PostConstruct
	public void init(){
		System.out.println("Created AwsImageUtil");
	}
	
	@Value("${dc_base_directory}")
	private String donationCertifcateBaseDirectory;

	@Value("${aws_access_key}") 
	private String accessKey;
	
	@Value("${aws_access_secret}") 
	private String accessSecret;

	@Value("${s3_bucket_name_for_dc}") 
	private String s3BucketnameForDonationCertificates;

	@Value("${s3_bucket_name_for_candidate_profile_pic}") 
	private String s3BucketnameForCandidateProfilePic;

	@Value("${s3_base_dir_for_dc}") 
	private String s3BaseDirectoryForDonationCertificates;

	@Value("${s3_base_dir_for_cabdidate_profile_pic}") 
	private String s3BaseDirectoryForCandidateProfilePic;

	@Value("${s3_base_http_path_dc}") 
	private String s3BaseHttpForDonation;
	
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
	
	private void uploadFileToS3(String awsKey, String awsSecret, String bucketName, String remoteFileNameAndPath, InputStream fileToUpload) throws FileNotFoundException {

		AmazonS3 s3client = new AmazonS3Client(new BasicAWSCredentials(awsKey, awsSecret));

		logger.info("Uploading a new object to S3 from input Stream to remote file "+bucketName+"/"+remoteFileNameAndPath);
		ObjectMetadata objectMetadata = new ObjectMetadata();
		objectMetadata.setCacheControl("max-age=2592000");
		objectMetadata.addUserMetadata("x-amz-storage-class", "RRS");
		PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, remoteFileNameAndPath, fileToUpload, objectMetadata);
		putObjectRequest.setCannedAcl(CannedAccessControlList.PublicRead);
		s3client.putObject(putObjectRequest);
		logger.info("File Uploaded");
	}

	public String uploadCandidateProfileImage(String remoteFileName, InputStream localFilePathToUpload) throws FileNotFoundException {
		String remoteFileNameAndPath = s3BaseDirectoryForCandidateProfilePic+"/"+remoteFileName;
		uploadFileToS3(accessKey, accessSecret, s3BucketnameForCandidateProfilePic, remoteFileNameAndPath, localFilePathToUpload);
		String httpPath = s3BaseHttpForDonation +"/"+ s3BucketnameForCandidateProfilePic + "/"+s3BaseDirectoryForCandidateProfilePic+"/"+remoteFileName;
		return httpPath;
	}
}
