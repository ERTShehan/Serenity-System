package com.assignment.serenity.controller;

import com.assignment.serenity.bo.custom.PatientBO;
import com.assignment.serenity.bo.custom.TherapistBO;
import com.assignment.serenity.bo.custom.TherapyProgramBO;
import com.assignment.serenity.bo.custom.TherapySessionBO;
import com.assignment.serenity.bo.custom.impl.PatientBOImpl;
import com.assignment.serenity.bo.custom.impl.TherapistBOImpl;
import com.assignment.serenity.bo.custom.impl.TherapyProgramBOImpl;
import com.assignment.serenity.bo.custom.impl.TherapySessionBOImpl;
import com.assignment.serenity.dto.TherapistDTO;
import com.assignment.serenity.dto.TherapyProgramDTO;
import com.assignment.serenity.dto.TherapySessionDTO;
import com.assignment.serenity.entity.Patient;
import com.assignment.serenity.entity.Therapist;
import com.assignment.serenity.entity.TherapyProgram;
import com.assignment.serenity.view.tdm.TherapySessionTM;
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

public class TherapySessionController implements Initializable {

    @FXML
    private Button btnClear;

    @FXML
    private Button btnDelete;

    @FXML
    private Button btnSave;

    @FXML
    private Button btnUpdate;

    @FXML
    private AnchorPane therapySessionPage;

    @FXML
    private Label lblSessionId;

    @FXML
    private DatePicker dpSessionDate;

    @FXML
    private TextField txtSessionTime;

    @FXML
    private ComboBox<String> cmbStatus;

    @FXML
    private ComboBox<Patient> cmbPatient;

    @FXML
    private ComboBox<Therapist> cmbTherapist;

    @FXML
    private ComboBox<TherapyProgram> cmbProgram;

    @FXML
    private TableView<TherapySessionTM> tblTherapySessions;

    @FXML
    private TableColumn<TherapySessionTM, String> colSessionId;

    @FXML
    private TableColumn<TherapySessionTM, LocalDate> colDate;

    @FXML
    private TableColumn<TherapySessionTM, String> colTime;

    @FXML
    private TableColumn<TherapySessionTM, String> colStatus;

    @FXML
    private TableColumn<TherapySessionTM, String> colPatient;

    @FXML
    private TableColumn<TherapySessionTM, String> colTherapist;

    @FXML
    private TableColumn<TherapySessionTM, String> colProgram;

