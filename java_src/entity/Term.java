 package entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
 
/**
 * 
 * @author Christian Squadrito
 */

public class Term implements Serializable{
    
        private static final long serialVersionUID = 1L;
	private String key="";
	private List<Definition> definitionSet;
	
	public Term(String key) {
		super();
		this.key = key;
		this.definitionSet=new ArrayList<>();
	}
	
	public Term(String key, List<Definition> definitionSet) {
		super();
		this.key = key;
		this.definitionSet=definitionSet;
	}
        
        /**
         * get the word related to the Term
         * @return String the word
         */

	public String getKey() {
		return key;
	}

        /**
         * the list of definitions related to the term
         * @return List all the definitions
         * @see Definition
         */
	public List<Definition> getDefinitions(){
		return definitionSet;
	}
	
        /**
         * add a definition for the term
         * @param d the definition
         */
	public void addDefinition(Definition d){
		definitionSet.add(d);
	}
	
        /**
         * print all the definitions
         * @return String definitions
         */
	public String toString() {
		String ret=""+key+":\n";
		for(Definition d:definitionSet) {
			ret=ret+d+"\n";
		}
		return ret;
	}
}
