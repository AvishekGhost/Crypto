import java.math.BigInteger;

public class RSA {
    static BigInteger one = new BigInteger("1");

    static BigInteger privateKey;
    static BigInteger publicKey;
    static BigInteger modulus;

    public static BigInteger encrypt(BigInteger message) {
        return message.modPow(publicKey, modulus);
    }

    public static BigInteger decrypt(BigInteger encrypted) {
        return encrypted.modPow(privateKey, modulus);
    }

    public static String output(BigInteger n) {
        String s = "";
        s += "Public key pair{e,n}  = (" + publicKey + ", " + n + ")" + "\n";
        s += "Private key pair{d,n} = (" + privateKey + "," + n + ")" + "\n";

        return s;
    }

    public static void main(String[] args) {
        if (args.length > 0 && args[0] != null) {
            int N = Integer.parseInt("70");
            BigInteger p = new BigInteger("27923325097");
            BigInteger q = new BigInteger("17201642803");
            System.out.println("p= " + p + "   q=" + q);
            BigInteger phi = (p.subtract(one)).multiply(q.subtract(one));

            String msg = args[0], w = "", s = "";
            int i, x = 0;
            modulus = p.multiply(q);
            System.out.println("n=(p*q) = " + modulus);
            System.out.println("phi = " + phi);
            publicKey = new BigInteger("9999999967");
            privateKey = publicKey.modInverse(phi);

            BigInteger encrypt = new BigInteger("00");
            BigInteger decrypt = new BigInteger("00");
            String enc = "";
            for (i = 0; i < msg.length(); i++) {

                if (msg.charAt(i) >= 65 && msg.charAt(i) <= 90) {
                    encrypt = encrypt(new BigInteger("" + (msg.charAt(i) - '0')));
                    decrypt = decrypt(encrypt);
                    w += (char) (decrypt.intValue() + 48);
                    enc += Integer.toString(encrypt.intValue()) + " ";

                } else {
                    encrypt = encrypt(new BigInteger("" + (msg.charAt(i))));
                    decrypt = decrypt(encrypt);
                    w += Integer.toString(decrypt.intValue());
                    enc += Integer.toString(encrypt.intValue()) + " ";

                }

            }
            System.out.println(output(modulus));
            System.out.println("plain text   = " + msg);
            System.out.println("encrpyted  (textinput.pow(e).mod (n)) = " + enc);
            System.out.println("decrypted (d.pow(e).mod n) = " + w);
        } else {
            System.out.println("corrent is: RSA <msg>");
        }
    }
}