    private final TherapySessionBO therapySessionBO = new TherapySessionBOImpl();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colSessionId.setCellValueFactory(new PropertyValueFactory<>("sessionId"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        colTime.setCellValueFactory(new PropertyValueFactory<>("time"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
        colPatient.setCellValueFactory(new PropertyValueFactory<>("patientName"));
        colTherapist.setCellValueFactory(new PropertyValueFactory<>("therapistName"));
        colProgram.setCellValueFactory(new PropertyValueFactory<>("programName"));

        cmbStatus.getItems().addAll("Scheduled", "Completed", "Cancelled", "No Show");

        loadAllSessions();
        loadComboBoxData();
        try {
            generateNewSessionId();
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

            TherapistBO therapistBO = new TherapistBOImpl();
            ArrayList<TherapistDTO> therapists = therapistBO.loadAllTherapists();
            cmbTherapist.getItems().clear();
            for (TherapistDTO therapistDTO : therapists) {
                cmbTherapist.getItems().add(new Therapist(
                        therapistDTO.getTherapistID(),
                        therapistDTO.getTherapistName(),
                        therapistDTO.getSpecialization(),
                        therapistDTO.getAvailability(),
                        new ArrayList<>(),
                        new ArrayList<>()
                ));
            }

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
        } catch (Exception e) {
            showAlert("Error", "Failed to load combo box data", Alert.AlertType.ERROR);
            e.printStackTrace();
        }
    }

    private void generateNewSessionId() throws Exception {
        lblSessionId.setText(therapySessionBO.getNextSessionId());
    }

    private void loadAllSessions() {
        try {
            ArrayList<TherapySessionDTO> sessions = therapySessionBO.getAllSessions();
            ObservableList<TherapySessionTM> obList = FXCollections.observableArrayList();

            if (sessions != null) {
                for (TherapySessionDTO dto : sessions) {
                    obList.add(new TherapySessionTM(
                            dto.getSessionId(),
                            dto.getDate(),
                            dto.getTime(),
                            dto.getStatus(),
                            dto.getPatient() != null ? dto.getPatient().getName() : "N/A",
                            dto.getTherapist() != null ? dto.getTherapist().getTherapistName() : "N/A",
                            dto.getProgram() != null ? dto.getProgram().getProgramName() : "N/A"
                    ));
                }
            }
            tblTherapySessions.setItems(obList);
        } catch (Exception e) {
            showAlert("Error", "Failed to load therapy sessions", Alert.AlertType.ERROR);
            e.printStackTrace();
        }
    }

    @FXML
    void btnSaveOnAction(ActionEvent event) {
        try {
            TherapySessionDTO dto = new TherapySessionDTO(
                    lblSessionId.getText(),
                    dpSessionDate.getValue(),
                    txtSessionTime.getText(),
                    cmbStatus.getValue(),
                    cmbPatient.getValue(),
                    cmbTherapist.getValue(),
                    cmbProgram.getValue()
            );

            boolean isSaved = therapySessionBO.saveSession(dto);
            if (isSaved) {
                showAlert("Success", "Session saved successfully", Alert.AlertType.INFORMATION);
                loadAllSessions();
                clearFields();
                generateNewSessionId();
            } else {
                showAlert("Error", "Failed to save session", Alert.AlertType.ERROR);
            }
        } catch (Exception e) {
            showAlert("Error", "Invalid data or missing fields", Alert.AlertType.ERROR);
            e.printStackTrace();
        }
    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) {
        TherapySessionTM selectedSession = tblTherapySessions.getSelectionModel().getSelectedItem();
        if (selectedSession == null) {
            showAlert("Error", "Please select a session to update", Alert.AlertType.ERROR);
            return;
        }

        try {
            TherapySessionDTO dto = new TherapySessionDTO(
                    selectedSession.getSessionId(),
                    dpSessionDate.getValue(),
                    txtSessionTime.getText(),
                    cmbStatus.getValue(),
                    cmbPatient.getValue(),
                    cmbTherapist.getValue(),
                    cmbProgram.getValue()
            );

            boolean isUpdated = therapySessionBO.updateSession(dto);
            if (isUpdated) {
                showAlert("Success", "Session updated successfully", Alert.AlertType.INFORMATION);
                loadAllSessions();
                clearFields();
            } else {
                showAlert("Error", "Failed to update session", Alert.AlertType.ERROR);
            }
        } catch (Exception e) {
            showAlert("Error", "Invalid data or missing fields", Alert.AlertType.ERROR);
            e.printStackTrace();
        }
    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) {
        TherapySessionTM selectedSession = tblTherapySessions.getSelectionModel().getSelectedItem();
        if (selectedSession == null) {
            showAlert("Error", "Please select a session to delete", Alert.AlertType.ERROR);
            return;
        }

        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION,
                "Are you sure you want to delete this session?", ButtonType.YES, ButtonType.NO);
        Optional<ButtonType> result = confirm.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.YES) {
            try {
                boolean isDeleted = therapySessionBO.deleteSession(selectedSession.getSessionId());
                if (isDeleted) {
                    showAlert("Success", "Session deleted successfully", Alert.AlertType.INFORMATION);
                    loadAllSessions();
                    clearFields();
                } else {
                    showAlert("Error", "Failed to delete session", Alert.AlertType.ERROR);
                }
            } catch (Exception e) {
                showAlert("Error", "Failed to delete session", Alert.AlertType.ERROR);
                e.printStackTrace();
            }
        }
    }

    @FXML
    void btnClearOnAction(ActionEvent event) throws Exception {
        clearFields();
        generateNewSessionId();
    }

    @FXML
    void tblTherapySessionsOnMouseClicked(javafx.scene.input.MouseEvent mouseEvent) {
        TherapySessionTM selectedSession = tblTherapySessions.getSelectionModel().getSelectedItem();
        if (selectedSession != null) {
            lblSessionId.setText(selectedSession.getSessionId());
            dpSessionDate.setValue(selectedSession.getDate());
            txtSessionTime.setText(selectedSession.getTime());
            cmbStatus.setValue(selectedSession.getStatus());
        }
    }

    private void clearFields() {
        dpSessionDate.setValue(null);
        txtSessionTime.clear();
        cmbStatus.getSelectionModel().clearSelection();
        cmbPatient.getSelectionModel().clearSelection();
        cmbTherapist.getSelectionModel().clearSelection();
        cmbProgram.getSelectionModel().clearSelection();
    }

    private void showAlert(String title, String message, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }
}