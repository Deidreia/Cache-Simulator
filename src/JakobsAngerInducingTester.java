import java.util.Scanner;

public class JakobsAngerInducingTester {

	public static void main(String[] args) {
		Cache myCash = new Cache(2, 2, 2);
		Scanner in = new Scanner(System.in);
		boolean looper = true;
		while (looper) {
			String response = in.nextLine();
			if (response.equals("stop"))
				looper = false;
			else {
				/*
				int tag = in.nextInt();
				int index = in.nextInt();
				int offset = in.nextInt();
				in.nextLine();
				String typeS = in.nextLine();
				char type = typeS.charAt(0);
				type = Character.toUpperCase(type);
				System.out.println(myCash.nextAddress("        ", tag, index, offset, type));
				*/
				
				
				System.out.println(myCash.nextAddress("        ", 5, 1, 0, 'R'));
				System.out.println(myCash.nextAddress("        ", 6, 1, 0, 'R'));
				System.out.println(myCash.nextAddress("        ", 5, 1, 0, 'R'));
				System.out.println(myCash.nextAddress("        ", 6, 1, 0, 'R'));
				System.out.println(myCash.nextAddress("        ", 4, 0, 0, 'R'));
				System.out.println(myCash.nextAddress("        ", 0, 1, 4, 'R'));
				System.out.println(myCash.nextAddress("        ", 4, 0, 0, 'R'));
				System.out.println(myCash.nextAddress("        ", 4, 1, 0, 'R'));
				
				
				/*
				System.out.println(myCash.nextAddress("        ", 0, 18, 12, 'W'));
				System.out.println(myCash.nextAddress("        ", 0, 19, 0, 'R'));
				System.out.println(myCash.nextAddress("        ", 4, 19, 4, 'R'));
				System.out.println(myCash.nextAddress("        ", 4, 19, 8, 'W'));
				System.out.println(myCash.nextAddress("        ", 8, 19, 0, 'W'));
				System.out.println(myCash.nextAddress("        ", 8, 19, 4, 'R'));
				System.out.println(myCash.nextAddress("        ", 0, 19, 0, 'R'));
				*/
				
			}
			
			
		}

	}

}
