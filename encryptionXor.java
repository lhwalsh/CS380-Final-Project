public class encryptionXor { 
    private static int[] encrypt(String str, String key) {
        int[] output = new int[str.length()];
        for(int i = 0; i < str.length(); i++) {
            int o = (Integer.valueOf(str.charAt(i)) ^ Integer.valueOf(key.charAt(i % (key.length() - 1)))) + '0';
            output[i] = o;
        }
        return output;        
    }

    private static String decrypt(int[] input, String key) {
        String output = "";        
        for(int i = 0; i < input.length; i++) {
            output += (char) ((input[i] - 48) ^ (int) key.charAt(i % (key.length() - 1)));
        }
        return output;
    }
    
    public static void main(String args[]) { // test     
   	 String value = "SampleStringToBeEncrypted";
       String keyval = "thisIsAKey";
       
       int[] encrypted = encrypt(value,keyval);
       for(int i = 0; i < encrypted.length; i++)
           System.out.print((char)encrypted[i]);
       System.out.println("");
       System.out.println(decrypt(encrypted,keyval));        
   }


	
}