
public class FileCheckSum
{
    // Returns a 64 bit hash in the form of an 8 byte array.
    static public byte[] hash(byte[] toHash)
    {
        Long sum = new Long(0);
        for(int i = 0; i < toHash.length; i++)
        {
            Long temp = (((long)(toHash[i] >> 2) * 63) * Long.MAX_VALUE) % Long.MAX_VALUE;
            sum += temp;
        }
        return Long.toHexString(sum).getBytes();
    }
    // Rehashes the received data and coverts the new hash and the received hash
    // to strings and compares them.
    static public boolean compareHash(byte[] toHash, byte[] hash)
    {
        byte[] newHash = FileCheckSum.hash(toHash);
        String newHashString = FileCheckSum.hashToString(newHash);
        String actualHashString = FileCheckSum.hashToString(hash);
        //System.out.println("a: " + newHashString);
        //System.out.println("b: " + actualHashString);
        if(actualHashString.equals(newHashString))
        {
            return true;
        }
        else
        {
            return false;
        }
    }
    // Converts the byte array hash to a HexString.
    static public String hashToString(byte[] toString)
    {
        String s = new String("");
        for(int i = 0; i < toString.length; i++)
        {
            s += ((char) toString[i]);
        }
        return s;
    }
    // Test
    public static void main(String args[])
    {
        byte[] a = new String("Test string").getBytes();
        byte[] b = FileCheckSum.hash(a);
        //System.out.println(FileCheckSum.hashToString(b));
        byte[] c = new String("Not hash").getBytes();
        Boolean d = FileCheckSum.compareHash(a,c);
        System.out.println(d.toString());
        d = FileCheckSum.compareHash(a,b);
        System.out.println(d);

    }

}
