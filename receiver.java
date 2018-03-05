import java.io.DataInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
import java.io.DataOutputStream;
import java.io.File;

public class receiver extends Thread {

    private ServerSocket ss;

    public receiver(int port, boolean asciiArmoring) {
        try {
            ss = new ServerSocket(port);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void run() {
	System.out.println("Looking for connection...");
        while (true) {
            try {
                Socket clientSock = ss.accept();
		if (verify(clientSock))
		    saveFile(clientSock);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private boolean verify(Socket clientSock) throws IOException {
	DataInputStream dis = new DataInputStream(clientSock.getInputStream());
	String attempt = dis.readUTF();
	Scanner sc = new Scanner(new File("info.txt"));
	String actual = sc.next();
	DataOutputStream dos = new DataOutputStream(clientSock.getOutputStream());
	if (attempt.equals(actual)) {
	    dos.writeByte(0);
	    dis.close();
	    dos.close();
	    return false;
	} else {
	    dos.writeByte(1);
	}
	attempt = dis.readUTF();
	actual = sc.next();
	if (attempt.equals(actual)) {
	    dos.writeByte(0);
	    dis.close();
	    dos.close();
	    return false;
	}
	return true;
    }

    private void saveFile(Socket clientSock) throws IOException {
        DataInputStream dis = new DataInputStream(clientSock.getInputStream());
        FileOutputStream fos = new FileOutputStream("testfile.txt");
        byte[] buffer = new byte[4096];
	int read = 0;
	int totalRead = 0;
        while((read = dis.read(buffer, 0, buffer.length)) > 0) {
	    totalRead += read;
	    System.out.println("read " + totalRead + " bytes.");
            fos.write(buffer, 0, read);
        }

        fos.close();
        dis.close();
    }
    private void sendConfirmation()
    {

    }
    //this is only to test the receiver class without the driver class
    public static void main(String[] args) {
        receiver fs = new receiver(1988, false);
        fs.start();
    }

}
