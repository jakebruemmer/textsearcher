package kwic;

/** Represents the original and matching forms of a word.  
 * You must implement 
 * {@link Object#hashCode()} correctly as well as
 * {@link Object#equals(Object)} 
 * for this to work.
 * Jake Bruemmer
 * Lab Section B
 * jakebruemmer@wustl.edu
 * CSE 132 Lab 2a
 * Word.java
 * Takes a String and stores two instance variables of Strings. One String is the same
 * as the parsed String and the other is run through the .makeCanoncial() method of the
 * WordFilter class. The filtered instance variable is used to compare two Words to see
 * if they are equal to one another. 
 */

public class Word {

	public final String match, original;
	/** Represent a word of a {@link Phrase}
	 * @param w The original word
	 */
	public Word(String w){
		
		original = w;
		match = WordFilter.makeCanonical(original);
	}

	/**
	 * Follows same logic as .equals() method. Word that have the same match instance
	 * variables will be stored at the same hash value. 
	 * 
	 * @return int of hash value
	 */
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((match == null) ? 0 : match.hashCode());
		return result;
	}
	
	/**
	 * Checks to see if two words are equal to one another. Word objects are considered
	 * to be equal if their match instance variables are string equivalent. 
	 * 
	 * Returns TRUE if words contain same characters regardless of punctuation. I.e.,
	 * The Words "FuN" and "fUn" would be equal.
	 * 
	 * @param obj Word to be compared
	 * @return equivalence of the two Words
	 */

	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Word other = (Word) obj;
		return match.equals(other.match);
		//return true;
	}



	
	/**
	 * The word used for matching is the original word run throgh
	 * the WordCanonical filter.
	 * @return the form of the word used for matching.
	 * 
	 */
	public String getMatchWord() {
		//
		// FILL ME IN
		// This should never return null
		return match;
	}

	/**
	 * 
	 * @return the original word
	 */
	public String getOriginalWord() {
		//
		// FILL ME IN
		// This should never return null
		return original;
	}

	/** 
	 * You must implement this right, see lab writeup notes.
	 * 
	 * This is commented out so you can have eclipse generate
	 * a skeleton of it for you.
	 */
//	public int hashCode() { 
//		
//	}


	/**
	 * You must implement this so that two words equal each
	 * other if their matched forms equal each other.
	 * You can let eclipse generate this method automatically,
	 * but you have to modify the resulting code to get the
	 * desired effect.
	 * 
	 * This method is commented out so you can have eclipse generate
	 * a skeleton of it for you.
	 */
//	public boolean equals(Object o) {
//	}

	/**
	 * @return the word and its matching form, if different
	 */
	public String toString(){
		return getMatchWord();
	}

}
