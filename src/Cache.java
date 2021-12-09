

/**
 * @author Daniel Foster
 * @author Jakob Knight
 *
 * Simulates how a cache would work
 */
public class Cache {
	
	/**
	 * Number of sets specified in each file
	 */
	int numSets;
	/**
	 * Set size specified in each file
	 */
	int setSize;
	/**
	 * @return the numSets
	 */
	/**
	 * size of the line
	 */
	int lineSize;
	/**
	 * counter tracking how old each value is for the purpose of replacing old values for new ones
	 */
	int counter = 0;
	/**
	 * an array of CSets with a size depending on the constructor's numSets parameter
	 */
	CSet[] sets;
	/**
	 * value tracking memory references. This is useful because it tracks
	 * not only memory references, but can indicate a hit or miss
	 */
	int memrefs = 0;
	
	/**
	 * Constructor for Cache
	 * @param numSets  number of sets in the cache
	 * @param setSize  number of lines per set
	 * @param lineSize the size of the line
	 */
	public Cache(int numSets, int setSize, int lineSize) {
		this.sets = new CSet[numSets];
		for (int i = 0; i < numSets; i++) {
			sets[i] = new CSet(setSize);
		}
		this.numSets = numSets;
		this.setSize = setSize;
		this.lineSize = lineSize;
	}

	/**
	 * lineSize getter
	 * @return the lineSize
	 */
	public int getLineSize() {
		return lineSize;
	}

	/**
	 * lineSize setter
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
		int setIndex = -1;//tracks which set in the sets array is going to be modified. -1 indicates no matching index
		for (int i = 0; i < sets.length; i++) {
			if(sets[i].index == index)
				setIndex = i;//overwrite setIndex's -1 value to indicate a match was found at index i
		}
		if (setIndex == -1) {//if there was no set found with a matching index
			setIndex = 0;//start by assuming the set at index 0 is going to be replaced
			for (int i = 0; i < sets.length; i++) {//find out which set is older
				if (sets[i].counter < sets[setIndex].counter) {
					setIndex = i;//at this point, set index will be holding the index of the set that will be modified
				}
			}
		}
		sets[setIndex].setCounter(counter);//update the counter so that the age of this set can be tracked
		this.memrefs = sets[setIndex].nextAdd(tag, offset, type);//pass values to the set so that it can update line
		String access = "  read";//calculate read or write
		if (type == 'W')
			access = " write";
		String missOrHit = "   MISS";//calculate hit or miss
		if (this.memrefs == 0)
			missOrHit = "    HIT";
		String result = access + " " + address + "   " + intToString(tag) + " " + intToString(index) + "  " + 
			intToString(offset) + missOrHit + "   " + intToString(this.memrefs);
		return result;
		//return "This is a drill";
	}
	
	/**
	 * Takes in an integer and turns it into a 5 character string
	 * @param  value the integer to be converted
	 * @return a 5 character string with spaces first, then the given integer
	 */
	public String intToString(int value) {
		String result = String.valueOf(value);
		for (int i = 0; i < (5-result.length()); i++) {
			result = " " + result;
		}
		return result;
	}
	
	/**
	 * @return numSets
	 */
	public int getNumSets() {
		return numSets;
	}

	/**
	 * @return the setSize
	 */
	public int getSetSize() {
		return setSize;
	}
	
	
}