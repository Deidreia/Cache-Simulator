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
	 * @throws FileNotFoundException 
	 * @throws IOException 
	 */
	Cache readFile(Scanner scanner) throws FileNotFoundException {
		//TODO uncomment before turning in
		//System.out.print("Please enter a file path: ");
		//String file = scanner.nextLine();
		
		//TODO remove before turning in
		String file = "./input/assoc.dt.txt";
		
		
		File input = new File(file);
		Scanner reader = new Scanner(input);
		
		//Grabbing cache information from file
		String numSets = reader.nextLine();
		String setSize = reader.nextLine();
		String lineSize = reader.nextLine();
		
		//Scraping unneeded information
		int newNumSets = Integer.parseInt(numSets.replaceAll("[^0-9]", ""));
		int newSetSize = Integer.parseInt(setSize.replaceAll("[^0-9]", ""));
		int newLineSize = Integer.parseInt(lineSize.replaceAll("[^0-9]", ""));
		
		//Using information to create a cache object
		Cache cache = new Cache(newNumSets, newSetSize, newLineSize);
		
		//Processing the rest of the the data in the file.
		while(reader.hasNextLine()) {
			String currLine = reader.nextLine();
			simulate(currLine);
		}
		
		reader.close();
		return cache;
	}
	
	void simulate(String data) {
		//First part of data
		String adr = lineSplitter(data, 1);
		//Second part of data
		String access = lineSplitter(data, 2);
		//Third part of data (offset maybe? need to check again to make sure)
		String offset = lineSplitter(data, 3);
		

		

	}
	
	
	public static String lineSplitter(String line, int index) {
		int cnt = 0;
		String tmp = "";
		
		
		for(int i = 0; i < line.length(); i++) {
			
			//Counts number of letters after cnt is 1 less than index, in order to start recording the word at the
			//nth index
			if (cnt == (index - 1)) {
				if(line.charAt(i) != ':') {
					tmp = tmp + line.charAt(i);
				}
			}
			
			//checks how many spaces have been looped past
			if (line.charAt(i) == ':') {
				cnt++;
			}
			
			//Used to remove last :
			tmp = tmp.trim();
		}
		return tmp;
	}
	
	
	/**
	 * 
	 */
	void finalCacheState() {
		System.out.println("Testing to make sure we get to finalCacheState");
	}
	
	void finalOutput(String finalState) {
		System.out.println("Access Address    Tag   Index Offset Result Memrefs");
		System.out.println("------ -------- ------- ----- ------ ------ -------");
		
		
		//TODO Add read address, tag, index, offset, result, mem refs here
		
		
		System.out.println("Simulation Summary Statistics");
		System.out.println("-----------------------------");
		System.out.println("Total hits                 :");
		System.out.println("Total misses               :");
		System.out.println("Total accesses             :");
		System.out.println("Total memory references    :");
		System.out.println("Hit ratio                  :");
		System.out.println("Miss ratio                 :");
		
		//if f was set as f or F when the program was initially ran, run finalCacheState()
		if(finalState.equals("f") || finalState.equals("F")) {
			finalCacheState();
		}
		
	}
	
	/**
	 * @param finalState
	 */
	void go(String finalState){
		Scanner scanner = new Scanner(System.in);
		
		try {
			readFile(scanner);
		} 
		catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		
		
		
		scanner.close();
		finalOutput(finalState);
	}
	
	
}
