
import java.util.Scanner;

public class driver {
    public static void main(String args[]) {
	Scanner sc = new Scanner(System.in);
	System.out.println("Would you like to send or receive a file?");
	boolean sendOrReceive = false;
	boolean asciiArmoring = false;
	while (!sendOrReceive) {
	    String input = sc.next().toUpperCase();
	    if (input.charAt(0) == 'S' || input.contains("SEND")) {
		sendOrReceive = true;
		//the parameters will need to be changed later
		sender send = new sender("localhost", 1888, "test.txt");
	    } else if (input.charAt(0) == 'R' || input.contains("RECEIVE")) {
		sendOrReceive = true;
		System.out.println("Do you require ASCII Armoring?");
		input = sc.next().toUpperCase();
		if (input.charAt(0) == 'Y' || input.contains("YES"))
		    asciiArmoring = true;
		//port number should be the same as the sender
		receiver receive = new receiver(1888, asciiArmoring);
	    } else {
	    System.out.println("Unclear input, please specify if you wish to send or receive a file.");
	    }
	}
    }
}
