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
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author jubai
 */
public class FXMLHomeController implements Initializable {
    ObservableList<WorksModel> oblist = FXCollections.observableArrayList();
    
    public static String idd = null;
    
    @FXML
    private TableView<WorksModel> tableView;
    @FXML
    private TableColumn<WorksModel, String> id;
    @FXML
    private TableColumn<WorksModel, String> username;
    @FXML
    private TableColumn<WorksModel, String> works;
    @FXML
    private TableColumn<WorksModel, String> place;
    @FXML
    private TableColumn<WorksModel, String> date;
    @FXML
    private TableColumn<WorksModel, String> time;
    @FXML
    private Button deleteworks;
    @FXML
    private Button editworks;
    @FXML
    private Button addworks;
    @FXML
    private Button addUser;
    @FXML
    private Button logout;
    @FXML
    private AnchorPane p;

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
            Connection conn = DbConnection.getConnection();
            String sql = "select * from works";
            ResultSet rs = conn.createStatement().executeQuery(sql);

            while (rs.next()) {
                oblist.add(new WorksModel(rs.getString("id"), rs.getString("username"), rs.getString("works"), rs.getString("place"), rs.getString("date"), rs.getString("time")
                ));
            }

        } catch (SQLException ex) {
            System.out.print(ex);
        }
        id.setCellValueFactory(new PropertyValueFactory<>("id"));
        username.setCellValueFactory(new PropertyValueFactory<>("username"));
        works.setCellValueFactory(new PropertyValueFactory<>("works"));
        place.setCellValueFactory(new PropertyValueFactory<>("place"));
        date.setCellValueFactory(new PropertyValueFactory<>("date"));
        time.setCellValueFactory(new PropertyValueFactory<>("time"));

        tableView.setItems(oblist);
    }    

    @FXML
    private void deleteworksAction(ActionEvent event) throws IOException {
        try {
            WorksModel user = (WorksModel) tableView.getSelectionModel().getSelectedItem();
            connection = DbConnection.getConnection();
            String query = "DELETE  from works where id=?";

            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, user.getId());
            preparedStatement.executeUpdate();
            
            reload();
            success.setText("Works has been Deleted Successfully");
            preparedStatement.close();
                        
        } catch (SQLException ex) {
            System.out.println(ex);
        }
        
    }
    
    private void reload() {
        oblist.clear();

        try {
            Connection conn = DbConnection.getConnection();
            String sql = "select * from works";
            ResultSet rs = conn.createStatement().executeQuery(sql);

            while (rs.next()) {
                oblist.add(new WorksModel(rs.getString("id"), rs.getString("username"), rs.getString("works"), rs.getString("place"), rs.getString("date"), rs.getString("time")
                ));
            }

        } catch (SQLException ex) {
            System.out.print(ex);
        }
        id.setCellValueFactory(new PropertyValueFactory<>("id"));
        username.setCellValueFactory(new PropertyValueFactory<>("username"));
        works.setCellValueFactory(new PropertyValueFactory<>("works"));
        place.setCellValueFactory(new PropertyValueFactory<>("place"));
        date.setCellValueFactory(new PropertyValueFactory<>("date"));
        time.setCellValueFactory(new PropertyValueFactory<>("time"));

        tableView.setItems(oblist);

    }

    @FXML
    private void editworksAction(ActionEvent event) throws IOException {
        WorksModel user = (WorksModel) tableView.getSelectionModel().getSelectedItem();
        idd = user.getId();
        Parent home_page_parent = FXMLLoader.load(getClass().getResource("WorksAdd.fxml"));
        Scene home_page_scene = new Scene(home_page_parent);
        Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        app_stage.hide();
        app_stage.setScene(home_page_scene);
        app_stage.show();
    }

    @FXML
    private void addworksAction(ActionEvent event) throws IOException {
        Parent home_page_parent = FXMLLoader.load(getClass().getResource("WorksAdd.fxml"));
        Scene home_page_scene = new Scene(home_page_parent);
        Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        app_stage.hide();
        app_stage.setScene(home_page_scene);
        app_stage.show();
    }

    @FXML
    private void addUserAction(ActionEvent event) throws IOException {
        Parent home_page_parent = FXMLLoader.load(getClass().getResource("FXMLAddUser.fxml"));
        Scene home_page_scene = new Scene(home_page_parent);
        Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        app_stage.hide();
        app_stage.setScene(home_page_scene);
        app_stage.show();
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
