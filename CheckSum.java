
public class CheckSum
{

    // Returns a 64 bit hash in the form of a 8 byte array.
    static public byte[] hash(byte[] toHash)
    {
        Long sum = new Long(0);
        for(int i = 0; i < toHash.length; i++)
        {
            Long temp = (((long)(toHash[i] >> 2) * 63) * Long.MAX_VALUE) % Long.MAX_VALUE;
            sum += temp;
        }
        // Print initial hash to be returned.
        System.out.println(Long.toHexString(sum));
        return Long.toHexString(sum).getBytes();

    }

    public static void main(String args[])
    {
        byte[] a = new String("Test string").getBytes();
        byte[] b = CheckSum.hash(a);
        String s = new String("");
        // Test if the hash is still the same after the conversion.
        for(int i = 0; i < b.length; i++)
        {
            s += ((char) b[i]);
        }
        System.out.println(s);

    }

}
