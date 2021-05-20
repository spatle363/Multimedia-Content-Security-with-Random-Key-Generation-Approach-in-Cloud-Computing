package uploader;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;

import AES.file_encryption;
import Blowfish.EncryptFile;
import RSAalgo.Encryption;
import TrippleDES.TDESencryption;
import cloudfile.DriveUploader;
import savelog.datamanager;
import encryption.getName;
import encryption.key;

/**
 * Servlet implementation class UploadFile
 */
@WebServlet("/UploadFile")
public class UploadFile extends HttpServlet {

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//		String backuppath=getServletContext().getRealPath("/backup");
		
		HttpSession session = request.getSession();
		PrintWriter out = response.getWriter();

		// process only if its multipart content
		if (ServletFileUpload.isMultipartContent(request)) {
			try {
				List<FileItem> multiparts = new ServletFileUpload(
						new DiskFileItemFactory()).parseRequest(request);

				for (FileItem item : multiparts) {
					if (!item.isFormField()) {

						String name = new File(item.getName()).getName();
						System.out.println(name); // print with extenssion

						long sizeInBytes = item.getSize();
						System.out.println("byte" + sizeInBytes);
						double kilobytes = sizeInBytes / 1024.0;
						String kb = String.valueOf(kilobytes);
						System.out.println("byte :" + kilobytes);

						String fileNameWithOutExt = FilenameUtils
								.removeExtension(name);
						System.out.println(fileNameWithOutExt); // print
						
						String extention = FilenameUtils.getExtension(name);

						path p = new path();
						String username = (String) session.getAttribute("username");

						File udir = new File(p.path + username);
						if (!(udir.exists())) {
							udir.mkdir();
						}

						File a = new File(udir + "//" + name);
						String  absolutePath = udir + "//" + name;
						String destination = udir + "//Enc_" + name;
						int i = 0;
						while (a.exists()) {
							i++;
							a = new File(udir + "//" + i + name);
							absolutePath = udir + "//" +i+ name;
							name = i+ name;
							destination = udir + "//Enc_" + name;
							
						}
				
						
						System.out.println(absolutePath);
						item.write(a);
//						String ky = key.passer();
//						file_encryption.encryption(absolutePath, aesdestination, ky);
						
						String ky="";
						
							if(extention.equalsIgnoreCase("pdf")||extention.equalsIgnoreCase("doc") ||extention.equalsIgnoreCase("docx")||extention.equalsIgnoreCase("txt")||extention.equalsIgnoreCase("jpeg")||extention.equalsIgnoreCase("bmp")||extention.equalsIgnoreCase("png")||extention.equalsIgnoreCase("jpg")||extention.equalsIgnoreCase("ppt")||extention.equalsIgnoreCase("xlsx")){
								ky = key.passer();
								ky = ky.replaceAll(",", "");
								file_encryption.encryption(absolutePath,destination, ky);
							}else if(extention.equalsIgnoreCase("mp3")|| extention.equalsIgnoreCase("mp4") || extention.equalsIgnoreCase("wav") || extention.equalsIgnoreCase("3gp") || extention.equalsIgnoreCase("avi") || extention.equalsIgnoreCase("wmv") || extention.equalsIgnoreCase("mov") || extention.equalsIgnoreCase("mpeg") || extention.equalsIgnoreCase("webm")||extention.equalsIgnoreCase("jpeg")||extention.equalsIgnoreCase("bmp")||extention.equalsIgnoreCase("png")||extention.equalsIgnoreCase("jpg")){
								ky = key.passer();
								ky = ky.replaceAll(",", "");
								EncryptFile enc = new EncryptFile(ky);
								enc.encrypt(absolutePath, destination);
							}
							else if(extention.equalsIgnoreCase("mp3")|| extention.equalsIgnoreCase("mp4") || extention.equalsIgnoreCase("wav") || extention.equalsIgnoreCase("3gp") || extention.equalsIgnoreCase("avi") || extention.equalsIgnoreCase("wmv") || extention.equalsIgnoreCase("mov") || extention.equalsIgnoreCase("mpeg") || extention.equalsIgnoreCase("webm")||extention.equalsIgnoreCase("zip")||extention.equalsIgnoreCase("doc")||extention.equalsIgnoreCase("pdf")||extention.equalsIgnoreCase("docx")||extention.equalsIgnoreCase("xlsx")){
								ky = key.passer();
								ky = ky.replaceAll(",", "");
								TDESencryption.encryption(absolutePath,destination, ky, udir, name);
							}
							else if(extention.equalsIgnoreCase("txt")){
								Encryption e = new Encryption();
								ky = e.enc(name, kb, absolutePath, destination);
							}else{
								out.println("<script type=\"text/javascript\">");
					        	out.println("alert('Something went wrong')");
					        	out.println("location=\"userhome.jsp\"");
					        	out.println("</script>");
							}
						
						String drivename = getName.name(name);
						DriveUploader.DriveUploder(destination, drivename);

						System.out.println(absolutePath);
						datamanager dm = new datamanager();
						extnslector sx = new extnslector();
						String LOgoPath = sx.extnslector(name);
						dm.UploadLog(name.substring(0, name.lastIndexOf('.')), name, drivename, username, kilobytes+"",LOgoPath , ky, "n");

						
//						a.deleteOnExit();
//						File b = new File(destination);
//						b.deleteOnExit();
						
						
						
						System.out.println("Attempting to delete " + a.getAbsolutePath());
						if (!a.exists())
						  System.out.println("  Doesn't exist");
						else if (!a.canWrite())
						  System.out.println("  No write permission");
						else
						{
						  if (a.delete())
						    System.out.println("  Deleted!");
						  else
						    System.out.println("  Delete failed - reason unknown");
						}
						
						
//						System.out.println("Attempting to delete " + b.getAbsolutePath());
//						if (!b.exists())
//						  System.out.println("  Doesn't exist");
//						else if (!b.canWrite())
//						  System.out.println("  No write permission");
//						else
//						{
//						  if (b.delete())
//						    System.out.println("  Deleted!");
//						  else
//						    System.out.println("  Delete failed - reason unknown");
//						}
//						request.getRequestDispatcher("index.jsp").include(request,
//								response);
						out.println("<script type=\"text/javascript\">");
						out.println("alert('File Uploding Successfully')");
						out.println("location=\"home.jsp\"");
						out.println("</script>");
					}
					
				}
			} catch (Exception ex) {
				System.out.println(ex);
			}
		} else {
			request.setAttribute("message",
					"Sorry this Servlet only handles file upload request");
		}
	}

}
