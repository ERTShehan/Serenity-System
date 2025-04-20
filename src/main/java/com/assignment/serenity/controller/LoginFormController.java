package com.assignment.serenity.controller;

import com.assignment.serenity.bo.BoFactory;
import com.assignment.serenity.bo.custom.UserBo;
import com.assignment.serenity.dto.UserDto;
import com.assignment.serenity.exception.InvalidCredentialException;
import com.assignment.serenity.exception.MissingFieldException;
import com.assignment.serenity.util.SessionManager;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginFormController {
    @FXML
    private JFXTextField txtUserName;

    @FXML
    private JFXPasswordField txtPassword;

    @FXML
    private JFXTextField txtShowPassword;

    @FXML
    private CheckBox checkBox;

    private final UserBo userBo = BoFactory.getInstance().getBo(BoFactory.BoType.USER);

    @FXML
    void onLoginAction(ActionEvent event) {
        try {
            String username = txtUserName.getText().trim();
            String password = checkBox.isSelected() ?
                    txtShowPassword.getText().trim() : txtPassword.getText().trim();

            UserDto user = userBo.authenticate(username, password);
            SessionManager.getInstance().setCurrentUser(user);
            navigateToDashboard();

        } catch (MissingFieldException e) {
            showAlert(Alert.AlertType.ERROR, "Login Error", e.getMessage());
        } catch (InvalidCredentialException e) {
            showAlert(Alert.AlertType.ERROR, "Login Failed", e.getMessage());
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Error", "An unexpected error occurred");
            e.printStackTrace();
        }
    }

    private void navigateToDashboard() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/assignment/serenity/view/dashboard.fxml"));
        Parent root = loader.load();

        DashboardController controller = loader.getController();
        controller.initializeWithUser(SessionManager.getInstance().getCurrentUser());

        Stage stage = (Stage) txtUserName.getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.setTitle("Serenity Therapy Center - Dashboard");
        stage.centerOnScreen();
    }

    @FXML
    void checkBoxOnMouseClicked(MouseEvent event) {
        if (checkBox.isSelected()) {
            txtShowPassword.setText(txtPassword.getText());
            txtShowPassword.setVisible(true);
            txtPassword.setVisible(false);
        } else {
            txtPassword.setText(txtShowPassword.getText());
            txtPassword.setVisible(true);
            txtShowPassword.setVisible(false);
        }
    }

    @FXML
    void onForgotPasswordAction(ActionEvent event) {
        showAlert(Alert.AlertType.INFORMATION, "Forgot Password", "Please contact your administrator");
    }

    private void showAlert(Alert.AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public void txtShowPwOnKeyTyped(KeyEvent keyEvent) {
    }
}