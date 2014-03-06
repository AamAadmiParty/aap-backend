package com.next.aap.cache;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;

import net.spy.memcached.ConnectionFactory;
import net.spy.memcached.ConnectionFactoryBuilder;
import net.spy.memcached.MemcachedClient;
import net.spy.memcached.internal.GetFuture;
import net.spy.memcached.internal.OperationFuture;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

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


	public static void main(String[] args){
		MemcachClient_SpyMemecached ms = new MemcachClient_SpyMemecached("myaapin.c4rkdapgr3v5.us-east-1.rds.amazonaws.com", 11211,500, 3600);
		ms.saveData("name1", "Ravi");
		System.out.println("Name : "+ ms.getData("name"));
		List<String> allKeys = new ArrayList<>();
		allKeys.add("name");
		allKeys.add("LOC_LSTATE6");
		
		Map<String, Object> data = ms.getDataForAll(allKeys);
		System.out.println("Name : "+ data);
		
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
			cfb.setOpTimeout(opTimeOut);
			ConnectionFactory cf = cfb.build();
			List<InetSocketAddress> servers = new ArrayList<>();
			servers.add(new InetSocketAddress(hostName, portNumber));
			memcachedClient = new MemcachedClient(cf, servers);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void saveData(String key, Object value) {
		long startTime = System.currentTimeMillis();
		try{
			System.out.println("defaultTimeToLive="+defaultTimeToLive);
			OperationFuture<Boolean> future = memcachedClient.set(key, defaultTimeToLive, value);
			System.out.println("Waiting for Future to finish");
			System.out.println("Status = "+ future.get() +", "+future.getStatus());
		}catch(Exception ex){
			ex.printStackTrace();
		}
		long endTime = System.currentTimeMillis();
		System.out.println("Total Time "+(endTime - startTime) +" ms");
		
	}

	@Override
	public Object getData(String key) {
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
	}
	@Override
	public <T> T getData(String key, Class<T> classType) {
		Future<Object> f = memcachedClient.asyncGet(key);
		Object returnObject = null;
		try {
			System.out.println("Waiting for Get Future to finish");
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
