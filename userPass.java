
import java.util.*;
import java.io.*;

public class userPass{

	public static void main(String args[]) throws IOException
	{
		InputStreamReader reader = new InputStreamReader(System.in);
    		BufferedReader br = new BufferedReader(reader);
    
		System.out.print("username: ");
		String username = br.readLine();
		System.out.print("password : ");
		String password = br.readLine();

		FileInputStream input= new FileInputStream("pass.txt");
		BufferedReader read = new BufferedReader(new InputStreamReader(input));
		
		Map<String, String> map = new HashMap<String,String>();
		Map<String, String> map2 = new HashMap<String,String>();
		map2.put(username, password);
		System.out.println(map2);
		String line;

		while((line = read.readLine()) != null)
		{
			String[] split = line.split(":");
			map.put(split[0], split[1]);
			String correct = map.get(username); //checks if password is correct
			if (correct != null && correct.equals(password))
				System.out.println("True");
			else
				System.out.println("False");
		}
	}
}
