/*
 * Copyright (c) 2010, Oracle and/or its affiliates. All rights reserved.
 *
 * You may not modify, use, reproduce, or distribute this software
 * except in compliance with the terms of the license at:
 * http://developer.sun.com/berkeley_license.html
 */

package session;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import entity.Customer;

/**
 *
 * @author tgiunipero
 */ 
public class CustomerFacade extends AbstractFacade<Customer> { 

    public CustomerFacade() {
        super(Customer.class);
    }
    
    public Customer find(int id) { 
        Customer customer = new Customer();
        Connection con = util.getConnection();
        String sql = "select * from customer where id=?";
        
        PreparedStatement statement = null;
        try {
            statement = con.prepareStatement(sql);
            statement.setInt(1, id);
            
            ResultSet rs = statement.executeQuery();
            while(rs.next()){ 
                customer.setId(id);
                customer.setName(rs.getString(2));
                customer.setEmail(rs.getString(3));
                customer.setPhone(rs.getString(4));
                customer.setAddress(rs.getString(5));
                customer.setCityRegion(rs.getString(6));
                customer.setCcNumber(rs.getString(7));
            } 
        } catch (SQLException e) { 
            e.printStackTrace();
        }finally{
            if(statement != null){
                try {
                    statement.close();
                } catch (SQLException e) { 
                    e.printStackTrace();
                }
            }
        }
                
        return customer;
    }
    
    public Customer find(String name) { 
        Customer customer = new Customer();
        Connection con = util.getConnection();
        String sql = "select * from customer where name=?";
        
        PreparedStatement statement = null;
        try {
            statement = con.prepareStatement(sql);
            statement.setString(1, name);
            
            ResultSet rs = statement.executeQuery();
            while(rs.next()){ 
                customer.setId(rs.getInt(1));
                customer.setName(rs.getString(2));
                customer.setEmail(rs.getString(3));
                customer.setPhone(rs.getString(4));
                customer.setAddress(rs.getString(5));
                customer.setCityRegion(rs.getString(6));
                customer.setCcNumber(rs.getString(7));
            } 
        } catch (SQLException e) { 
            e.printStackTrace();
        }finally{
            if(statement != null){
                try {
                    statement.close();
                } catch (SQLException e) { 
                    e.printStackTrace();
                }
            }
        }
                
        return customer;
    }

}