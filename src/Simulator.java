import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * @author Daniel Foster
 * @author Jakob Knight
 *
 */
public class Simulator {
	
	
	/**
	 * Reads the file and splits it into necessary data
	 * @param scanner - Scanner 
	 * @throws FileNotFoundException - stops the program if a file is not found
	 * @throws IOException - stops the program if there is an input/output error
	 */
	Cache readFile(String finalState) throws FileNotFoundException {
		//TODO uncomment before turning in
		//System.out.print("Please enter a file path: ");
		//String file = scanner.nextLine();
		
		//TODO remove before turning in
		String file = "./input/trace.dt.txt";
		
		ArrayList<ArrayList<String>> tmpArr = new ArrayList<ArrayList<String>>();
		
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
			tmpArr.add(getValues(currLine, cache, finalState));
		}
		//prints the second half of the table
		printTable2(cache, finalState);
		
		simulate(cache, tmpArr);
		
		
		//if f was set as f or F when the program was initially ran, run finalCacheState()
		if(finalState.equals("f") || finalState.equals("F")) {
			finalCacheState(cache, tmpArr);
		}
		
		reader.close();
		return cache;
	}
	
	
	static boolean isPowerOfTwo(int n) {
	    if(n==0) {
	    return false;
	    }
	    
	return (int)(Math.ceil((Math.log(n) / Math.log(2)))) == (int)(Math.floor(((Math.log(n) / Math.log(2)))));
	}
	 
	/**
	 * @param arr -  Array consisting of [cache line][address, address in binary, accesstype, access size, tag, tag in binary, index, index in binary, offset, and offset in binary]

	 */
	public void simulate(Cache cache, ArrayList<ArrayList<String>> arr) {
		ArrayList<String> outputArr = new ArrayList<String>(arr.size());
		for(int i = 0; i < arr.size(); i++) {
			String currAdr =(arr.get(i).get(0));
			int currTag = Integer.parseInt(arr.get(i).get(4));
			int currIndex = Integer.parseInt(arr.get(i).get(6));
			int currOff = Integer.parseInt(arr.get(i).get(8));
			int currAccSize = Integer.parseInt(arr.get(i).get(3));
			
			
			
			System.out.println(currAdr + " " + currTag + " " + currIndex + " " + currOff + " " + currAccSize);
			//outputArr.add(currTag, "byte addresses " + currAdr);
			outputArr.add("byte addresses " + currAdr + "-" + (currAdr + currOff) + ", tag " + currTag);
		}
		System.out.println(outputArr.get(0));
	}
	
	
	/**
	 * @param data - address, accessType, and accessSize squeezed together, and separated by ":"
	 * @param cache - cache to perform operations on
	 * @param finalState - whether or not to print out final cache state data
	 * @return - Array consisting of address, access type, and access size
	 */
	ArrayList<String> getValues(String data, Cache cache, String finalState) {
		
		//Array consisting of address, accesstype, accesstype, tag, index, and offset
		ArrayList<String> dataArr = new ArrayList<String>();
		
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
		
		dataArr.add(adr);
		dataArr.add(adrToBin);
		dataArr.add(accessType);
		dataArr.add(accessSize);
		dataArr.add(String.valueOf(tag));
		dataArr.add(tagBin);
		dataArr.add(String.valueOf(index));
		dataArr.add(indexBin);
		dataArr.add(String.valueOf(offset));
		dataArr.add(offsetBin);
		//[address, address in binary, accesstype, access size, tag, tag in binary, index, index in binary, offset, and offset in binary]
		return dataArr;
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
	
	/**
	 * @param line - line of data you want to split, separated by a ":"
	 * @param index - how many : to skip until you start recording data
	 * @return returns a string of data between two ":"
	 */
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
	 * @param cache - Printing the first part of the cache, and it's associated data
	 */
	public void printTable1(Cache cache) {
		//Printing Cache Configuration
		System.out.println("Cache Configuration");
		
		System.out.println("\n\t" + cache.getSetSize() + "-way set associative entries");
		System.out.println("\t" + cache.getNumSets() + " sets total");
		System.out.println("\t" + cache.getLineSize() / 4 + " words per set");
		
		//Check if the cache is direct mapped or associative
			if(cache.getSetSize() == 1) {
				System.out.println("\n\tDIRECT MAPPED CACHE");
			}
		
		System.out.println("\nResults for Each Reference");
		
		//Printing Columns
		System.out.println("\n\nAccess Address    Tag   Index Offset Result Memrefs");
		System.out.println("------ -------- ------- ----- ------ ------ -------");
		
	}
	
	/**
	 * @param cache - Printing the second part of the cache, and it's associated data
	 * @param finalState - whether or not to print out final cache state data
	 */
	public void printTable2(Cache cache, String finalState) {
		System.out.println("\n\nSimulation Summary Statistics");
		System.out.println("-----------------------------");
		System.out.println("Total hits                 : " + cache.getHits());
		System.out.println("Total misses               : " + (cache.getReferences() - cache.getHits()));
		System.out.println("Total accesses             : " + cache.getReferences());
		System.out.println("Total memory references    : " + cache.getTotalMemrefs());
		double hitter = cache.getHits();
		double refffs = cache.getReferences();//It's too close to due time to make these names make sense
		double hRatio = (hitter / refffs);
		System.out.println("Hit ratio                  : " + hRatio);
		double mRatio = 1 - hRatio;
		System.out.println("Miss ratio                 : " + mRatio);
		
	}
	
	/**
	 * @param cache - Cache to print the final state of
	 * @param arr - Array consisting of [cache line] [address, accesstype, accesstype, tag, index, and offset]
	 */
	void finalCacheState(Cache cache, ArrayList<ArrayList<String>> arr) {
		System.out.println("\n\n   Final Data Cache State");
		System.out.println("-----------------------------");
		for(int i = 0; i < cache.getNumSets(); i++) {
			System.out.println("set " + i);
			for(int j = 0; j < cache.getSetSize(); j++) {
				System.out.print("   line " + j + " = ");
				if(arr.get(i).get(1).equals("R")) {
					System.out.println("byte address " + arr.get(4).get(0) + "-" + 
							(Integer.parseInt(arr.get(4).get(0)) + Integer.parseInt(arr.get(4).get(2))) + ", ");
				}
			}
		}
	}
	
	/**
	 * @param finalState - whether or not to print out final cache state data
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
