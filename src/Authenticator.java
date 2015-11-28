public class Authenticator {
    
    public static void main(final String[] args) {
        
        Key key = new Key();
        String secret = args[0];
        key.setKey(secret);
        
        HmacAlgorithm hmac = new HmacSha1();
        hmac.setKey(key);
        
        HotpAlgorithm totp = new Totp();
        totp.setHmac(hmac);
        
        String code = totp.generate();
        
        System.out.println(code);
    }
}
