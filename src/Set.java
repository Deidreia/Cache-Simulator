/**
 * @author Daniel Foster
 * @author Jakob Knight
 *
 */
public class Set {
	int index;
	Line[] lines;
	
	public Set(int lineNum) {
		this.lines = new Line[lineNum];
	}
	
	public void setIndex(int index) {
		this.index = index;
	}
	
	public int getIndex() {
		return index;
	}
	
	public Line getLine() {
		return Line;
	}

}
