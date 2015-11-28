public interface HotpAlgorithm {
    
    public abstract String generate();
    
    public abstract void setHmac(HmacAlgorithm hmac);
    
    public abstract HmacAlgorithm getHmac();
    
    public abstract void setDigits(int digits);
    
    public abstract int getDigits();
    
}
