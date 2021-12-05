import java.util.Scanner;

/**
 * @author Daniel Foster
 * @author Jakob Knight
 *
 */
public class Simulator {
	
	
	/**
	 * @param scanner
	 * @throws IOException 
	 */
	void readFile(Scanner scanner) {
		String numSets = scanner.nextLine();
		String setSize = scanner.nextLine();
		String lineSize = scanner.nextLine();
		
		int newNumSets = Integer.parseInt(numSets.replaceAll("[^0-9]", ""));
		int newSetSize = Integer.parseInt(setSize.replaceAll("[^0-9]", ""));
		int newLineSize = Integer.parseInt(lineSize.replaceAll("[^0-9]", ""));
		
		Cache cache = new Cache(newNumSets, newSetSize, newLineSize);
		
	}
	
	
	/**
	 * 
	 */
	void finalCacheState() {
		
	}
	
	/**
	 * @param finalState
	 */
	void go(String finalState){
		Scanner scanner = new Scanner(System.in);
			readFile(scanner);
		
		
		if(finalState.equals("f") || finalState.equals("F")) {
			finalCacheState();
		}
		
		scanner.close();
		
	}
	
	
}
