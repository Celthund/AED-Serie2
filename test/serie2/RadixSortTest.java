package serie2;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static serie2.Utils.radixSort;

public class RadixSortTest {

    @Test
    public void radixSort_onEmptyArray(){
        String[] strs=new String[0];
        radixSort(strs,3);
        assertEquals(true,isSorted(strs));
    }

    @Test
    public void radixSort_onSingletonArray(){
        String[] strs= {"aed"};
        radixSort(strs,3);
        assertEquals(true,isSorted(strs));
    }

    @Test
    public void radixSort_onEqualElementsArray(){
        String[] strs= {"aed","aed","aed","aed"};
        radixSort(strs,3);
        assertEquals(true,isSorted(strs));
    }

    @Test
    public void radixSort_onDistinctElementsArray(){
        String[] strs= {"bcb","aed","abd","bca","abc"};
        radixSort(strs,3);
        assertEquals(true,isSorted(strs));
    }

    @Test
    public void radixSort_onRandomElementsArray(){
        String[] strs=generateStringArray(100,10);
        radixSort(strs,10);
        assertEquals(true,isSorted(strs));
    }


    private static boolean isSorted(String[] array){
        if(array.length>0){
            String str=array[0];
            for(int i=0; i<array.length;i++){
                if(str.compareTo(array[i])>0) return false;
            }
        }
        return true;
    }

    private String[] generateStringArray(int dim,int size){
        String[] str=new String[dim];
       for(int i=0;i<str.length;i++){
           str[i]=getAlphaNumericString(size);
       }
       return str;
    }

    private static String getAlphaNumericString(int n) {
        String AlphaNumericString = "abcdefghijklmnopqrstuvxyz0123456789";
        StringBuilder sb = new StringBuilder(n);
        for (int i = 0; i < n; i++) {
            int index = (int) (AlphaNumericString.length() * Math.random());
            sb.append(AlphaNumericString.charAt(index));
        }
        return sb.toString();
    }
    }
