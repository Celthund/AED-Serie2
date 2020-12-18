package serie2;

import java.util.Iterator;

import static java.lang.Math.abs;

class HashNode <K, V> {
    K key;
    V value;
    HashNode<K, V> next;

    HashNode(K key, V value){
        this.key = key;
        this.value = value;
    }

    public K getKey() {
        return key;
    }

    public V getValue() {
        return value;
    }

    public void setKey(K key) {
        this.key = key;
    }

    public void setValue(V value) {
        this.value = value;
    }

    public void setNext(HashNode<K, V> next) {
        this.next = next;
    }
}

public class HashMap <K, V> implements Iterable<HashNode<K, V>> {

    HashNode<K, V>[] hashmap;
    int initial_size = 100, size = 0, dim = initial_size;

    HashMap(){
        hashmap = new HashNode[initial_size];
    }

    private int hash(K key){
        return hash(key, dim);
    }

    private int hash(K key, int dim){
        return abs(key.hashCode()) % dim;
    }

    public boolean containsKey(K key) {
        int pos = hash(key);
        HashNode<K, V> node = hashmap[pos];
        while (node != null){
            if (node.key.equals(key))
                break;
            node = node.next;
        }
        return node != null;
    }

    public void put(K key, V val){
        increaseSizeIfNeeded();
        int pos = hash(key);
        if (!containsKey(key)){
            HashNode<K, V> node = new HashNode<>(key, val);
            node.next = hashmap[pos];
            hashmap[pos] = node;
            size++;
        } else {
            HashNode<K, V> node = hashmap[pos];
            while (node != null){
                if (node.key.equals(key)) {
                    node.setValue(val);
                    break;
                }
                node = node.next;
            }
        }
    }

    private void increaseSizeIfNeeded() {
        if ((float)size / dim <= 1.33) return;
        int pos, dim = this.dim + initial_size;
        HashNode<K, V>[] hashmap = new HashNode[dim];

        Iterator<HashNode<K, V>> iter = iterator();
        HashNode<K, V> node;
        while (iter.hasNext()){
            node = iter.next();
            pos = hash(node.key, dim);
            node.next = hashmap[pos];
            hashmap[pos] = node;
        }
        this.hashmap = hashmap;
    }

    public V get(K key){
        int pos = hash(key);
        HashNode<K, V> node = hashmap[pos];
        while (node != null){
            if (node.key.equals(key))
                break;
            node = node.next;
        }
        return node != null ? node.getValue() : null;
    }

    public void remove(K key){
        if (containsKey(key)){
            int pos = hash(key);
            HashNode<K, V> node = hashmap[pos], prev = null;
            while (node != null){
                if (node.key.equals(key)){
                    if (prev == null){
                        hashmap[pos] = node.next;
                    } else {
                        prev.next = node.next;
                    }
                    size--;
                    break;
                }
                prev = node;
                node = node.next;
            }
        }
    }

    public int size(){
        return size;
    }


    @Override
    public Iterator<HashNode<K, V>> iterator() {
        return new Iterator<HashNode<K, V>>() {
            int i = 0;
            HashNode <K, V> node = null, prev = null;

            @Override
            public boolean hasNext() {
                if (node != null) return true;
                for (; i < dim; i++){
                    if (prev != null){
                        node = prev.next;
                        prev = null;
                    } else
                        node = hashmap[i];
                    if (node != null) return true;
                }
                return false;
            }

            @Override
            public HashNode<K, V> next() {
                if (hasNext()) {
                    prev = node;
                    node = null;
                    return prev;
                }
                return null;
            }
        };
    }
}
