package modelo.codificacion;

import modelo.fileSystem.ReaderTextFile;
import modelo.fileSystem.WriterFile;
import modelo.fileSystem.WriterTextFile;

import java.io.IOException;
import java.util.HashMap;

public class Codificador {

    HashMap<String, String> table;


    public Codificador(HashMap<String, String> table){
        this.table = table;
    }

    public void codingFile(String inputFileName, String outputFileName) throws IOException {
        //Escribe tabla
        WriterTextFile outputText = new WriterTextFile(outputFileName);
        outputText.writeString(getFormatTable());
        outputText.close();
        String contain = getContain(inputFileName); //Obtiene contenido
        int lengthTable = getFormatTable().length();
        double lengthContent = (double) contain.length() / 8;
        System.out.format("TABLA\n%-8d bytes\nDIMENSION\n4 bytes\nCONTENIDO\n%-10.2f bytes\nTOTAL\n%-10.2f bytes\n", lengthTable, lengthContent, lengthTable + lengthContent + 4);
        writeContain(contain, outputFileName); //Escribe dimension y contenido

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

    public String getFormatTable(){
        StringBuilder str = new StringBuilder();
        for(String word : table.keySet())
            str.append(String.format("%s|%s;", word, table.get(word)));
        str.append("\n");
        return str.toString();
    }
}
