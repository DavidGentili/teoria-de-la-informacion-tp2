package modelo.codificacion;

import modelo.fileSystem.ReaderFile;
import modelo.fileSystem.ReaderTextFile;
import modelo.fileSystem.WriterTextFile;
import modelo.helpers.Binary;
import modelo.helpers.General;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;

public class Decodificador {
    private HashMap<String, String> table;

    private String message;

    public Decodificador(){
        table = new HashMap<>();
    }

    public void readFile(String fileName) throws IOException{
        ReaderFile reader = new ReaderFile(fileName);
        readTable(reader);
        int length = readLength(reader);
        readContain(reader, length);
        reader.closeFile();
    }

    private void readTable(ReaderFile reader) throws IOException {
        StringBuilder buffer = readBuffer(reader);
        while (!buffer.isEmpty()){
            String word = readWord(buffer);
            int bitslength = (int) General.readByte(buffer);
            String code = General.readCode(buffer, bitslength);
            table.put(code, word);
        }
    }

    private StringBuilder readBuffer(ReaderFile reader) throws IOException {
        int bitsSize = reader.readInteger();
        int byteSize = Math.floorDiv(bitsSize, 8) + 1;
        StringBuilder buffer = new StringBuilder();
        int i = 0;
        while (i < byteSize && !reader.isFinish()) {
            buffer.append(Binary.getBinaryByInt(reader.read()));
            i++;
        }
        buffer.delete(bitsSize, buffer.length());
        return buffer;
    }

    private String readWord(StringBuilder buffer){
        byte[] buff = new byte[50];
        int i = 0;
        byte separator = (byte) '|';
        byte current = General.readByte(buffer);
        while(!buffer.isEmpty() && current != separator){
            buff[i] = current;
            i++;
            current = General.readByte(buffer);
        }
        byte[] cleanBuff = new byte[i];
        for(int j = 0 ; j < cleanBuff.length ; j++)
            cleanBuff[j] = buff[j];
        return new String(cleanBuff, StandardCharsets.UTF_8);
    }

    private int readLength(ReaderFile reader) throws IOException {
        return reader.readInteger();
    }

    private void readContain(ReaderFile reader, int length) throws IOException {
        String contain = getContain(reader);
        this.message = translateContain(contain, length);
    }

    private String getContain(ReaderFile reader) throws IOException {
        String buffer = "";
        while (!reader.isFinish()){
            buffer += Binary.getBinaryByInt(reader.read());
        }
        return buffer;
    }

    private String translateContain(String contain, int length){
        StringBuilder message = new StringBuilder();
        String chunk = contain.substring(0, length);
        String code = "";
        while(chunk.length() > 0){

            code += chunk.charAt(0);
            chunk = chunk.substring(1);
            if(table.containsKey(code)){
                message.append(table.get(code));
                if(chunk.length() > 0)
                    message.append(" ");
                code = "";
            }
        }
        return message.toString();
    }

    public void writeFile(String fileName) throws IOException {
        WriterTextFile writer = new WriterTextFile(fileName);
        writer.writeString(this.message);
        writer.close();
    }

    public String getMessage() {
        return message;
    }

}
