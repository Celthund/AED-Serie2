package serie2;

import java.util.Iterator;

import static java.lang.Math.abs;

// LinkedList class
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

    // The array we use has the hashmap
    HashNode<K, V>[] hashmap;

    // initial_size is the initial size of the array
    // size is the size the user
    int initial_size = 100, size = 0, dim = initial_size;

    // Constructor of the HashMap, in case the user doesnt define a size it will default
    //to the initial_size
    HashMap(){
        hashmap = new HashNode[initial_size];
    }

    // Returns the HashCode of the key
    private int hash(K key){
        return hash(key, dim);
    }

    // Returns the HashCode of the key
    private int hash(K key, int dim){
        return abs(key.hashCode()) % dim;
    }

    // Checks if the key variable is in the hashmap
    public boolean containsKey(K key) {
        // Gets the hashcode of the key so it can search on that index of the hashmap
        int pos = hash(key);

        // Node that searches through the list in that index
        HashNode<K, V> node = hashmap[pos];

        // If the null is null it means there is no element with that key
        while (node != null){

            // Searches the list for a node with a equal key
            if (node.key.equals(key))
                break;
            node = node.next;
        }

        // Return true if the node is found
        return node != null;
    }

    // Puts a key on to the hashmap
    public void put(K key, V val){
        // Increases the size of the hashmap to maintain a good load factor
        increaseSizeIfNeeded();

        // Gets the hascode of that key
        int pos = hash(key);

        // If it that key already exists it will replace the value of that key
        if (!containsKey(key)){
            HashNode<K, V> node = new HashNode<>(key, val);
            node.next = hashmap[pos];
            hashmap[pos] = node;
            size++;
        }
        // If not it will add the new key to the hashmap
        else {
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

    // Increases the size of the hashmap to mantain a good Load Factor
    private void increaseSizeIfNeeded() {
        // Checks if the load factor has surpassed the threshold
        if ((float)size / dim <= 1.33) return;

        // If it has it will increase it size
        int pos, dim = this.dim + initial_size;

        // Gets the new hashmap with the increased size
        HashNode<K, V>[] hashmap = new HashNode[dim];

        // Iterator to put all the keys on to the new positions
        Iterator<HashNode<K, V>> iter = iterator();
        HashNode<K, V> node;
        while (iter.hasNext()){
            node = iter.next();
            pos = hash(node.key, dim);
            node.next = hashmap[pos];
            hashmap[pos] = node;
        }

        // Put the new hashmap we created has the hashmap to use
        this.hashmap = hashmap;
    }

    // Return the value on the key (if it exists)
    public V get(K key){

        // Gets the hashcode of the key
        int pos = hash(key);

        // Gets the linkedlist on that position
        HashNode<K, V> node = hashmap[pos];

        // If the null is null it means there is no element with that key
        while (node != null){
            // Searches the list for a node with a equal key
            if (node.key.equals(key))
                break;
            node = node.next;
        }

        // Returns the value of the node (if there is a node in that position) it
        //will return null
        return node != null ? node.getValue() : null;
    }

    // Remove the element in key (if there is one)
    public void remove(K key){

        // If it that key already exists it will replace the value of that key
        if (containsKey(key)){
            // Gets the hashcode of that key
            int pos = hash(key);

            // If it that key already exists it will replace the value of that key
            HashNode<K, V> node = hashmap[pos], prev = null;

            // If the null is null it means there is no element with that key
            while (node != null){
                // Searches the list for a node with a equal key
                if (node.key.equals(key)){
                    // In case is the first node of the list it will just the element next to that node to null
                    if (prev == null){
                        hashmap[pos] = node.next;
                    }
                    // If not it will just connect the previous element to the next element
                    else {
                        prev.next = node.next;
                    }
                    size--;
                    break;
                }

                // Moves to the next node of the list
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
                // Checks if the current node is null, if it isnt it means that it has a valid element
                //so it will keep returning true until it will be used
                if (node != null) return true;

                // Cycle to run the array in search of the next valid element of the hashmap
                for (; i < dim; i++){
                    // Runs through the list in the hashmap
                    if (prev != null){
                        node = prev.next;
                        prev = null;
                    }
                    // If prev is null it means we are at the beginning of a new list
                    else
                        node = hashmap[i];

                    // When the node is not null it means it found a valid element so it will return true
                    if (node != null) return true;
                }

                // When it reaches the end of the cycle it means there are no more valid elements so it will
                //return false
                return false;
            }

            @Override
            public HashNode<K, V> next() {
                if (hasNext()) {
                    // Returns the vale in the node
                    prev = node;
                    node = null;
                    return prev;
                }
                // In case the node being null it doesnt enter in the if so it will return null
                return null;
            }
        };
    }
}
