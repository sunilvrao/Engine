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

import entity.Category;
import entity.Product;

/**
 *
 * @author tgiunipero
 */ 
public class ProductFacade extends AbstractFacade<Product> {
    public ProductFacade() {
        super(Product.class);
    }
    
    public List<Product> getAll(Short categoryID){
        CategoryFacade categoryFacade = new CategoryFacade();
        
        Category category = categoryFacade.find(categoryID);
        List<Product> products = new ArrayList<Product>();
        
        Connection con = util.getConnection(); 
        String sql = "select * from product where category_id=?";
        
        PreparedStatement statement = null;
        try {
            statement = con.prepareStatement(sql);
            statement.setShort(1, categoryID);
            
            ResultSet rs = statement.executeQuery();
            while(rs.next()){ 
                Product product = new Product();
                product.setId(rs.getInt(1));
                product.setName(rs.getString(2));
                product.setPrice(rs.getBigDecimal(3));
                product.setLastUpdate(rs.getDate(5));
                product.setCategory(category);
                
                products.add(product);
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
                
        return products;
    }
    
    public Product find(int id) { 
        CategoryFacade categoryFacade = new CategoryFacade();
        Product product = new Product();
        Connection con = util.getConnection();
        String sql = "select * from product where id=?";
        
        PreparedStatement statement = null;
        try {
            statement = con.prepareStatement(sql);
            statement.setInt(1, id);
            
            ResultSet rs = statement.executeQuery();
            while(rs.next()){ 
                product.setId(rs.getInt(1));
                product.setName(rs.getString(2));
                product.setPrice(rs.getBigDecimal(3));
                product.setLastUpdate(rs.getDate(5));
                Short categoryId = rs.getShort(6);
                product.setCategory(categoryFacade.find(categoryId));
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
                
        return product;
    }

}