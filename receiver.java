import java.io.DataInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
<<<<<<< HEAD
import java.io.ObjectOutputStream;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
=======
import java.util.Scanner;
import java.io.DataOutputStream;
import java.io.File;
>>>>>>> 8e7fef00843c9d147437e3e2a3755467fc37fe77

public class receiver extends Thread {

    private ServerSocket ss;
    private Socket clientSock;
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
<<<<<<< HEAD
                clientSock = ss.accept();
                saveFile(clientSock);
=======
                Socket clientSock = ss.accept();
		if (verify(clientSock))
		    saveFile(clientSock);
>>>>>>> 8e7fef00843c9d147437e3e2a3755467fc37fe77
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    public byte[] unpack(byte[] received) throws FileNotFoundException , IOException
    {
        byte[] temp = received;
        byte[] hash = new byte[8];
        byte[] data = new byte[4096];
        temp = encryptionXor.encrypt(temp, getKey());
        int i = 0;
        for(;i<temp.length - 8;i++)
        {
            data[i] = temp[i];
        }
        for(;i<temp.length;i++)
        {
            hash[i] = temp[i];
        }
        if(FileCheckSum.compareHash(data,hash))
        {
            sendConfirmation(true);
            return data;
        }
        else
        {
            sendConfirmation(false);
            return null;
        }
    }
    private String getKey() throws FileNotFoundException {
	    Scanner sc = new Scanner( new File("key.txt"));
	    String answer = "";
	    while (sc.hasNext()) {
	        answer = answer + sc.next();
    	}
	    return answer;
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
        byte[] buffer = new byte[5004];
	    int read = 0;
	    int totalRead = 0;
        while((read = dis.read(buffer, 0, buffer.length)) > 0) {
	        totalRead += read;
	        byte[] newBuffer = unpack(buffer);
	        System.out.println("read " + totalRead + " bytes.");
            fos.write(newBuffer, 0, read-8);
        }

        fos.close();
        dis.close();
    }
    private void sendConfirmation(Boolean status) throws IOException
    {
        ObjectOutputStream oos = new ObjectOutputStream(clientSock.getOutputStream());
        oos.flush();
        if(status)
        {
            oos.writeObject("ok");
        }
        else
        {
            oos.writeObject("resend");
        }
    }

    //this is only to test the receiver class without the driver class
    public static void main(String[] args) {
        receiver fs = new receiver(1988, false);
        fs.start();
    }

}
