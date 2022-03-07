<%-- 
    Document   : customer_page
    Created on : Feb 9, 2022, 4:03:07 PM
    Author     : kiptala
--%>


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
                   <h1>Resident Page</h1>                
            </div>
            <div class="form-group">
                <label>First Name : </label>
               <% Client c = (Client)request.getSession().getAttribute("client_object"); %>
                
                <label> <% out.print(c.getFirstname()); %></label>         
                                
            </div>
               
               
            
            <div class="form-group">
                <label>Email Address : </label>
               <label> <% out.print(c.getEmail()); %></label>           
            </div>
            
            <div class="form-group">
                
                   <h2 style="color:greenyellow;"
                       >Message: ${message}</h2>
                                
            </div>
                <%
                    // java code here
                Client client =  (Client)session.getAttribute("client_object");
                System.out.println("client name "+client.getFirstname());
                
                %>
         
        </form>  
                <%-- Order room service form --%>
                <form  action="resident_servlet" method="post"
                    class="form-group" 
                      style="background-color: rosybrown;
                      padding: 20px;
                      margin: 40px;
                      border-radius: 20px">
            <h3>Order Food Room Service</h3>
            <label>Food Name</label><br>
            <input type="text" name="item_name" required><br>
            <input type="hidden" name="order_category" value="food" required>            
            <label>Description</label><br>
            <textarea name="description" cols ="40" rows ="3" required></textarea>
<br>
            <input type="submit" value="Order Food" class="btn btn-success">            
        </form>              
        
                
               </form>  
                <%-- order laundry form --%>
                <form  action="resident_servlet" method="post"
                    class="form-group" style="background-color:rosybrown;
                      padding: 20px;
                      margin: 40px;
                      border-radius: 20px">
            <h3>Order Laundry Service</h3>
            <input type="hidden" name="order_category" value="laundry" required> 
            <input type="hidden" name="item_name" value="laundry" required> 
            <input type="hidden" name="description" value="laundry cleaning service" required> 
            
            <label>Landry will be measured by the staff...</label>
            <br>
            <label>Click the button below to Order  the service.</label>
            <label>After ordering a Hotel staff will visit You</label>
            <br>
            <input type="submit" value="Order Laundry" class="btn btn-success">            
        </form>
     </form>  
                <%-- order House cleaning Service form --%>
                <form  action="resident_servlet" method="post" 
                    class="form-group" style="background-color: rosybrown;
                      padding: 20px;
                      margin: 40px;
                      border-radius: 20px">
                    
                    <input type="hidden" name="order_category" value="room_cleaning" required> 
                     <input type="hidden" name="item_name" value="room_cleaning" required>
                     <input type="hidden" name="description" value="house cleaning service" required>
                    
            <h3>Order Room Cleaning Service </h3>
            <label></label>
            
            <label>Click the button below to Order the service.After ordering a Hotel staff will visit You</label><br>          
            <input type="submit" value="Order Room Cleaning" class="btn btn-success">            
        </form>
        </div>
        </div>
            <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-ka7Sk0Gln4gmtz2MlQnikT1wXgYsOg+OMhuP+IlRH9sENBO0LRn5q+8nbTov4+1p" crossorigin="anonymous"></script>
    </body>
    
    
</html>

