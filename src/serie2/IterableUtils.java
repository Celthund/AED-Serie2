package serie2;
import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.function.BiPredicate;

public class IterableUtils {
    public static <K,U> Iterable<K> filterBy(Iterable<K> src1, Iterable<U> src2, BiPredicate<K,U> predicate) {
        return new Iterable<K>() {
            @Override
            public Iterator<K> iterator() {
                return new Iterator<K>() {
                    K curr = null, res = null;

                    //Stores the iterators
                    Iterator it1 = src1.iterator(), it2 = src2.iterator();
                    @Override
                    public boolean hasNext() {
                        // If the curr variable has a element then it wont enter the cycle and waits for the "next()"
                        //method to use it
                        while (curr == null) {
                            if (it1.hasNext() && it2.hasNext()) {
                                // curr takes the value of the the next available position
                                curr = (K)it1.next();
                                // If the result of the predicate method returns true if the values match, so if it doesnt match
                                //curr will be null so it can check the next value
                                if(!predicate.test(curr,(U)it2.next()))
                                    curr = null;
                            }
                            else
                                // If one of the iterators has no more elements to check then this method will always return false
                                //because there is no more elements to compare
                                return false;
                        }
                        return true;
                    }

                    @Override
                    public K next() {
                        if (!hasNext())
                            throw new NoSuchElementException("No Elements");
                        // res takes the value of the curr variable so it can put the curr null to check the next element
                        //and then return the res
                        res = curr;
                        curr = null;
                        return res;
                    }


                };
            }


        };
    }

    public static <K,V> Iterable<V> filterByMap(Iterable<K> src, Map<K,V> map){
        return new Iterable<V>() {
            @Override
            public Iterator<V> iterator() {
                return new Iterator<V>() {
                    // Runs through the iterator
                    Iterator<K> it = src.iterator();

                    // Stores the current key
                    K currKey = null;

                    // Stores the current element and the res will send the element in the next() method
                    V currValue = null, res = null;

                    @Override
                    public boolean hasNext() {
                        // If current is null it means it has no element so it will try to find the next element in the iterator
                        while(currValue == null){

                            // Checks if the iterator has any more elements in it
                            if(it.hasNext()){

                                // Takes the value of that element so it can check if the map variable contains that key
                                currKey = it.next();
                                if(map.containsKey(currKey)){
                                    // If it has then if takes the value
                                    currValue = map.get(currKey);
                                }
                                else
                                    // If not it will be null so it can check again
                                    currValue = null;
                            }

                            // If the iterator has no more elements then it will always return false
                            else
                                return false;
                        }
                        // Because the current value was still not used so it will return false until the next() method is call
                        return true;
                    }

                    @Override
                    public V next() {
                        if(!hasNext())
                            throw new NoSuchElementException("No elements");

                        // res takes the value of the curr variable so it can put the curr null to check the next element
                        //and then return the res
                        res = currValue;
                        currValue = null;
                        return res;
                    }
                };
            }
        };
    }

}
