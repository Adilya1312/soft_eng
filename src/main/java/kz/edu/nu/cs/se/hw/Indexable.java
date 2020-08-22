package kz.edu.nu.cs.se.hw;

public interface Indexable extends Comparable<Indexable> {
    public String getEntry();
    public int getLineNumber();
    public boolean equals(Indexable o);
}
