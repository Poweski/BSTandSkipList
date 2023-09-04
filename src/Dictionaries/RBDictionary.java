package Dictionaries;

import Dictionaries.IDictionary;

import java.util.TreeMap;

public class RBDictionary<K, V> implements IDictionary<K, V> {
    private final TreeMap<K, V> treeMap;

    public RBDictionary() {
        treeMap = new TreeMap<>();
    }

    @Override
    public V insert(K key, V value) {
        return treeMap.put(key, value);
    }

    @Override
    public V get(K key) {
        return treeMap.get(key);
    }

    @Override
    public V remove(K key) {
        return treeMap.remove(key);
    }

    @Override
    public void clear() {
        treeMap.clear();
    }
}
