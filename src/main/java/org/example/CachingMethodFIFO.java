package org.example;

import java.util.*;

public class CachingMethodFIFO<K, V> extends AbstractCachingMethod<K, V> {
    private final Queue<Entry<K, V>> cache = new LinkedList<>();

    public CachingMethodFIFO(int capacity) {
        super(capacity);
    }

    @Override
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
        callsNumber++;
    }

    @Override
    public void setCapacity(int capacity) {
        if (capacity <= 0) throw new IllegalArgumentException("capacity must be greater than 0");
        if (capacity < size()) {
            int diff = size() - capacity;
            for (int i = 0; i < diff; i++)
                cache.poll();
        }
        super.setCapacity(capacity);
    }

    @Override
    public int size() {
        return cache.size();
    }

    @Override
    public boolean contains(K key) {
        return cache.stream().anyMatch(e -> e.key.equals(key));
    }

    @Override
    public V get(K key) {
        return cache.stream().filter(e -> e.key.equals(key)).findFirst().orElseThrow(IllegalArgumentException::new).val;
    }

    @Override
    public Map<K, V> cache() {
        Map<K, V> res = new HashMap<>();
        cache.forEach(e -> res.put(e.key, e.val));
        return res;
    }

    @Override
    public List<K> keys() {
        return cache.stream().map(e -> e.key).toList();
    }

    @Override
    public List<V> values() {
        return cache.stream().map(e -> e.val).toList();
    }

    @Override
    protected void evictIfFull() {
        if (cache.size() == capacity())
            cache.poll();
    }
}
