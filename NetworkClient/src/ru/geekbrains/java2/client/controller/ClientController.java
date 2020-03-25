package ru.geekbrains.java2.client.controller;

import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import ru.geekbrains.java2.client.model.NetworkService;
import ru.geekbrains.java2.client.view.AuthDialog;
import ru.geekbrains.java2.client.view.ClientChat;
import ru.geekbrains.java2.client.view.Message;

import javax.swing.*;
import java.io.IOException;

public class ClientController {

  private final NetworkService networkService;
  private String nickname;
  private Stage primaryStage;
  private Parent rootChat;

  public ClientController(String serverHost, int serverPort, Stage primaryStage) {
    this.networkService = new NetworkService(serverHost, serverPort);
    this.primaryStage = primaryStage;
  }

  public void runApplication() throws IOException {
    openAuth();
    connectToServer();
    runAuthProcess();
  }

  private void openAuth() {
    FXMLLoader loaderAuth = new FXMLLoader();
    try {
      rootChat = loaderAuth.load(getClass().getResourceAsStream("../view/AuthDialog.fxml"));
    } catch (IOException e) {
      e.printStackTrace();
    }
    AuthDialog authDialog = loaderAuth.getController();
    authDialog.setController(this);

    primaryStage.setTitle("Chat client 1.0");
    primaryStage.setScene(new Scene(rootChat));
    primaryStage.setIconified(false);
    primaryStage.show();
    primaryStage.setOnCloseRequest(e -> System.exit(0));
  }

  private void runAuthProcess() {
    networkService.setSuccessfulAuthEvent(
        nickname -> {
          setUserName(nickname);
          Platform.runLater(this::openChat);
        });
    //        authDialog.setVisible(true);

  }

  private void openChat() {
    FXMLLoader loaderChat = new FXMLLoader();
    try {
      rootChat = loaderChat.load(getClass().getResourceAsStream("../view/ClientChat.fxml"));
    } catch (IOException e) {
      e.printStackTrace();
    }
    ClientChat clientChat = loaderChat.getController();
    clientChat.setController(this);

    primaryStage.setTitle("Chat client 1.0 : " + nickname);
    primaryStage.setScene(new Scene(rootChat));
    primaryStage.setIconified(false);
    primaryStage.show();
    primaryStage.setOnCloseRequest(e -> System.exit(0));
    networkService.setMessageHandler(clientChat::appendMessage);
  }

  private void setUserName(String nickname) {
    this.nickname = nickname;
  }

  private void connectToServer() throws IOException {
    try {
      networkService.connect();
    } catch (IOException e) {
      System.err.println("Failed to establish server connection");
      throw e;
    }
  }

  public void sendAuthMessage(String login, String pass) throws IOException {
    networkService.sendAuthMessage(login, pass);
  }

  public void sendMessage(String message) {
    try {
      networkService.sendMessage(message);
    } catch (IOException e) {
      Message.ShowMessage("Error", "Failed to send message!", Alert.AlertType.ERROR);
      e.printStackTrace();
    }
  }

  public void shutdown() {
    networkService.close();
  }

  public String getUsername() {
    return nickname;
  }
}
