/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dht.test1;

import com.dht.pojo.Category;
import com.dht.services.CategoryServices;
import com.dht.services.Utils;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author Admin
 */
public class CategoryTester {
    private static Connection conn;
    private List<Category> cats;
    
    @BeforeClass
    public static void setUp() {
        conn = Utils.getConn();
    }
    
    @AfterClass
    public static void tearDown() {
        try {
            conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(CategoryTester.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Before
    public void initTc() {
        try {
            cats = CategoryServices.getCategories();
        } catch (SQLException ex) {
            Logger.getLogger(CategoryTester.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Test
    public void testCateName() {
        for (Category c: cats)
            Assert.assertNotEquals("", c.getName().trim());
    }
    
    @Test
    public void testMinCates() {
        Assert.assertTrue(cats.size() >= 1);
    }
}
