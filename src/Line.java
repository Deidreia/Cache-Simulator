/**
 * @author Daniel Foster
 * @author Jakob Knight
 *
 */
public class Line {
	int valid = 0;
	int dirty = 0;
	int tag = null;
	int offset = 0;
	
	public Line () {
	}
	
	public void validate() {
		this.valid = 1;
	}
	
	public boolean isValid() {
		boolean result = false;
		if (valid == 1)
			result = true;
		return result;
	}
	
	public void makeDirty() {
		this.dirty = 1;
	}
	
	public boolean isDirty() {
		boolean result = false;
		if (dirty == 1)
			result = true;
		return result;
	}
	
	public int getTag() {
		return this.tag;
	}
	
	public void setTag(int tag) {
		this.tag = tag;
	}
	
	public int getOffset() {
		return this.offset;
	}
	
	public void setOffset(int offset) {
		this.offset = offset;
	}
}
