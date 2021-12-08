/**
 * @author Daniel Foster
 * @author Jakob Knight
 *
 */
public class CSet {
	int index;
	Line[] lines;
	int counter = 0;
	
	public CSet(int lineNum) {
		this.lines = new Line[lineNum];
	}
	
	public void setIndex(int index) {
		this.index = index;
	}
	
	public int getIndex() {
		return index;
	}
	
	public Line getLine(int fromIndex) {
		return lines[fromIndex];
	}

	public void setCounter(int count) {
		this.counter = count;
	}
	
	public int nextAdd(int tag, int offset, char type) {
		int lineIndex = -1;//tracks which line in the lines array is going to be modified
		for (int i = 0; i < lines.length; i++) {
			if(lines[i] != null && lines[i].tag == tag)
				lineIndex = i;
		}
		if (lineIndex == -1) {//if there was no set found with a matching tag
			lineIndex = 0;
			if (lines[lineIndex] != null) {
				for (int i = 0; i < lines.length; i++) {//find out which line is older
					if (lines[i] == null)
						lineIndex = i;
					if (lines[lineIndex] != null && lines[i].counter < lines[lineIndex].counter) {
						lineIndex = i;
					}
				}
			}
		}
		int result = 0;
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
