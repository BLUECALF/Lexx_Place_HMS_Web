/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import javax.mail.Authenticator;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 *
 * @author kiptala
 */
public class java_mail {
    String subject;
    String body;
    
    
    public void sendMail(String recipient,String subject,String body) throws MessagingException
    {
        this.subject = subject;
        this.body = body;
        
        System.out.println("\n preparing to send mail ");
      Properties  properties =  new Properties();
          
      
      ///properties.setProperty("mail.transport.protocol", "smtp");
      properties.put("mail.smtp.auth", "true");
      properties.put("mail.smtp.starttls.enable","true");
      properties.put("mail.smtp.host","smtp.gmail.com");
      properties.put("mail.smtp.port","587");
      
      final String Email = "kiptalaleonard@gmail.com";
      final String password = "*$/tala/$*";
      
      Session session  = Session.getInstance(properties, new Authenticator(){
      

          @Override
          protected PasswordAuthentication getPasswordAuthentication() {
              return new PasswordAuthentication(Email,password);
          }
           
            
      });
      
      // make the message
      Message message = prepareMessage(session,Email,recipient);
      Transport.send(message);    
      System.out.println("\n\nEmail sent sucessfully");
    
    
    }
      
      //mail.smtp.auth
      //mail.smtp.starttls.enable
      //mail.smtp.host - smtp.gmail.com
      //mail.smtp.port -587

    private  Message prepareMessage(Session session,String Email,String recipient) {
        try {
            Message message  = new MimeMessage(session);
            message.setFrom(new InternetAddress(Email));
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(recipient));
            message.setSubject(subject);
            message.setText(body);
            
            return message;
        } catch (Exception ex) {
            Logger.getLogger(java_mail.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    
      
    }

