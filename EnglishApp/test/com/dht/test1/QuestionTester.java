/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dht.test1;

import com.dht.pojo.Choice;
import com.dht.pojo.Question;
import com.dht.services.QuestionServices;
import com.dht.services.Utils;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

/**
 *
 * @author Admin
 */
public class QuestionTester {
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
    
    @Test
    public void testTwoCorrectAnswers() {
        
    }
    
    @Test
    public void testAddSuccessful() {
        String id = UUID.randomUUID().toString();
        String content = "Test Question";
        Question q = new Question(id, content, 1);
        
        List<Choice> choices = new ArrayList<>();
        choices.add(new Choice(UUID.randomUUID().toString(), "A", id, true));
        choices.add(new Choice(UUID.randomUUID().toString(), "B", id, false));
        
        Assert.assertTrue(QuestionServices.addQuestion(q, choices));
        
        try {
            Question question = QuestionServices.getQuestionById(id);
            Assert.assertEquals(content, question.getContent());
            Assert.assertEquals(2, QuestionServices.getChoicesByQuestionId(id).size());
        } catch (SQLException ex) {
            Logger.getLogger(QuestionTester.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Test
    public void testSearchQuestion() {
        try {
            List<Question> kq1 = QuestionServices.getQuestions("");
            List<Question> kq2 = QuestionServices.getQuestions(null);
            
            Assert.assertEquals(kq1.size(), kq2.size());
            
            for (int i = 0; i < kq1.size(); i++)
                Assert.assertEquals(kq1.get(i).getId(), kq2.get(i).getId());
        } catch (SQLException ex) {
            Logger.getLogger(QuestionTester.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Test
//    @Ignore
    public void testSearchWithKeyword() {
        try {
            List<Question> kq = QuestionServices.getQuestions("Question");
            
            Assert.assertEquals(3, kq.size());
        } catch (SQLException ex) {
            Logger.getLogger(QuestionTester.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    @Test
    public void testDeleteSuccessful() {
        String id = UUID.randomUUID().toString();
        String content = "Test Question";
        Question q = new Question(id, content, 1);
        
        List<Choice> choices = new ArrayList<>();
        choices.add(new Choice(UUID.randomUUID().toString(), "A", id, true));
        choices.add(new Choice(UUID.randomUUID().toString(), "B", id, false));
        choices.add(new Choice(UUID.randomUUID().toString(), "C", id, false));
        choices.add(new Choice(UUID.randomUUID().toString(), "D", id, false));
        
        QuestionServices.addQuestion(q, choices);
        
        boolean kq = QuestionServices.deleteQuestion(id);
        Assert.assertTrue(kq);
        
        List<Choice> kq2;
        try {
            kq2 = QuestionServices.getChoicesByQuestionId(id);
            Assert.assertEquals(0, kq2.size());
            
            Question kq3 = QuestionServices.getQuestionById(id);
            Assert.assertNull(kq3);
        } catch (SQLException ex) {
            Logger.getLogger(QuestionTester.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Test
    public void testUpdateSuccessful()  {
//        Question q = tbQuestions.getSelectionModel().getSelectedItem();
//        q.setContent(txtContent.getText());
//        q.setCategoryId(cbCategories.getSelectionModel().getSelectedItem().getId());
        
        try {                   
            Question q = QuestionServices.getQuestionById("88ad8bc2-5ad9-4b64-a3d3-b3c195b38f4a");
            q.setContent("new");
            q.setCategoryId(4);
            
            List<Choice> choices = QuestionServices.getChoicesByQuestionId(q.getId());
            choices.get(0).setCorrect(false);
            
            boolean kq = QuestionServices.updateQuestion(q, choices);
            Assert.assertTrue(kq);
            
            Question q2 = QuestionServices.getQuestionById("88ad8bc2-5ad9-4b64-a3d3-b3c195b38f4a");
            Assert.assertEquals("new", q2.getContent());
            Assert.assertEquals(4, q2.getCategoryId());
            
            choices = QuestionServices.getChoicesByQuestionId(q.getId());
            Assert.assertFalse(choices.get(0).isCorrect());
            
        } catch (SQLException ex) {
            Logger.getLogger(QuestionTester.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
            
}
