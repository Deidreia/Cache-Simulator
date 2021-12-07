

import java.util.Set;

/**
 * @author Daniel Foster
 * @author Jakob Knight
 *
 */
public class Cache {
	int lineSize;
	int counter = 0;
	CSet[] sets;
	
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
	
	
	
	
	public boolean nextAddress(int tag, int index, int offset, char type) {
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
		CSet current = sets[setIndex];
		return current.nextAdd(tag, offset, type);
	}
	
	
}