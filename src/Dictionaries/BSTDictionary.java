package Dictionaries;

import DataStructures.BST.BST;

import java.util.Comparator;

public class BSTDictionary<K, V> implements IDictionary<K, V> {

    private record Pair<K, V>(K key, V value) {
    }

    private final BST<Pair<K, V> > bst;

    public BSTDictionary(Comparator<K> keyComparator) {
        this.bst = new BST<>(Comparator.comparing(Pair::key, keyComparator));
    }

    @Override
    public V insert(K key, V value) {
        Pair<K, V> pair = new Pair<>(key, value);
        Pair<K, V> existingPair = bst.find(pair);
        if (existingPair != null) {
            bst.delete(existingPair);
        }
        bst.insert(pair);
        return existingPair != null ? existingPair.value() : null;
    }

    @Override
    public V get(K key) {
        Pair<K, V> searchKey = new Pair<>(key, null);
        Pair<K, V> pair = bst.find(searchKey);
        return pair != null ? pair.value() : null;
    }

    @Override
    public V remove(K key) {
        Pair<K, V> searchKey = new Pair<>(key, null);
        Pair<K, V> pair = bst.find(searchKey);
        if (pair != null) {
            bst.delete(pair);
            return pair.value();
        }
        return null;
    }

    @Override
    public void clear() {
        bst.clear();
    }
}
