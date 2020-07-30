package com.cache.wiley.strategies;

import com.cache.wiley.CacheStrategy;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedDeque;

public class LRUStrategy<T> extends CacheStrategy<T> {
	
	private int cacheSize;
	
	private ConcurrentLinkedDeque<T> objectAgeStore;	
	private ConcurrentHashMap<T, Integer> objectStore;
	
	public LRUStrategy(int cacheSize) {
		if (cacheSize <= 0)
			throw new IllegalArgumentException();
		
		this.cacheSize = cacheSize;
		
		objectStore = new ConcurrentHashMap<T, Integer>(cacheSize);
		objectAgeStore = new ConcurrentLinkedDeque<T>();
	}
	
	public void putObject(T object) {	
		if (Objects.isNull(object))
			throw new IllegalArgumentException();
		
		if (!objectStore.containsKey(object) && objectAgeStore.size() == cacheSize)
			objectStore.remove(objectAgeStore.removeLast());			
		else
			objectAgeStore.remove(object);
		
		objectAgeStore.push(object);
		objectStore.putIfAbsent(object, 0);
	}	
	
	public Set<T> viewAll() {
		return objectStore.keySet();
	}

}
