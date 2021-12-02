/**
 * 
 */

/**
 * @author Daniel Foster
 * @author Jakob Knight
 *
 */
public class Cache {
	int numSets;
	int setSize;
	int lineSize;
	
	public Cache(int numSets, int setSize, int lineSize) {
		this.numSets = numSets;
		this.setSize = setSize;
		this.lineSize = lineSize;
	}

	/**
	 * @return the numSets
	 */
	public int getNumSets() {
		return numSets;
	}

	/**
	 * @param numSets the numSets to set
	 */
	public void setNumSets(int numSets) {
		this.numSets = numSets;
	}

	/**
	 * @return the setSize
	 */
	public int getSetSize() {
		return setSize;
	}

	/**
	 * @param setSize the setSize to set
	 */
	public void setSetSize(int setSize) {
		this.setSize = setSize;
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
}
