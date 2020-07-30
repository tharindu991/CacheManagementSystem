package com.cache.wiley;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import com.cache.wiley.strategies.LRUStrategy;

public class LRUStrategyTest {
	
	@Test
	public void testLeastRecentlyUsedRemove() {
		LRUStrategy<PlainObject> lru = new LRUStrategy<>(3);
		
		lru.putObject(new PlainObject(4));
		lru.putObject(new PlainObject(1));
		lru.putObject(new PlainObject(4));
		lru.putObject(new PlainObject(0));
		lru.putObject(new PlainObject(8));
		
		assertFalse(lru.viewAll().contains(new PlainObject(1)), "Least recently object not has been removed");			
	}
	
	@Test
	public void testMostRecentlyUsedSave() {
		LRUStrategy<PlainObject> lru = new LRUStrategy<>(3);
		
		lru.putObject(new PlainObject(4));
		lru.putObject(new PlainObject(1));
		lru.putObject(new PlainObject(4));
		lru.putObject(new PlainObject(0));
		lru.putObject(new PlainObject(8));
		lru.putObject(new PlainObject(1));
		
		assertTrue(lru.viewAll().contains(new PlainObject(0)), "Recently used object (0) has been removed");	
		assertTrue(lru.viewAll().contains(new PlainObject(8)), "Recently used object (8) has been removed");			
	}

}
