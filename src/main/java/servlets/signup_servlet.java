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
import java.util.Random;
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
@WebServlet(name = "signup_servlet", urlPatterns = {"/signup_servlet"})
public class signup_servlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            // get details from html.
            String firstname = req.getParameter("firstname");
            String lastname = req.getParameter("lastname");           
            String gender = req.getParameter("gender");
            String email = req.getParameter("email");
            String phone = req.getParameter("phone");
            String password = req.getParameter("password");
            String repeatpassword = req.getParameter("repeatpassword");
            
            // first check if password are same.
            if(!password.equals(repeatpassword))
            {
                req.setAttribute("error","repeatpassword must be same as password");
                req.setAttribute("detailed_error","details in repeatpassword and in password fields must be same ");
                req.getRequestDispatcher("error.jsp").forward(req, resp);
                return;
            }
            
            // first we query if there is same email or phone in db.
            String query = "SELECT * FROM clients where email="+"\""+email+"\""+"or phone ="+"\""+phone+"\"";
            
            java_sql_helper db =  new java_sql_helper();
            ResultSet rs = db.query_function(query);
            boolean next = rs.next();
            if(next)
            { // there is record in db with this username and pass
                req.setAttribute("error","Aready Used Email or Phone number");
                req.setAttribute("detailed_error","the Email or password you entered is in our database. Someone already registered with that details.");
                req.getRequestDispatcher("error.jsp").forward(req, resp);
                
            }
            else
            {// their records are unique
               Client client = new Client(firstname,lastname,gender,email,phone,password);
                
                // make a secret code
                Random r = new Random();
               int secret_code =  r.nextInt(1000000000);
               
               //make session object
               HttpSession session = req.getSession();
               session.setAttribute("secret_code", String.valueOf(secret_code));
               session.setAttribute("client_object", client);
                              
               
               
               // send secret code and change his page.
               java_mail j_mail = new java_mail();
                try {
                    j_mail.sendMail(email, "LEXX PLACE HOTEL : secret_code", secret_code+"");
                    
                     req.getRequestDispatcher("verification.jsp").forward(req, resp);
                    
                } catch (MessagingException ex) {
                    Logger.getLogger(signup_servlet.class.getName()).log(Level.SEVERE, null, ex);
                }
              
               

            }} catch (SQLException ex) {
            Logger.getLogger(signup_servlet.class.getName()).log(Level.SEVERE, null, ex);
        }
       
       
    }
    
  

}
