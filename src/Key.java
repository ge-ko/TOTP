import java.nio.ByteBuffer;
import org.apache.commons.codec.binary.Base32;

public class Key {
    
    protected String keyString;
    protected byte[] key;

    public void setKey(String key) {
        keyString = key.replaceAll("\\s", "");
        setKey(decodeKey(key));
    }

    public void setKey(final byte[] key) {
        assert key.length <= 64 : "Key is too long. Provide sha1(key)";
        this.key = ByteBuffer.allocate(64).put(key).array();
    }

    public byte[] getBytes() {
        return key;
    }

    private static byte[] decodeKey(String key) {
        return new Base32().decode(key);
    }
    
}
