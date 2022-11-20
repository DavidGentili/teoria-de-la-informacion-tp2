package modelo.codificacion;

import modelo.fileSystem.ReaderFile;
import modelo.fileSystem.ReaderTextFile;
import modelo.fileSystem.WriterTextFile;
import modelo.helpers.Binary;

import java.io.IOException;
import java.util.HashMap;

public class Decodificador {
    private HashMap<String, String> table;

    private String message;

    public Decodificador(){
        table = new HashMap<>();
    }

    public void readFile(String fileName) throws IOException{
        int offset = readTable(fileName);
        ReaderFile reader = new ReaderFile(fileName, offset + 1 ); //El mas uno es para pasar el salto de linea del final de tabla
        int length = readLength(reader);
        readContain(reader, length);
    }

    private int readTable(String fileName) throws IOException{
        ReaderTextFile reader = new ReaderTextFile(fileName);
        String word;
        String code;
        char endWord = '|';
        char endCode = ';';
        while(!reader.isFinish() && !reader.isJump()){
            word = reader.readWord(endWord);
            code = reader.readWord(endCode);
            if(!word.isBlank() && !word.isEmpty())
                table.put(code,word);
        }
        reader.closeFile();
        return reader.getOffset();
    }

    private int readLength(ReaderFile reader) throws IOException {
        return reader.readInteger();
    }

    private void readContain(ReaderFile reader, int length) throws IOException {
        String contain = getContain(reader);
        reader.closeFile();
        this.message = translateContain(contain, length);
    }

    private String getContain(ReaderFile reader) throws IOException {
        String buffer = "";
        while (!reader.isFinish()){
            buffer += Binary.getbinaryByInt(reader.read());
        }
        return buffer;
    }

    private String translateContain(String contain, int length){
        String message = "";
        String chunk = contain.substring(0, length);
        String code = "";
        while(chunk.length() > 0){
            code += chunk.charAt(0);
            chunk = chunk.substring(1);
            if(table.containsKey(code)){
                message += table.get(code);
                if(chunk.length() > 0)
                    message += " ";
                code = "";
            }
        }
        return message;
    }

    public void writeFile(String fileName) throws IOException {
        WriterTextFile writer = new WriterTextFile(fileName);
        writer.writeString(message);
        writer.close();
    }

    public String getMessage() {
        return message;
    }
}
