/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import java.io.IOException;
import java.io.PrintWriter;
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
@WebServlet(name = "verification_servlet", urlPatterns = {"/verification_servlet"})
public class verification_servlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
      
        String secret_code_area = req.getParameter("secret_code_area");
        String secret_code = req.getParameter("secret_code");
       
        HttpSession session = req.getSession();
        
        Client client = (Client)session.getAttribute("client_object");
        
        String secret_code_from_session = (String) session.getAttribute("secret_code");
        
        System.out.println("secret code area :"+secret_code_area);
        System.out.println("secret_code :"+secret_code);
        System.out.println("secret_code_from session :"+secret_code_from_session);
        
        //System.out.println("gamers name "+ gamer.firstname);
        
        if(!secret_code_area.equals(secret_code_from_session))
        {
            // he entered wrong code.
           req.setAttribute("error","wrong secret code");
           req.setAttribute("detailed_error","Please check your email for correct secret_code we have sent you");
           req.getRequestDispatcher("error.jsp").forward(req, resp);
        
        }
        else
        {
            // corect code , now we sign him up.
            java_sql_helper db = new java_sql_helper();
            
            
            String firstname = client.firstname;
            String lastname = client.lastname;           
            String gender = client.gender;
            String email = client.email;
            String phone = client.phone;
            String password = client.password;
            
            String insert_statement_prepared = "INSERT INTO clients VALUES(?,?,?,?,?,?)";
            Object[] data = new Object[6];
            data[0] = firstname;
            data[1] = lastname;
            data[2] = gender;
            data[3] = email;
            data[4] = phone;
            data[5] = password;
           
            
            int i;
            for(i=0;i<6;i++){
            System.out.println("value in array is : "+data[i]);}
            
            int rows = db.insert_function(insert_statement_prepared, 6, data);
            
            if (rows==1)
            {
                // record was inserted successfully.
            req.setAttribute("email",email);
            req.setAttribute("firstname",firstname);            
            req.getRequestDispatcher("customer_page.jsp").forward(req, resp);
            }
            else
            {
                // his data wasnt entered in db
                req.setAttribute("error"," Sign-up failed");
                req.setAttribute("detailed_error","Your record wasn't entered to database");
                req.getRequestDispatcher("error.jsp").forward(req, resp);            
            }
        }
    }

    
}
