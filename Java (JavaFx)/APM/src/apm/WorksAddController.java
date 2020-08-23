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
import java.util.logging.Level;
import java.util.logging.Logger;
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
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author jubai
 */
public class WorksAddController implements Initializable {
    ObservableList list = FXCollections.observableArrayList();
    
    @FXML
    private ChoiceBox<String> employee;
    @FXML
    private TextField place;
    @FXML
    private TextField date;
    @FXML
    private TextField time;
    @FXML
    private TextArea works;
    @FXML
    private Button saveWorks;
    @FXML
    private Button backHome;

    @FXML
    private Label success;
    
    private Connection connection;
    private PreparedStatement preparedStatement;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            loadData();
            String query = "select * from works where id = ?";
            Connection connection = DbConnection.getConnection();

            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, FXMLHomeController.idd);

            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {

                employee.setValue(rs.getString("username"));
                place.setText(rs.getString("place"));
                date.setText(rs.getString("date"));
                time.setText(rs.getString("time"));
                works.setText(rs.getString("works"));
            }
            preparedStatement.close();
            rs.close();
        } catch (Exception e) {

            System.out.println(e);
        }
        
    }    

    @FXML
    private void saveWorksAction(ActionEvent event) {
        try {
            connection = DbConnection.getConnection();
            String sql;
            if(FXMLHomeController.idd==null)
                sql = "insert into works  (username, works, place, date, time) values (?,?,?,?,?) ";
            else 
                sql = "Update  works  set  username=?, works=?, place=?, date=? , time=? where id ='" + FXMLHomeController.idd + "'";
            
            preparedStatement = connection.prepareStatement(sql);

            
            preparedStatement.setString(1, employee.getValue());
            preparedStatement.setString(2, works.getText());
            preparedStatement.setString(3, place.getText());
            preparedStatement.setString(4, date.getText());
            preparedStatement.setString(5, time.getText());

            preparedStatement.execute();
            
            if(FXMLHomeController.idd==null)
                success.setText("New Works has been Added Successfully");
            else 
                success.setText("Works has been Updated Successfully");

            preparedStatement.close();

            works.clear();
            place.clear();
            date.clear();
            time.clear();
            FXMLHomeController.idd = null;

        } catch (Exception ex) {
            System.out.println(ex);
        }
    }

    @FXML
    private void backHomeAction(ActionEvent event) throws IOException {
        FXMLHomeController.idd = null;
        Parent home_page_parent = FXMLLoader.load(getClass().getResource("FXMLHome.fxml"));
        Scene home_page_scene = new Scene(home_page_parent);
        Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        app_stage.hide();
        app_stage.setScene(home_page_scene);
        app_stage.show();
    }
    
    private void loadData() throws SQLException
    {
        list.removeAll(list);
        String query = "select * from users";
        connection = DbConnection.getConnection();
        ResultSet rs = connection.createStatement().executeQuery(query);
        while (rs.next())
        {
            String s = rs.getString("username");
            list.addAll(s);
        }
        employee.getItems().addAll(list);
    }
    
}
