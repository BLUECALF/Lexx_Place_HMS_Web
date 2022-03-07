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
import java.util.Date;
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
import javax.websocket.Session;

/**
 *
 * @author kiptala
 */
@WebServlet(name = "resident_servlet", urlPatterns = {"/resident_servlet"})
public class resident_servlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
      
        try {
            HttpSession session = req.getSession();
            Client client =(Client) session.getAttribute("client_object");
            //get Fields we need for Entry to booking table
            java_sql_helper db = new java_sql_helper();
            
            
            String firstname = client.firstname;           
            String email = client.email;
            
            //send to residents
            req.setAttribute("email",email);
            
            
            
            
//            // check if client is a resident.
String query = "SELECT * FROM residents where email="+"\""+email+"\"";
ResultSet rs = db.query_function(query);

if(rs.next())
{
    // the client is a resident
   req.getRequestDispatcher("resident_page.jsp").forward(req, resp); 
   
   // add  room_no,firstname,and resident_id to session.
   session.setAttribute("room_no", rs.getString("room_no"));
   session.setAttribute("firstname", rs.getString("firstname"));
   session.setAttribute("resident_id", rs.getString("resident_id"));
    
}
else
{
    //send him back to customer page but message him he is not a resident.
    req.setAttribute("message", "You are NOT a resident");
    req.getRequestDispatcher("customer_page.jsp").forward(req, resp); 
    

}
        } catch (SQLException ex) {
            Logger.getLogger(resident_servlet.class.getName()).log(Level.SEVERE, null, ex);
        }
                
               
                
         
        
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
       //post will handle all the orders.
       
       HttpSession session = req.getSession();
       java_sql_helper db = new java_sql_helper();
        
       String item_name =req.getParameter("item_name");
       String order_category =req.getParameter("order_category");
       String description =req.getParameter("description");
       String order_id;
       String room_no = (String) session.getAttribute("room_no");
       String firstname = (String) session.getAttribute("firstname");
       String resident_id = (String) session.getAttribute("resident_id");
       Client c = (Client) session.getAttribute("client_object");
       String email = c.getEmail();
       
        // generate order_id
            while (true)
            {
            try {
                Random r = new Random();
                order_id = r.nextInt(9999) +"";
                
                String query = "SELECT * FROM orders where order_id="+"\""+order_id+"\"";
                ResultSet rs = db.query_function(query);
                if(rs.next()==false)
                { break;}
            } catch (SQLException ex) {
                Logger.getLogger(booking_servlet.class.getName()).log(Level.SEVERE, null, ex);
            }
             
            }
            
            // insert The Order Into the Database ;
            String insert_statement_prepared = "INSERT INTO orders VALUES(?,?,?,?,?,?,?)";
            Object[] data = new Object[7];
            data[0] = order_id;
            data[1] = room_no;
            data[2] = firstname;
            data[3] = order_category;
            data[4] = item_name;
            data[5] = description;
            data[6] = resident_id;
           
            
            int i;
            for(i=0;i<7;i++){
            System.out.println("value in array is : "+data[i]);}
            
            int rows = db.insert_function(insert_statement_prepared, 7, data);
            
            if (rows==1)
            {
           try {
               // record was inserted successfully.
               
               String emailed_message = "Dear "+firstname+",\n"+
                       "You Have Ordered :"+item_name+
                       "\nTo be Served at Room number: "+room_no+
                       "\n Order Id is :"+order_id+
                       "\n description is :"+ description+
                       "\n\n\nNOTE:: PLEASE DO NOT DELETE THIS EMAIL IT WILL BE USED AT BILLING OF THE ORDER";
               
               
               // send email to confirm the order.
               java_mail jm = new java_mail();
               
               jm.sendMail(email, "DONT DELETE :: Order Success",emailed_message);
               req.setAttribute("message", "Order of "+item_name+" was successful , Please check your email");
               req.getRequestDispatcher("resident_page.jsp").forward(req, resp);
           } catch (MessagingException ex) {
              req.setAttribute("error","Messaging Error");
                       req.setAttribute("detailed_error",ex.toString());
                       req.getRequestDispatcher("error.jsp").forward(req, resp);
           }
            
                       
                   
            
            }
            else
            {
                // his data wasnt entered in db
                req.setAttribute("error","Order of "+item_name+" failed");
                req.setAttribute("detailed_error","Your record wasn't entered to database");
                req.getRequestDispatcher("error.jsp").forward(req, resp);            
            }
            
            
            
       
    }
  
}
