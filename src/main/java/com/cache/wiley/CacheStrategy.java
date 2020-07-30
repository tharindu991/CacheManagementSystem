package com.cache.wiley;
import java.util.concurrent.ConcurrentHashMap;

public abstract class CacheStrategy<K> {
    private final ConcurrentHashMap<K, Long> objectsStorage;

    public CacheStrategy() {
        objectsStorage = new ConcurrentHashMap<>();
    }

    public abstract void putObject(K key);


    public boolean isObjectPresent(K key) {
        return objectsStorage.containsKey(key);
    }

}
