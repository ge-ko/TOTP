import java.nio.ByteBuffer;

public class HmacCustomImpl implements HmacAlgorithm {
    
    protected HashFunction hashFunction;
    protected int blockLength;
    protected Key key;
    
    public HashFunction getHashFunction() {
        return hashFunction;
    }

    public void setHashFunction(HashFunction hashFunction) {
        this.hashFunction = hashFunction;
    }

    public int getBlockLength() {
        return blockLength;
    }

    public void setBlockLength(int blockLength) {
        this.blockLength = blockLength;
    }

    @Override
    public Key getKey() {
        return key;
    }

    @Override
    public void setKey(Key key) {
        this.key = key;
    }
    
    @Override
    public byte[] generate(final byte[] message) {
        final byte[] ipad = xor(key.getBytes(), (byte) 0x36);
        final byte[] opad = xor(key.getBytes(), (byte) 0x5C);
        
        return hashFunction.hash(concatenate(opad, hashFunction.hash(concatenate(ipad, message))));
    }

    protected byte[] xor(final byte[] a, byte b) {
        final byte[] result = a.clone();
        for (int i = 0; i < result.length; i++) {
            result[i] ^= b;
        }
        return result;
    }
    
    protected byte[] concatenate(final byte[] a, final byte[] b) {
        return ByteBuffer.allocate(a.length + b.length).put(a).put(b).array();
    }
}