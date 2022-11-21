package modelo.fileSystem;

import modelo.helpers.Binary;

import java.io.*;

public class ReaderFile {

    private InputStream file;
    private DataInputStream reader;
    private byte currentByte;

    private boolean EoF;
    private int offset;

    public ReaderFile(String fileName, int offset) throws IOException {
        file = new FileInputStream(fileName);
        reader = new DataInputStream(file);
        EoF = false;
        this.offset = offset;
        currentByte = 0;
        readNext();
    }

    public ReaderFile(String fileName) throws IOException {
        file = new FileInputStream(fileName);
        reader = new DataInputStream(file);
        EoF = false;
        this.offset = 0;
        currentByte = 0;
        readNext();
    }

    public boolean isFinish() { return EoF;}

    public void closeFile() throws IOException {
        file.close();
        reader.close();
    }

    public byte read() throws IOException {
        byte current = currentByte;
        readNext();
        return current;
    }

    public void readNext() throws IOException {
        if(EoF)
            throw new IOException("El archivo ya se encuentra terminado");
        try{
            currentByte = reader.readByte();
            offset += (currentByte < 32) ? ((currentByte < 0) ? 3 : 2) : 1;
        } catch (EOFException e){
            EoF = true;
        }
    }

    public byte getCurrentByte() { return currentByte;}

    public int readInteger() throws IOException {
        StringBuilder chunk = new StringBuilder();
        for(int i = 0 ; i < 4 ; i++){
            chunk.append(Binary.getBinaryByInt(currentByte));
            readNext();
        }
        return Binary.getIntByBinary(chunk.toString());
    }

    public int getOffset(){
        return offset;
    }

}
