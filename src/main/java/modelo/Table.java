package modelo;

import modelo.fileSystem.ReaderFile;
import modelo.fileSystem.ReaderTextFile;
import modelo.fileSystem.WriterTextFile;
import modelo.huffman.HuffmanTree;
import modelo.shannon.Shannon;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Table {
    HashMap<String, Palabra> table;

    public Table(){
        this.table = new HashMap<>();
    }

    public void readFile(String fileName) throws IOException {
        ReaderTextFile reader = new ReaderTextFile(fileName);
        int wordCount = 0;
        while(!reader.isFinish()){
            String word = reader.readWord(' ').trim();
            if(!word.isBlank() && !word.isEmpty()){
                if(table.containsKey(word))
                    table.get(word).addCount();
                else
                    table.put(word, new Palabra(word));
                wordCount++;
            }
        }
        reader.closeFile();
        for(String word : table.keySet())
            table.get(word).setProbability(wordCount);
    }

    public HashMap<String, Palabra> getTable() {
        return table;
    }

    public HashMap<String, String> getTableCode() {
        HashMap<String, String> res = new HashMap<>();
        for(String word : table.keySet())
            res.put(word, table.get(word).getCode());
        return res;
    }

    public double getAvlength(){
        double acum = 0;
        for(Palabra word : table.values())
            acum += word.getCode().length() * word.getProbability();
        return acum;
    }

    public double getEntrophy(){
        double acum = 0;
        for(Palabra word : table.values())
            acum += word.getProbability() * (- Math.log(word.getProbability())/Math.log(2));
        return acum;
    }

    public double getRendimiento(){
        return getEntrophy() / getAvlength();
    }

    public void generateShannonCode(){
        for(Palabra word : table.values())
            word.resetCode();
        Shannon<Palabra> shannon = new Shannon<>(table.values().iterator());
        shannon.shannonFano();
    }

    public void generateHuffmanCode(){
        ArrayList<Nodeable> nodes = new ArrayList<>();
        for(Palabra word : table.values()){
            word.resetCode();
            nodes.add(word);
        }
        HuffmanTree tree = new HuffmanTree();
        tree.loadTree(nodes.iterator());
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        str.append("La tabla tiene " + table.size() + " palabras\n");
        for(String word : table.keySet())
            str.append(table.get(word) + "\n");
        return str.toString();
    }
}
