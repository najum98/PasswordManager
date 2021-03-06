import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;
import java.util.*;
import javax.swing.*;
import java.io.*;
import java.math.BigInteger; 
import java.security.MessageDigest; 
import java.security.NoSuchAlgorithmException;

public class searchRecord extends HttpServlet
{
    public String generateMD5(String rawPassword)
    {
        try { 

			// Static getInstance method is called with hashing MD5 
			MessageDigest md = MessageDigest.getInstance("MD5"); 

			// digest() method is called to calculate message digest 
			// of an input digest() return array of byte 
			byte[] messageDigest = md.digest(rawPassword.getBytes()); 

			// Convert byte array into signum representation 
			BigInteger no = new BigInteger(1, messageDigest); 

			// Convert message digest into hex value 
			String hashtext = no.toString(16); 
			while (hashtext.length() < 32) { 
				hashtext = "0" + hashtext; 
			} 
			return hashtext; 
		} 

		// For specifying wrong message digest algorithms 
		catch (NoSuchAlgorithmException e) { 
			throw new RuntimeException(e); 
		}
    }
    public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException
    {
        
        HttpSession sess=req.getSession(false);
        if(sess!=null)
        {
        String website=req.getParameter("website");
        String userID=(String)sess.getAttribute("email");
        HttpSession session = req.getSession(false);
        try 
        {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) 
        {
            e.printStackTrace();
        }
        try
        {
        String url = "jdbc:mysql://127.0.0.1/passwordmanager";
        Connection con=DriverManager.getConnection(url,"root","1234");
        Statement st=con.createStatement();
        //password=generateMD5(password);
        String query="SELECT username,password FROM credentials WHERE userID='"+userID+"' and website='"+website+"';";
        ResultSet rs = st.executeQuery( query );
            if(rs!=null)
            {
                rs.next();
                req.setAttribute("username",rs.getString("username"));
                req.setAttribute("password",rs.getString("password"));
                RequestDispatcher rd = req.getRequestDispatcher("searchRecord.jsp");
                rd.forward(req,res);
                
            }
            else
            {
                //yaha change kr rha hu me
                // req.setAttribute("error","Record not found");
                // RequestDispatcher rd = req.getRequestDispatcher("main.jsp");
                // rd.forward(req,res);
                PrintWriter out = res.getWriter();
                out.println("<html><body><h1>Record Not Found</h1></body></html>");
                }

        }
        catch(SQLException e)
        {
            // e.printStackTrace();
            // sess.setAttribute("error",new String("Record not found"));
            //     RequestDispatcher rd = req.getRequestDispatcher("main.jsp");
            //     rd.forward(req,res);
                PrintWriter out = res.getWriter();
                out.println("<html><body><h1>Record Not Found</h1></body></html>");
        }  
    }
        else
        {
            res.sendRedirect("index.jsp");
        }
        

    }
}