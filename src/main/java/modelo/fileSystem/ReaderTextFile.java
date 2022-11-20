package modelo.fileSystem;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class ReaderTextFile {
    File file;
    private BufferedReader reader;
    private int currentSymbol;

    private int offset;

    public ReaderTextFile(String fileName, int offset) throws IOException {
        file = new File(fileName);
        reader = new BufferedReader(new InputStreamReader(new FileInputStream(file), "utf-8"));
        reader.skip(offset);
        offset = offset;
        currentSymbol = 0;
        readNext();
    }

    public ReaderTextFile(String fileName) throws IOException {
        file = new File(fileName);
        reader = new BufferedReader(new InputStreamReader(new FileInputStream(file), "utf-8"));
        offset = -1;
        currentSymbol = 0;
        readNext();
    }

    public boolean isFinish(){
        return currentSymbol == -1;
    }

    public void closeFile() throws IOException {
        file = null;
        reader.close();
    }

    private void readNext() throws IOException {
        if(currentSymbol == -1)
            throw new IOException("El archivo ya se encuentra terminado");
        currentSymbol = reader.read();
        byte currentByte = (byte) currentSymbol;
        offset += (currentByte <=  32 && currentByte != 10 && currentByte != 13) ? 2 : 1; //Salto dos veces por lectura de caracter especial
    }

    public String readWord(char separator) throws IOException {
        String str = "";
        while(currentSymbol != -1 && currentSymbol == separator)
            readNext();
        while(currentSymbol != -1 && currentSymbol != separator){
            str += (char) currentSymbol;
            readNext();
        }
        if(!isFinish())
            readNext();
        return str;
    }

    public boolean isJump(){
        return currentSymbol == '\n';
    }

    public int getOffset(){ return offset;}


}
