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
	 * @param scanner - Scanner 
	 * @throws FileNotFoundException 
	 * @throws IOException 
	 */
	Cache readFile(String finalState) throws FileNotFoundException {
		//TODO uncomment before turning in
		//System.out.print("Please enter a file path: ");
		//String file = scanner.nextLine();
		
		//TODO remove before turning in
		String file = "./input/trace.dt.txt";
		
		
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
		
		//Checks to make sure line size is a power of 2, and exits if it is not
		if(isPowerOfTwo(newLineSize) == false) {
			//We can hardcode line 3 in because Dr. Kreahling said we could assume the first 3
			//lines would always be the same.
			System.out.println("Error on line 3: line size is not a power of 2");
			System.exit(0);
		}
		
		//Using information to create a cache object
		Cache cache = new Cache(newNumSets, newSetSize, newLineSize);
		
		//Printing out the table
		printTable1(cache);
		
		//Processing the rest of the the data in the file.
		while(reader.hasNextLine()) {
			String currLine = reader.nextLine();
			getValues(currLine, cache, finalState);
		}
		
		printTable2(cache, finalState);
		
		reader.close();
		return cache;
	}
	
	
	static boolean isPowerOfTwo(int n) {
	    if(n==0) {
	    return false;
	    }
	    
	return (int)(Math.ceil((Math.log(n) / Math.log(2)))) == (int)(Math.floor(((Math.log(n) / Math.log(2)))));
	}
	 
	
	
	
	void getValues(String data, Cache cache, String finalState) {
		//Memory address of the data
		String adr = lineSplitter(data, 1);
		//What time of access the current line is
		String accessType = lineSplitter(data, 2);
		//Turning accessType into a char
		char accessChar = accessType.charAt(0);
		//Size in bytes to either read or write
		String accessSize = lineSplitter(data, 3);
		
		//Converting the address into binary from hex
		int tmpNum = (Integer.parseInt(adr, 16));
		String adrToBin = (Integer.toBinaryString(tmpNum));;
		
		//Used to get leading 0s back, so we have 4(adress.length) bits
		String adrLen = "%" + String.valueOf(adr.length() * 4) + "s";
		adrToBin = String.format(adrLen, adrToBin).replace(" ", "0");
		

		
		// Number of bytes to get to whatever line size is
		int offsetSize = calcOffset(cache);
		// Number of by to get to whatever set size is
		int indexSize = (int) (Math.log(cache.getNumSets()) / Math.log(2));
		// Number of bytes left after offset and index
		int tagSize = (adr.length() * 4 - offsetSize - indexSize);
		
		//Grabs the bits that make up the tag
		String tagBin = adrToBin.substring(0, tagSize);
		
		//If tag size was zero, it would cause a crash
		if(tagSize == 0) {
			for(int i = 0; i < adrToBin.length(); i++) {
				tagBin += "0";
			}
		}
		
		//Convert tag to decimal
		int tag = Integer.parseInt(tagBin, 2);
		
		//Grabs the bits that make up the index
		String indexBin = adrToBin.substring(tagSize, tagSize + indexSize);
		//Convert index to decimal
		int index = Integer.parseInt(indexBin, 2);
		
		//Grabs the bits that make up the offset
		String offsetBin = adrToBin.substring(adrToBin.length() - offsetSize);
		//Convert offset to decimal
		int offset = Integer.parseInt(offsetBin, 2);
		
		
		System.out.println(cache.nextAddress(adr, tag, index, offset, accessChar));
		
	}
	
	
	public int calcOffset(Cache cache) {
		
		// Number of bytes to get to whatever line size is
		String offsetSize = Integer.toBinaryString(cache.getLineSize());
		// Converting line number into binary
		offsetSize = String.format("%4s", offsetSize).replace(" ", "0");
				
		//Removing 1 from answer above, so that we have the number of bytes it takes to get to
		//line size, not including line size
		//ex 8  = 1000, to represent 8, we need 3 bits because 000 - 111 = 0 - 7, which is 8 spaces
		int finalOffsetSize = offsetSize.length() -1;
		
		return finalOffsetSize;
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
	
	

	void finalCacheState() {
		System.out.println("\n\n   Final Data Cache State");
	}
	
	//void finalOutput(Cache cache, String finalState) {
	public void printTable1(Cache cache) {
		//Printing Cache Configuration
		System.out.println("Cache Configuration");
		System.out.println("\n\t" + cache.getSetSize() + "-way set associative entries");
		System.out.println("\t" + cache.getNumSets() + " sets total");
		System.out.println("\t" + cache.getLineSize() / 4 + " words per set");
		
		System.out.println("\nResults for Each Reference");
		
		//Printing Columns
		System.out.println("\n\nAccess Address    Tag   Index Offset Result Memrefs");
		System.out.println("------ -------- ------- ----- ------ ------ -------");
		
		//TEMP line to see spacings
		//System.out.println("  read" + "       58" + "       5" + "     1" + "      0" + "   MISS" + "       1");
		System.out.println("|----|" + " |------|" + " |-----|" + " |---|" + " |----|" + " |----|" + " |-----|");
	}
	
	public void printTable2(Cache cache, String finalState) {
		System.out.println("\n\nSimulation Summary Statistics");
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
		Cache cache = null;
		
		try {
			cache = readFile(finalState);
		} 
		catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		scanner.close();
	}
	
	
}
