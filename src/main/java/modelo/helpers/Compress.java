package modelo.helpers;

public class Compress {

    public static String RLC(String str){
        char current;
        StringBuilder res = new StringBuilder();
        int i = 0;
        while(i < str.length()){
            current = str.charAt(i);
            i++;
            int cont = 1;
            while(i < str.length() && str.charAt(i) == current){
                cont++;
                i++;
            }
            res.append(current);
            if(cont > 2)
                res.append(cont);
            if(cont == 2)
                res.append(current);
        }
        return res.toString();
    }
}
