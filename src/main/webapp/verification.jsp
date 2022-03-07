<%-- 
    Document   : verification
    Created on : Feb 11, 2022, 4:57:55 PM
    Author     : kiptala
--%>

<html>
    <head>
        <title>Lexx Place Hotel  - verification</title>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
        
      

        
    </head>
    <body>
        <div class="container">
            <div class="row">
        <div>
         
        <form action = "verification_servlet" method = "post">
            <div class="form-group">
                   <h1> Lexx Place Hotel </h1>
                   <h1>Client Verification Page</h1>                
            </div>
            <div class="form-group">
                <label>Note : A secret code has been sent to your email<br>Enter it below</label>
                <input type="text" id="secret_code" name="secret_code_area" placeholder="Secret Code" class="form-control" required>
            </div>
            <br><br/>     
            <div class="form-group">
                <button type="submit" class="btn btn-success">Verify</button>
            </div>            
                  
            <%
            String secret_code = request.getParameter("secret_code");
            System.out.println("the secret code is:"+secret_code);
            %>
            
            <input type="hidden" name="secret_code" value="${secret_code}">            
        </form>      
        secret code is ${secret_code}
        </div>
        </div>
            <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-ka7Sk0Gln4gmtz2MlQnikT1wXgYsOg+OMhuP+IlRH9sENBO0LRn5q+8nbTov4+1p" crossorigin="anonymous"></script>
    </body>
</html>