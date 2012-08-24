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
import entity.CustomerOrder;

/**
 *
 * @author tgiunipero
 */ 
public class CustomerOrderFacade extends AbstractFacade<CustomerOrder> { 

    private CustomerFacade customerFacade = new CustomerFacade();
    
    public CustomerOrderFacade() {
        super(CustomerOrder.class);
    }

    // overridden - refresh method called to retrieve order id from database
    public CustomerOrder find(int id) {
        //throw new RuntimeException();
        CustomerOrder order = new CustomerOrder();
        Connection con = util.getConnection();
        String sql = "select * from customer_order where ID=?";
         
        
        PreparedStatement statement = null;
        try {
            statement = con.prepareStatement(sql);
            statement.setInt(1, id);
            
            ResultSet rs = statement.executeQuery();
            while(rs.next()){
                order.setId(id);
                order.setAmount(rs.getBigDecimal(2));
                order.setDateCreated(rs.getDate(3));
                order.setConfirmationNumber(rs.getInt(4));
                int customerId = rs.getInt(5);
                CustomerFacade customerFacade = new CustomerFacade();
                Customer customer = customerFacade.find(customerId);
                order.setCustomer(customer);
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
        return order;
        /*CustomerOrder order = em.find(CustomerOrder.class, id);
        em.refresh(order);
        return order;*/
    }

    // in this implementation, there is only one order per customer
    // the data model however allows for multiple orders per customer 
    public CustomerOrder findByCustomer(int customerID) {
        Customer customer = customerFacade.find(customerID);
        CustomerOrder order = new CustomerOrder();
        Connection con = util.getConnection();
        String sql = "select * from customer_order where customer_id=?";
         
        
        PreparedStatement statement = null;
        try {
            statement = con.prepareStatement(sql);
            statement.setInt(1, customerID);
            
            ResultSet rs = statement.executeQuery();
            while(rs.next()){
                order.setId(rs.getInt(1));
                order.setAmount(rs.getBigDecimal(2));
                order.setDateCreated(rs.getDate(3));
                order.setConfirmationNumber(rs.getInt(4));
                order.setCustomer(customer);
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
        return order;
        //return (CustomerOrder) em.createNamedQuery("CustomerOrder.findByCustomer").setParameter("customer", customer).getSingleResult();
    } 
}