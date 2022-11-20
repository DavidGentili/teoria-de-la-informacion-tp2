package modelo.fileSystem;


import modelo.helpers.Binary;

import java.io.*;

public class WriterFile {
    String buffer = "";
    FileOutputStream outputStream;
    DataOutputStream dataOutputStream;


    public WriterFile(String fileName, boolean append) throws IOException {
        this.outputStream = new FileOutputStream(fileName, append);
        this.dataOutputStream = new DataOutputStream(outputStream);
    }

    public WriterFile(String fileName) throws IOException {
        this.outputStream = new FileOutputStream(fileName);
        this.dataOutputStream = new DataOutputStream(outputStream);
    }


    private void write(int content) throws IOException {
        dataOutputStream.writeByte(content);
    }

    public void writeCode(String chunk) throws IOException {
        while(chunk.length() > 0){
            buffer += (char) chunk.charAt(0);
            chunk = chunk.substring(1);
            if(buffer.length() == 8){
                write(Binary.getIntByBinary(buffer));
                buffer = "";
            }
        }
    }

    public void close() throws IOException {
        if(buffer.length() != 0)
            write(Binary.getIntByBinary(Binary.completeWithCeros(buffer)));
        outputStream.close();
        dataOutputStream.close();
    }

    public void writeInteger(int number) throws IOException {
        dataOutputStream.writeInt(number);
    }



}
