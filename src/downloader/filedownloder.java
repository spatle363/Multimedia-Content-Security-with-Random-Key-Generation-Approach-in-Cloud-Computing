package downloader;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import uploader.path;

import Database.DatabaseConnection;

/**
 * Servlet implementation class filedownloder
 */
@WebServlet("/filedownloder")
public class filedownloder extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public filedownloder() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		
	}

	/**
	 * @param path
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String backuppath=getServletContext().getRealPath("/backup");
		PrintWriter out = response.getWriter();
		HttpSession session = request.getSession();
		String user = (String) session.getAttribute("username");
		String file = request.getParameter("filename");
		String key = request.getParameter("deckey");
		String uploader = request.getParameter("uploder");		
		
		String filePath = path.path + uploader;

		File pathToCreate = new File(filePath); 
		while(!(pathToCreate.exists())){
			pathToCreate.mkdir();
		}

		filePath = path.path + uploader + "//" + file;

		DatabaseConnection db = new DatabaseConnection();
		db.dbconnection();
		String dcryptPath = preProcessing.preProcessing(uploader, key, file, db, user, backuppath);

		try {
			if (!(dcryptPath.equals(""))) {

		        response.setContentType("application/octet-stream");
		        response.setHeader("Content-Disposition",
		                "attachment;filename="+file);

				FileInputStream fileInputStream = new FileInputStream(dcryptPath);  
	            
				int i;   
				while ((i=fileInputStream.read()) != -1) {  
				out.write(i);   
				}   
				fileInputStream.close();   
				out.close(); 
				
				db.closeConnection(db);
				
			} else {

				response.setContentType("text/html;charset=UTF-8");
				response.getWriter().write("fail");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
