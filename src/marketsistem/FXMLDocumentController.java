/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package marketsistem;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javax.swing.JOptionPane;


/**
 *
 * @author ASUS
 */
public class FXMLDocumentController implements Initializable {
    
    @FXML
    private Label label;
    
    @FXML
    private Button close;

    @FXML
    private Button create_closebttn;

    @FXML
    private Button create_createbttn;

    @FXML
    private Button create_loginbttn;

    @FXML
    private PasswordField create_password;

    @FXML
    private TextField create_username;

    @FXML
    private AnchorPane createform;

    @FXML
    private Button login_bttnsignup;

    @FXML
    private FontAwesomeIcon login_closebttn;

    @FXML
    private Button login_loginbttn;

    @FXML
    private PasswordField login_password;

    @FXML
    private TextField login_username;

    @FXML
    private AnchorPane loginform;
    
    //database
    private Connection connect;
    private ResultSet result;
    private PreparedStatement prepare;
    
    private double x= 0;
    private double y= 0;
    
    public void login(){
       
        connect = database.connectDb();
        String adminData = "SELECT * FROM admin WHERE username = ? and password = ?";
        try{
            Alert alert;
                prepare = connect.prepareStatement(adminData);
                
                if(login_username.getText().isEmpty() || login_password.getText().isEmpty()){
                    alert = new Alert(AlertType.ERROR);
                    alert.setTitle("Error Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Please fill all blank fields");
                    alert.showAndWait();
                } else {
                prepare.setString(1, login_username.getText());
                prepare.setString(2,login_password.getText());
                }
                result = prepare.executeQuery();
                
                
                
                if(result.next()){
                    
                    GetData.username = login_username.getText();
                    
                    JOptionPane.showMessageDialog(null, "Successfully Login", "", 
                            JOptionPane.INFORMATION_MESSAGE);
                    
                    login_loginbttn.getScene().getWindow().hide();
                    
                    Parent root = FXMLLoader.load(getClass().getResource("adminDashboard.fxml"));
                    Stage stage = new Stage(); 
                    Scene scene = new Scene(root);
                    
                    root.setOnMousePressed((MouseEvent event) ->{
                        x = event.getSceneX();
                        y = event.getSceneY();
                    });

                    root.setOnMouseDragged((MouseEvent event) ->{
                       stage.setX(event.getScreenX() - x);
                       stage.setY(event.getScreenY() - y);

                       stage.setOpacity(.8);
                    });

                    root.setOnMouseReleased((MouseEvent event) ->{
                        stage.setOpacity(1);
                    });

                    stage.initStyle(StageStyle.TRANSPARENT);

                    stage.setScene(scene);
                    stage.setTitle("smpl dashboard");
                    stage.show();
                }else{
                    JOptionPane.showMessageDialog(null, "Wrong Username/password", "",
                            JOptionPane.ERROR_MESSAGE);
                }
                //40:49 
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void signup(ActionEvent event){
        connect = database.connectDb();
        
        String adminData = "INSERT INTO admin VALUES(?, ?)";
        try{
                Alert alert;
                prepare = connect.prepareStatement(adminData);
                
                if(create_username.getText().isEmpty() || create_password.getText().isEmpty()){
                    alert = new Alert(AlertType.ERROR);
                    alert.setTitle("Error Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Please fill all blank fields");
                    alert.showAndWait();
                } else {
                prepare.setString(1, create_username.getText());
                prepare.setString(2, create_password.getText());
                }
                prepare.execute();
                
                JOptionPane.showMessageDialog(null, 
                        "Successfull create new Account!", "", 
                        JOptionPane.INFORMATION_MESSAGE);
            
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    

    public void switchForm(ActionEvent event){
        if(event.getSource()== login_bttnsignup){
            loginform.setVisible(false);
            createform.setVisible(true);
        }else if(event.getSource()== create_loginbttn){
            loginform.setVisible(true);
            createform.setVisible(false);
        }
    }
    
    
    
    public void close(){
        System.exit(0);
    }


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
