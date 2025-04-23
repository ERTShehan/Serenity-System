package com.assignment.serenity.controller;

import com.assignment.serenity.bo.custom.PatientBO;
import com.assignment.serenity.bo.custom.PaymentBO;
import com.assignment.serenity.bo.custom.TherapyProgramBO;
import com.assignment.serenity.bo.custom.impl.PatientBOImpl;
import com.assignment.serenity.bo.custom.impl.PaymentBOImpl;
import com.assignment.serenity.bo.custom.impl.TherapyProgramBOImpl;
import com.assignment.serenity.dto.PaymentDTO;
import com.assignment.serenity.dto.TherapyProgramDTO;
import com.assignment.serenity.entity.Patient;
import com.assignment.serenity.entity.TherapyProgram;
import com.assignment.serenity.view.tdm.PaymentTM;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

public class PaymentController implements Initializable {

    @FXML
    private Button btnClear;

    @FXML
    private Button btnDelete;

    @FXML
    private Button btnSave;

    @FXML
    private Button btnUpdate;

    @FXML
    private AnchorPane paymentPage;

    @FXML
    private Label lblPaymentId;

    @FXML
    private DatePicker dpPaymentDate;

    @FXML
    private ComboBox<Patient> cmbPatient;

    @FXML
    private ComboBox<TherapyProgram> cmbProgram;

    @FXML
    private TextField txtAmount;

    @FXML
    private ComboBox<String> cmbPaymentMethod;

    @FXML
    private TableView<PaymentTM> tblPayments;

    @FXML
    private TableColumn<PaymentTM, String> colPaymentId;

    @FXML
    private TableColumn<PaymentTM, LocalDate> colDate;

    @FXML
    private TableColumn<PaymentTM, Double> colAmount;

    @FXML
    private TableColumn<PaymentTM, String> colMethod;

    @FXML
    private TableColumn<PaymentTM, String> colPatient;

    @FXML
    private TableColumn<PaymentTM, String> colProgram;

