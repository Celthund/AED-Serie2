package serie2;



import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.Comparator;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static serie2.ListUtilTest.getRandomListWithoutSentinel;
import static serie2.ListUtilTest.isSorted;
import static serie2.ListUtils.quicksort;

public class QuickSortTest {

	static final Comparator<Integer> CMP_REVERSE_ORDER = (i1,i2)-> i2.compareTo(i1);


	static final Comparator<Integer> CMP_NATURAL_ORDER = (i1,i2)-> i1.compareTo(i2);


	@Test
	public void quicksort_empty_list() {
		Node<Integer> list = ListUtilTest.emptyListWithoutSentinel();
		quicksort(list, list, CMP_NATURAL_ORDER);
		assertTrue(isSorted(list, list, CMP_NATURAL_ORDER));
	}

	@Test
	public void quicksort_with_oneElement_list() {
		Node<Integer> list = getRandomListWithoutSentinel(1);
		quicksort(list, list.next, CMP_NATURAL_ORDER);
		assertTrue(isSorted(list, list.next, CMP_NATURAL_ORDER));
	}

	@Test
	public void quicksort_with_increasingElements() {
		Node<Integer> list = ListUtilTest.getListWithoutSentinel(0, 20, 1);
		for (int i = 0; i < 20; i++) {
			Node<Integer> last = ListUtilTest.getNNode(i, list);
			quicksort(list, last, CMP_REVERSE_ORDER);
			assertTrue(isSorted(list, last, CMP_REVERSE_ORDER));
		}
	}

	@Test
	public void quicksort_with_SomeElements1_list() {
		ArrayList<Integer> array = new ArrayList<Integer>();
		array.add(22);
		array.add(-30);
		array.add(-30);
		array.add(32);
		array.add(-38);
		array.add(-34);
		array.add(-36);
		Node<Integer> list = ListUtilTest.getListWithoutSentinel(array);
		Node<Integer> last = ListUtilTest.getNNode(6, list);
		quicksort(list, last, CMP_NATURAL_ORDER);
		assertTrue(isSorted(list, last, CMP_NATURAL_ORDER));
	}

	@Test
	public void quicksort_with_som2Elements2_list() {
		ArrayList<Integer> array = new ArrayList<Integer>();
		array.add(27);
		array.add(-12);
		array.add(33);
		array.add(27);
		array.add(26);
		array.add(-33);
		array.add(14);
		array.add(26);
		array.add(8);
		array.add(9);
		array.add(-19);
		Node<Integer> list = ListUtilTest.getListWithoutSentinel(array);
		Node<Integer> last = ListUtilTest.getNNode(10, list);
		quicksort(list, last, CMP_NATURAL_ORDER);
		assertTrue(isSorted(list, last, CMP_NATURAL_ORDER));
	}

	@Test
	public void quicksort_with_randomElements_list() {
		Node<Integer> list = getRandomListWithoutSentinel(11);
		Node<Integer> last = ListUtilTest.getNNode(10, list);
		quicksort(list, last, CMP_NATURAL_ORDER);
		assertTrue(isSorted(list, last, CMP_NATURAL_ORDER));
	}

}

