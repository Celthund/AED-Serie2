package serie2;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static serie2.IterableUtils.filterByMap;

public class FilterByMapTest {

    static final Iterable<Integer> SEmpty = Collections.emptyList();
    static final Iterable<Integer> SSingle = Collections.singletonList(5);
    static final List<Integer> SEquals = Arrays.asList( 5, 5, 5, 5, 5 ) ;
    static final List<Integer> S1 =  Arrays.asList(2, 5, 3, 6, 2, 5, 10, 3 ) ;

    @Test
    public void filterByMap_withEmptySequences(){
        assertIterableEquals(SEmpty,filterByMap( SEmpty , new HashMap<>()));
    }

    @Test
    public void filterByMap_withSingletonSequence(){
        assertIterableEquals(SEmpty,filterByMap( SSingle , new HashMap<>()));
        HashMap<Integer,Integer> map=new HashMap<>();
        map.put(3,5);
        assertIterableEquals(SEmpty,filterByMap( SSingle , map));
        map.put(5,5);
        assertIterableEquals(SSingle,filterByMap( SSingle , map));
    }

    @Test
    public void filterByMap_withEqualsSequence(){
        assertIterableEquals(SEmpty,filterByMap( SEquals , new HashMap<>()));
        HashMap<Integer,Integer> map=new HashMap<>();
        map.put(5,5);
        assertIterableEquals(SEquals,filterByMap( SEquals , map));
    }

    @Test
    public void filterByMap_withSomeSequence(){
        assertIterableEquals(SEmpty,filterByMap( S1 , new HashMap<>()));
        HashMap<Integer,Integer> map=new HashMap<>();
        map.put(5,7);
        map.put(2,2);
        map.put(12,12);
        assertIterableEquals(Arrays.asList(2,7,2,7),filterByMap( S1 , map));
    }



}
