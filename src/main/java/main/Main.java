package main;

import modelo.Palabra;
import modelo.Table;
import modelo.codificacion.Codificador;
import modelo.codificacion.Decodificador;
import modelo.helpers.Compress;
import modelo.huffman.HuffmanTree;
import modelo.Nodeable;
import modelo.Symbol;
import modelo.shannon.Shannon;

import java.io.IOException;
import java.util.*;

public class Main {

    private static String ORIGINAL_FILE = "tp2_grupo3.txt";
    private static String OUTPUT_HUFFMAN = "tp2_grupo3.huf";
    private static String OUTPUT_SHANNON = "tp2_grupo3.fan";
    private static String RECOVER_HUFFMAN = "recover_huffman.txt";
    private static String RECOVER_SHANNON = "recover_shannon.txt";

    public static void main(String[] args) throws IOException {

        Table table = new Table();
        table.readFile(ORIGINAL_FILE);

        System.out.println("HUFFMAN");
        table.generateHuffmanCode();
        Codificador codHuffman = new Codificador(table.getTableCode());
        codHuffman.codingFile(ORIGINAL_FILE, OUTPUT_HUFFMAN);


        System.out.println("SHANNON");
        table.generateShannonCode();
        Codificador codShannon = new Codificador(table.getTableCode());
        codShannon.codingFile(ORIGINAL_FILE, OUTPUT_SHANNON);

        System.out.println("RECOVER HUFFMAN");
        Decodificador deco = new Decodificador();
        deco.readFile(OUTPUT_HUFFMAN);
        deco.writeFile(RECOVER_HUFFMAN);

        System.out.println("RECOVER SHANNON");
        deco.readFile(OUTPUT_SHANNON);
        deco.writeFile(RECOVER_SHANNON);

//        String formatTable = codHuffman.getFormatTable();
//        String RLC = Compress.RLC(formatTable);
//        System.out.format("Original: %d RLC: %d",formatTable.length(), RLC.length());
    }

    public static void exampleHuffman(){
        ArrayList<Nodeable> list = new ArrayList<>();
        list.add(new Symbol('a', 0.4));
        list.add(new Symbol('b', 0.3));
        list.add(new Symbol('c', 0.1));
        list.add(new Symbol('d', 0.075));
        list.add(new Symbol('e', 0.05));
        list.add(new Symbol('f', 0.05));
        list.add(new Symbol('g', 0.025));

        HuffmanTree tree = new HuffmanTree();
        tree.loadTree(list.iterator());

        for (Nodeable node : list) {
            System.out.println(node);
        }
    }

    public static void exampleShannon(){
        ArrayList<Symbol> list = new ArrayList<>();
//        list.add(new Symbol('a', 0.4));
//        list.add(new Symbol('b', 0.3));
//        list.add(new Symbol('c', 0.1));
//        list.add(new Symbol('d', 0.075));
//        list.add(new Symbol('e', 0.05));
//        list.add(new Symbol('f', 0.05));
//        list.add(new Symbol('g', 0.025));

//        list.add(new Symbol('a', 0.3));
//        list.add(new Symbol('b', 0.25));
//        list.add(new Symbol('c', 0.25));
//        list.add(new Symbol('d', 0.10));
//        list.add(new Symbol('e', 0.05));
//        list.add(new Symbol('f', 0.025));
//        list.add(new Symbol('g', 0.025));

        list.add(new Symbol('a', 0.38));
        list.add(new Symbol('b', 0.18));
        list.add(new Symbol('c', 0.15));
        list.add(new Symbol('d', 0.15));
        list.add(new Symbol('e', 0.13));


        Shannon<Symbol> shannon = new Shannon<>(list.iterator());
        shannon.shannonFano();

        for(Symbol sym : list)
            System.out.println(sym);

    }

    public static void example(){
        String str = "Holá cariño!";
        char[] buff = str.toCharArray();
        for(char c : buff){
            System.out.format("%c %d %d\n", c, (int) c, (byte) c);
        }
        System.out.println(getOffsetWord(str));
    }

    public static int getOffsetWord(String word){
        int offset = 0;
        char[] buff = word.toCharArray();
        for(char c : buff)
            offset += ((byte) c < 0) ? 2 : 1;
        return offset;
    }


}
