package serie2;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static serie2.IterableUtils.filterBy;
import java.util.*;



public class FilterByTest {
    static final Iterable<Integer> SEmpty = Collections.emptyList();
    static final Iterable<Integer> SSingle = Collections.singletonList(5);
    static final List<Integer> SEquals = Arrays.asList( 5, 5, 5, 5, 5 ) ;
    static final List<Integer> S1 =  Arrays.asList(2, 5, 3, 6, 2, 5, 10, 3 ) ;
    static final List<Integer> S2 =  Arrays.asList( 2, 1, 3, 6, 4, 5) ;

    @Test
    public void filterBy_withEmptySequences(){
     assertIterableEquals(SEmpty,filterBy( SEmpty , SEmpty, (x,y) -> x> y));
    }

    @Test
    public void filterBy_withSingletonSequence(){
        assertIterableEquals(SEmpty,filterBy( SSingle, SEmpty, (x,y) -> x> y));
        assertIterableEquals(SEmpty,filterBy( SEmpty, SSingle , (x,y) -> x> y));
    }

    @Test
    public void filterBy_withEqualSequences(){
        assertIterableEquals(SEmpty,filterBy( SEquals, SEquals, (x,y) -> x> y));
        assertIterableEquals(SEquals,filterBy( SEquals, SEquals , (x,y) -> x== y));
    }

    @Test
    public void filterBy_withExampleSequences(){
        List<Integer> SRes=  Arrays.asList( 5, 3, 2 ) ;
        assertIterableEquals(SRes,filterBy( S1, S2, (x,y) -> x + y == 6));
        SRes=  Arrays.asList( 1, 3, 4 );
        assertIterableEquals(SRes,filterBy( S2, S1 , (x,y) -> x + y == 6));
    }

    @Test
    public void filterBy_withSequences(){
        List<Integer> SRes=  Arrays.asList( 5 ) ;
        assertIterableEquals(SRes,filterBy( SEquals, S2, (x,y) -> x + y == 6));
    }


}
