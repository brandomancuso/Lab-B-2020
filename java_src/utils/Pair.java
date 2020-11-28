package utils;

import java.io.Serializable;

public class Pair<T, V> implements Serializable{
    
    private T first;
    private V last;
    
    private static final long serialVersionUID = 1L;
    
    public Pair(T first, V last) {
        this.first = first;
        this.last = last;
    }

    public T getFirst() {
        return first;
    }

    public V getLast() {
        return last;
    }
    
}
