package com.cache.wiley;

import com.cache.wiley.strategies.LFUStrategy;
import com.cache.wiley.strategies.LRUStrategy;
import com.cache.wiley.strategies.StrategyType;

public class TwoLevelCache<K, V> implements Cache<K, V> {

	private final MemoryCache<K, V> firstLevelCache;
	private final FileSystemCache<K, V> secondLevelCache;
	private final CacheStrategy<K> strategy;

	public TwoLevelCache(final int memoryCapacity, final int fileCapacity, final StrategyType strategyType) {
		this.firstLevelCache = new MemoryCache<>(memoryCapacity);
		this.secondLevelCache = new FileSystemCache<>(fileCapacity);
		this.strategy = getStrategy(strategyType);
	}

	private CacheStrategy<K> getStrategy(StrategyType strategyType) {
		switch (strategyType) {
		case LRU:
			return new LRUStrategy<>(3);
		case LFU:
		default:
			return new LFUStrategy<>(3);
		}
	}

	@Override
	public void putToCache(K newKey, V newValue) {

		if (firstLevelCache.isObjectPresent(newKey) || firstLevelCache.hasEmptyPlace()) {
			firstLevelCache.putToCache(newKey, newValue);
			if (secondLevelCache.isObjectPresent(newKey)) {
				secondLevelCache.removeFromCache(newKey);
			}
		} else if (secondLevelCache.isObjectPresent(newKey) || secondLevelCache.hasEmptyPlace()) {
			secondLevelCache.putToCache(newKey, newValue);
		}

		if (!strategy.isObjectPresent(newKey)) {
			strategy.putObject(newKey);
		}

	}

	@Override
	public synchronized V getFromCache(K key) {
		if (firstLevelCache.isObjectPresent(key)) {
			strategy.putObject(key);
			return firstLevelCache.getFromCache(key);
		} else if (secondLevelCache.isObjectPresent(key)) {
			strategy.putObject(key);
			return secondLevelCache.getFromCache(key);
		}
		return null;
	}

	@Override
	public synchronized void removeFromCache(K key) {
		if (firstLevelCache.isObjectPresent(key)) {
			firstLevelCache.removeFromCache(key);
		}
		if (secondLevelCache.isObjectPresent(key)) {
			secondLevelCache.removeFromCache(key);
		}
	}

	@Override
	public int getCacheSize() {
		return firstLevelCache.getCacheSize() + secondLevelCache.getCacheSize();
	}

	@Override
	public boolean isObjectPresent(K key) {
		return firstLevelCache.isObjectPresent(key) || secondLevelCache.isObjectPresent(key);
	}

	@Override
	public void clearCache() {
		firstLevelCache.clearCache();
		secondLevelCache.clearCache();
	}

	@Override
	public synchronized boolean hasEmptyPlace() {
		return firstLevelCache.hasEmptyPlace() || secondLevelCache.hasEmptyPlace();
	}

}
