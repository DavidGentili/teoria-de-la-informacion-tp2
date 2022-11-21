package modelo.codificacion;

import modelo.fileSystem.ReaderTextFile;
import modelo.fileSystem.WriterFile;
import modelo.fileSystem.WriterTextFile;
import modelo.helpers.Binary;

import java.io.IOException;
import java.util.HashMap;

public class Codificador {

    HashMap<String, String> table;


    public Codificador(HashMap<String, String> table){
        this.table = table;
    }

    public void codingFile(String inputFileName, String outputFileName) throws IOException {
        writeTable(outputFileName);
        String contain = getContain(inputFileName);
        int lengthTable = Math.floorDiv(getFormatTable().length(), 8) + 4 + 1;
        int lengthContent = Math.floorDiv(contain.length(), 8) + 4 + 1;
        System.out.format("TABLA\n%-8d bytes\nCONTENIDO\n%-8d bytes\nTOTAL\n%-8d bytes\n", lengthTable, lengthContent, lengthTable + lengthContent);
        writeContain(contain, outputFileName);

    }

    private void writeTable(String outputFileName) throws IOException {
        WriterFile writer = new WriterFile(outputFileName);
        String formatTable = getFormatTable();
        writer.writeInteger(formatTable.length());
        writer.writeCode(formatTable);
        writer.close();
    }

    private String getContain(String fileName) throws IOException {
        ReaderTextFile input = new ReaderTextFile(fileName);
        StringBuilder contain = new StringBuilder();

        while(!input.isFinish()) {
            String word = input.readWord(' ');
            if (!word.isEmpty() && !word.isBlank())
                contain.append(table.get(word));
        }
        input.closeFile();
        return contain.toString();
    }

    private void writeContain(String contain, String fileName) throws IOException {
        WriterFile output = new WriterFile(fileName, true);
        int length = contain.length();
        output.writeInteger(length);
        output.writeCode(contain);
        output.close();
    }

    private String getFormatTable(){
        StringBuilder buffer = new StringBuilder();
        char separator = '|';
        String binarySeparator = Binary.getBinaryByInt((int) separator);
        for(String word : table.keySet()){
            String binaryWord = Binary.getBinaryByString(word);
            String binaryLength = Binary.getBinaryByInt(table.get(word).length());
            buffer.append(String.format("%s%s%s%s", binaryWord, binarySeparator, binaryLength, table.get(word)));
        }
        return buffer.toString();
    }
}
