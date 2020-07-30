package com.cache.wiley;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import com.cache.wiley.strategies.LFUStrategy;

public class LFUStrategyTest {
	
	@Test
	public void testLeastFrequentlyRemove() {
		LFUStrategy<PlainObject> lfu = new LFUStrategy<>(3);
		
		lfu.putObject(new PlainObject(0));
		lfu.putObject(new PlainObject(1));
		lfu.putObject(new PlainObject(31));		
		lfu.putObject(new PlainObject(0));
		lfu.putObject(new PlainObject(1));
		lfu.putObject(new PlainObject(12));		
				
		assertFalse(lfu.viewAll().contains(new PlainObject(31)), "Least frequent object has not been removed");		
	}
	
	@Test
	public void testMostFrequentlySave() {
		LFUStrategy<PlainObject> lfu = new LFUStrategy<>(3);
		
		lfu.putObject(new PlainObject(0));
		lfu.putObject(new PlainObject(0));
		lfu.putObject(new PlainObject(31));
		lfu.putObject(new PlainObject(0));
		lfu.putObject(new PlainObject(0));
		lfu.putObject(new PlainObject(4));
		lfu.putObject(new PlainObject(31));		
		lfu.putObject(new PlainObject(0));		
		lfu.putObject(new PlainObject(31));
		lfu.putObject(new PlainObject(17));	
		
		assertTrue(lfu.viewAll().contains(new PlainObject(0)), "Frequently used object (0) has been removed");	
		assertTrue(lfu.viewAll().contains(new PlainObject(31)), "Frequently used object (23) has been removed");	
	}
	
}
