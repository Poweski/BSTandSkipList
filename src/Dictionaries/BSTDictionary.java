package Dictionaries;

import DataStructures.BST.BST;

import java.util.Comparator;

public class BSTDictionary<K, V> implements IDictionary<K, V> {

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

    private final BST<Pair<K, V> > bst;

    public BSTDictionary(Comparator<K> keyComparator) {
        this.bst = new BST<>(Comparator.comparing(Pair::getKey, keyComparator));
    }

    @Override
    public V insert(K key, V value) {
        Pair<K, V> pair = new Pair<>(key, value);
        Pair<K, V> existingPair = bst.find(pair);
        if (existingPair != null)
            bst.delete(existingPair);
        bst.insert(pair);
        return existingPair != null ? existingPair.getValue() : null;
    }

    @Override
    public V get(K key) {
        Pair<K, V> searchKey = new Pair<>(key, null);
        Pair<K, V> pair = bst.find(searchKey);
        return pair != null ? pair.getValue() : null;
    }

    @Override
    public V remove(K key) {
        Pair<K, V> searchKey = new Pair<>(key, null);
        Pair<K, V> pair = bst.find(searchKey);
        if (pair != null) {
            bst.delete(pair);
            return pair.getValue();
        }
        return null;
    }

    @Override
    public void clear() {
        bst.clear();
    }
}
