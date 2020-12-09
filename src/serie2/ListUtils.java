package serie2;

import java.util.Comparator;

public class ListUtils {

    public static <E> void removeAfterIntersectionPoint( Node<E> list1, Node<E> list2, Comparator<E> cmp) {
        Node<E> val1 = list1.previous, val2 = list2.previous;
        while (val1 != list1 && val2 != list2){
            if (val1.value != val2.value){
                val1.next = list1;
                list1.previous = val1;
                return;
            }
            val1 = val1.previous;
            val2 = val2.previous;
        }
        list1.previous = list1;
        list1.next = list1;
    }

    public static <E>void quicksort(Node<E> first, Node<E> last, Comparator<E> cmp){
        throw new UnsupportedOperationException();
    }

   public static <E> Node<E> merge(Node<E>[] lists, Comparator<E> cmp){
       throw new UnsupportedOperationException();
   }
}
