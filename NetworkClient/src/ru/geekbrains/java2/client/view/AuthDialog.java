package ru.geekbrains.java2.client.view;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class AuthDialog {
  @FXML public Button exitButton;
  public TextField userNameEdit;
  public Button authButton;
  public TextField userPassEdit;

  @FXML
  public void closeProgram(ActionEvent actionEvent) {
    // get a handle to the stage
    Stage stage = (Stage) exitButton.getScene().getWindow();
    // do what you have to do
    stage.close();
  }
}
