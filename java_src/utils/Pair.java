package utils;

import java.io.Serializable;

/**
 * Classe parametrizzata contenete una coppia di oggetti
 * @author Fedeli Andrea
 * @param <T> Primo oggetto
 * @param <V> Secondo oggetto
 */
public class Pair<T, V> implements Serializable{
    
    private T first;
    private V last;
    
    private static final long serialVersionUID = 1L;
    
    /**
     * Costruttore della classe
     * @param first Primo oggetto
     * @param last Secondo oggetto
     */
    public Pair(T first, V last) {
        this.first = first;
        this.last = last;
    }

    /**
     * Restituisce il contenuto il primo oggetto
     * @return Il primo oggetto della coppia
     */
    public T getFirst() {
        return first;
    }

    /**
     * Restituisce il secondo oggetto
     * @return Il secondo oggetto della coppia
     */
    public V getLast() {
        return last;
    }
    
}
