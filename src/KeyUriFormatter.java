import java.io.File;
import net.glxn.qrgen.javase.QRCode;

/**
 * https://github.com/google/google-authenticator/wiki/Key-Uri-Format
 * otpauth://$type/$label?secret=$secret&issuer=$issuer&algorithm=$algorithm&digits=$digits&counter=%counter
 * $type = hotp | totp
 * $label = $accountname | $issuer (“:” | “%3A”) *”%20” $accountname
 * $secret = base32 encoded key
 * $issuer = identity provider
 * $algorithm = SHA1 | SHA256 | SHA512 | MD5 (default SHA1)
 * $digits = 1..10 (default 6)
 * $counter = initial counter value (required for hotp)
 * $period = totp code validity period (default 30)
 */
public class KeyUriFormatter {

    public enum KeyType {
        HOTP("hotp"),
        TOTP("totp");

        private final String type;

        private KeyType(String type) {
            this.type = type;
        }

        @Override
        public String toString() {
            return type;
        }
    }
    
    public enum Algorithm {
        SHA1("SHA1"),
        SHA256("SHA256"),
        SHA512("SHA512"),
        MD5("MD5");

        private final String algorithm;

        private Algorithm(String algorithm) {
            this.algorithm = algorithm;
        }

        @Override
        public String toString() {
            return algorithm;
        }
    }

    protected KeyType type;
    protected String label;
    protected String accountname; // used to construct label if no explicit label provided
    protected String secret;
    protected String issuer;
    protected Algorithm algorithm; // default Algorithm.SHA1
    protected Integer digits; // default 6
    protected Long counter;
    protected Long period; // default 30
    
    public KeyType getKeyType() {
        return type;
    }

    public void setKeyType(KeyType type) {
        this.type = type;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getAccountname() {
        return accountname;
    }

    public void setAccountname(String accountname) {
        this.accountname = accountname;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret.replaceAll("\\s", "");
    }

    public String getIssuer() {
        return issuer;
    }

    public void setIssuer(String issuer) {
        this.issuer = issuer;
    }

    public Algorithm getAlgorithm() {
        return algorithm;
    }

    public void setAlgorithm(Algorithm algorithm) {
        this.algorithm = algorithm;
    }

    public Integer getDigits() {
        return digits;
    }

    public void setDigits(Integer digits) {
        this.digits = digits;
    }

    public Long getCounter() {
        return counter;
    }

    public void setCounter(Long counter) {
        this.counter = counter;
    }

    public Long getPeriod() {
        return period;
    }

    public void setPeriod(Long period) {
        this.period = period;
    }

    public String generateUri() {
        StringBuffer sb = new StringBuffer();
        sb.append("otpauth://");
        sb.append(type);
        sb.append("/");
        if (label != null) {
            sb.append(label);
        } else if (accountname != null) {
            sb.append(issuer);
            sb.append(":");
            sb.append(accountname);
        } else {
            sb.append(label); // will print null
        }
        sb.append("?secret=");
        sb.append(secret);
        if (issuer != null) {
            sb.append("&issuer=");
            sb.append(issuer);
        }
        if (algorithm != null) {
            sb.append("&algorithm=");
            sb.append(algorithm);
        }
        if (digits != null) {
            sb.append("&digits=");
            sb.append(digits);
        }
        if (counter != null) {
            sb.append("&counter=");
            sb.append(counter);
        }
        return sb.toString();
    }

    public void generateBarcode(File png) {
        QRCode.from(generateUri()).withSize(195, 195).file().renameTo(png);
    }
}
