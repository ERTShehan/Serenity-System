package com.assignment.serenity.controller;

import com.assignment.serenity.dto.UserDto;
import com.assignment.serenity.util.SessionManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.IOException;

public class DashboardController {
    @FXML
    private Label lblUserRole;

    @FXML
    private StackPane contentPane;

    public void initializeWithUser(UserDto user) {
        lblUserRole.setText("Logged in as: " + user.getRole());
        configureAccessBasedOnRole(user.getRole());
    }

    private void configureAccessBasedOnRole(String role) {
    }

    @FXML
    void onPatientAction(ActionEvent event) {
        loadView("/com/assignment/serenity/view/patient_form.fxml");
    }

    @FXML
    void onProgramAction(ActionEvent event) {
        if (SessionManager.getInstance().isAdmin()) {
            loadView("/com/assignment/serenity/view/program_form.fxml");
        } else {
            showAlert("Access Denied", "Only administrators can manage programs");
        }
    }

    @FXML
    void onTherapistAction(ActionEvent event) {
        if (SessionManager.getInstance().isAdmin()) {
            loadView("/com/assignment/serenity/view/therapist_form.fxml");
        } else {
            showAlert("Access Denied", "Only administrators can manage therapists");
        }
    }

    @FXML
    void onPaymentAction(ActionEvent event) {
        loadView("/com/assignment/serenity/view/payment_form.fxml");
    }

    @FXML
    void onLogoutAction(ActionEvent event) {
        try {
            SessionManager.getInstance().clearSession();
            Parent root = FXMLLoader.load(getClass().getResource("/com/assignment/serenity/view/login_form.fxml"));
            Stage stage = (Stage) contentPane.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Serenity Therapy Center - Login");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadView(String fxmlPath) {
        try {
            contentPane.getChildren().clear();
            contentPane.getChildren().add(FXMLLoader.load(getClass().getResource(fxmlPath)));
        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Error", "Failed to load view: " + fxmlPath);
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}