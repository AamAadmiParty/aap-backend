package com.next.aap.cache;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Value;

import net.spy.memcached.ConnectionFactory;
import net.spy.memcached.ConnectionFactoryBuilder;
import net.spy.memcached.MemcachedClient;
import net.spy.memcached.internal.OperationFuture;


public class MemcachClient_SpyMemecached implements CacheService{
	@Value("${mysql_memcache_host:localhost}")
	private String hostname;
	@Value("${mysql_memcache_post:11211}")
	private int portNumber;
	@Value("${mysql_memcache_timeout:5000}")
	private int opTimeOut;
	@Value("${mysql_memcache_default_time_to_live:3600}")
	private int defaultTimeToLive;
	private MemcachedClient memcachedClient;


	public MemcachClient_SpyMemecached() {
		try {
			ConnectionFactoryBuilder cfb = new ConnectionFactoryBuilder();
			cfb.setOpTimeout(opTimeOut);
			ConnectionFactory cf = cfb.build();
			List<InetSocketAddress> servers = new ArrayList<>();
			servers.add(new InetSocketAddress(hostname, portNumber));
			memcachedClient = new MemcachedClient(cf, servers);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void saveData(String key, Object value) {
		// System.out.println("Writing "+key+", value="+value);
		OperationFuture<Boolean> future = memcachedClient.set(key, defaultTimeToLive, value);
	}

	@Override
	public Object getData(String key) {
		// System.out.println("Reading "+key);
		// return memcachedClient.get(key);
		Future<Object> f = memcachedClient.asyncGet(key);
		Object returnObject = null;
		try {
			returnObject = f.get(opTimeOut, TimeUnit.MILLISECONDS);
			// throws expecting InterruptedException, ExecutionException
			// or TimeoutException
		} catch (Exception e) {
			f.cancel(true);
		}
		return returnObject;
	}
	@Override
	public <T> T getData(String key, Class<T> classType) {
		// System.out.println("Reading "+key);
		// return memcachedClient.get(key);
		Future<Object> f = memcachedClient.asyncGet(key);
		Object returnObject = null;
		try {
			returnObject = f.get(opTimeOut, TimeUnit.MILLISECONDS);
			// throws expecting InterruptedException, ExecutionException
			// or TimeoutException
		} catch (Exception e) {
			f.cancel(true);
		}
		return (T)returnObject;
	}

	@Override
	public void deleteData(String key) {
		// System.out.println("Deleting "+key);
		memcachedClient.delete(key);
	}

	@Override
	public Object getDataForAll(List<String> keys) {
		Map<String, Object> data = memcachedClient.getBulk(keys);
		return data;
	}
}
