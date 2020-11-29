 package server.game;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

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

	public String getKey() {
		return key;
	}

	public List<Definition> getDefinitions(){
		return definitionSet;
	}
	
	public void addDefinition(Definition d){
		definitionSet.add(d);
	}
	
	public String toString() {
		String ret=""+key+":\n";
		for(Definition d:definitionSet) {
			ret=ret+d+"\n";
		}
		return ret;
	}
}
