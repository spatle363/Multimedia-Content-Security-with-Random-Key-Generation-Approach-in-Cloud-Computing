package TrippleDES;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Arrays;

import javax.crypto.Cipher;
import javax.crypto.CipherOutputStream;
import javax.crypto.spec.SecretKeySpec;

public class TDESdecryptor {

	public static void main(String source, String destination, String dkey, String path, String filename) {

		String f = source;
		String o1 = path + "/1" + filename;
		String o2 = path + "/2" + filename;
		String o3 = destination;

		try {
			FileInputStream file = new FileInputStream(f);
			FileOutputStream outfile = new FileOutputStream(o1);

			byte k[] = dkey.getBytes();
			k = Arrays.copyOf(k, 8);
			SecretKeySpec key = new SecretKeySpec(k, "DES");
			Cipher enc = Cipher.getInstance("DES");
			enc.init(Cipher.DECRYPT_MODE, key);
			CipherOutputStream cos = new CipherOutputStream(outfile, enc);
			byte[] buf = new byte[1024];
			int read;
			while ((read = file.read(buf)) != -1) {
				cos.write(buf, 0, read);
			}
			file.close();
			outfile.flush();
			cos.close();
			outfile.close();
			System.out.println("file Decrypted");
			
			FileInputStream file1 = new FileInputStream(o1);
			FileOutputStream outfile1 = new FileOutputStream(o2);

			byte k1[] = dkey.getBytes();
			k1 = Arrays.copyOf(k1, 8);
			SecretKeySpec key1 = new SecretKeySpec(k1, "DES");
			Cipher enc1 = Cipher.getInstance("DES");
			enc1.init(Cipher.DECRYPT_MODE, key1);
			CipherOutputStream cos1 = new CipherOutputStream(outfile1, enc1);
			byte[] buf1 = new byte[1024];
			int read1;
			while ((read1 = file1.read(buf1)) != -1) {
				cos1.write(buf1, 0, read1);
			}
			file1.close();
			outfile1.flush();
			cos1.close();
			outfile1.close();
			System.out.println("file Decrypted");
			
			FileInputStream file2 = new FileInputStream(o2);
			FileOutputStream outfile2 = new FileOutputStream(o3);

			byte k2[] = dkey.getBytes();
			k2 = Arrays.copyOf(k2, 8);
			SecretKeySpec key2 = new SecretKeySpec(k2, "DES");
			Cipher enc2 = Cipher.getInstance("DES");
			enc2.init(Cipher.DECRYPT_MODE, key2);
			CipherOutputStream cos2 = new CipherOutputStream(outfile2, enc2);
			byte[] buf2 = new byte[1024];
			int read2;
			while ((read2 = file2.read(buf2)) != -1) {
				cos2.write(buf2, 0, read2);
			}
			file2.close();
			outfile2.flush();
			cos2.close();
			outfile2.close();
			System.out.println("file Decrypted");
			
			File a = new File(o1);
			a.delete();

			File b = new File(o2);
			b.delete();

		} catch (Exception e) {
			e.printStackTrace();
			System.out.print(e);
		}
	}
}
