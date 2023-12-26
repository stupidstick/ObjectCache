package org.example;

public class Main {
    public static void main(String[] args) {
        CachingMethod<String, Integer> cache = new CachingMethodFIFO<>(4);
        cache.put("a", 14);
        cache.put("b", 15);
        cache.put("c", 16);
        cache.put("d", 17);
        cache.put("e", 17);
        cache.put("f", 17);
        cache.put("g", 17);
        cache.setCapacity(2);
        cache.put("b", 2);
        cache.setCapacity(50);
        cache.put("a", 5);
        cache.put("h", 4);
        cache.put("k", 3);
        System.out.println(cache.keys());
    }
}