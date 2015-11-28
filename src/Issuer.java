import java.io.File;

public class Issuer {

    public static void main(String[] args) {
        KeyUriFormatter kf = new KeyUriFormatter();
        kf.setKeyType(KeyUriFormatter.KeyType.TOTP);
        kf.setIssuer(args[0]);
        kf.setAccountname(args[1]);
        kf.setSecret(args[2]);
        kf.generateBarcode(new File(args[3]));
    }
    
}
