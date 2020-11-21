/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package englishapp;

import com.dht.pojo.Category;
import com.dht.pojo.Choice;
import com.dht.pojo.Question;
import com.dht.services.CategoryServices;
import com.dht.services.QuestionServices;
import com.dht.services.Utils;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 *
 * @author Admin
 */
public class FXMLDocumentController implements Initializable {
    @FXML private ComboBox<Category> cbCategories;
    @FXML private TextField txtContent;
    @FXML private TextField txtA;
    @FXML private TextField txtB;
    @FXML private TextField txtC;
    @FXML private TextField txtD;
    @FXML private CheckBox chkA;
    @FXML private CheckBox chkB;
    @FXML private CheckBox chkC;
    @FXML private CheckBox chkD;
    @FXML TableView<Question> tbQuestions;
    @FXML TextField txtKeyword;
   
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            // TODO
            this.cbCategories.getItems().addAll(CategoryServices.getCategories());
            this.loadQuestions("");
            
             
        } catch (SQLException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
       txtKeyword.textProperty().addListener(e -> {
            try {
                loadQuestions(txtKeyword.getText());
            } catch (SQLException ex) {
                Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
       
       tbQuestions.setRowFactory(r -> {
           TableRow row = new TableRow();
           row.setOnMouseClicked(evt -> {
               try {
                   Question q = tbQuestions.getSelectionModel().getSelectedItem();
                   txtContent.setText(q.getContent());
                   
                   Category cat = CategoryServices.getCategoryById(q.getCategoryId());
                   cbCategories.getSelectionModel().select(cat);
                   
                   List<Choice> choices = QuestionServices.getChoicesByQuestionId(q.getId());
                   txtA.setText(choices.get(0).getContent());
                   txtB.setText(choices.get(1).getContent());
                   txtC.setText(choices.get(2).getContent());
                   txtD.setText(choices.get(3).getContent());
                   
                   chkA.setSelected(choices.get(0).isCorrect());
                   chkB.setSelected(choices.get(1).isCorrect());
                   chkC.setSelected(choices.get(2).isCorrect());
                   chkD.setSelected(choices.get(3).isCorrect());
               } catch (SQLException ex) {
                   Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
               }
           });
           
           return row;
       });
    }
    
    public void addQuestionHandler(ActionEvent evt) {
        Question q = new Question(UUID.randomUUID().toString(), 
                txtContent.getText(), 
                cbCategories.getSelectionModel().getSelectedItem().getId());
        List<Choice> choices = new ArrayList<>();
        choices.add(new Choice(UUID.randomUUID().toString(), 
                txtA.getText(), q.getId(), chkA.isSelected()));
        choices.add(new Choice(UUID.randomUUID().toString(), 
                txtB.getText(), q.getId(), chkB.isSelected()));
        choices.add(new Choice(UUID.randomUUID().toString(), 
                txtC.getText(), q.getId(), chkC.isSelected()));
        choices.add(new Choice(UUID.randomUUID().toString(), 
                txtD.getText(), q.getId(), chkD.isSelected()));
        
//        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        if (QuestionServices.addQuestion(q, choices) == true) {
            Utils.getAlert("SUCCESSUL", Alert.AlertType.INFORMATION).show();
//            alert.setContentText("SUCCESSFUL");
        } else {
            Utils.getAlert("FAILED", Alert.AlertType.ERROR).show();
//            alert.setContentText("FAILED");
        }
        
//        alert.show();
    }
    
    public void updateQuestionHandler(ActionEvent evt) {
        Question q = tbQuestions.getSelectionModel().getSelectedItem();
        q.setContent(txtContent.getText());
        q.setCategoryId(cbCategories.getSelectionModel().getSelectedItem().getId());
                   
        try {
            List<Choice> choices = QuestionServices.getChoicesByQuestionId(q.getId());
            
            choices.get(0).setContent(txtA.getText());
            choices.get(0).setCorrect(chkA.isSelected());
            
            choices.get(1).setContent(txtB.getText());
            choices.get(1).setCorrect(chkB.isSelected());
            
            choices.get(2).setContent(txtC.getText());
            choices.get(2).setCorrect(chkC.isSelected());
            
            choices.get(3).setContent(txtD.getText());
            choices.get(3).setCorrect(chkD.isSelected());
            
            if (QuestionServices.updateQuestion(q, choices) == true) {
                Utils.getAlert("UPDATE SUCCESSUL", Alert.AlertType.INFORMATION).show();
            } else {
                Utils.getAlert("UPDATE FAILED", Alert.AlertType.ERROR).show();
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    private void loadQuestions(String kw) throws SQLException {
        TableColumn colId = new TableColumn("Mã câu hỏi");
        colId.setCellValueFactory(new PropertyValueFactory("id"));
        
        TableColumn colContent = new TableColumn("Nội dung câu hỏi");
        colContent.setCellValueFactory(new PropertyValueFactory("content"));
        
        TableColumn colAction = new TableColumn();
        colAction.setCellFactory(v -> {
            Button btn = new Button("Delete");
            btn.setOnAction(evt -> {
                Question q = (Question) ((TableCell)((Button) evt.getSource()).getParent())
                                                                 .getTableRow().getItem();
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setContentText("Thao tác sẽ xóa luôn các lựa chọn liên quan. Bạn chắc chắn xóa không?");
                alert.showAndWait().ifPresent(res -> {
                    if (res == ButtonType.OK) 
                        if (QuestionServices.deleteQuestion(q.getId()))
                            try {
                                loadQuestions("");
                            } catch (SQLException ex) {
                                Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
                            }
                });
            });
            
            TableCell cell = new TableCell();
            cell.setGraphic(btn);
            return cell;
        });
        
        tbQuestions.getColumns().addAll(colId, colContent, colAction);
        tbQuestions.getItems().clear();
        tbQuestions.setItems(FXCollections.observableArrayList(QuestionServices.getQuestions(kw)));
    }
}
