public class encryptionXor { 
    private static byte[] encrypt(byte[] buffer, String key) {
   	 byte[] keyBytes = key.getBytes();
       byte[] output = new byte[buffer.length];
       for(int i = 0; i < buffer.length; i++) {
           byte o = (byte) (buffer[i] ^ keyBytes[i % keyBytes.length]);
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
    
    public static byte[] hexStringToByteArray(String s) {
       int len = s.length();
       byte[] data = new byte[len / 2];
       for (int i = 0; i < len; i += 2) {
           data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4)
                                + Character.digit(s.charAt(i+1), 16));
       }
       return data;
   }
    
    public static void main(String args[]) { // test     
   	 byte[] value = hexStringToByteArray("e04fd020ea3a6910a2d808002b30309d");     
   	 String keyval = "thisIsAKey";
       
       byte[] encrypted = encrypt(value,keyval);
       for(int i = 0; i < encrypted.length; i++)
           System.out.print(encrypted[i]);
       System.out.println("");
       //Turns bytes into Hex
       StringBuilder sb = new StringBuilder();
       for (byte b : encrypt(encrypted,keyval)) {
           sb.append(String.format("%02X ", b));
       }
       System.out.println(sb.toString());
               
   }


	
}