package modelo.fileSystem;

import modelo.helpers.Binary;

import java.io.*;

public class ReaderFile {

    InputStream file;
    DataInputStream reader;
    int currentSymbol;

    int offset;

    public ReaderFile(String fileName, int offset) throws IOException {
        file = new FileInputStream(fileName);
        reader = new DataInputStream(file);
        reader.skipBytes(offset);
        this.offset = offset;
        currentSymbol = 0;
        readNext();
    }

    public ReaderFile(String fileName) throws IOException {
        file = new FileInputStream(fileName);
        reader = new DataInputStream(file);
        offset = 0;
        currentSymbol = 0;
        readNext();
    }

    public boolean isFinish(){
        return currentSymbol == -1;
    }

    public void closeFile() throws IOException {
        file.close();
        reader.close();
    }


    private void readNext() throws IOException {
        if(currentSymbol == -1)
            throw new IOException("El archivo ya se encuentra terminado");
        currentSymbol = reader.read();
        byte currentByte = (byte) currentSymbol;
        offset += (currentByte < 32) ? ((currentByte < 0) ? 3 : 2) : 1;
    }

    public int read() throws IOException {
        int res = currentSymbol;
        readNext();
        return res;
    }

    public char getCurrentSymbol() {
        return (char) currentSymbol;
    }

    public void deleteJump() throws IOException {
        if(currentSymbol != -1 && currentSymbol == '\n')
            readNext();
    }

    public int readInteger() throws IOException {
        StringBuilder chunk = new StringBuilder();
        for(int i = 0 ; i < 4 ; i++){
            chunk.append(Binary.getbinaryByInt(currentSymbol));
            readNext();
        }
        return Binary.getIntByBinary(chunk.toString());
    }

    public int getOffset(){
        return offset;
    }

}
