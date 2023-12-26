package org.example.cache;

import java.util.Random;

public class CachingMethodRAND<K, V> extends AbstractCachingMethod<K, V> {
    private final Random random = new Random();
    public CachingMethodRAND(int capacity) {
        super(capacity);
    }

    @Override
    protected void evictIfFull() {
        if (isFull())
            cache.remove(random.nextInt(0, size()));
    }
}