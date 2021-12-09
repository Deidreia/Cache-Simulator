/**
 * @author Daniel Foster
 * @author Jakob Knight
 * Represents a cache's set
 */
public class CSet {
	/**
	 * index of this set in the cache
	 */
	int index;
	/**
	 * an array of object Lines under the given index
	 */
	Line[] lines;
	/**
	 * counter tracking how old each value is for the purpose of replacing old values for new ones
	 */
	int counter = 0;
	
	/**
	 * Constructor for CSet
	 * @param lineNum number of lines in the set
	 */
	public CSet(int lineNum) {
		this.lines = new Line[lineNum];
	}
	
	/**
	 * index setter
	 * @param index new index
	 */
	public void setIndex(int index) {
		this.index = index;
	}
	
	/**
	 * index getter
	 * @return index of this set
	 */
	public int getIndex() {
		return index;
	}
	
	/**
	 * Returns the line object. Useful for tracking/modifying the fields in testing or for later use
	 * @param fromIndex index of the desired line in set
	 * @return line object from CSet
	 */
	public Line getLine(int fromIndex) {
		return lines[fromIndex];
	}

	/**
	 * sets the counter for tracking age
	 * @param count integer representing the time this CSet was modified
	 */
	public void setCounter(int count) {
		this.counter = count;
	}
	
	/**
	 * Updates the lines in the cache
	 * @param tag    An integer representing the address tag
	 * @param offset Represents the address offset value
	 * @param type   R or W depending if this is a read or write
	 * @return the number of memory references used to update this line in the cache
	 */
	public int nextAdd(int tag, int offset, char type) {
		int lineIndex = -1;//tracks which line in the lines array is going to be modified
		for (int i = 0; i < lines.length; i++) {
			if(lines[i] != null && lines[i].tag == tag)
				lineIndex = i;
		}
		if (lineIndex == -1) {//if there was no set found with a matching tag
			lineIndex = 0;
			if (lines[lineIndex] != null) {// if this value is null, it needs to be the one that gets updated
				for (int i = 0; i < lines.length; i++) {//find out which line is older
					if (lines[i] == null)// if this value is null, it needs to be the one that gets updated
						lineIndex = i;
					else if (lines[i].counter < lines[lineIndex].counter) {
						lineIndex = i;//holding the oldest line's index
					}
				}
			}
		}
		int result = 0;//this result represents the number of memory references
		//if  we are overwriting  AND  this tag is the tag we are using  AND  the line is valid (this last one might be redundant)
		if (lines[lineIndex] != null && (lines[lineIndex].getTag() == tag && lines[lineIndex].isValid()))
			lines[lineIndex].makeDirty();
		else 
			result += 1;
		lines[lineIndex] = new Line(tag, offset);
		lines[lineIndex].setCounter(counter);
		
		if (type == 'W' && lines[lineIndex].isDirty())
			result +=1;
		lines[lineIndex].setTag(tag);
		lines[lineIndex].setOffset(offset);
		return result;
	}
}
