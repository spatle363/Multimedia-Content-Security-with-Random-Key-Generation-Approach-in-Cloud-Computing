package RSAalgo;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;

public class Encryption {
	public RSA rsa;

	public Encryption() {
		rsa = new RSA();
	}

	public String enc(String name, String sizeInBytes, String absolutePath, String destination) {
		String plainText;
		String key = "";
		try {
			plainText = FileUtils.readFileToString(new File(absolutePath));
			String encryptedText;
			String publicKey = "";
			String privateKey = "";

			rsa.generateKeys(plainText);
			encryptedText = rsa.encrypt(name, sizeInBytes, plainText);

			System.out.println(plainText.length());

			if (rsa.getN() != null && rsa.getD() != null) {
				publicKey = rsa.getN().toString();
				privateKey = rsa.getD().toString();

				System.out.println("privateKey " + privateKey);
				System.out.println("publicKey " + publicKey);
				System.out.println("encryptedText " + encryptedText);
				FileUtils.writeStringToFile(new File(destination), encryptedText);
				key = privateKey+","+publicKey;
			} else {
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return key;
	}
}
