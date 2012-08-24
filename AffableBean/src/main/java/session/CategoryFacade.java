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
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import entity.Category;

/**
 *
 * @author tgiunipero
 */ 
public class CategoryFacade extends AbstractFacade<Category> {  

    public CategoryFacade() {
        super(Category.class);
    }
    
    public List<Category> getAll(){
        List<Category> categories = new ArrayList<Category>();
        
        Connection con = util.getConnection();
        Statement statement = null;
        try {
            statement = con.createStatement();
            ResultSet rs = statement.executeQuery("select * from category");
            while(rs.next()){
                Category category = new Category();
                category.setId(rs.getShort(1));
                category.setName(rs.getString(2));
                categories.add(category);
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
                
        return categories;
    }
    
    public Category find(Short id) { 
        Category category = new Category();
        Connection con = util.getConnection();
        String sql = "select * from category where id=?";
        
        PreparedStatement statement = null;
        try {
            statement = con.prepareStatement(sql);
            statement.setShort(1, id);
            
            ResultSet rs = statement.executeQuery();
            while(rs.next()){ 
                category.setId(rs.getShort(1));
                category.setName(rs.getString(2));
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
                
        return category;
    }

}