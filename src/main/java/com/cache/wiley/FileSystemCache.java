package com.cache.wiley;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class FileSystemCache<K, V> implements Cache<K, V> {

	private Map<K, V> objectsStorage;
	private int capacity;

	FileSystemCache(int capacity) {
		this.capacity = capacity;
		this.objectsStorage = new ConcurrentHashMap<>(capacity);
	}

	@Override
	public void putToCache(K key, V value) {
		objectsStorage.put(key, value);
	}

	@Override
	public void removeFromCache(K key) {
		objectsStorage.remove(key);
	}

	@Override
	public int getCacheSize() {
		return objectsStorage.size();
	}

	@Override
	public boolean isObjectPresent(K key) {
		return objectsStorage.containsKey(key);
	}

	@Override
	public boolean hasEmptyPlace() {
		return getCacheSize() < this.capacity;
	}
	
	@Override
	public V getFromCache(K key) {
		return objectsStorage.get(key);
	}

	@Override
	public void clearCache() {
		objectsStorage.clear();
	}
}
