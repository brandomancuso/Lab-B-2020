 package server.game;

import entity.Definition;
import entity.Term;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
 
/**
 * 
 * @author Christian Squadrito
 */

public class Dictionary {
	HashMap<String, List<Definition>> dictionary;
		
	public Dictionary() {
		this.dictionary=new HashMap<>();	
	}
        
        /**
         * add a word in the volatile dictionary
         * @param t the term to insert
         * @see Term
         */
	
	public void addTerm(Term t) {
		dictionary.put(t.getKey(), t.getDefinitions());
	}
	
        /**
         * retrive the term in the dictionary
         * @param key the word which you're searching for
         * @return Term the term who you're searching for
         * @throws InvalidKey 
         * @see InvalidKey
         * @see Term
         */
        
	public Term getTerm(String key)throws InvalidKey{
		if(exists(key)){
			return new Term(key, dictionary.get(key));
		}
		else throw new InvalidKey();
	}
        
        /**
         * knowing the existence of the word in the dictionary
         * @param key the word which you're searching for
         * @return boolean exist it or not
         */
	
	public boolean exists(String key) {
		return dictionary.containsKey(key);
	}
	
        /**
         * get all the words in the dictionary
         * @return Set<String> words in the dictionary
         */
	public Set<String> getKeys(){
		return dictionary.keySet();
	}
	
        /**
         * get the number of words inside the dictionary
         * @return long size of dictionary
         */
	public long getSize() {
		return dictionary.size();
	}
	
}
