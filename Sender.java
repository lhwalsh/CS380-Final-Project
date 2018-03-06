import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Sender {

    private Socket s;
    private ServerSocket inSock;
    private String host;
    private int port;
    private String file;
    public Sender(String host, int port, String file,int tryCount) {
        this.host = host;
        this.port = port;
        this.file = file;
        try {
            s = new Socket(host, port);
            sendFile(file,tryCount);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void sendFile(String file,int tryCount) throws IOException , ClassNotFoundException{
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
	    System.out.println(bufferHash.length + " " + buffer.length + " " +  chunk.length);
            encryptionXor.encrypt(chunk,getKey());
	    System.out.println(chunk.length);
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
    private String getKey() throws FileNotFoundException {
	    Scanner sc = new Scanner( new File("key.txt"));
	    String answer = "";
	    while (sc.hasNext()) {
	        answer = answer + sc.next();
    	}
	    return answer;
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
        Sender fc = new Sender("localhost", 1988, "test.txt",3);
    }

}
