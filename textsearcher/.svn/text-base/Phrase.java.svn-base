package kwic;
import java.util.HashSet;
import java.util.Set;
import java.util.StringTokenizer;

/**
 * Represent a phrase.
 * Jake Bruemmer
 * Lab Section B
 * jakebruemmer@wustl.edu
 * CSE 132 Lab 2a
 * Phrase.java
 * Takes String, removes unnecessary punctuation, converts to lower case, and stores
 * that as an instance variable. Phrases are composed of Word objects. Can see Word 
 * objects in a Phrase by using getWords() method.
 */
public class Phrase {

	final protected String string;

	public Phrase(String s){
		string = cleanUp(s);
	}

	/** 
	 * Provide the words of a phrase.  
	 * Each word may have to be cleaned up:  
	 * punctuation removed, put into lower case
	 * 
	 * Pass instance variable into StringTokenizer. Iterate through StringTokenzier
	 * and add every String in the StringTokenizer into a Set of Words to be returned.
	 * 
	 * @return a Set of Word objects made from the String instance variable
	 */

	public Set<Word> getWords() {
		// FILL ME IN
		// Use StringTokenizer to break the
		//  phrase into words
		// Return a Set of such words
		// This should never return null
		Set<Word> set = new HashSet<Word>();
		StringTokenizer st = new StringTokenizer(string);
		while(st.hasMoreTokens()) {
			set.add(new Word(st.nextToken()));
		}
		return set;
	}

	/** The behavior of this lab depends on how you view this method.
      Are two phrases the same because they have the same words?
      Or are they the same because they are string-equlivalent.
      <UL>
       <LI> What song,  Is that Becky
       <LI> What song is that, Becky
      </UL>
      The above phrases have the same words but are different strings.
     * Phrases are considered equal if they are string equivalent because string 
     * equivalence dictates semantics. Phrases are equal if they mean the same thing. 
     * This method compares the string instance variables of the two Phrases.
     * 
     * @return equivalence of the two phrases
      
	 */
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Phrase other = (Phrase) obj;
		return this.string.equals(other.string);
	}

	/** This method must also be properly defined, or else your {@link HashSet}
      structure won't operate properly.
	 */
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((string == null) ? 0 : string.hashCode());
		return result;
	}

	/** Filter the supplied {@link String} (which is the String of
      a {@link Phrase} presumably) into a canonical form
      for subsequent matching.
      The actual filtering depends on what you consider to be
      insignificant in terms of matching.  
      <UL> <LI> If punctuation is
      irrelevant, remove puncutation.
           <LI> If case does not matter, than convert to lower (or upper)
	        case.
      </UL>
     * @param s String to be cleaned
     * @return cleaned up version of s after being passsed through WordFilter
	 */
	
	protected static String cleanUp(String s){
		// FIX ME
		// Don't just return s, but return a cleaned up version of s
		//   as described above
		return WordFilter.makeCanonical(s);
	}
	
	public String getString() {
		return string;
	}

	public String toString(){
		return string;
	}

}
