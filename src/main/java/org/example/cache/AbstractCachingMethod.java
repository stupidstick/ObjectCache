package org.example.cache;

import java.util.*;

public abstract class AbstractCachingMethod<K, V> implements CachingMethod<K, V> {
    protected LinkedList<Entry<K, V>> cache = new LinkedList<>();
    private int capacity;
    protected int putsNumber = 0;
    protected int hitsNumber = 0;

    public AbstractCachingMethod(int capacity) {
        if (capacity <= 0) throw new IllegalArgumentException("capacity must be greater than 0");
        this.capacity = capacity;
    }

    public void put(K key, V val) {
        if (contains(key)) {
            Objects.requireNonNull(cache.stream()
                    .filter(e -> e.key.equals(key))
                    .findFirst()
                    .orElse(null)
            ).val = val;
            hitsNumber++;
        } else {
            evictIfFull();
            cache.add(new Entry<>(key, val));
        }
        putsNumber++;
    }

    public double statistic() {
        return (double) hitsNumber / putsNumber;
    }

    public void setCapacity(int capacity) {
        if (capacity <= 0) throw new IllegalArgumentException("capacity must be greater than 0");
        if (capacity < size()) {
            int diff = size() - capacity;
            for (int i = 0; i < diff; i++)
                evictIfFull();
        }
        this.capacity = capacity;
    }


    public int capacity() {
        return capacity;
    }

    public int size() {
        return cache.size();
    }

    public boolean contains(K key) {
        return cache.stream().anyMatch(e -> e.key.equals(key));
    }

    public V get(K key) {
        return cache.stream().filter(e -> e.key.equals(key)).findFirst().orElseThrow(IllegalArgumentException::new).val;
    }

    public Map<K, V> cache() {
        Map<K, V> res = new LinkedHashMap<>();
        cache.forEach(e -> res.put(e.key, e.val));
        return res;
    }

    public List<K> keys() {
        return cache.stream().map(e -> e.key).toList();
    }

    public List<V> values() {
        return cache.stream().map(e -> e.val).toList();
    }

    protected boolean isFull() {
        return (size() >= capacity);
    }

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
