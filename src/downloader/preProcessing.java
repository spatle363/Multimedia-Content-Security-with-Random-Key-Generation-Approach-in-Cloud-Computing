package downloader;

import java.io.File;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.sql.ResultSet;

import org.apache.commons.io.FilenameUtils;

import RSAalgo.Decryption;
import TrippleDES.TDESdecryptor;
import cloudfile.DriveDownloader;
import uploader.path;
import AES.decryptor;
import Blowfish.DecryptFile;
import Database.DatabaseConnection;

public class preProcessing {

	public static String preProcessing(String user, String key, String file, DatabaseConnection db, String shuser, String backuppath) {
		
		String downloadfile = "";
		String dcryptPath = "";
		
		String sql = " select filenamedrive,enckey,bkey from  files where uploder='"+ user + "' and enckey = '" + key + "' and filenameextn='"+ file + "'";
		//System.out.println(sql);
		ResultSet rs = db.getResultSet(sql);

		try {
			if (rs.next()) {
				
				String filenamedrive = rs.getString("filenamedrive");
				String enckey = rs.getString("enckey");
				
				DriveDownloader.DriveDownloder(filenamedrive, user);
				
				downloadfile = path.path + user + "/" +filenamedrive;
				dcryptPath = path.path + user + "/download_" + filenamedrive;
				
				String extention = FilenameUtils.getExtension(filenamedrive);
				 
				/****************This Use for share file downloading******************/
				
				if(extention.equalsIgnoreCase("pdf")||extention.equalsIgnoreCase("doc") ||extention.equalsIgnoreCase("docx")||extention.equalsIgnoreCase("txt")||extention.equalsIgnoreCase("jpeg")||extention.equalsIgnoreCase("bmp")||extention.equalsIgnoreCase("png")||extention.equalsIgnoreCase("jpg")||extention.equalsIgnoreCase("ppt")||extention.equalsIgnoreCase("xlsx")){
					enckey = enckey.substring(0, 16);
					decryptor.main(downloadfile, dcryptPath, enckey);
				}
				else if(extention.equalsIgnoreCase("mp3")|| extention.equalsIgnoreCase("mp4") || extention.equalsIgnoreCase("wav") || extention.equalsIgnoreCase("3gp") || extention.equalsIgnoreCase("avi") || extention.equalsIgnoreCase("wmv") || extention.equalsIgnoreCase("mov") || extention.equalsIgnoreCase("mpeg") || extention.equalsIgnoreCase("webm")||extention.equalsIgnoreCase("jpeg")||extention.equalsIgnoreCase("bmp")||extention.equalsIgnoreCase("png")||extention.equalsIgnoreCase("jpg")){
					DecryptFile dec = new DecryptFile(enckey);
					dec.decrypt(downloadfile, dcryptPath);
				}
				else if(extention.equalsIgnoreCase("mp3")|| extention.equalsIgnoreCase("mp4") || extention.equalsIgnoreCase("wav") || extention.equalsIgnoreCase("3gp") || extention.equalsIgnoreCase("avi") || extention.equalsIgnoreCase("wmv") || extention.equalsIgnoreCase("mov") || extention.equalsIgnoreCase("mpeg") || extention.equalsIgnoreCase("webm")||extention.equalsIgnoreCase("zip")||extention.equalsIgnoreCase("doc")||extention.equalsIgnoreCase("pdf")||extention.equalsIgnoreCase("docx")||extention.equalsIgnoreCase("xlsx")){
					TDESdecryptor.main(downloadfile, dcryptPath, enckey, path.path + user, filenamedrive);
				}
				else if(extention.equalsIgnoreCase("txt")){
					Decryption d = new Decryption();
					d.decrypt(downloadfile, dcryptPath, enckey);
				}

			} else {
				dcryptPath = "";
			}
		} catch (Exception e) {
			System.out.println("e:" + e);
		}
		return dcryptPath;

	}

	public static String random() {

		StringBuilder generatedToken = new StringBuilder();
		try {
			SecureRandom number = SecureRandom.getInstance("SHA1PRNG");
			// Generate 20 integers 0..20
			for (int i = 0; i < 6; i++) {
				generatedToken.append(number.nextInt(9));
			}
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}

		return generatedToken.toString();
	}
}
