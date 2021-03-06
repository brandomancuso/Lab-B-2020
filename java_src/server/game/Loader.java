 package server.game;


import entity.ItemType;
import entity.Definition;
import entity.Term;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
 
/**
 * it's the class for load the dictionary
 * @author Christian Squadrito
 */

public class Loader {
		
        /**
         * This method allows to load the dictionary from a file
         * @param file the path of the file where there is the physical dictionary
         * @return Dictionary
         * @throws IOException 
         * @see IOException 
         * @see Dictionary
         */
	public Dictionary loadDictionaryFromFile(File file) throws IOException  {
	
        Dictionary dictionary=new Dictionary();
        ZipFile zf = new ZipFile(file);
        
        Enumeration<? extends ZipEntry> entries = zf.entries();
        
        BufferedReader in = new BufferedReader(new InputStreamReader(zf.getInputStream(zf.getEntry("dictionaries/th_it_IT_v2.dat")),"ISO8859-15"));
        
        
		String temp;
		int numDefs=0;
		Term lastTerm=null;
		while(( temp= in.readLine()) != null) {
			if(temp.startsWith("ISO8859-15")) continue;
		  	if(numDefs>0){
		       	analyzeDefinition(temp, lastTerm);
		       	numDefs--;
		    }
		    else{
		       	String[] result;
		   		result = temp.split("\\|");
		   		numDefs=Integer.parseInt(result[1]);
		   		lastTerm=new Term(result[0]);
		   		dictionary.addTerm(lastTerm);
		    }
		}
		zf.close();
		in.close();
        return dictionary;
    }
	
        /**
         * allow to analyze the definition
         * @param s
         * @param t 
         */
	public void analyzeDefinition(String s, Term t) {
		String[] result;
		result = s.split("\\|");
		Definition d=new Definition(getItemType(result[0]));	
		d.setDefinition(result[0]);
		if(result.length>1)
			for (int i=1; i<result.length; i++)
				d.addSynonym(result[i]);

		t.addDefinition(d);
	}
	
        /**
         * allow to get the type of the term
         * @param s the word
         * @return ItemType thy word type
         * @see ItemType
         */
	public ItemType getItemType(String s) {
		if(s.startsWith("(s.m.")) return ItemType.sostantivo_maschile;
		else if(s.startsWith("(s.f.")) return ItemType.sostantivo_femminile;
		else if(s.startsWith("(v.")) return ItemType.verbo;
		else if(s.startsWith("(agg.")) return ItemType.aggettivo;
		else if(s.startsWith("(avv.")) return ItemType.avverbio;
		else if(s.startsWith("(cong.")) return ItemType.congiunzione;
		else if(s.startsWith("(prep.")) return ItemType.preposizione;
		else if(s.startsWith("(inter.")) return ItemType.interiezione;
		else return ItemType.other;
	}
}
