/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;


import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.MessagingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author kiptala
 */
@WebServlet(name = "login_servlet", urlPatterns = {"/login_servlet"})
public class login_servlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
       
       // get data from html;
       String email = req.getParameter("email");
       String password = req.getParameter("password");
       
       int password_hash =password.hashCode();
                      
       // conect to database     
       java_sql_helper db = new java_sql_helper();
       String query = "SELECT * from clients WHERE email="+"\"" +email+"\""+"and password ="+"\""+password+"\""; 
       ResultSet rs = db.query_function(query);
        try {           
           if(rs.next())
           {
            String db_password =  rs.getString("password");
            System.out.println(db_password+" password in db");
            //good login.
            // we ned now to get all details and put it in session.
            query = "SELECT * from clients where email ="+"\"" +email+"\"";
            System.out.println("the query iz "+ query);
            ResultSet rs2 = db.query_function(query);
            rs2.next();
            
            Client client = new Client(
            rs2.getString("firstname"),
            rs2.getString("lastname"),
            rs2.getString("gender"),
            rs2.getString("email"),
            rs2.getString("phone"),
            rs2.getString("password")
            );
            
            HttpSession session  = req.getSession();
            session.setAttribute("client_object", client);
            
            req.setAttribute("email",email);
            req.setAttribute("firstname",rs.getString("firstname"));            
            req.getRequestDispatcher("customer_page.jsp").forward(req, resp);
           }else
           {// wrong email or password
           req.setAttribute("error","wrong Email or password");
           req.setAttribute("detailed_error","the Email or password you entered can't be found in database");
           req.getRequestDispatcher("error.jsp").forward(req, resp);
           
           }
           
           
        } catch (SQLException ex) {
            Logger.getLogger(login_servlet.class.getName()).log(Level.SEVERE, null, ex);
            req.setAttribute("error","SQL error");
            req.setAttribute("detailed_error", ex.getMessage());
            req.getRequestDispatcher("error.jsp").forward(req, resp);
        }
       
     
     
    }

   
    
     

}
