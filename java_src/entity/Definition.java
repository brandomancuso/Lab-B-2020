 package entity;


import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import server.game.ItemType;
 
/**
 * 
 * @author Christian Squadrito
 */

public class Definition implements Serializable{
	private ItemType type;
	private String definition;
	private Set<String> synonymSet;
	private static final long serialVersionUID = 1L;
        
	public Definition(ItemType type) {
		super();
		this.type = type;
		this.definition="";
		this.synonymSet=new HashSet<String>();
	}
        
        /**
         * add a synonym for a specific word 
         * @param synonym
         * @return boolean if the method went fine
         */
	
	public boolean addSynonym(String synonym) {
		return synonymSet.add(synonym);
	}
        
        /**
         * set the definition for a specific word
         * @param definition 
         */
	
	public void setDefinition(String definition) {
		this.definition=definition;
	}
	
        /**
         * print all the definition for the word
         * @return String the definition
         */
	public String toString(){
		String ret=definition+ " ";
		for(String synonym : synonymSet) {
			ret=  ret+ " "+synonym+",";
		}
		return ret;
	}
        
        /**
         * type of the word (verb,adjective...)
         * @return ItemType the type
         * @see ItemType
         */

	public ItemType getType() {
		return type;
	}
}
