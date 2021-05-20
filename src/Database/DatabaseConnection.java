/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Database;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author kp
 */
public class DatabaseConnection {

    String db = "MultimediaSecurity";
    String username = "root";
    String password = "";
    Connection con = null;
    Statement stmt;
    ResultSet rs = null;

    public Connection dbconnection() {

        try {
            String url = "jdbc:mysql://localhost:3306/" + db;
            Class.forName("com.mysql.jdbc.Driver").newInstance();

            if(con == null || con.isClosed())
            {
            con = DriverManager.getConnection(url, username, password);
            }

        } catch (SQLException ex) {
            Logger.getLogger(DatabaseConnection.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            Logger.getLogger(DatabaseConnection.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(DatabaseConnection.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(DatabaseConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
        return con;
    }
    
    public ResultSet getResultSet(String query) {
        try {
            stmt = con.createStatement();
            rs = stmt.executeQuery(query);
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
        return rs;
    }

    public int getUpdate(String query) {
        int i = 0;
        try {
            stmt = con.createStatement();
            i = stmt.executeUpdate(query);
        } catch (Exception e) {
        }
        return i;
    }

    public void setCommit()
    {
        try
        {
            con.setAutoCommit(false);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
    
    public void doneCommit()
    {
        try
        {
            con.commit();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
    
    
    public void rollBack()
    {
        try
        {
            con.rollback();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
    
    public void closeConnection(DatabaseConnection dbcon) {
        try {
            dbcon.dbconnection().close();
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}