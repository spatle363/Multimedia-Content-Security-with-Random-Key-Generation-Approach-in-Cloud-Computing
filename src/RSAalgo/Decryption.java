package RSAalgo;

import java.io.File;
import java.math.BigInteger;

import org.apache.commons.io.FileUtils;

public class Decryption {
	public RSA rsa;

	public Decryption() {
		rsa = new RSA();
	}

	public void decrypt(String downloadfile, String dcryptPath, String enckey) {
		String key[] = enckey.split(",");
		String privateky = key[0];
		String publicky = key[1];
		try {
			String cipher = FileUtils.readFileToString(new File(downloadfile));
			String decryptedText;
			BigInteger privateKey;
			BigInteger publicKey = new BigInteger(publicky);
			privateKey = new BigInteger(privateky);
			decryptedText = rsa.decrypt(cipher, publicKey, privateKey);
			FileUtils.writeStringToFile(new File(dcryptPath), decryptedText);
		} catch (Exception ex) {
		}
	}
}
