/**
 * @author Daniel Foster
 * @author Jakob Knight
 * Represents a line in a set in a cache
 */
public class Line {
	/**
	 * valid bit
	 */
	int valid = 0;
	/**
	 * dirty bit
	 */
	int dirty = 0;
	/**
	 * tag for this line
	 */
	int tag = 0;
	/**
	 * address offset
	 */
	int offset = 0;
	/**
	 * counter representing age of the line
	 */
	int counter = 0;
	
	/**
	 * Line object constructor
	 * @param tag    Tag for this line
	 * @param offset Address offset value
	 */
	public Line (int tag, int offset) {
		this.tag = tag;
		this.offset = offset;
		this.validate();
		//if the line is being modified, it becomes valid
		//this doesn't do much besides proof of concept
	}
	
	/**
	 * validates the valid bit 
	 */
	public void validate() {
		this.valid = 1;
	}
	
	/**
	 * checks if the line is valid
	 * @return true if this line is valid
	 */
	public boolean isValid() {
		boolean result = false;
		if (valid == 1)
			result = true;
		return result;
	}
	
	/**
	 * flips the dirty bit over from 0 to 1
	 */
	public void makeDirty() {
		this.dirty = 1;
	}
	
	/**
	 * tests value of dirty bit
	 * @return true if dirty bit = 1
	 */
	public boolean isDirty() {
		boolean result = false;
		if (dirty == 1)
			result = true;
		return result;
	}
	
	/**
	 * tag getter
	 * @return tag
	 */
	public int getTag() {
		return this.tag;
	}
	
	/**
	 * tag setter
	 * @param tag new tag for this line
	 */
	public void setTag(int tag) {
		this.tag = tag;
	}
	
	/**
	 * offset getter
	 * @return offset value
	 */
	public int getOffset() {
		return this.offset;
	}
	
	/**
	 * offset setter
	 * @param offset new offset value
	 */
	public void setOffset(int offset) {
		this.offset = offset;
	}
	
	/**
	 * Sets the counter for this line
	 * @param count integer showing age of this line
	 */
	public void setCounter(int count) {
		this.counter = count;
	}
}
