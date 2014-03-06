package com.next.aap.cache;

import java.util.List;
import java.util.Map;

public interface CacheService {

	void saveData(String key,Object value);
	Object getData(String key);
	<T> T getData(String key, Class<T> classType);
	void deleteData(String key);
	Map<String, Object> getDataForAll(List<String> key);
}
