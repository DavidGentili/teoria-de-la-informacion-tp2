package modelo.helpers;

public class General {
    private static String extractSubString(StringBuilder str, int size){
        String aux;
        int limit = Math.min(size, str.length());
        aux = str.substring(0, limit);
        str.delete(0,limit);
        return aux;
    }

    public static byte readByte(StringBuilder buffer){
        return Binary.getByteByBinary(extractSubString(buffer,8));
    }

    public static String readCode(StringBuilder buffer, int size){
        return extractSubString(buffer, size);
    }
}
