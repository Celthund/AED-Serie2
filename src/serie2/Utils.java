package serie2;

import java.util.Arrays;

public class Utils {
    public static void radixSort(String[] a, int w) {
        // If the size of the words are less than 1 or if the array is just one word it'll just return
        if(w < 1 || a.length < 2)
            return;

        // Creates and array with the index of the possible characters in a word
        int [] occurrences = new int['z' - '0' + 1];

        // It will serve as the result of this method, will order through this array
        String [] res = a;

        // Cycle that will run through every character in all words, starting at the last position
        //that is the least significant digit
        for(int i = 0; i < w; i++) {
            // Fills the occurences array with 0's so we later can count in it
            Arrays.fill(occurrences, 0);

            // Puts all the characters on the index correspondent with the character value -'0'
            for(int j = 0; j < res.length; j++){
                occurrences[res[j].charAt(w - 1 - i) - '0']++;
            }

            // Then starts counting, sums the previous index with the current index
            for(int j = 1; j < occurrences.length; j++){
                occurrences[j] += occurrences[j - 1];
            }

            // Will sort the array by order
            String [] sorted = new String[res.length];
            for(int j = 0; j < res.length; j++){
                // Gets the current word being checked, starting on the last
                String word = res[res.length - 1 - j];
                // Puts in the sort array the word which has the smallest char
                sorted[occurrences[res[res.length - 1 - j].charAt(w - 1 - i) - '0'] - 1] = word ;
                // Decrements the count of that char
                occurrences[res[res.length - 1 - j].charAt(w - 1 - i) - '0']--;
            }
            res = sorted;
        }

        // a stores the values of the res array that is now in order
        for(int i = 0; i < res.length; i++){
            a[i] = res[i];
        }
    }
}