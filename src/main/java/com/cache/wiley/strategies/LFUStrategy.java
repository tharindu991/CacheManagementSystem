package com.cache.wiley.strategies;

import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import com.cache.wiley.CacheStrategy;

public class LFUStrategy<T> extends CacheStrategy<T> {
	
	private int cacheSize;

	private ConcurrentHashMap<T, Integer> objectStore;
	
	public LFUStrategy(int cacheSize) {
		if (cacheSize <= 0)
			throw new IllegalArgumentException();
		
		this.cacheSize = cacheSize;
		
		objectStore = new ConcurrentHashMap<T, Integer>(cacheSize);
	}
	
	public void putObject(T object) {
		if (Objects.isNull(object))
			throw new IllegalArgumentException();

		objectStore.computeIfPresent(object, (k, v) -> v + 1);
		
		if (objectStore.size() == cacheSize) {
			objectStore.entrySet().parallelStream().min(Map.Entry.comparingByValue()).ifPresent(x -> {
				if (!object.equals(x.getKey()) && !objectStore.containsKey(object)) 
					objectStore.remove(x.getKey());
			});
		}

		objectStore.putIfAbsent(object, 1);
	}
	
	public Set<T> viewAll() {
		return objectStore.keySet();
	}

	public boolean isObjectPresent(T key) {
        return objectStore.containsKey(key);
    }


}
