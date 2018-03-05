import java.util.Scanner;

public class driver {
    public static void main(String args[]) {
	// user input on whether to use sender or receiver class
	Scanner sc = new Scanner(System.in);
	System.out.println("Would you like to send or receive a file?");

	//is set to true when the user has specified if they would like to send or receive
	boolean sendOrReceive = false;

	//boolean to tell the sender class whether they need to use ASCII Armoring
	boolean asciiArmoring = false;

	//will continue to loop until it is clear if the user wants to send, receive, or quit
	while (!sendOrReceive) {
	    //sets the input to all upper case to prevent capitalization confusion
	    String input = sc.next().toUpperCase();
	    //decides the user is sending if they begin the sentence with s or use the word send
	    if (input.charAt(0) == 'S' || input.contains("SEND")) {
			//used to quit out of the loop
			sendOrReceive = true;
			//determining if ASCII Armoring is required
			System.out.println("Do you require ASCII Armoring?");
			input = sc.next().toUpperCase();
			//will decide ASCII Armoring is necessary if the user's sentence begins with y or contains yes
			if (input.charAt(0) == 'Y' || input.contains("YES"))
			    asciiArmoring = true;
				//the parameters will need to be changed later
				//creates the sender object which will automatically send if a receiver is found, or quit otherwise
				Sender send = new Sender("localhost", 4444, "test.txt", 3);
			    //decides the user is receiving if they begin the sentene with r or use the word receive
	    } else if (input.charAt(0) == 'R' || input.contains("RECEIVE")) {
			//used to quit out of the loop
			sendOrReceive = true;
			//creates receiver object
			//port number should be the same as the sender
			receiver receive = new receiver(4444, asciiArmoring);
			//starts the receiver object to look for a file to receive, if nothing is found it will wait
			receive.start();
		    //if the user says they want to quit or exit it will exit the loop and program
	    } else if (input.contains("EXIT") || input.contains("QUIT")) {
			//used to quit out of the loop
			sendOrReceive = true;
		    //if another input is used and it doesn't match any of the above, it is thrown away and a new input is obtained
	    } else {
	    	System.out.println("Unclear input, please specify if you wish to send or receive a file.");
	    }
	}
    }
}
