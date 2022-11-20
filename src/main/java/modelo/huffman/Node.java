package modelo.huffman;

import modelo.Nodeable;

public class Node implements Comparable{
    private Node left;
    private Node right;
    private Nodeable value;
    private double probability;

    public Node(Nodeable value){
        this.value = value;
        this.left = null;
        this.right = null;
        probability = value.getProbability();
    }

    public Node(double probability){
        this.probability = probability;
        this.left = null;
        this.right = null;
    }

    public Node getLeft() {
        return left;
    }

    public Node getRight() {
        return right;
    }

    public void setLeft(Node left) {
        this.left = left;
    }

    public void setRight(Node right) {
        this.right = right;
    }

    public double getProbability() {
        return probability;
    }

    public void assignCode(String code){
        if(value != null)
            value.setCode(code);
    }

    @Override
    public int compareTo(Object o) {
        Node other = (Node) o;
        return Double.compare(this.probability, other.probability);
    }

    @Override
    public boolean equals(Object obj) {
        Node other = (Node) obj;
        return this.value.equals(other.value);
    }
}
