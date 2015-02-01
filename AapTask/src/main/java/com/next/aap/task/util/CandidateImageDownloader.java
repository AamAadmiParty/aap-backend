package com.next.aap.task.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import au.com.bytecode.opencsv.CSVReader;

public class CandidateImageDownloader {


    public static void main(String[] args) throws Exception {
        CSVReader csvReader = new CSVReader(new FileReader(new File("/Users/ravi/Desktop/candidates.csv")));
        List<String[]> lines = csvReader.readAll();
        String[] oneLine;
        HttpClient httpClient = new DefaultHttpClient();
        for (int i = 1; i < lines.size(); i++) {
            oneLine = lines.get(i);
            String fileNameToDownload = oneLine[0].toLowerCase() + "_" + oneLine[1].toLowerCase();
            String url = oneLine[3];
            if (url == null || url.equals("NULL")) {
                continue;
            }
            String filePath = "/Users/ravi/Desktop/cand/" + fileNameToDownload + url.substring(url.length() - 4, url.length());
            // System.out.println("Hitting Url = " + url);
            // System.out.println("and saving to  = " + filePath);
            saveHttpImage(httpClient, url, filePath);
        }
        csvReader.close();
    }

    public static void saveHttpImage(HttpClient httpClient, String url, String filePath) throws ClientProtocolException, IOException {
        System.out.println("Hitting Url = " + url);
        System.out.println("and saving to  = " + filePath);
        HttpGet httpGet = new HttpGet(url);
        HttpResponse httpResponse = httpClient.execute(httpGet);
        // System.out.println("Got Response= "+ httpResponse);
        HttpEntity httpEntity = httpResponse.getEntity();
        // System.out.println("Converting to String= "+ httpEntity);
        FileOutputStream fileOutputStream = new FileOutputStream(new File(filePath));
        // System.out.println("IOUtils.copy(httpEntity.getContent(), byteArrayOutputStream);");
        IOUtils.copy(httpEntity.getContent(), fileOutputStream);
        fileOutputStream.close();
    }
}
