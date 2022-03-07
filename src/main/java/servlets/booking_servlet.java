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
import java.text.ParseException;
import java.text.SimpleDateFormat;
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

/**
 *
 * @author kiptala
 */
@WebServlet(name = "booking_servlet", urlPatterns = {"/booking_servlet"})
public class booking_servlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
       
        Date now_date = null;
        Date checkindate = null;
        Date checkoutdate= null;
        // we have clients details from the Http session
        
        //now get room he needs
        //get checkin date
       String checkin_date_string = req.getParameter("checkindate");
        try {
            System.out.println("checkin date"+checkin_date_string);
            checkindate = new SimpleDateFormat("yyyy-MM-dd").parse(checkin_date_string);
            System.out.println("checkin date.to string is :"+checkindate.toString());
        } catch (ParseException ex) {
            req.setAttribute("error","You Entered Wrong Date");
                req.setAttribute("detailed_error",ex.toString());
                req.getRequestDispatcher("error.jsp").forward(req, resp);
        }
        
        //get checkin date
       String checkout_date_string = req.getParameter("checkoutdate");
        try {
            checkoutdate = new SimpleDateFormat("yyyy-MM-dd").parse(checkout_date_string);
            System.out.println("checkout date iz:"+checkoutdate.toString());
        } catch (ParseException ex) {
            req.setAttribute("error","You Entered Wrong Date");
                req.setAttribute("detailed_error",ex.toString());
                req.getRequestDispatcher("error.jsp").forward(req, resp);
        }
        
       // get date now
      now_date = new Date();
        System.out.println("NOw :: is "+now_date.getDate());
        
        
       if(checkindate.before(now_date))
       {
           System.out.println("checkin date is past ");
           req.setAttribute("error","You Cannot Checkin at a Past Date");
                req.setAttribute("detailed_error","Check in date must be now or after .");
                req.getRequestDispatcher("error.jsp").forward(req, resp);
       }
       else if(checkoutdate.before(checkindate))
       {
           req.setAttribute("error","Checkout Date Must be after Checkin date");
                req.setAttribute("detailed_error","checkout date must be after Checkin date");
                req.getRequestDispatcher("error.jsp").forward(req, resp);       
       }
       else{
            try {
                // every detail is ok now one can find room
                String query1 = "SELECT room_no FROM bookings where ("+"\""+checkin_date_string+"\""+">= checkin_date and "+"\""+checkin_date_string+"\""+"<= checkout_date)" ;
                String query2 = " OR ("+"\""+checkin_date_string+"\""+"<= checkin_date and "+"\""+checkout_date_string+"\""+">= checkin_date)";
                System.out.println("Room cash query is");
                System.out.println(query1);
                System.out.println(query2);
                String full_query = query1 + query2;
                System.out.println(full_query);
                java_sql_helper db =  new java_sql_helper();
                ResultSet rs = db.query_function(full_query);
                System.out.println(rs.toString());
                
                // after this we have found rooms that will be bussy that period.
                String exception ="";
                while(rs.next())
                {
                    exception = exception +"room_no ="+rs.getString("room_no")+" OR ";
                }
                exception = exception +"room_no = \"\"";
                
                 System.out.println("Exception is");
                 System.out.println(exception);
                 
                 // now Fond rooms that will be available by filtering sql statement
                 String available_room_query = "SELECT * from rooms where NOT("+exception+")";
                 System.out.println("");
                 System.out.println(available_room_query);
                 ResultSet rooms_rs = db.query_function(available_room_query);
                 
                 //we can pass a string of rooms.
                 String available_rooms_string ="";
                 while(rooms_rs.next())
                 {
                     System.out.println(rooms_rs.getString("room_no"));
                     available_rooms_string = available_rooms_string + rooms_rs.getString("room_no")+"<br>";
                 }
                 HttpSession session = req.getSession();
                 req.setAttribute("available_rooms", available_rooms_string);
                 session.setAttribute("checkindate", checkindate);
                 session.setAttribute("checkoutdate", checkoutdate);
                 req.getRequestDispatcher("book_room.jsp").forward(req, resp);                 
                
            } catch (SQLException ex) {
                Logger.getLogger(booking_servlet.class.getName()).log(Level.SEVERE, null, ex);
            }          
              
       }
               
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
     
        HttpSession session = req.getSession();
        Client client =(Client) session.getAttribute("client_object");
        //get Fields we need for Entry to booking table
         java_sql_helper db = new java_sql_helper();
            
            
            String firstname = client.firstname;           
            String email = client.email;
            String room_no = req.getParameter("room_no");
            Date checkindate = (Date)session.getAttribute("checkindate");
            Date checkoutdate = (Date)session.getAttribute("checkoutdate");
            
            System.out.println("Checkin Date as an atrribute is"+checkindate);
            
            String booking_id;
            
            // generate booking id
            while (true)
            {
            try {
                Random r = new Random();
                booking_id = r.nextInt(999999) +"";
                
                String query = "SELECT * FROM bookings where booking_id="+"\""+booking_id+"\"";
                ResultSet rs = db.query_function(query);
                if(rs.next()==false)
                { break;}
            } catch (SQLException ex) {
                Logger.getLogger(booking_servlet.class.getName()).log(Level.SEVERE, null, ex);
            }
             
            }
            
            
            
            String insert_statement_prepared = "INSERT INTO bookings VALUES(?,?,?,?,?,?)";
            Object[] data = new Object[6];
            
            data[0] = booking_id;
            data[1] = firstname;
            data[2] = email;
            data[3] = room_no;
            data[4] = checkindate;
            data[5] = checkoutdate;
       
            
            int i;
            for(i=0;i<6;i++){
            System.out.println("value in array is : "+data[i]);}
            
            int rows = db.insert_function(insert_statement_prepared, 6, data);
            
            if (rows==1)
            {
                // record was inserted successfully. 
            
            req.setAttribute("message", "Booking of Room "+ room_no+" Successful ..");
            req.getRequestDispatcher("customer_page.jsp").forward(req, resp);
            
            String Message = "Dear "+firstname+",\n"+
                    "You Have Booked Room number :"+room_no+
                    "\nBooking Id is :"+booking_id+
                    "\nCheck-in date or arrival Date is :"+checkindate+
                    "\nCheck-Out Date or Deparure date is  :"+checkoutdate;
            
            java_mail jm = new java_mail();
            try {
                jm.sendMail(email, "LEXX PLACE HOTEL : Booking Room Successful", Message);
            } catch (MessagingException ex) {
                Logger.getLogger(booking_servlet.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            }
        
        
    }
     


}
