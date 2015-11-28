import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import org.junit.Assert;
import org.junit.Test;

public class TotpTest extends Totp {

    @Test
    public void testReduceDigits() {
        setDigits(8);
        Assert.assertEquals("34567890", reduceDigits(1234567890));
        
    }
    
    @Test
    public void testTruncate() {

        int a = 0xCAFEBABE & 0x7FFFFFFF;
        byte[] x = {
                    (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0xCA, (byte) 0xFE, (byte) 0xBA, (byte) 0xBE,
                    (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00,
                    (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x04 };

        int b = truncate(x);
        Assert.assertEquals(a, b);
    }
    
    @Test
    public void testGetTimeCounter() {
        setEpochStart(1000L);
        setTimeStep(100L);
        Assert.assertEquals(31L, getTimeCounter(4199L));
    }
    
    @Test
    public void testLongToByteArray() {
        byte[] x = { (byte) 0x12, (byte) 0x34, (byte) 0x56, (byte) 0x78, (byte) 0xCA, (byte) 0xFE, (byte) 0xBA, (byte) 0xBE };
        Assert.assertArrayEquals(x, longToByteArray(0x12345678CAFEBABEL));
    }
    
    @Test
    public void testGenerateWithStandardHmacSha1() {
        String secret = "WRAK X3R5 2SN7 WAG6";
        Key key = new Key();
        key.setKey(secret);
        
        HmacAlgorithm hmac = new HmacSha1();
        hmac.setKey(key);
        
        TotpAlgorithm totp = new Totp();
        totp.setHmac(hmac);
        
        String code = totp.generate(1427673256);

        Assert.assertEquals("934741", code);
    }
    
    @Test
    public void testGenerateWithOwnHmacSha1() throws NoSuchAlgorithmException {
        String secret = "WRAK X3R5 2SN7 WAG6";
        Key key = new Key();
        key.setKey(secret);
        
        HmacCustomImpl hmac = new HmacCustomImpl();
        hmac.setKey(key);
        final MessageDigest md = MessageDigest.getInstance("SHA-1");
        hmac.setHashFunction(in -> md.digest(in));
        
        TotpAlgorithm totp = new Totp();
        totp.setHmac(hmac);
        
        String code = totp.generate(1427673256);

        Assert.assertEquals("934741", code);
    }
}
