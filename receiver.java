import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.io.ObjectOutputStream;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Base64;

public class receiver extends Thread {

    private ServerSocket ss;
    private Socket clientSock;
    private boolean asciiArmoring;
    public receiver(int port) {
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
                clientSock = ss.accept();
		        if(!verify(clientSock))
                {
                    System.exit(1);
                }
                saveFile(clientSock);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public boolean verify(Socket clientSock) throws FileNotFoundException, IOException {
	Scanner sc = new Scanner(new File("userpass.txt"));
	DataOutputStream dos = new DataOutputStream(clientSock.getOutputStream());
	DataInputStream dis = new DataInputStream(clientSock.getInputStream());
	String attempt = dis.readUTF();
	if (attempt.equals(sc.next())) {
	    dos.writeByte(1);
	} else {
	    dos.writeByte(0);
	    sc.close();
	    dos.close();
	    dis.close();
	    System.out.println("Incorrect Username.");
	    return false;
	}
	attempt = dis.readUTF();
	if (attempt.equals(sc.next())) {
	    dos.writeByte(1);
	} else {
	    dos.writeByte(0);
	    sc.close();
	    dos.close();
	    dis.close();
	    System.out.println("Incorrect Password.");
	    return false;
	}
	if (dis.readByte() == 1) {
	    asciiArmoring = true;
	} else {
	    asciiArmoring = false;
	}
	sc.close();
	dos.close();
	dis.close();
	return true;
    }


    public byte[] unpack(byte[] received) throws FileNotFoundException , IOException
    {
        byte[] temp = received;
        byte[] hash = new byte[16];
        byte[] data = new byte[4096];
        temp = encryptionXor.encrypt(temp, getKey());
        int i = 0;
        for(;i<data.length;i++)
        {
            data[i] = temp[i];
        }
        int tempCounter = 0;
        for(;i<temp.length;i++)
        {
            hash[tempCounter] = temp[i];
            tempCounter++;
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

    private void saveFile(Socket clientSock) throws IOException {
        DataInputStream dis = new DataInputStream(clientSock.getInputStream());
        FileOutputStream fos = new FileOutputStream("testfile.txt");
        byte[] buffer = new byte[4112];
	    int read = 0;
	    int totalRead = 0;
        while((read = dis.read(buffer, 0, buffer.length)) > 0) {
	        totalRead += read;
		if (asciiArmoring) {
		    buffer = Base64.getDecoder().decode(buffer);
		}
	        byte[] newBuffer = unpack(buffer);
	        System.out.println("read " + totalRead + " bytes.");
            fos.write(newBuffer, 0, read-16);
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
        receiver fs = new receiver(1988);
        fs.start();
    }

}
