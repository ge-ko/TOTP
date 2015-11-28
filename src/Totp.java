import java.nio.ByteBuffer;

/**
 * Time-Based One-Time Password Algorithm (TOTP) - https://tools.ietf.org/html/rfc6238
 * HMAC-Based One-Time Password Algorithm (HOTP) - https://tools.ietf.org/html/rfc4226
 * Keyed-Hashing for Message Authentication (HMAC) - http://tools.ietf.org/html/rfc2104
 *
 */
public class Totp implements TotpAlgorithm {
    
    protected HmacAlgorithm hmac;
    protected long epochStart = 0L;
    protected long timeStep = 30L;
    protected int digits = 6;
    
    @Override
    public int getDigits() {
        return digits;
    }

    @Override
    public void setDigits(int digits) {
        this.digits = digits;
    }
    
    @Override
    public HmacAlgorithm getHmac() {
        return hmac;
    }
    
    @Override
    public void setHmac(HmacAlgorithm hmac) {
        this.hmac = hmac;
    }
    
    @Override
    public long getEpochStart() {
        return epochStart;
    }
    
    @Override
    public void setEpochStart(long epochStart) {
        this.epochStart = epochStart;
    }
    
    @Override
    public long getTimeStep() {
        return timeStep;
    }
    
    @Override
    public void setTimeStep(long timeStep) {
        this.timeStep = timeStep;
    }
    
    @Override
    public String generate() {
        return generate(System.currentTimeMillis() / 1000L);
    }

    @Override
    public String generate(long unixTime) {
        long timeCounter = getTimeCounter(unixTime);
        byte[] counter = longToByteArray(timeCounter);
        
        byte[] hash = hmac.generate(counter);
        
        int num = truncate(hash);
        
        String result = reduceDigits(num);

        return result;
        
    }

    protected String reduceDigits(int num) {
        num = num % (int) Math.pow(10, digits);
        return String.format("%0" + digits + "d", num);
    }

    protected int truncate(byte[] x) {
        assert x.length >= 20 : "Hash expected to be at least 20 bytes long";
        int offset = x[x.length - 1] & 0xF;
        
        int result = (x[offset] & 0x7F) << 24 | (x[offset + 1] & 0xFF) << 16 | (x[offset + 2] & 0xFF) << 8 | x[offset + 3] & 0xFF;
        
        return result;
    }
    
    protected long getTimeCounter(long unixTime) {
        return (unixTime - epochStart) / timeStep;
    }

    public byte[] longToByteArray(long x) {
        return ByteBuffer.allocate(8).putLong(x).array();
    }
}
