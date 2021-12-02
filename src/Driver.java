/**
 * 
 */

/**
 * @author Daniel Foster
 * @author Jakob Knight
 *
 */
public class Driver {

	/**
	 * @param args arguments passed in from command line interface
	 */
	public static void main(String[] args) {
		//If there are any arguments, continue
		if(args.length > 0) {
			//If the argument is not f or F, quit
			if(!args[0].equals("f") && !args[0].equals("F")) {
				System.out.println("Usage: Driver [F/f]");
				System.exit(0);
			}
			try {
				Simulator Sim = new Simulator();
				Sim.go(args[0]);	
			}
			catch (ArrayIndexOutOfBoundsException e){
				System.out.println("Usage: Driver [F/f]");
				System.exit(0);
			}
		}
		
		else {
			Simulator sim = new Simulator();
			sim.go("");
		}
	}
}
