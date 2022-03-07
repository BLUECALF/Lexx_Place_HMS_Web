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
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.MessagingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.JOptionPane;

/**
 *
 * @author kiptala
 */
@WebServlet(name = "cancel_booking_servlet", urlPatterns = {"/cancel_booking_servlet"})
public class cancel_booking_servlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
      
        String booking_id = req.getParameter("booking_id").trim();
       String Message;
         java_sql_helper db = new java_sql_helper();
         
         String query = "select * from bookings where booking_id="+"\""+booking_id+"\"";
         
         ResultSet rs = db.query_function(query);
         
        try {
            if(rs.next()) //this means the booking id is valid.
            {
                // get his Details First
               String firstname = rs.getString("firstname");
               String room_no = rs.getString("room_no");  
               String email = rs.getString("email");
               Date checkindate =rs.getDate("checkin_date");
               Date checkoutdate = rs.getDate("checkout_date");
               
               String query1 = "SELECT * from residents WHERE resident_id="+"\"" +booking_id+"\"";
            ResultSet rs0 = db.query_function(query1);
            
           
                if(rs0.next()==true)
                {
                    // you are a resident - a d resident cannot cancel booking
                  req.setAttribute("message"," Cancel Failed: A resident cannot cancel booking of room number "+room_no);
                       req.setAttribute("firstname",firstname);   
                       req.getRequestDispatcher("customer_page.jsp").forward(req, resp);
                    return;
                }
                
               String Remove_statement = "DELETE from bookings where booking_id="+"\""+booking_id+"\"";
               int rows = db.update_function(Remove_statement);
               if(rows==1)
               {
                   // cancel of booking Was Successful
                   
                   
                  Message = "Dear "+firstname+",\n"+
                    "You Have CANCELED BOOKING of Room number :"+room_no+
                    "\nBooking Id is :"+booking_id+
                    "\nCheck-in date or arrival Date is :"+checkindate+
                    "\nCheck-Out Date or Deparure date is  :"+checkoutdate;
                   
                   java_mail jm = new java_mail();
                   try {
                       jm.sendMail(email, "LEXX PLACE HOTEL: canceled Booking",Message);
                       
                       req.setAttribute("email",email);
                       req.setAttribute("message","You have canceled booking of room number "+room_no);
                       req.setAttribute("firstname",firstname);   
                       req.getRequestDispatcher("customer_page.jsp").forward(req, resp);
                       
                   } catch (MessagingException ex) {
                     
                       req.setAttribute("error","Messaging Error");
                       req.setAttribute("detailed_error",ex.toString());
                       req.getRequestDispatcher("error.jsp").forward(req, resp);
                   }
               
               }
                
                
            }else // the booking_id is invalid. foward to error page.
            {
            req.setAttribute("error","wrong Booking id");
            req.setAttribute("detailed_error","The booking Id does not exist in our Database");
           req.getRequestDispatcher("error.jsp").forward(req, resp);
            }
        } catch (SQLException ex) {
         
            req.setAttribute("error","SQL Error");
            req.setAttribute("detailed_error",ex.toString());
           req.getRequestDispatcher("error.jsp").forward(req, resp);
        }
         
    }

    
}
