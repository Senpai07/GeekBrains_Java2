package lesson4;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ClientChat extends Application {

  public static void main(String[] args) {
    launch(args);
  }

  @Override
  public void start(Stage primaryStage) throws Exception{
    Parent root = FXMLLoader.load(getClass().getResource("mainScene.fxml"));
    primaryStage.setTitle("Chat client 1.0");
    primaryStage.setScene(new Scene(root));
    primaryStage.show();
  }
}
