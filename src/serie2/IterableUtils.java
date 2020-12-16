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
                    Iterator it1 = src1.iterator(), it2 = src2.iterator();
                    @Override
                    public boolean hasNext() {
                        while (curr == null) {
                            if (it1.hasNext() && it2.hasNext()) {
                                curr = (K)it1.next();
                                if(!predicate.test(curr,(U)it2.next()))
                                    curr = null;
                            }
                            else
                                return false;
                        }
                        return true;
                    }

                    @Override
                    public K next() {
                        if (!hasNext())
                            throw new NoSuchElementException("No Elements");
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
                    Iterator<K> it = src.iterator();
                    K currKey = null;
                    V currValue = null, res = null;

                    @Override
                    public boolean hasNext() {
                        while(currValue == null){

                            if(it.hasNext()){
                                currKey = it.next();
                                if(map.containsKey(currKey)){
                                    currValue = map.get(currKey);
                                }
                                else
                                    currValue = null;
                            }
                            else
                                return false;
                        }
                        return true;
                    }

                    @Override
                    public V next() {
                        if(!hasNext())
                            throw new NoSuchElementException("No elements");

                        res = currValue;
                        currValue = null;
                        return res;
                    }
                };
            }
        };
    }

}
