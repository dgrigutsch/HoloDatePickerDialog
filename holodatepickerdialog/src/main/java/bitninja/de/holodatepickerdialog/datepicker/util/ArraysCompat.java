package bitninja.de.holodatepickerdialog.datepicker.util;

public class ArraysCompat {

    public static byte[] copyOfRange(byte[] from, int start, int end){
        int length = end - start;
        byte[] result = new byte[length];
        System.arraycopy(from, start, result, 0, length);
        return result;
    }
    public static String[] copyOfRange(String[] from, int start, int end){
        int length = end - start;
        String[] result = new String[length];
        System.arraycopy(from, start, result, 0, length);
        return result;
    }
}
