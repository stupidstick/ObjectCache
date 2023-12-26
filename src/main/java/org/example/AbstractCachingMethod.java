package org.example;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class AbstractCachingMethod<K, V> implements CachingMethod<K, V> {
    private int capacity;
    protected int callsNumber = 0;
    protected int hitsNumber = 0;

    public AbstractCachingMethod(int capacity) {
        if (capacity <= 0) throw new IllegalArgumentException("capacity must be greater than 0");
        this.capacity = capacity;
    }

    @Override
    abstract public void put(K key, V val);

    @Override
    public double statistic() {
        return (double) hitsNumber / callsNumber;
    }

    @Override
    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    @Override
    public int capacity() {
        return capacity;
    }

    @Override
    abstract public int size();

    @Override
    abstract public Map<K, V> cache();

    @Override
    abstract public List<K> keys();

    @Override
    abstract public List<V> values();

    @Override
    abstract public boolean contains(K key);

    @Override
    abstract public V get(K key);

    abstract protected void evictIfFull();

    protected static class Entry<K, V> {
        protected K key;
        protected V val;

        public Entry(K key, V val) {
            this.key = key;
            this.val = val;
        }
    }

}