    private final PaymentBO paymentBO = new PaymentBOImpl();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colPaymentId.setCellValueFactory(new PropertyValueFactory<>("paymentId"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        colAmount.setCellValueFactory(new PropertyValueFactory<>("amount"));
        colMethod.setCellValueFactory(new PropertyValueFactory<>("paymentMethod"));
        colPatient.setCellValueFactory(new PropertyValueFactory<>("patientName"));
        colProgram.setCellValueFactory(new PropertyValueFactory<>("programName"));

        cmbPaymentMethod.getItems().addAll("Cash", "Credit Card", "Debit Card", "Bank Transfer");

        loadAllPayments();
        loadComboBoxData();
        try {
            generateNewPaymentId();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void loadComboBoxData() {
        try {
            PatientBO patientBO = new PatientBOImpl();
            ArrayList<Patient> patients = patientBO.loadAllpatientsInCombo();
            cmbPatient.getItems().clear();
            cmbPatient.getItems().addAll(patients);

            TherapyProgramBO programBO = new TherapyProgramBOImpl();
            ArrayList<TherapyProgramDTO> programs = programBO.loadAllTherapyPrograms();
            cmbProgram.getItems().clear();

            for (TherapyProgramDTO programDTO : programs) {
                cmbProgram.getItems().add(new TherapyProgram(
                        programDTO.getProgramId(),
                        programDTO.getProgramName(),
                        programDTO.getDuration(),
                        programDTO.getFee(),
                        new ArrayList<>(),
                        new ArrayList<>()
                ));
            }

            if (!cmbPatient.getItems().isEmpty()) {
                cmbPatient.getSelectionModel().selectFirst();
            }
            if (!cmbProgram.getItems().isEmpty()) {
                cmbProgram.getSelectionModel().selectFirst();
            }

        } catch (Exception e) {
            showAlert("Error", "Failed to load combo box data: " + e.getMessage(), Alert.AlertType.ERROR);
            e.printStackTrace();
        }
    }

    private void generateNewPaymentId() throws Exception {
        lblPaymentId.setText(paymentBO.getNextPaymentId());
    }

    private void loadAllPayments() {
        try {
            ArrayList<PaymentDTO> payments = paymentBO.getAllPayments();
            ObservableList<PaymentTM> obList = FXCollections.observableArrayList();

            if (payments != null) {
                for (PaymentDTO dto : payments) {
                    obList.add(new PaymentTM(
                            dto.getPaymentId(),
                            dto.getDate(),
                            dto.getAmount(),
                            dto.getPaymentMethod(),
                            dto.getPatient() != null ? dto.getPatient().getName() : "N/A",
                            dto.getProgram() != null ? dto.getProgram().getProgramName() : "N/A"
                    ));
                }
            }
            tblPayments.setItems(obList);
        } catch (Exception e) {
            showAlert("Error", "Failed to load payments", Alert.AlertType.ERROR);
            e.printStackTrace();
        }
    }

    @FXML
    void btnSaveOnAction(ActionEvent event) {
        try {
            PaymentDTO dto = new PaymentDTO(
                    lblPaymentId.getText(),
                    dpPaymentDate.getValue(),
                    Double.parseDouble(txtAmount.getText()),
                    cmbPaymentMethod.getValue(),
                    cmbPatient.getValue(),
                    cmbProgram.getValue()
            );

            boolean isSaved = paymentBO.savePayment(dto);
            if (isSaved) {
                showAlert("Success", "Payment saved successfully", Alert.AlertType.INFORMATION);
                loadAllPayments();
                clearFields();
                generateNewPaymentId();
            } else {
                showAlert("Error", "Failed to save payment", Alert.AlertType.ERROR);
            }
        } catch (NumberFormatException e) {
            showAlert("Error", "Invalid amount format", Alert.AlertType.ERROR);
        } catch (Exception e) {
            showAlert("Error", "Invalid data or missing fields", Alert.AlertType.ERROR);
            e.printStackTrace();
        }
    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) {
        PaymentTM selectedPayment = tblPayments.getSelectionModel().getSelectedItem();
        if (selectedPayment == null) {
            showAlert("Error", "Please select a payment to update", Alert.AlertType.ERROR);
            return;
        }

        try {
            PaymentDTO dto = new PaymentDTO(
                    selectedPayment.getPaymentId(),
                    dpPaymentDate.getValue(),
                    Double.parseDouble(txtAmount.getText()),
                    cmbPaymentMethod.getValue(),
                    cmbPatient.getValue(),
                    cmbProgram.getValue()
            );

            boolean isUpdated = paymentBO.updatePayment(dto);
            if (isUpdated) {
                showAlert("Success", "Payment updated successfully", Alert.AlertType.INFORMATION);
                loadAllPayments();
                clearFields();
            } else {
                showAlert("Error", "Failed to update payment", Alert.AlertType.ERROR);
            }
        } catch (Exception e) {
            showAlert("Error", "Invalid data or missing fields", Alert.AlertType.ERROR);
            e.printStackTrace();
        }
    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) {
        PaymentTM selectedPayment = tblPayments.getSelectionModel().getSelectedItem();
        if (selectedPayment == null) {
            showAlert("Error", "Please select a payment to delete", Alert.AlertType.ERROR);
            return;
        }

        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION,
                "Are you sure you want to delete this payment?", ButtonType.YES, ButtonType.NO);
        Optional<ButtonType> result = confirm.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.YES) {
            try {
                boolean isDeleted = paymentBO.deletePayment(selectedPayment.getPaymentId());
                if (isDeleted) {
                    showAlert("Success", "Payment deleted successfully", Alert.AlertType.INFORMATION);
                    loadAllPayments();
                    clearFields();
                } else {
                    showAlert("Error", "Failed to delete payment", Alert.AlertType.ERROR);
                }
            } catch (Exception e) {
                showAlert("Error", "Failed to delete payment", Alert.AlertType.ERROR);
                e.printStackTrace();
            }
        }
    }

    @FXML
    void btnClearOnAction(ActionEvent event) throws Exception {
        clearFields();
        generateNewPaymentId();
    }

    @FXML
    void tblPaymentsOnMouseClicked(javafx.scene.input.MouseEvent mouseEvent) {
        PaymentTM selectedPayment = tblPayments.getSelectionModel().getSelectedItem();
        if (selectedPayment != null) {
            lblPaymentId.setText(selectedPayment.getPaymentId());
            dpPaymentDate.setValue(selectedPayment.getDate());
            txtAmount.setText(String.valueOf(selectedPayment.getAmount()));
            cmbPaymentMethod.setValue(selectedPayment.getPaymentMethod());
        }
    }

    private void clearFields() {
        dpPaymentDate.setValue(null);
        txtAmount.clear();
        cmbPaymentMethod.getSelectionModel().clearSelection();
        cmbPatient.getSelectionModel().clearSelection();
        cmbProgram.getSelectionModel().clearSelection();
    }

    private void showAlert(String title, String message, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }
}