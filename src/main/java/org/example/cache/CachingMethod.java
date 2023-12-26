package org.example.cache;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface CachingMethod<K, V> {
    void put(K key, V val);
    double statistic();
    void setCapacity(int capacity);
    int capacity();
    int size();
    boolean contains(K key);
    V get(K key);
    Map<K, V> cache();
    List<K> keys();
    List<V> values();
}
