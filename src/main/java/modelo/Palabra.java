package modelo;

import java.util.Objects;

public class Palabra implements Comparable, Nodeable{
    private String word;
    private int count;
    private double probability;
    private String code;

    public Palabra(String word){
        this.word = word;
        this.count = 1;
        this.code = "";
    }


    public String getWord() {
        return word;
    }

    public int getCount() {
        return count;
    }

    public double getProbability() {
        return probability;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void addCount(){
        this.count++;
    }

    public void setProbability(double probability){
        this.probability = probability;
    }

    public void setProbability(int count){
        assert count != 0;
        this.probability = (double) this.count / count;
    }

    public void appendPrefix(String prefix){
        code = prefix + code;
    }

    public void appendSufix(String sufix){
        code += sufix;
    }

    @Override
    public void resetCode() {
        this.code = "";
    }

    @Override
    public boolean equals(Object o) {
        Palabra other = (Palabra) o;
        return  this.word == other.word;
    }

    @Override
    public int hashCode() {
        return Objects.hash(word, count, probability, code);
    }

    @Override
    public String toString() {
        return String.format("%-20s %-3d %8.2f : %s", word, count, probability, code);
    }

    @Override
    public int compareTo(Object o) {
        Palabra other = (Palabra) o;
        if(Double.compare(this.probability, other.probability) != 0)
            return Double.compare(this.probability,other.probability);
        return this.word.compareTo(other.word);
    }

}
