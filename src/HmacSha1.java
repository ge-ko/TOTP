import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

/* Copyright VMware, Inc. All rights reserved. -- VMware Confidential */

public class HmacSha1 implements HmacAlgorithm {

    protected Key key;

    @Override
    public Key getKey() {
        return key;
    }

    @Override
    public void setKey(Key key) {
        this.key = key;
    }

    @Override
    public byte[] generate(byte[] message) {
        try {
            Mac hmacSha1 = Mac.getInstance("HmacSHA1");
            final SecretKeySpec macKey = new SecretKeySpec(key.getBytes(), "RAW");
            hmacSha1.init(macKey);
            return hmacSha1.doFinal(message);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Could not instantiate HmacSHA1 algorithm", e);
        } catch (InvalidKeyException e) {
            throw new RuntimeException("Key is not accepted by HmacSHA1", e);
        }
        
    }
}
