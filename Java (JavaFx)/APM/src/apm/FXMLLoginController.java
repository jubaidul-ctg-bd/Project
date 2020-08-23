 /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package apm;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author jubai
 */
public class FXMLLoginController implements Initializable {

    private static void showAlert(String login_Successful) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    ObservableList list = FXCollections.observableArrayList();
    
    public static String uname = null;
    
    @FXML
    private ChoiceBox<String> Type;
    
    @FXML
    private TextField username;
    
    @FXML
    private PasswordField password;
    
    public void handleButtonAction(ActionEvent event) throws IOException {
        
        //System.out.println(username.getText() + " " + password.getText());
        try {
            Connection conn = DbConnection.getConnection();
            String sql = "select * from users where username='" + username.getText() + "' and password = '"+ password.getText()+"' and type = '"+ Type.getValue()+"'";
            ResultSet rs = conn.createStatement().executeQuery(sql);
            if(rs.next()){
                uname = username.getText();
                Parent home_page_parent;
                if("Admin".equals(Type.getValue()))
                    home_page_parent = FXMLLoader.load(getClass().getResource("FXMLHome.fxml"));
                else 
                    home_page_parent = FXMLLoader.load(getClass().getResource("UserView.fxml"));
                Scene home_page_scene = new Scene(home_page_parent);
                Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                app_stage.hide();
                app_stage.setScene(home_page_scene);
                app_stage.show();
            }
            else {
                showAlert("Email or password do not match");
            }
                
        } catch (SQLException ex) {
                ex.printStackTrace();
        }
        
        
        
        
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        loadData();
    }    
    private void loadData()
    {
        list.removeAll(list);
        String a="Admin";
        String b="User";
        list.addAll(a, b);
        Type.getItems().addAll(list);
    }
    
}
