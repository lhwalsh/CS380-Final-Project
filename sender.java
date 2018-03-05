import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class sender {

    private Socket s;
    private ServerSocket ss;
    private String host;
    private int port;
    private String file;

    public sender(String host, int port, String file,int tryCount) {
        this.host = host;
        this.port = port;
        this.file = file;
        try {
	    System.out.println("Inside try statement");
            ss = new ServerSocket(port);
	    System.out.println("ServerSocket accepted");
	    s = ss.accept();
	    System.out.println("Socket accepted");
	    if (verify())
		sendFile(file,tryCount);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

<<<<<<< HEAD
    public void sendFile(String file,int tryCount) throws IOException , ClassNotFoundException{
=======
    public boolean verify() throws IOException {
	Scanner sc = new Scanner(System.in);
	System.out.println("Please enter the username.");
	String username = sc.next();
	System.out.println("Please enter the password.");
	String password = sc.next();
	DataOutputStream dos = new DataOutputStream(s.getOutputStream());
	dos.writeUTF(username);
	DataInputStream dis = new DataInputStream(s.getInputStream());
	byte answer = dis.readByte();
	if (answer == 0) {
	    System.out.println("Incorrect Username.");
	    dos.close();
	    dis.close();
	    return false;
	}
	dos.writeUTF(password);
	answer = dis.readByte();
	if (answer == 0) {
	    System.out.println("Incorrect Password.");
	    dos.close();
	    dis.close();
	    return false;
	}
	return true;
    }

    public void sendFile(String file,int tryCount) throws IOException {
>>>>>>> 8e7fef00843c9d147437e3e2a3755467fc37fe77
        int remainingAttempts = tryCount;
        BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()));
        DataOutputStream dos = new DataOutputStream(s.getOutputStream());
        FileInputStream fis = new FileInputStream(file);
        byte[] buffer = new byte[4096];
        while (fis.read(buffer) > 0)
        {
            byte[] bufferHash = FileCheckSum.hash(buffer);
	    //array with both chunk and checksum
	    byte[] chunk = new byte[bufferHash.length + buffer.length];
	    System.arraycopy(bufferHash, 0, chunk, 0, bufferHash.length);
	    System.arraycopy(buffer, 0, chunk, bufferHash.length, buffer.length);
            encryptionXor.encrypt(chunk,getKey());
            do{

                dos.write(buffer);
                remainingAttempts--;
                if(remainingAttempts <= 0)
                {
                    System.out.println("Exceeded allowed send attempts. Terminating Connection");
                    fis.close();
                    dos.close();
                    s.close();
                    return;
                }
            }while(!waitForResponse());


        }
        fis.close();
        dos.close();
    }
<<<<<<< HEAD
    private String getKey() throws FileNotFoundException {
	    Scanner sc = new Scanner( new File("key.txt"));
	    String answer = "";
	    while (sc.hasNext()) {
	        answer = answer + sc.next();
    	}
	    return answer;
=======

    private String getKey() throws FileNotFoundException {
	Scanner sc = new Scanner( new File("key.txt"));
	String answer = "";
	while (sc.hasNext()) {
	    answer = answer + sc.next();
	}
	return answer;
>>>>>>> 8e7fef00843c9d147437e3e2a3755467fc37fe77
    }

    private boolean waitForResponse() throws ClassNotFoundException
    {
        try
        {
            s.shutdownOutput();
            ObjectInputStream oIn = new ObjectInputStream(s.getInputStream());
            String confirmation = (String)oIn.readObject();
            if(confirmation.equals("ok")){
                return true;
            }
            else{
                return false;
            }
        }
        catch(IOException ie)
        {
            return false;
        }

    }

    //this is only to test the sender class without the driver class
    public static void main(String[] args) {
        sender fc = new sender("localhost", 1988, "test.txt",3);
    }

}
