package savelog;

import java.sql.ResultSet;
import java.sql.SQLException;

import Database.DatabaseConnection;
import sendmail.SimpleSendEmail;

public class datamanager {


	public static void UploadLog(String filename, String FileExtansionname, String filehash, String username, String Size,String logo,String key,String bkey) throws SQLException {
		DatabaseConnection db = new DatabaseConnection();
		db.dbconnection();
		
		String sql = "select email from users where username = '"+username+"'";
		ResultSet rs = db.getResultSet(sql);
		
		if(rs.next()){
			//SimpleSendEmail emails = new SimpleSendEmail(rs.getString(1),"File Report ", filename +" sucessfully uploaded decryption key is "+key);
			
			String Query = "insert into files values('" + filename + "','"
					+ FileExtansionname + "','" + Size + "','" + filehash + "','" +  username+ "','" +logo+ "','"+key+"','"+bkey+"' )";
			
			System.out.println(Query);
			int i = db.getUpdate(Query);

			db.closeConnection(db);
		}
		
		
		
		
	}

}
