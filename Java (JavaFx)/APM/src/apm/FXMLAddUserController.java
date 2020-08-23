/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package apm;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author ASUS
 */
public class FXMLAddUserController implements Initializable {

    @FXML
    private TextField name11;
    @FXML
    private TextField username111;
    @FXML
    private TextField password;
    @FXML
    private TextField phone111;
    @FXML
    private TextField address;
    @FXML
    private TextField email;
    @FXML
    private Button back;
    @FXML
    private Button saveuser;
    @FXML
    private TableView<UserModel> table;
    @FXML
    private TableColumn<UserModel, String> namcCol;
    @FXML
    private TableColumn<UserModel, String> usernameCol;
    @FXML
    private TableColumn<UserModel, String> phoneCol;
    
    
    ObservableList<UserModel> oblist = FXCollections.observableArrayList();
    private Connection connection;
    private PreparedStatement preparedStatement;
    
    @FXML
    private Label success; 
    @FXML
    private Label faild;
    @FXML
    private Button btndel;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //makeDragable();
        try {
            Connection conn = DbConnection.getConnection();
            String sql = "select * from users";
            ResultSet rs = conn.createStatement().executeQuery(sql);

            while (rs.next()) {
                oblist.add(new UserModel(rs.getString("name"), rs.getString("username"), rs.getString("phone")));
            }

        } catch (SQLException ex) {
            System.out.print(ex);
        }
        namcCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        usernameCol.setCellValueFactory(new PropertyValueFactory<>("username"));
        phoneCol.setCellValueFactory(new PropertyValueFactory<>("phone"));
       

        table.setItems(oblist);
    }     

    @FXML
    private void backAction(ActionEvent event) throws IOException {
        Parent home_page_parent = FXMLLoader.load(getClass().getResource("FXMLHome.fxml"));
        Scene home_page_scene = new Scene(home_page_parent);
        Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        app_stage.hide();
        app_stage.setScene(home_page_scene);
        app_stage.show();
    }

    @FXML
    private void saveuserAction(ActionEvent event) {
        try {
            faild.setText("");
            success.setText("");
            connection = DbConnection.getConnection();
            String sql = "select * from users where username='" + username111.getText() + "'";
            ResultSet rs = connection.createStatement().executeQuery(sql);
            if(rs.next() || name11.getText()==null || username111.getText()==null || password.getText()==null){
                faild.setText("User name should be unique, username and password should not be null");
            } else {
                sql = "insert into users  (name, username, password, email, phone, address, type) values (?,?,?,?,?,?,?) ";

                preparedStatement = connection.prepareStatement(sql);

                String t = "User";
                preparedStatement.setString(1, name11.getText());
                preparedStatement.setString(2, username111.getText());
                preparedStatement.setString(3, password.getText());
                preparedStatement.setString(4, email.getText());
                preparedStatement.setString(5, phone111.getText());
                preparedStatement.setString(6, address.getText());
                preparedStatement.setString(7, t);

                preparedStatement.execute();

                reload();
                success.setText("New User has been Added Successfully");

                preparedStatement.close();

                name11.clear();
                username111.clear();
                password.clear();
                email.clear();
                phone111.clear();
                address.clear();
            }

        } catch (Exception ex) {
            System.out.println(ex);
        }
    }
    
    private void reload()
    {
        oblist.clear();
        try {
            Connection conn = DbConnection.getConnection();
            String sql = "select * from users";
            ResultSet rs = conn.createStatement().executeQuery(sql);

            while (rs.next()) {
                oblist.add(new UserModel(rs.getString("name"), rs.getString("username"), rs.getString("phone")));
            }

        } catch (SQLException ex) {
            System.out.print(ex);
        }
        namcCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        usernameCol.setCellValueFactory(new PropertyValueFactory<>("username"));
        phoneCol.setCellValueFactory(new PropertyValueFactory<>("phone"));
       
        table.setItems(oblist);
    }

    @FXML
    private void btndelaction(ActionEvent event) {
        try {
            faild.setText("");
            success.setText("");
            UserModel user = (UserModel) table.getSelectionModel().getSelectedItem();
            connection = DbConnection.getConnection();
            String query = "DELETE  from users where username=?";

            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, user.getUsername());
            preparedStatement.executeUpdate();
            
            reload();
            success.setText("Users has been Deleted Successfully");
            preparedStatement.close();
                        
        } catch (SQLException ex) {
            System.out.println(ex);
        }
    }
    
}
