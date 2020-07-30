package com.cache.wiley;

import com.cache.wiley.strategies.StrategyType;

public class Main {
	
	private static final int VALUE1 = 0;
	private static final int VALUE2 = 1;
	private static final int VALUE3 = 31;
	private static final int VALUE4 = 0;
	private static final int VALUE5 = 1;
	private static final int VALUE6 = 12;
	private static final int VALUE7 = 20;


	private static TwoLevelCache<Integer, Integer> twoLevelCache;

	public static void main(String[] args) {
		
		System.out.println("Started");
		
		twoLevelCache = new TwoLevelCache<Integer, Integer>(3, 3, StrategyType.LFU);
		twoLevelCache.putToCache(0, VALUE1);
		twoLevelCache.putToCache(1, VALUE2);
		twoLevelCache.putToCache(3, VALUE3);
		twoLevelCache.putToCache(4, VALUE4);
		twoLevelCache.putToCache(5, VALUE5);
		twoLevelCache.putToCache(6, VALUE6);
		twoLevelCache.putToCache(7, VALUE7);

		System.out.println("Two Level Cache Size : "+ twoLevelCache.getCacheSize());

	}

}
