package TrippleDES;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;

import javax.crypto.Cipher;
import javax.crypto.CipherOutputStream;
import javax.crypto.spec.SecretKeySpec;

public class TDESencryption {
	public static void encryption(String source, String destination, String MYkey, File udir, String filename) throws IOException {

		String I = source;
		String O1 = udir + "//Enc1_" + filename;
		String O2 = udir + "//Enc_2" + filename;
		String O3 = destination;

		try {
			FileInputStream file1 = new FileInputStream(I);
			FileOutputStream outfile1 = new FileOutputStream(O1);

			byte k[] = MYkey.getBytes();
			k = Arrays.copyOf(k, 8);
			SecretKeySpec key = new SecretKeySpec(k, "DES");
			Cipher enc = Cipher.getInstance("DES");
			enc.init(Cipher.ENCRYPT_MODE, key);
			CipherOutputStream cos = new CipherOutputStream(outfile1, enc);
			byte[] buf = new byte[1024];
			int read;
			while ((read = file1.read(buf)) != -1) {
				cos.write(buf, 0, read);
			}
			file1.close();
			outfile1.flush();
			cos.close();
			System.out.println("file Encrypted");
			outfile1.close();

			FileInputStream file2 = new FileInputStream(O1);
			FileOutputStream outfile2 = new FileOutputStream(O2);

			byte k2[] = MYkey.getBytes();
			k2 = Arrays.copyOf(k2, 8);
			SecretKeySpec key2 = new SecretKeySpec(k2, "DES");
			Cipher enc2 = Cipher.getInstance("DES");
			enc2.init(Cipher.ENCRYPT_MODE, key2);
			CipherOutputStream cos2 = new CipherOutputStream(outfile2, enc2);
			byte[] buf2 = new byte[1024];
			int read2;
			while ((read2 = file2.read(buf2)) != -1) {
				cos2.write(buf2, 0, read2);
			}
			file2.close();
			outfile2.flush();
			cos2.close();
			System.out.println("file Encrypted");
			outfile2.close();

			FileInputStream file3 = new FileInputStream(O2);
			FileOutputStream outfile3 = new FileOutputStream(O3);

			byte k3[] = MYkey.getBytes();
			k3 = Arrays.copyOf(k3, 8);
			SecretKeySpec key3 = new SecretKeySpec(k3, "DES");
			Cipher enc3 = Cipher.getInstance("DES");
			enc3.init(Cipher.ENCRYPT_MODE, key3);
			CipherOutputStream cos3 = new CipherOutputStream(outfile3, enc3);
			byte[] buf3 = new byte[1024];
			int read3;
			while ((read3 = file3.read(buf3)) != -1) {
				cos3.write(buf3, 0, read3);
			}
			file3.close();
			outfile3.flush();
			cos3.close();
			System.out.println("file Encrypted");
			outfile3.close();

			File a = new File(O1);
			a.delete();

			File b = new File(O2);
			b.delete();

		} catch (Exception e) {
			e.printStackTrace();
			System.out.print(e);
		}
	}
}
