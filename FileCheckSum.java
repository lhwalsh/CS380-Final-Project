
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
        String compareHash = new String("");
        for(int i = 0; i < hash.length; i++)
        {
            compareHash += ((char) hash[i]);
        }
        byte[] hash1 = FileCheckSum.hash(toHash);
        String otherHash = new String("");
        for(int i = 0; i < hash1.length; i++)
        {
            otherHash += ((char) hash[i]);
        }
        if(compareHash.equals(otherHash))
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
        System.out.println(FileCheckSum.hashToString(b));
        Boolean d = FileCheckSum.compareHash(a,b);
        System.out.println(d.toString());

    }

}
