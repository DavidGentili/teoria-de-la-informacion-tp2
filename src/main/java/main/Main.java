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
        codinghuffman(table);
        decodingHuffman();
        codingShannon(table);
        decodingShannon();
    }

    public static void codinghuffman(Table table) throws IOException {
        System.out.println("HUFFMAN");
        table.generateHuffmanCode();
        Codificador cod = new Codificador(table.getTableCode());
        cod.codingFile(ORIGINAL_FILE, OUTPUT_HUFFMAN);
        System.out.format("Longitud media: %4.2f Entropia: %4.2f Rendimineto : %4.2f Redundancia : %4.2f\n\n", table.getAvlength(), table.getEntrophy(), table.getRendimiento(), 1 - table.getRendimiento());
        System.out.println();
    }

    public static void codingShannon(Table table) throws IOException {
        System.out.println("SHANNON");
        table.generateShannonCode();
        Codificador cod = new Codificador(table.getTableCode());
        cod.codingFile(ORIGINAL_FILE, OUTPUT_SHANNON);
        System.out.format("Longitud media: %4.2f Entropia: %4.2f Rendimineto : %4.2f Redundancia : %4.2f\n\n", table.getAvlength(), table.getEntrophy(), table.getRendimiento(), 1 - table.getRendimiento());
        System.out.println();

    }

    public static void decodingHuffman() throws IOException {
        System.out.println("RECOVER HUFFMAN");
        Decodificador deco = new Decodificador();
        deco.readFile(OUTPUT_HUFFMAN);
        deco.writeFile(RECOVER_HUFFMAN);
        System.out.println();
    }

    public static void decodingShannon() throws IOException {
        System.out.println("RECOVER SHANNON");
        Decodificador deco = new Decodificador();
        deco.readFile(OUTPUT_SHANNON);
        deco.writeFile(RECOVER_SHANNON);
        System.out.println();
    }


}
