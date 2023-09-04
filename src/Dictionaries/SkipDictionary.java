package Dictionaries;

import DataStructures.SkipList;

import java.util.Comparator;

public class SkipDictionary<K, V> implements IDictionary<K, V> {

    private static class Pair<K, V> {
        private final K key;
        private final V value;

        public Pair(K key, V value) {
            this.key = key;
            this.value = value;
        }

        public K getKey() {
            return key;
        }

        public V getValue() {
            return value;
        }
    }

    private final SkipList<Pair<K, V>> skipList;

    public SkipDictionary(double p, Comparator<K> keyComparator) {
        this.skipList = new SkipList<>(p, Comparator.comparing(Pair::getKey, keyComparator));
    }

    @Override
    public V insert(K key, V value) {
        Pair<K, V> pair = new Pair<>(key, value);
        Pair<K, V> existingPair = skipList.search(pair);
        if (existingPair != null)
            skipList.remove(existingPair);
        skipList.insert(pair);
        return existingPair != null ? existingPair.getValue() : null;
    }

    @Override
    public V get(K key) {
        Pair<K, V> searchKey = new Pair<>(key, null);
        Pair<K, V> pair = skipList.search(searchKey);
        return pair != null ? pair.getValue() : null;
    }

    @Override
    public V remove(K key) {
        Pair<K, V> searchKey = new Pair<>(key, null);
        Pair<K, V> pair = skipList.search(searchKey);
        if (pair != null) {
            skipList.remove(pair);
            return pair.getValue();
        }
        return null;
    }
    @Override
    public void clear() {
        skipList.clear();
    }
}