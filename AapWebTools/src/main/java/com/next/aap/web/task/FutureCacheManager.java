package com.next.aap.web.task;

import java.util.concurrent.Future;

import org.apache.commons.collections.map.LRUMap;
import org.springframework.stereotype.Component;

@Component
public class FutureCacheManager {

	private LRUMap lruMap = new LRUMap(500);

	public void setImageKeyFuture(String imageKey,Future<Boolean> future){
		lruMap.put(imageKey, future);
	}
	public Future<Boolean> getImageKeyFuture(String imageKey){
		return (Future<Boolean>)lruMap.get(imageKey);
	}
}
