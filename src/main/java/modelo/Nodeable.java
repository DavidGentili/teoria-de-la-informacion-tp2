package modelo;

public interface Nodeable extends Comparable{
    public double getProbability();
    public void setCode(String code);
    public String getCode();
    public void appendPrefix(String prefix);
    public void resetCode();
}
