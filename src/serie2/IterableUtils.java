package serie2;
import java.util.Map;
import java.util.function.BiPredicate;

public class IterableUtils {

    public static <K,U> Iterable<K> filterBy(Iterable<K> src1, Iterable<U> src2, BiPredicate<K,U> predicate){
        throw new UnsupportedOperationException();
    }

    public static <K,V> Iterable<V> filterByMap(Iterable<K> src, Map<K,V> map){
        throw new UnsupportedOperationException();
    }

}
