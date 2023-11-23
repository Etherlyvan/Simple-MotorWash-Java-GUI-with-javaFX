import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
 
public class App extends Application {
    @Override
    public void start(Stage primaryStage) {
        
  
  
    Parent root;
    try {
        root = FXMLLoader.load(getClass().getResource("Login.fxml"));
         Scene scene = new Scene(root);
  
  
    
  
        primaryStage.setTitle("Zero Logic!");
        primaryStage.setScene(scene);
        primaryStage.show();
    } catch (IOException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
    }
 
    }
 
 public static void main(String[] args) {
    try{
            String url = "jdbc:sqlserver://localhost:1433;databaseName=FlashLight;IntegratedSecurity=true;encrypt=false;";
            try( Connection connection = DriverManager.getConnection(url)){
                System.out.println("Connected");
            }
        }
        catch(SQLException e){
            
            System.out.println("Error Connection");
            e.printStackTrace();
        }

        launch(args);
    }
}