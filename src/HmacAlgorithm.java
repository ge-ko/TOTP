public interface HmacAlgorithm {

    public abstract Key getKey();

    public abstract void setKey(Key key);

    public abstract byte[] generate(byte[] message);
}