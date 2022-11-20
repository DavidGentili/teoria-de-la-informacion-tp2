package modelo.shannon;

import modelo.Nodeable;

import java.util.Iterator;
import java.util.TreeSet;

public class Shannon <E extends Nodeable> {
    private TreeSet<E> tree;

    public Shannon(){
        tree = new TreeSet<>();
    }

    public Shannon(Iterator<E> it){
        tree = new TreeSet<>();
        while(it.hasNext())
            tree.add(it.next());
    }

    public void shannonFano(){
        TreeSet<E> nuevo = (TreeSet<E>) tree.clone();
        generateShannonCode(nuevo);
    }

    private void generateShannonCode(TreeSet<E> tree){
        if(tree.size() > 1){
            TreeSet<E> nuevo = new TreeSet<>();
            double prevDiff;
            double diff = 1;
            do{
                prevDiff = diff;
                passLastElement(tree, nuevo);
                diff = Math.abs(getSummation(tree) - getSummation(nuevo));
            }while(diff <= prevDiff);
            passFirstElement(nuevo, tree);
            appendPrefix(nuevo, "0");
            appendPrefix(tree, "1");
            generateShannonCode(nuevo);
            generateShannonCode(tree);
        }

    }

    /**
     * Pasa el ultimo elemento (El mas grande) del primer set al segundo set
     * @param first primer set
     * @param second segundo set
     */
    private void passLastElement(TreeSet<E> first, TreeSet<E> second){
        if(first.size() > 0){
            E aux = first.last();
            first.remove(aux);
            second.add(aux);
        }
    }

    /**
     * Pasa el primer elemento (El mas pequeño) del primer set al segundo set
     * @param first primer set
     * @param second segundo set
     */
    private void passFirstElement(TreeSet<E> first, TreeSet<E> second){
        if(first.size() > 0){
            E aux = first.first();
            first.remove(aux);
            second.add(aux);
        }
    }

    /**
     * Retorna la sumatoria de probabilidades
     * @param tree
     * @return
     */
    private double getSummation(TreeSet<E> tree){
        double sum = 0;
        Iterator<E> it = tree.iterator();
        while(it.hasNext())
            sum += it.next().getProbability();
        return sum;
    }

    private void appendPrefix(TreeSet<E> tree, String prefix){
        Iterator<E> it = tree.iterator();
        while(it.hasNext())
            it.next().appendPrefix(prefix);
    }


}
