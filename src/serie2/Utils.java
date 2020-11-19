package serie2;

import java.util.Arrays;

public class Utils {
    public static void radixSort(String[] a, int w) {
        if(w < 1 || a.length < 2)
            return;

        int [] occurrences = new int['z' - '0' + 1];
        String [] res = a;

        for(int i = 0; i < w; i++) {
            Arrays.fill(occurrences, 0);


            for(int j = 0; j < res.length; j++){
                occurrences[res[j].charAt(w - 1 - i) - '0']++;
            }

            for(int j = 1; j < occurrences.length; j++){
                occurrences[j] += occurrences[j - 1];
            }

            String [] sorted = new String[res.length];
            for(int j = 0; j < res.length; j++){
                String word = res[res.length - 1 - j];
                sorted[occurrences[res[res.length - 1 - j].charAt(w - 1 - i) - '0'] - 1] = word ;
                occurrences[res[res.length - 1 - j].charAt(w - 1 - i) - '0']--;
            }
            res = sorted;
        }

        for(int i = 0; i < res.length; i++){
            a[i] = res[i];
        }
    }
}