package serie2;

import java.util.Comparator;

public class ListUtils {

    public static <E> void removeAfterIntersectionPoint(Node<E> list1, Node<E> list2, Comparator<E> cmp) {
        // We start at the end and will check until the value of val1 and val2 doesn't match
        Node<E> val1 = list1.previous, val2 = list2.previous;

        // While cycle that runs from the tail till the head of one of the lists
        while (val1 != list1 && val2 != list2) {

            // If the compares method return a value different from 0 it means that the value are not equal
            //so we move the point of the index of the list1 ahead so it can return to the last time the values of
            //the 2 lists were equal and we connect the head to that node to get all the nodes equal to the one in the list2
            if (cmp.compare(val1.value, val2.value) != 0) {
                val1.next = list1;
                list1.previous = val1;
                return;
            }

            // Moves to the previous node
            val1 = val1.previous;
            val2 = val2.previous;
        }

        // If it reaches this point it means that all elements of the list1 contains the same value of the ones on the list2
        //so it just connects the head to the last previous node of the val1
        list1.previous = val1;
        val1.next = list1;
    }

// Another way we did the quicksort method, using pointers instead of changing the values.
//It doesnt work with the current pointers because this method rearranges the nodes instead of the values
/*
    public static <E> void quicksort_with_pointers(Node<E> first, Node<E> last, Comparator<E> cmp) {
        if (first == null || last == null || first == last) return;
          Node<E> pivot = last, curr = first, tmp;
          while (curr != pivot) {
              if (cmp.compare(curr.value, pivot.value) > 0){
                  tmp = curr.next;
                  if (first == curr)
                      first = first.next;
                  curr.next.previous = curr.previous;
                  if (curr.previous != null)
                    curr.previous.next = curr.next;
                  curr.next = last.next;
                  curr.previous = last;
                  last.next = curr;
                  last = curr;
                  curr = tmp;
              } else
                curr = curr.next;
          }
          if (first != pivot && first.next != pivot)
              quicksort_with_pointers(first, pivot.previous, cmp);
          if (last != pivot && last.previous != pivot)
            quicksort_with_pointers(pivot.next, last, cmp);
    }
*/


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
        // List that will be returned
        Node<E> res = new Node<E>();

        // nullCounter counts all the nodes that are currently null on the lists array
        // i is the current index being checked in the lists array
        // smallestIdx stores smallest value index of the lists array
        int nullCounter = 0, i = 0, smallestIdx = 0;

        // Creates the Circular Doubly Lists by conection the head to itself
        res.next = res.previous = res;

        // If, when it runs to the lists array all position are null, it will finish the cycle
        while (nullCounter < lists.length) {
            // When i is the same value as lists.length it means that it has checked all the position of the
            //lists array, so the smallestIdx variable has the index of node with the smallest value of that
            //iteration so it can be stored in the res list
            if (i >= lists.length) {
                // To start checking the lists array for another node that contains the next smallest value
                i = 0;

                // To help connecting the node to the res
                Node <E> tmp = lists[smallestIdx];

                // If the tmp is null it means that currently the value that it iterator were all null and so it
                //didnt found any node to put on the res list
                if(tmp != null) {
                    // Connects the smallest node in the lists array on the tail of the res list
                    lists[smallestIdx] = lists[smallestIdx].next;
                    tmp.previous = res.previous;
                    res.previous.next = tmp;
                    res.previous = tmp;
                    tmp.next = res;
                }
            }
            // If the current index of lists is null increments the nullCounter
            if (lists[i] == null)
                nullCounter++;

            // If not it will return to 0 because it found a node, if we didnt reset nullCounter it when we ran through
            //a position that is null multiple times, the program could return because nullCounter could reach the lists.length - 1 value
            else {
                nullCounter = 0;

                // stores the current smallext index
                if (lists[smallestIdx] == null || cmp.compare(lists[smallestIdx].value, lists[i].value) > 0)
                    smallestIdx = i;
            }
            i++;
        }

        // Returns the list
        return res;
    }
}
