package org.example.cache;

import java.util.Objects;

public class CachingMethodLRU<K, V> extends AbstractCachingMethod<K, V> {
    private int callsNumber = 0;

    public CachingMethodLRU(int capacity) {
        super(capacity);
    }

    @Override
    public void put(K key, V val) {
        if (contains(key)) {
            EntryLRU<K, V> entry = (EntryLRU<K, V>) Objects.requireNonNull(cache.stream()
                    .filter(e -> e.key.equals(key))
                    .findFirst()
                    .orElse(null)
            );
            entry.val = val;
            entry.lastCallNum = callsNumber;
            hitsNumber++;
        } else {
            evictIfFull();
            cache.add(new EntryLRU<>(key, val, callsNumber));
        }
        callsNumber++;
        putsNumber++;
    }

    @Override
    public V get(K key) {
        EntryLRU<K, V> entry = (EntryLRU<K, V>) cache.stream().filter(e -> e.key.equals(key)).findFirst().orElseThrow(NullPointerException::new);
        entry.lastCallNum = callsNumber;
        callsNumber++;
        return entry.val;
    }

    @Override
    protected void evictIfFull() {
        if (isFull()) {
            int minLastCall = ((EntryLRU<K, V>) cache.getFirst()).lastCallNum;
            int iMin = 0;
            for (int i = 0; i < size(); i++){
                EntryLRU<K, V> entryLRU = (EntryLRU<K, V>) cache.get(i);
                if (entryLRU.lastCallNum < minLastCall) {
                    iMin = i;
                    minLastCall = entryLRU.lastCallNum;
                }

            }
            cache.remove(iMin);
        }
    }


    protected static class EntryLRU<K, V> extends Entry<K, V> {
        private int lastCallNum;
        public EntryLRU(K key, V val, int lastCallNum) {
            super(key, val);
            this.lastCallNum = lastCallNum;
        }
    }

}
