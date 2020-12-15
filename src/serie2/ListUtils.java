package serie2;

import java.util.Comparator;

public class ListUtils {

    public static <E> void removeAfterIntersectionPoint(Node<E> list1, Node<E> list2, Comparator<E> cmp) {
        Node<E> val1 = list1.previous, val2 = list2.previous;
        while (val1 != list1 && val2 != list2) {
            if (cmp.compare(val1.value, val2.value) != 0) {
                val1.next = list1;
                list1.previous = val1;
                return;
            }

            val1 = val1.previous;
            val2 = val2.previous;
        }
        list1.previous = val1;
        val1.next = list1;
    }

    public static <E> void quicksort(Node<E> first, Node<E> last, Comparator<E> cmp) {
        //Terminal Condition
        if (last != null && first != last && first != last.next) {
            //Variable that stores the node of the pivot after the swaps
            Node<E> tmp = partition(first, last, cmp);

            //Will organize the Nodes to the left of the last pivot
            quicksort(first, tmp.previous, cmp);
            //Will organize the Nodes to the Right of the last pivot
            quicksort(tmp.next, last, cmp);
        }
    }

    private static <E> Node partition(Node<E> first, Node<E> last, Comparator<E> cmp) {
        E pivotValue = last.value;

        // Gets the position of the last node that has the value smaller than the pivot
        Node<E> lastSwapIdx = first.previous;

        //Will run through the list so it can check whos nodes are smaller or greater than the pivot
        Node<E> currIdx = first;

        while (currIdx != last) {

            //If the currentNode value is smaller than the pivot it will swap with the last swap location
            // and store the new location in lastSwapIdx variable
            if (cmp.compare(pivotValue, currIdx.value) > 0) {
                //If the variable is null then it hasnt switched any values, so it will default to the first position
                if (lastSwapIdx == null)
                    lastSwapIdx = first;
                    //Else it will just move to the next position on the list
                else
                    lastSwapIdx = lastSwapIdx.next;

                //Switches the value of the new value smallest than the pivot and the position after tge lastSwapIdx
                E tmp = lastSwapIdx.value;
                lastSwapIdx.value = currIdx.value;
                currIdx.value = tmp;
            }
            currIdx = currIdx.next;
        }
        //Will check one last time so it can switch the pivot between the values lower than it (to the left)
        // and higher than it (to the right)
        if (lastSwapIdx == null)
            lastSwapIdx = first;
        else
            lastSwapIdx = lastSwapIdx.next;
        E temp = lastSwapIdx.value;
        lastSwapIdx.value = last.value;
        last.value = temp;

        //Returns the node that contains the pivot so the quickSort function can know the actual position
        // and use that to keep ordering the list
        return lastSwapIdx;
    }

    public static <E> Node<E> merge(Node<E>[] lists, Comparator<E> cmp) {
        Node<E> res = new Node<E>(), last = res;
        int nullCounter = 0, i = 0, smallestIdx = 0;

        res.next = res.previous = res;

        while (nullCounter < lists.length) {
            if (i >= lists.length) {
                i = 0;

                Node <E> tmp = lists[smallestIdx];

                if(tmp != null) {
                    lists[smallestIdx] = (lists[smallestIdx].next == null) ? null : lists[smallestIdx].next;

                    if (tmp.next != null)
                        tmp.next.previous = tmp.previous;

                    res.previous = tmp;
                    tmp.next = res;
                    tmp.previous = last;
                    last.next = tmp;
                    last = tmp;
                }
            }

            if (lists[i] == null)
                nullCounter++;

            else {
                nullCounter = 0;
                if (lists[smallestIdx] == null || cmp.compare(lists[smallestIdx].value, lists[i].value) > 0)
                    smallestIdx = i;
            }
            i++;
        }

        return res;
    }
}
