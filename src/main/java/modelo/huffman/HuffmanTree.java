package modelo.huffman;

import modelo.Nodeable;

import java.util.Iterator;
import java.util.PriorityQueue;

public class HuffmanTree {

    private Node root;

    public HuffmanTree(){
        this.root = null;
    }

    public void loadTree(Iterator<Nodeable> it){
        PriorityQueue<Node> queue = getPriorityQueue(it);
        this.root = getRoot(queue);
        assignCodes(root, "");
    }

    private PriorityQueue<Node> getPriorityQueue(Iterator<Nodeable> it){
        PriorityQueue<Node> queue = new PriorityQueue<>();
        while(it.hasNext())
            queue.add(new Node(it.next()));
        return queue;
    }

    private Node getRoot(PriorityQueue<Node> queue){
        while(queue.size() > 1){
            mergeNodes(queue);
        }
        return queue.poll();
    }

    private void mergeNodes(PriorityQueue<Node> queue){
        Node left = queue.poll();
        Node right = queue.poll();
        if(left != null && right != null){
            double newValue = right.getProbability() + left.getProbability();
            Node newNode = new Node(newValue);
            newNode.setLeft(left);
            newNode.setRight(right);
            queue.add(newNode);
        }
    }

    private void assignCodes(Node node, String currentCode){
        if(node != null){
            if(node.getRight() == null && node.getLeft() == null)
                node.assignCode(currentCode);
            else{
                assignCodes(node.getLeft(), currentCode + "0");
                assignCodes(node.getRight(), currentCode + "1");
            }
        }
    }

}
