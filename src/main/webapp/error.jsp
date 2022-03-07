<%-- 
    Document   : error
    Created on : Feb 11, 2022, 12:00:34 PM
    Author     : kiptala
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Error page</title>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
        

        
    </head>
    <body>
        <div class="container">
            <div class="row">
        <div>
         
        <form>
            <div class="form-group">
                   <h1> LEXX PLACE HOTEL</h1>
                   <h1>Error Page</h1>                
            </div>
            <div class="form-group">
                <label>Error</label>
                <labe class="form-control">${error}</label>
            </div>
            <div class="form-group">
                <label>Error details</label>
                <label class="form-control">${detailed_error}</label> <br/>
            </div>       
                     
                  
        </form>      
              
        
        </div>
        </div>
            <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-ka7Sk0Gln4gmtz2MlQnikT1wXgYsOg+OMhuP+IlRH9sENBO0LRn5q+8nbTov4+1p" crossorigin="anonymous"></script>
    </body>
</html>

