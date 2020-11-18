package serie2;

import org.junit.jupiter.api.Test;
import static serie2.ListUtils.removeAfterIntersectionPoint;
import static serie2.ListUtilTest.*;
import static serie2.ListUtilTest.assertListEqualsWithSentinel;


public class RemoveAfterIntersectionPointTest {
    private static final Node<String> EMPTY_LIST = emptyListWithSentinel();
    private static final Node<String> SINGLETON_LIST_WITH_STR_A = makeListWithSentinel("A");
    private static final Node<String> SINGLETON_LIST_WITH_STR_b = makeListWithSentinel("b");
    private static final Node<String> SINGLETON_LIST_WITH_STR_c = makeListWithSentinel("c");

    @Test
    public void remove_after_intersectionPoint_empty_lists(){
        removeAfterIntersectionPoint( EMPTY_LIST , EMPTY_LIST, String::compareTo);
        assertListEqualsWithSentinel(EMPTY_LIST,emptyListWithSentinel(),String::compareTo);
        removeAfterIntersectionPoint( EMPTY_LIST, SINGLETON_LIST_WITH_STR_A, String::compareTo );
        assertListEqualsWithSentinel(EMPTY_LIST,emptyListWithSentinel(),String::compareTo);
        removeAfterIntersectionPoint( SINGLETON_LIST_WITH_STR_A,EMPTY_LIST, String::compareTo);
       assertListEqualsWithSentinel(SINGLETON_LIST_WITH_STR_A, makeListWithSentinel("A") ,String::compareTo);
    }

    @Test
    public void  remove_after_intersectionPoint_one_element_lists(){
        Node<String> l1 =  makeListWithSentinel("A");
        removeAfterIntersectionPoint(  l1 , makeListWithSentinel( "a" ),String::compareToIgnoreCase );
        assertListEqualsWithSentinel(EMPTY_LIST,l1,String::compareToIgnoreCase);
        Node<String> list= makeListWithSentinel( "a" );
       removeAfterIntersectionPoint(list,SINGLETON_LIST_WITH_STR_A, String::compareTo );
       assertListEqualsWithSentinel(SINGLETON_LIST_WITH_STR_A,list,String::compareToIgnoreCase);
    }

  @Test
    public void  remove_after_intersectionPoint_one_element_match(){
        Node<String> l1 = makeListWithSentinel( "b", "a" );
        Node<String> l2 = SINGLETON_LIST_WITH_STR_A;
        removeAfterIntersectionPoint(l1, l2, String::compareToIgnoreCase);
        assertListEqualsWithSentinel(SINGLETON_LIST_WITH_STR_b,l1,String::compareTo);
    }

    @Test
    public void  remove_after_intersectionPoint_two_element_match() {
        Node<String> l1 = makeListWithSentinel("c", "b", "a");
        Node<String> l2 = makeListWithSentinel("f", "b", "a");
        removeAfterIntersectionPoint(l1, l2, String::compareToIgnoreCase);
        assertListEqualsWithSentinel(SINGLETON_LIST_WITH_STR_c, l1, String::compareToIgnoreCase);
    }

   @Test
    public void  remove_after_intersectionPoint_not_match(){
        Node<String> l = makeListWithSentinel( "b", "a" );
       Node<String> equalToL = makeListWithSentinel( "b", "a" );
        Node<String> reverseL = makeListWithSentinel( "a", "b" );
        Node<String> greaterL = makeListWithSentinel("b", "a", "c" );
        removeAfterIntersectionPoint(l,reverseL,String::compareTo);
        assertListEqualsWithSentinel(equalToL,l,String::compareTo);
       removeAfterIntersectionPoint(l,greaterL,String::compareTo);
       assertListEqualsWithSentinel(equalToL,l,String::compareTo);
       removeAfterIntersectionPoint(l,SINGLETON_LIST_WITH_STR_A,String::compareTo);
       assertListEqualsWithSentinel(equalToL,l,String::compareTo);
    }

    @Test
    public void remove_after_intersectionPoint_all_match() {
      Node<String> fiveElements = makeListWithSentinel("a", "b", "c", "d", "e");
        removeAfterIntersectionPoint(fiveElements, makeListWithSentinel("a", "b", "c", "d", "e"), String::compareToIgnoreCase);
        assertListEqualsWithSentinel(EMPTY_LIST, fiveElements, String::compareToIgnoreCase);
    }

    @Test
    public void remove_after_intersectionPoint_some_match(){
       Node<Integer> n1= makeListWithSentinel(3,5,2,7,4);
       Node<Integer> n2= makeListWithSentinel(9,3,10,8,2,7,4);
       Node<Integer> expected= makeListWithSentinel(3,5);
       removeAfterIntersectionPoint(n1,n2,Integer::compareTo);
       assertListEqualsWithSentinel(expected,n1,Integer::compareTo);
    }

}
