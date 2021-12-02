import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

/**
 * @author Daniel Foster
 * @author Jakob Knight
 *
 */
public class Simulator {
	
	
	/**
	 * @param scanner
	 */
	void readFile(Scanner scanner) {
		//File input = new File();
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
		
		System.out.println("Look here: " + finalState);
		
		if(finalState.equals("f") || finalState.equals("F")) {
			finalCacheState();
		}
	}
	
	
}
