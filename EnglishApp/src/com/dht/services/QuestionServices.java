/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dht.services;

import com.dht.pojo.Choice;
import com.dht.pojo.Question;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Admin
 */
public class QuestionServices {
    
    public static boolean updateQuestion(Question q, List<Choice> choices) {
        Connection conn = Utils.getConn();
        try {
            conn.setAutoCommit(false);
            String sql = "UPDATE question SET content=?, category_id=? WHERE id=?";
            PreparedStatement stm = conn.prepareStatement(sql);
            
            stm.setString(1, q.getContent());
            stm.setInt(2, q.getCategoryId());
            stm.setString(3, q.getId());

            int r = stm.executeUpdate();
            if (r > 0 && choices != null) {
                for (Choice c: choices) {
                    sql = "UPDATE choice "
                            + "SET content=?, question_id=?, is_correct=? "
                            + "WHERE id=?";
                    stm = conn.prepareStatement(sql);
                    
                    stm.setString(1, c.getContent());
                    stm.setString(2, q.getId());
                    stm.setBoolean(3, c.isCorrect());
                    stm.setString(4, c.getId());
                    stm.executeUpdate();
                }
                
                conn.commit();
            
                return true;
            }
            
        } catch (SQLException ex) {
            ex.getStackTrace();
            try {
                conn.rollback();
            } catch (SQLException ex1) {
                Logger.getLogger(QuestionServices.class.getName()).log(Level.SEVERE, null, ex1);
            }
        }
        
         return false;
    }
   
    public static boolean addQuestion(Question q, List<Choice> choices) {
        Connection conn = Utils.getConn();
        try {
            conn.setAutoCommit(false);
            String sql = "INSERT INTO question(content, category_id, id) VALUES (?, ?, ?)";
            PreparedStatement stm = conn.prepareStatement(sql);
            stm.setString(1, q.getContent());
            stm.setInt(2, q.getCategoryId());
            stm.setString(3, q.getId());

            int r = stm.executeUpdate();
            if (r > 0 && choices != null) {
                for (Choice c: choices) {
                    sql = "INSERT INTO choice(content, question_id, is_correct, id) VALUES (?, ?, ?, ?)";
                    stm = conn.prepareStatement(sql);
                    stm.setString(1, c.getContent());
                    stm.setString(2, q.getId());
                    stm.setBoolean(3, c.isCorrect());
                    stm.setString(4, c.getId());
                    stm.executeUpdate();
                }
                
                conn.commit();
            
                return true;
            }
            
        } catch (SQLException ex) {
            ex.getStackTrace();
            try {
                conn.rollback();
            } catch (SQLException ex1) {
                Logger.getLogger(QuestionServices.class.getName()).log(Level.SEVERE, null, ex1);
            }
        }
        
         return false;
    }
    
    public static List<Question> getQuestions(String kw) throws SQLException {
        Connection conn = Utils.getConn();
        
        
        String sql = "SELECT * FROM question";
        if (kw != null && !kw.isEmpty())
            sql += " WHERE content like ?";
        
        
        PreparedStatement stm = conn.prepareStatement(sql);
        if (kw != null && !kw.isEmpty())
            stm.setString(1, String.format("%%%s%%", kw));
        
        ResultSet rs = stm.executeQuery();
        
        List<Question> questions = new ArrayList<>();
        while (rs.next()) {
            String id = rs.getString("id");
            String content = rs.getString("content");
            int cateId = rs.getInt("category_id");
            
            Question q = new Question(id, content, cateId);
            questions.add(q);
        }
        
        return questions;
    }
    
    public static Question getQuestionById(String id) throws SQLException  {
        Connection conn = Utils.getConn();
        PreparedStatement stm = conn.prepareStatement("SELECT * FROM question WHERE id=?");
        stm.setString(1, id);
        
        ResultSet rs = stm.executeQuery();
        while (rs.next()) {
            String idd = rs.getString("id");
            String content = rs.getString("content");
            int cateId = rs.getInt("category_id");
            
            Question q = new Question(idd, content, cateId);
            
            return q;
        }
        
        return null;
    }
    
    public static List<Choice> getChoicesByQuestionId(String questionId) throws SQLException {
        Connection conn = Utils.getConn();
        PreparedStatement stm = conn.prepareStatement("SELECT * FROM choice WHERE question_id=?");
        stm.setString(1, questionId);
        
        ResultSet rs = stm.executeQuery();
        
        List<Choice> kq = new ArrayList<>();
        while (rs.next()) {
            String idd = rs.getString("id");
            String content = rs.getString("content");
            boolean correct = rs.getBoolean("is_correct");
            
            Choice c = new Choice(idd, content, questionId, correct);
            
            kq.add(c);
        }
        
        return kq;
    }
    
    public static boolean deleteQuestion(String questionId) {
        try {
            Connection conn = Utils.getConn();
            String sql = "DELETE FROM question WHERE id=?";
            PreparedStatement stm = conn.prepareStatement(sql);
            stm.setString(1, questionId);
            
            return stm.executeUpdate() >= 0;
        } catch (SQLException ex) {
            Logger.getLogger(QuestionServices.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return false;
    }
}
