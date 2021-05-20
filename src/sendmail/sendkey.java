package sendmail;

import java.io.IOException;
import java.sql.ResultSet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import Database.DatabaseConnection;

/**
 * Servlet implementation class sendkey
 */
@WebServlet("/sendkey")
public class sendkey extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public sendkey() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpSession session=request.getSession();
		String filename = request.getParameter("filename");
		String username1 = (String) session.getAttribute("username");

        DatabaseConnection db=new DatabaseConnection();
    	db.dbconnection();
    	
    	try {
			String sql1 = "SELECT u.email,f.bkey FROM users AS u LEFT JOIN files AS f ON f.uploder=u.username and file='"+filename+"' where username='" + username1 + "'";
			ResultSet rs = db.getResultSet(sql1);
			if (rs.next()) 
			{
				String email=rs.getString("email");
				String bkey=rs.getString("bkey");
				
				SimpleSendEmail emails = new SimpleSendEmail(email,"File Decryption key ", filename +" file decryption key is "+bkey);
				
		        response.setContentType("text/html;charset=UTf-8");
		        response.getWriter().write("success");
			}else{
				response.setContentType("text/html;charset=UTF-8");
				response.getWriter().write("fail");
			}
		} catch (Exception e) {
			System.out.println(e);
		}
	}

}
