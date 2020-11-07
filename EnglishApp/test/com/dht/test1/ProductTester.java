/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dht.test1;

import com.dht.pojo.Question;
import com.dht.services.QuestionServices;
import com.dht.services.Utils;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author Admin
 */
public class ProductTester {
    private static Connection conn;
    
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
    
    @Test
    public void testWithoutCategories() {
        Question q = new Question(UUID.randomUUID().toString(), "A", 0);
        boolean kq = QuestionServices.addQuestion(q, null);
        Assert.assertFalse(kq);
    }
    
    @Test
    public void testChoiceIsNull() {
        Question q = new Question(UUID.randomUUID().toString(), "A", 1);
        boolean kq = QuestionServices.addQuestion(q, null);
        
        Assert.assertFalse(kq);
    }
    
    @Test
    public void testNoChoices() {
        
    }
    
    
    @Test
    public void testNoQuestionContent() {
        
    }
    
    @Test
    public void testNoChoiceContent() {
        
    }
    
    public void testTwoCorrectAnswers() {
        
    }
}
