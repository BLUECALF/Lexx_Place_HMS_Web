/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 *
 * @author kiptala
 */
public class java_sql_helper{
    //members
    JFrame j;
    Connection con;
    
    java_sql_helper()
    {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            // loading jdbc driver is amust in servlet
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(java_sql_helper.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        String url = "jdbc:mysql://localhost/lexx_place_hotel";
        String uname = "root";
        String pass = "";
        
        try {   
             con  = DriverManager.getConnection(url,uname,pass);
            System.out.println("connected to database");
        } catch (SQLException ex) {
            Logger.getLogger(java_sql_helper.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("NOT connected to database");
        }
        
    
    } 
    
    public int insert_function(String insert_statement_prepared,int size,Object[] data)
    {
        System.out.println("data in index 0 is"+data[0]);
        try {
            PreparedStatement p_st = con.prepareStatement(insert_statement_prepared);
            
            int i;
            for(i=0;i < size;i++)
            {
                p_st.setObject(i+1,data[i]);
            //inserting data to prepared statement
            }
            int rows = p_st.executeUpdate();
            //return rows
            return rows;
            
        } catch (SQLException ex) {
            Logger.getLogger(java_sql_helper.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
    return 0;
    }
    
    public ResultSet query_function(String query)
    {
        try {
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(query);
            
            //return rows
            return rs;
            
            
        } catch (SQLException ex) {
            Logger.getLogger(java_sql_helper.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null; // if nothing happens or there is no record
    }
    
    public int update_function(String update_statement)
    {
        try {
            Statement st = con.createStatement();
            int rows = st.executeUpdate(update_statement);
            
            //return rows
            return rows;
            
        } catch (SQLException ex) {
            Logger.getLogger(java_sql_helper.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
    return 0;
    }
    
}
 