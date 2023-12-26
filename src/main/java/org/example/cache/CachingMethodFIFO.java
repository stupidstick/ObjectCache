package org.example.cache;

public class CachingMethodFIFO<K, V> extends AbstractCachingMethod<K, V> {

    public CachingMethodFIFO(int capacity) {
        super(capacity);
    }

    @Override
    protected void evictIfFull() {
        if (cache.size() >= capacity()){
            cache.poll();
        }
    }
}
