/**
 * @author Daniel Foster
 * @author Jakob Knight
 *
 */
public class Set {
	int index;
	Line line;
	
	public Set(int index, int tag, int offset) {
		this.index = index;
		this.lint = new Line(tag, offset);
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
