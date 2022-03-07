<%-- 
    Document   : customer_page
    Created on : Feb 9, 2022, 4:03:07 PM
    Author     : kiptala
--%>


<%@page import="javax.mail.Session"%>
<%@page import="javax.ws.rs.core.Request"%>
<%@page import="java.time.LocalDateTime"%>
<%@page import="servlets.Client"%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>customer page</title>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
                
    </head>
    <body style="background-image: url(https://imgcy.trivago.com/d_dummy.jpeg,f_auto,q_auto/if_iw_lte_ih,c_scale,w_236/if_else,c_scale,h_160/e_improve,q_auto:low//partnerimages/70/57/705786454.jpeg);
          background-repeat: no-repeat;
          background-size: 100%;">
        <div class="container">
            <div class="row">
        <div>
         
        <form>
            <div class="form-group">
               
                
                   <h1>Lexx Place Hotel </h1>
                   <h1>Client Page</h1>                
            </div>
            <div class="form-group">
                <label>First Name : </label>
                <% Client c = (Client)request.getSession().getAttribute("client_object"); %>
                
                <label> <% out.print(c.getFirstname()); %></label>
                                
            </div>
            
            <div class="form-group">
                <label>Email Address : </label>
                <label> <% out.print(c.getEmail().toString()); %></label> <br/>               
            </div>
                <%
                    // java code here
                Client client =  (Client)session.getAttribute("client_object");
                System.out.println("client name "+client.getFirstname());
                
                %>
         
        </form>  
                <%-- book room form --%>
                <form  action="booking_servlet" method="post"
                    class="form-group" 
                      style="background-color: blueviolet;
                      padding: 20px;
                      margin: 40px;
                      border-radius: 20px">
            <h3>Book A Room</h3>
            <label>Available Rooms according to Room number</label>
            <p style="color: white;">${available_rooms}</p>
            <label>Room Number</label>
            <input type="text" name="room_no" required><br>
            <input type="submit" value="Book Now" class="btn btn-success">            
        </form>              
        
            <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-ka7Sk0Gln4gmtz2MlQnikT1wXgYsOg+OMhuP+IlRH9sENBO0LRn5q+8nbTov4+1p" crossorigin="anonymous"></script>
    </body>
    
    
</html>

