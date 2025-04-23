package com.assignment.serenity.controller;

import com.assignment.serenity.util.Role;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AdminDashboardController implements Initializable {

    private static AdminDashboardController instance;
    private Role currentUserRole;

    @FXML
    private AnchorPane AdminDashboardPage;

    @FXML
    private AnchorPane AnchorPaneAdminDashboard;

    @FXML
    private Button btnLogOut;

    @FXML
    private Button btnPatient;

    @FXML
    private Button btnPayment;

    @FXML
    private Button btnReporting;

    @FXML
    private Button btnTherapist;

    @FXML
    private Button btnTherapyProgram;

    @FXML
    private Button btnTherapySession;

    public AdminDashboardController() {
        instance = this;
    }

    public static AdminDashboardController getInstance() {
        return instance;
    }

    public void setCurrentUserRole(Role role) {
        this.currentUserRole = role;
        setupRoleBasedAccess();
    }

    private void setupRoleBasedAccess() {
        if (currentUserRole == Role.RECEPTIONIST) {
            btnTherapist.setDisable(true);
            btnTherapyProgram.setDisable(true);
        } else {
            btnTherapist.setDisable(false);
            btnTherapyProgram.setDisable(false);
        }
    }

    @FXML
    void btnLogOutOnAction(ActionEvent event) throws IOException {
        // Clear the current user role
        this.currentUserRole = null;

        AdminDashboardPage.getChildren().clear();
        AdminDashboardPage.getChildren().add(FXMLLoader.load(getClass().getResource("/view/LoginPage.fxml")));
    }

    @FXML
    void btnPatientOnAction(ActionEvent event) {
        navigateTo("/view/Patient.fxml");
    }

    @FXML
    void btnPaymentOnAction(ActionEvent event) {
        navigateTo("/view/Payment.fxml");
    }

    @FXML
    void btnReportingOnAction(ActionEvent event) {

    }

    @FXML
    void btnTherapistOnAction(ActionEvent event) {
        navigateTo("/view/TherapistManagement.fxml");
    }

    @FXML
    void btnTherapyProgramOnAction(ActionEvent event) {
        navigateTo("/view/TherapyProgram.fxml");
    }

    @FXML
    void btnTherapySessionOnAction(ActionEvent event) {
        navigateTo("/view/TherapySession.fxml");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
//        navigateTo("/view/TherapistManagement.fxml");
    }

    public void navigateTo(String fxmlPath) {
        try {
            AnchorPaneAdminDashboard.getChildren().clear();
            AnchorPane load = FXMLLoader.load(getClass().getResource(fxmlPath));

            load.prefWidthProperty().bind(AnchorPaneAdminDashboard.widthProperty());
            load.prefHeightProperty().bind(AnchorPaneAdminDashboard.heightProperty());
            AnchorPaneAdminDashboard.getChildren().add(load);

        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Error", "Failed to load page!", Alert.AlertType.ERROR);
        }
    }

    private void showAlert(String title, String message, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void loadUI(String resource) {
        AdminDashboardPage.getChildren().clear();
        try {
            AdminDashboardPage.getChildren().add(FXMLLoader.load(getClass().getResource(resource)));
        } catch (IOException e) {
            showAlert("Error", "Failed to load dashboard!", Alert.AlertType.ERROR);
        }
    }

}
