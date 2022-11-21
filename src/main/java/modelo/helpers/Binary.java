package modelo.helpers;

import java.nio.charset.StandardCharsets;

public class Binary {
    public static int getIntByBinary(String buffer){
        int res = 0;
        char chars[] = buffer.toCharArray();
        for(int i = 0 ; i < chars.length ; i++){
            int pos = chars.length - i - 1;
            res += (chars[i] == '1') ? Math.pow(2, pos) : 0;
        }
        return res;
    }

    public static byte getByteByBinary(String buffer){
        byte res = 0;
        char chars[] = buffer.toCharArray();
        for(int i = 0 ; i < chars.length ; i++){
            int pos = chars.length - i - 1;
            res += (chars[i] == '1') ? Math.pow(2, pos) : 0;
        }
        return res;
    }

    public static String getBinaryByInt(int number){
        String binary = Integer.toBinaryString(number & 0xFF);
        return String.format("%8s", binary).replaceAll(" ","0");
    }

    public static String completeWithCeros(String buffer){
        return String.format("%-8s", buffer).replaceAll(" ", "0");
    }

    public static String getBinaryByString(String message){
        byte[] buff = message.getBytes(StandardCharsets.UTF_8);
        String chunk = "";
        for(byte b : buff){
            chunk += getBinaryByInt(b);
        }
        return chunk;
    }
}
