package ru.geekbrains.java2.client.view;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import ru.geekbrains.java2.client.controller.ClientController;

public class ClientChat {
  @FXML public ListView<String> userList;
  @FXML public TextField userMessageEdit;
  @FXML public Button sendButton;
  @FXML public ListView<Object> chatMessageList;
  @FXML public Button exitButton;
  @FXML public Button clearButton;

  private ClientController controller;

  public void setController(ClientController controller) {
    this.controller = controller;
  }

  public void initialize() {
    userList.getItems().add("username1");
    userList.getItems().add("username2");
    userList.getItems().add("username3");
    clearButton.setTooltip(new Tooltip("Очистить чат"));
    sendButton.setTooltip(new Tooltip("Отправить сообщение"));
    exitButton.setTooltip(new Tooltip("Закрыть программу"));
  }

  @FXML
  public void closeProgram(ActionEvent actionEvent) {
    // get a handle to the stage
    Stage stage = (Stage) exitButton.getScene().getWindow();
    // do what you have to do
    stage.close();
    System.exit(0);
  }

  @FXML
  public void sendMessage(ActionEvent actionEvent) {

    String newMessage = userMessageEdit.getCharacters().toString();
    if (!newMessage.isBlank()) {

      Text myText = new Text(String.format("%s: %s", "Я", newMessage));
      myText.setStyle("-fx-font-weight: bold");
      chatMessageList.getItems().addAll(myText);

      if (!userList.getSelectionModel().isEmpty()) {
        controller.sendMessage(
            String.format("/w %s %s", userList.getSelectionModel().getSelectedItem(), newMessage));
        //        chatMessageList
        //            .getItems().add(
        //                String.format("%s: %s", userList.getSelectionModel().getSelectedItem(),
        //                    userMessageEdit.getCharacters().toString()));
        userList.getSelectionModel().clearSelection();
      } else controller.sendMessage(newMessage);

      userMessageEdit.clear();
    }
  }

  @FXML
  public void clearChat(ActionEvent actionEvent) {
    chatMessageList.getItems().clear();
  }

  public void appendMessage(String incomeMessage) {
    Platform.runLater(() -> chatMessageList.getItems().addAll(incomeMessage));
  }
}
