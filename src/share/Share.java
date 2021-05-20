package share;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import sendmail.SimpleSendEmail;
import Database.DatabaseConnection;

/**
 * Servlet implementation class Share
 */
@WebServlet("/Share")
public class Share extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Share() {
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
		String file = request.getParameter("filenameextn");
		String selecteduser = request.getParameter("selecteduser");
		if(selecteduser.equals("0"))
		{
			selecteduser = request.getParameter("selectedgroup");
		}
		//System.out.println(selecteduser + file);

		HttpSession session = request.getSession();

		String username = (String) session.getAttribute("username");

		DatabaseConnection db = new DatabaseConnection();
		db.dbconnection();
		PrintWriter out = response.getWriter();
		String sql = "select * from files where filenameextn ='" + file
				+ "' and uploder='" + username + "'";
		ResultSet rs = db.getResultSet(sql);
		try {
			if (rs.next()) {
				String key = rs.getString("enckey");
				
				sql = "insert into sharefiles values('" + rs.getString("file")
						+ "','" + rs.getString("filenameextn") + "','"
						+ rs.getString("filesize") + "','"
						+ rs.getString("filenameDrive") + "'" + ",'"+username+"','" + selecteduser
						+ "','" + rs.getString("logo") + "','"
						+ rs.getString("enckey") + "')";
				db.getUpdate(sql);
				
				String sq="Select * From cgroup Where gname='"+selecteduser+"'";
				//System.out.println(sq);
				ResultSet res = db.getResultSet(sq);
				if(res.next())
				{
					String guser = res.getString("member");
					//System.out.println(guser);
					String[] alluser={};
					alluser=guser.split(",");
					//System.out.println(alluser);
					
						for(int k=0; k<alluser.length; k++)
						{
							sql = "select email from users where username = '"
									+ alluser[k] + "'";
							ResultSet res1 = db.getResultSet(sql);
			
							if (res1.next()) 
							{
								SimpleSendEmail emails = new SimpleSendEmail(
										res1.getString(1),
										"File Report ",
										file
												+ " shared by user "
												+ username
												+ " you can decrypt this file using this key: "
												+ key);
							}
						}
				}
				else
				{
					sql = "select email from users where username = '"
							+ selecteduser + "'";
					rs = db.getResultSet(sql);
	
					if (rs.next()) 
					{
						SimpleSendEmail emails = new SimpleSendEmail(
								rs.getString(1),
								"File Report ",
								file
										+ " shared by user "
										+ username
										+ " you can decrypt this file using this key: "
										+ key);
					}
				}
				
				out.println("<script type=\"text/javascript\">");
				out.println("alert('File Shared with "+selecteduser+"');");
				out.println("location=\"home.jsp\";");
				out.println("</script>");
				
			} else {
				out.println("<script type=\"text/javascript\">");
				out.println("alert('Problem while sharing file');");
				out.println("location=\"home.jsp\";");
				out.println("</script>");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
