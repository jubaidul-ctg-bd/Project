/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package apm;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author ASUS
 */
public class UserViewController implements Initializable {

    @FXML
    private TableView<FindingWork> UserTable;
    @FXML
    private TableColumn<FindingWork, String> Workcol;
    @FXML
    private TableColumn<FindingWork, String> Plccol;
    @FXML
    private TableColumn<FindingWork, String> timecol;
    @FXML
    private TableColumn<FindingWork, String> datecol;
    @FXML
    private Button logout;
    ObservableList<FindingWork> oblist = FXCollections.observableArrayList();
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            
            Connection conn = DbConnection.getConnection();
            String sql = "select * from works where username= '" + FXMLLoginController.uname + "'";
            ResultSet rs = conn.createStatement().executeQuery(sql);

            while (rs.next()) {
                oblist.add(new FindingWork(rs.getString("works"),rs.getString("place"), rs.getString("time"),rs.getString("date")));
            }

        } catch (SQLException ex) {
            System.out.print(ex);
        }
        Workcol.setCellValueFactory(new PropertyValueFactory<>("works"));
        Plccol.setCellValueFactory(new PropertyValueFactory<>("place"));
        timecol.setCellValueFactory(new PropertyValueFactory<>("date"));
        datecol.setCellValueFactory(new PropertyValueFactory<>("time"));

        UserTable.setItems(oblist);
    }
    
    @FXML
    private void logoutAction(ActionEvent event) throws IOException {
        FXMLLoginController.uname = null;
        Parent home_page_parent = FXMLLoader.load(getClass().getResource("FXMLLogin.fxml"));
        Scene home_page_scene = new Scene(home_page_parent);
        Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        app_stage.hide();
        app_stage.setScene(home_page_scene);
        app_stage.show();
    }
}    

    
    

