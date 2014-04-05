package com.next.aap.cache;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;

import net.spy.memcached.AddrUtil;
import net.spy.memcached.BinaryConnectionFactory;
import net.spy.memcached.ConnectionFactory;
import net.spy.memcached.ConnectionFactoryBuilder;
import net.spy.memcached.MemcachedClient;
import net.spy.memcached.internal.OperationFuture;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

import com.next.aap.cache.beans.DonationBean;
import com.next.aap.cache.beans.DonationCampaignInfo;

public class MemcachClient_SpyMemecached implements CacheService{
	@Value("${mysql_memcache_host:localhost}")
	private String hostname;
	@Value("${mysql_memcache_port:11211}")
	private int portNumber;
	@Value("${mysql_memcache_timeout:5000}")
	private int opTimeOut;
	@Value("${mysql_memcache_default_time_to_live:3600}")
	private int defaultTimeToLive = 1898969838;
	
	private MemcachedClient memcachedClient;
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());


	private static void addOneDonation(DonationCampaignInfo donationCampaignInfo, double amount, String cid, String donorName){
		DonationBean donationBean = new DonationBean();
		donationBean.setAmt(amount);
		donationBean.setDid(cid);
		donationBean.setDt(new Date());
		donationBean.setName(donorName);
		donationCampaignInfo.getDns().add(donationBean);
	}
	public static void main(String[] args){
		MemcachClient_SpyMemecached ms = new MemcachClient_SpyMemecached("myaapin.c4rkdapgr3v5.us-east-1.rds.amazonaws.com", 11211,500, 3600);
		ms.saveData("name1", "Ravi");
		System.out.println("Name : "+ ms.getData("name"));
		List<String> allKeys = new ArrayList<>();
		allKeys.add("name");
		allKeys.add("RAVI_TEST");
		
		DonationCampaignInfo donationCampaignInfo = new DonationCampaignInfo();
		donationCampaignInfo.setTamt(100.0);
		donationCampaignInfo.setTtxn(12);
		for(int i=1;i<500;i++){
			addOneDonation(donationCampaignInfo, 50 * i, "DID"+i, "Ravi Sharma Diya"+i);
		}
		
		//ms.saveData("RAVI_TEST", donationCampaignInfo);
		//Map<String, Object> data = ms.getDataForAll(allKeys);
		DonationCampaignInfo data = (DonationCampaignInfo)ms.getData("LOC_LPC233");
		System.out.println("Name : "+ data);
		System.out.println("Size : "+ data.getDns().size());
		
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, 2030);
		System.out.println("Unix Time = " +cal.getTimeInMillis());
	}
	
	public MemcachClient_SpyMemecached(String hostName, int portNumber, int opTimeOut, int defaultTimeToLive) {
		this.hostname = hostName;
		this.portNumber = portNumber;
		this.opTimeOut = opTimeOut;
		this.defaultTimeToLive = defaultTimeToLive;
		createMemcacheClient(hostName, portNumber, opTimeOut, defaultTimeToLive);
	}
	public MemcachClient_SpyMemecached() {
	}
	@PostConstruct
	public void init() {
		createMemcacheClient(hostname, portNumber, opTimeOut, defaultTimeToLive);
	}
	public void createMemcacheClient(String hostName, int portNumber, int opTimeOut, int defaultTimeToLive) {
		try {
			logger.info("Memcache Host Name : "+hostName);
			logger.info("portNumber : "+portNumber);
			logger.info("opTimeOut : "+opTimeOut);
			logger.info("defaultTimeToLive : "+defaultTimeToLive);
			ConnectionFactoryBuilder cfb = new ConnectionFactoryBuilder();
			//cfb.setTranscoder(new CustomSerializingTranscoder()); // use this line
			//cfb.setProtocol(ConnectionFactoryBuilder.Protocol.BINARY);
			cfb.setOpTimeout(opTimeOut);
			cfb.setReadBufferSize(10240000);
			ConnectionFactory cf = cfb.build();
			List<InetSocketAddress> servers = new ArrayList<>();
			servers.add(new InetSocketAddress(hostName, portNumber));
			//memcachedClient = new MemcachedClient(cf, servers);
			memcachedClient = new MemcachedClient(new BinaryConnectionFactory(), servers);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void saveData(String key, Object value) {
		long startTime = System.currentTimeMillis();
		try{
			OperationFuture<Boolean> future = memcachedClient.set(key, defaultTimeToLive, value);
			logger.info("memcache set result " + future.get());
		}catch(Exception ex){
			logger.error("Unable to set key in cache"+key);
		}
		long endTime = System.currentTimeMillis();
		logger.info("Total Time "+(endTime - startTime) +" ms");
		
	}

	@Override
	public Object getData(String key) {
		/*
		long startTime = System.currentTimeMillis();
		GetFuture<Object> f = memcachedClient.asyncGet(key);
		Object returnObject = null;
		try {
			System.out.println("opTimeOut="+opTimeOut);
			returnObject = f.get(opTimeOut, TimeUnit.MILLISECONDS);
			System.out.println("Waiting for Get Future to finish");
			System.out.println("Status = "+ f.get() +", "+f.getStatus());
			// throws expecting InterruptedException, ExecutionException
			// or TimeoutException
		} catch (Exception e) {
			e.printStackTrace();
			f.cancel(true);
		}
		long endTime = System.currentTimeMillis();
		System.out.println("Total Time "+(endTime - startTime) +" ms");
		return returnObject;
		*/
		return memcachedClient.get(key);
	}
	@Override
	public <T> T getData(String key, Class<T> classType) {
		Future<Object> f = memcachedClient.asyncGet(key);
		Object returnObject = null;
		try {
			returnObject = f.get(opTimeOut, TimeUnit.MILLISECONDS);
			// throws expecting InterruptedException, ExecutionException
			// or TimeoutException
		} catch (Exception e) {
			e.printStackTrace();
			f.cancel(true);
		}
		return (T)returnObject;
	}

	@Override
	public void deleteData(String key) {
		memcachedClient.delete(key);
	}

	@Override
	public Map<String, Object> getDataForAll(List<String> keys) {
		Map<String, Object> data = memcachedClient.getBulk(keys);
		return data;
	}
}
