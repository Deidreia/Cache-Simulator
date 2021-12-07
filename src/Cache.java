

/**
 * @author Daniel Foster
 * @author Jakob Knight
 *
 */
public class Cache {
	int lineSize;
	int counter = 0;
	CSet[] sets;
	int memrefs = 0;
	
	public Cache(int numSets, int setSize, int lineSize) {
		this.sets = new CSet[numSets];
		for (int i = 0; i < numSets; i++) {
			sets[i] = new CSet(setSize);
		}
		this.lineSize = lineSize;
	}

	/**
	 * @return the lineSize
	 */
	public int getLineSize() {
		return lineSize;
	}

	/**
	 * @param lineSize the lineSize to set
	 */
	public void setLineSize(int lineSize) {
		this.lineSize = lineSize;
	}
	
	
	//+++++++++++++++READ THIS+++++++++++++++++
	//	ADDRESS PARAM MUST BE A STRING OF 8 CHARACTERS
	//  LOOK AT intToString TO GET AN IDEA FOR HOW TO DO THIS
	//  MAKE SURE THE SECOND BULLET UNDER DETAILS IS COVERED IN Simulator.java OR Driver.java
	/**
	 * Creates a String representing the activities of the cache simulation
	 * @param address A string representing the memory address, must be 8 characters long to get correct line spacing
	 * @param tag     An integer representing the address tag
	 * @param index   An integer representing the address index
	 * @param offset  An integer representing the address offset
	 * @param type    A character (R or W) representing if the access is a read or write. Will default to read for unexpected input
	 * @return        A string formatted for printing a single line in the table
	 */
	public String nextAddress(String address, int tag, int index, int offset, char type) {
		counter += 1;
		int setIndex = -1;//tracks which set in the sets array is going to be modified
		for (int i = 0; i < sets.length; i++) {
			if(sets[i].index == index)
				setIndex = i;
		}
		if (setIndex == -1) {//if there was no set found with a matching index
			for (int i = 0; i < sets.length; i++) {//find out which set is older
				if (sets[i].counter < sets[setIndex].counter) {
					setIndex = i;
				}
			}
		}
		sets[setIndex].setCounter(counter);
		this.memrefs = sets[setIndex].nextAdd(tag, offset, type);
		String access = "  read";
		if (type == 'W')
			access = " write";
		String missOrHit = "   MISS";
		if (this.memrefs > 0)
			missOrHit = "    HIT";
		String result = access + " " + address + "   " + intToString(tag) + " " + intToString(index) + "  " + 
			intToString(offset) + missOrHit + "   " + intToString(this.memrefs);
		//return result;
		return "https://www.youtube.com/watch?v=0RpdPzJgaBw";
	}
	
	public String intToString(int value) {
		String result = String.valueOf(value);
		for (int i = 0; i < (5-result.length()); i++) {
			result = " " + result;
		}
		return result;
	}
	
	
}