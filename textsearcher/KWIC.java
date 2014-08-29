package kwic;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.beans.PropertyChangeSupport;
import java.io.*;

//TA: MB Grade: 100/100

/** 
 * Key Word in Context
 * 
 * Jake Bruemmer (Student ID: 419767)
 * Lab Section B
 * jakebruemmer@wustl.edu
 * CSE 132 Lab 2a
 * KWIC.java
 * Searches through given file or String array to find the number of Phrases that 
 * contain a given Word. The Word and Phrase objects are defined in other classes. 
 * KWIC class has PropertyChangeSupport and HashMap instance variables. Words are 
 * used as key values mapped to a Set of Phrases that contain that Word.
 *
 */

public class KWIC {

	protected PropertyChangeSupport pcs;
	private HashMap<Word, HashSet<Phrase>> map;

	public KWIC() { 
		pcs = (new PropertyChangeSupport(this));
		map = new HashMap<Word, HashSet<Phrase>>();
	}

	/** 
	 * Required for part (b) of this lab.
	 * Accessor for the {@link PropertyChangeSuppport} 
	 */

	public PropertyChangeSupport getPCS() { return pcs; }

	/** 
	 * Convenient interface, accepts a standrd Java {@link String}
	 * @param s String to be added
	 */
	public void addPhrase(String s) {
		addPhrase(new Phrase(s));
	}
	
	/**
	 * Add each line in the file as a phrase.
	 * For each line in the file, call {@link addPhrase(String)} to
	 *   add the line as a phrase.
	 * @param file the file whose lines should be loaded as phrases
	 * @throws IOException 
	 */
	public void addPhrases(File file) throws IOException {
		//
		// FILL ME IN after you have everything else working
		//
		BufferedReader br = new BufferedReader(new FileReader(file));
		String line;
		while ((line = br.readLine()) != null) {
			addPhrase(line);			
		}
		
	}

	/** 
	 * For each {@link Word} in the {@link Phrase}, 
	 * add the {@link Word}
	 * to the association.
	 * Use reduction to {@link #forceAssoc(Word, Phrase)}.
	 * @param p Phrase to be added
	 */
	public void addPhrase(Phrase p) {
		for (Word w : p.getWords()) {
			forceAssoc(w, p);			
		}
		pcs.firePropertyChange("Phrase Added",false, true);
	}


	/** For each word in the {@link Phrase}, delete the association between
      the word and the phrase.
      Use reduction to {@link #dropAssoc(Word, Phrase)}.
	 */
	public void deletePhrase(Phrase p) {
		for (Word w : p.getWords()) {
			dropAssoc(w,p);
		}
		pcs.firePropertyChange("Phrase Deleted",false,true);
	}

	/** Force a mapping between the speicified {@link Word} and {@link Phrase}
	 * 
	 * @param w Word key value in instance map, p Phrase to be added to Set of Phrases mapped to given Word value
	 * 
	 * Check to see if the instance map already has a key of the Word parsed into the method, 
	 * if TRUE, then get the Set of Phrases associated with that method and add the parsed Phrase
	 * 
	 * Else create a Set with the parsed Phrase and put that set into the instance map with the 
	 * parsed Word as the key value
	 *
	 */
	public void forceAssoc(Word w, Phrase p) {
		//
		// FILL ME IN
		// Leave the following line as the last line of this method
		//
		if (map.containsKey(w)) {
			map.get(w).add(p);
		}
		else {
			HashSet<Phrase> phrases = new HashSet<Phrase>();
			phrases.add(p);
			map.put(w, phrases);
		}
		pcs.firePropertyChange("Phrase Added",false,true);
	}


	/** 
	 * Drop the association between the 
	 * specified {@link Word} and {@link Phrase}, if any
	 * 
	 * @param w Word key value in instance map, p Phrase to be removed from Set of Phrases mapped to given Word value 
	 * 
	 * Check to see if the parsed Word is a key value in the instance map
	 * if True then get the Set of Phrases mapped to that key and remove the parsed Phrase
	 */
	public void dropAssoc(Word w, Phrase p) {
		//
		// FILL ME IN
		// Leave the following line as the last line of this method
		//
		if (map.containsKey(w)) {
			map.get(w).remove(p);
		}
		pcs.firePropertyChange("Phrase Deleted",false,true);
	}


	/** Return a Set that provides each {@link Phrase} associated with
    the specified {@link Word}.
     *
     *@param w Word that is the key for the Set of Phrases you wish to obtain
     *
	 * Check to see if the instance map has a key value with the associated word, if it doesn't return a phrase
	 * of zero length
	 * 
	 * Returns Set of Phrases from the instance map using the .get() method from the HashMap API
	 */
	
	public Set<Phrase> getPhrases(Word w) {
		//
		// FILL ME IN
		// This method should never return null
		//
		if (!map.containsKey(w)) {
			HashSet<Phrase> phrases = new HashSet<Phrase>();
			map.put(w, phrases);
			return map.get(w);
		}
		return map.get(w);
	}
	
	/** 
	 * Drop a word completely from the KWIC 
	 * 
	 * @param w Word to be dropped
	 */
	public void deleteWord(Word w) {
		//
		// FILL ME IN
		// Leave the following line as the last line
		//
		if (map.containsKey(w)) {
			map.get(w).clear();
			map.remove(w);
		}
		pcs.firePropertyChange("Word Deleted",false,true);
	}

	/** Rerturn a Set of all words */
	public Set<Word> getWords() {
		//
		// FILL ME IN
		// This method should never return null
		return map.keySet();
	}
}
