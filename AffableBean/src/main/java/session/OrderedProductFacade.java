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
import java.util.ArrayList;
import java.util.List;

import entity.CustomerOrder;
import entity.OrderedProduct;

/**
 *
 * @author tgiunipero
 */ 
public class OrderedProductFacade extends AbstractFacade<OrderedProduct> {
     

    public OrderedProductFacade() {
        super(OrderedProduct.class);
    }

    // manually created
    public List<OrderedProduct> findByOrderId(int customerOrderID) {
        CustomerOrderFacade customerOrderFacade = new CustomerOrderFacade();
        CustomerOrder customerOrder = customerOrderFacade.find(customerOrderID);

        ProductFacade productFacade = new ProductFacade();
        
        List<OrderedProduct> list = new ArrayList<OrderedProduct>();
        String sql = "select * from ordered_product where CUSTOMER_ORDER_ID=?";
         
        Connection con = util.getConnection();
        PreparedStatement statement = null;
        try {
            statement = con.prepareStatement(sql);
            statement.setInt(1, customerOrderID);
            
            ResultSet rs = statement.executeQuery();
            while(rs.next()){
                OrderedProduct op = new OrderedProduct();
                op.setCustomerOrder(customerOrder);
                int productID = rs.getInt(2);
                op.setProduct(productFacade.find(productID));
                op.setQuantity(rs.getShort(3));
                
                list.add(op);
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
        return list;
    }

}