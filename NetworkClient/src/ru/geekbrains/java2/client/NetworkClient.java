package ru.geekbrains.java2.client;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import ru.geekbrains.java2.client.controller.ClientController;

import java.io.IOException;

public class NetworkClient extends Application {

  public static void main(String[] args) {
    launch(args);
  }

  @Override
  public void start(Stage authStage) throws Exception {
    Parent root = FXMLLoader.load(getClass().getResource("view/AuthDialog.fxml"));
    authStage.setTitle("Chat client 1.0");
    authStage.setScene(new Scene(root));
    authStage.show();
    try {
      ClientController clientController = new ClientController("localhost", 8189);
      clientController.runApplication();
    } catch (IOException e) {
      System.err.println("Failed to connect to server! Please, check you network settings");
    }
  }
}
