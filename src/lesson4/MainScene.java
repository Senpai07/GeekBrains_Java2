package lesson4;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class MainScene {
  @FXML public ListView<String> userList;
  @FXML public TextField userMessageEdit;
  @FXML public Button sendButton;
  @FXML public ListView<Object> chatMessageList;
  @FXML public Button exitButton;
  @FXML public Button clearButton;

  public void initialize() {
    userList.getItems().add("Гуляев Петр");
    userList.getItems().add("Нестеров Руслан");
    userList.getItems().add("Варенцова Ольга");
    userList.getItems().add("Логинова Светлана");
    userList.getItems().add("Леонов Сергей");
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
  }

  @FXML
  public void sendMessage(ActionEvent actionEvent) {
    if (!userMessageEdit.getCharacters().toString().isBlank()) {

      Text myText =
          new Text(String.format("%s: %s", "Я", userMessageEdit.getCharacters().toString()));
      myText.setStyle("-fx-font-weight: bold");
      chatMessageList.getItems().addAll(myText);

      if (!userList.getSelectionModel().isEmpty()) {
        chatMessageList
            .getItems()
            .add(
                String.format(
                    "%s: %s",
                    userList.getSelectionModel().getSelectedItem(),
                    userMessageEdit.getCharacters().toString()));
        userList.getSelectionModel().clearSelection();
      }
      userMessageEdit.clear();
    }
  }

  @FXML
  public void clearChat(ActionEvent actionEvent) {
    chatMessageList.getItems().clear();
  }
}
