package authentication;

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
 * Servlet implementation class chk_signin
 */
@WebServlet("/chk_signin")
public class chk_signin extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public chk_signin() {
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
		
		DatabaseConnection db=new DatabaseConnection();
    	db.dbconnection();
		
		String username = request.getParameter("username");
		String password = request.getParameter("password");

		try {
			String sql1 = "select * from users where username='" + username + "' and password = '"+password+"'";
			ResultSet rs = db.getResultSet(sql1);
			if (rs.next()) {
				session.setAttribute("username", username);
				String firstname = rs.getString("fullname");
				session.setAttribute("firstname", firstname);
				response.sendRedirect("home.jsp");	
			} else {
				response.sendRedirect("login.jsp");
			}
		} catch (Exception e) {
			System.out.println(e);
		}
		db.closeConnection(db);
	}

}
