/**
 * 
 */

/**
 * @author Daniel Foster
 * @author Jakob Knight
 *
 */
public class Cache {
	int lineSize;
	Set[] sets;
	
	public Cache(int numSets, int setSize, int lineSize) {
		sets = new Set[numSets];
		for (int i = 0; i < numSets; i++) {
			sets[i] = new Set(setSize);
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
	
	
	
	
	public void nextAddress(int tag, int index, int offset, char type) {
	
	
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}