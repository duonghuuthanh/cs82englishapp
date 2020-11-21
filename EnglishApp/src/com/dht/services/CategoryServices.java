/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dht.services;

import com.dht.pojo.Category;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Admin
 */
public class CategoryServices {
    public static List<Category> getCategories() throws SQLException {
        Connection conn = Utils.getConn();
        Statement stm = conn.createStatement();
        ResultSet rs = stm.executeQuery("SELECT * FROM category");
        
        List<Category> kq = new ArrayList<>();
        while (rs.next()) {
            int id = rs.getInt("id");
            String name = rs.getString("name");
            Category c = new Category(id, name);
            
            kq.add(c);
        }
        
        return kq;
    }
    
    public static Category getCategoryById(int id) throws SQLException {
        Connection conn = Utils.getConn();
        PreparedStatement stm = conn.prepareStatement("SELECT * FROM category WHERE id=?");
        stm.setInt(1, id);
        
        ResultSet rs = stm.executeQuery();
        while (rs.next()) {
            int idd = rs.getInt("id");
            String content = rs.getString("name");
         
            Category cat = new Category(idd, content);
            
            return cat;
        }
        
        return null;
    }
}
