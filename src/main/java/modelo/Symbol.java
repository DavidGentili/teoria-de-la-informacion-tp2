package modelo;

import java.util.Objects;

public class Symbol implements Nodeable, Comparable{
    private char symbol;
    private double probability;

    private String code;

    public Symbol(char symbol, double probability){
        this.symbol = symbol;
        this.probability = probability;
        this.code = "";
    }

    public char getSymbol() {
        return symbol;
    }

    public double getProbability() {
        return probability;
    }

    public String getCode() {
        return code;
    }

    @Override
    public void appendPrefix(String prefix) {
        code = prefix + code;
    }

    @Override
    public void resetCode() {
        this.code = "";
    }

    @Override
    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return String.format("%c %5.3f : %s", symbol, probability, code);
    }

    @Override
    public int compareTo(Object o) {
        Symbol other = (Symbol) o;
        if(Double.compare(this.probability, other.probability) != 0)
            return Double.compare(this.probability,other.probability);
        else
            return Character.compare(this.symbol, other.symbol);

    }

    @Override
    public int hashCode() {
        return Objects.hash(symbol, probability);
    }

    @Override
    public boolean equals(Object obj) {
        Symbol other = (Symbol) obj;
        return this.symbol == other.symbol;
    }

}
