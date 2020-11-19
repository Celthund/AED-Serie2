package serie2;

public class Utils{

    public static void radixSort(String[] a, int w) {
        lsd(a, w);
    }

    public static void lsd(String[] a, int w){
        if (a.length < 2) return ;
        String[] tmp = a.clone();
        int pos = w - 1;
        int[] freq;
        char c1;
        while (pos >= 0){
            freq = new int[('9' - '0' + 1) + ('z' - 'a' + 1)];
            // Create frequency table
            for(int i = 0; i < tmp.length; i++){
                c1 = tmp[i].charAt(pos);
                if (c1 <= '9'){
                    freq[c1 - '0'] += 1;
                } else {
                    freq[c1 - 'a' + ('9' - '0') + 1] += 1;
                }
            }

            // Increment frequency table;
            for(int i = 1; i < freq.length; i++) {
                freq[i] += freq[i-1];
            }

            for(int i = tmp.length - 1; i >= 0 ;i--){
                c1 = tmp[i].charAt(pos);
                if (c1 <= '9'){
                    a[freq[c1 - '0'] - 1] = tmp[i];
                    freq[c1 - '0'] -= 1;
                } else {
                    a[freq[c1 - 'a' + ('9' - '0') + 1] - 1] = tmp[i];
                    freq[c1 - 'a' + ('9' - '0') + 1] -= 1;
                }
            }
            tmp = a.clone();
            pos--;
        }
    }



    public static String[] msd(){
        return null;
    }
}
