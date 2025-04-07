package main.controller;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.geometry.Insets;

import main.factory.PatientFactory;
import main.io.*;
import main.logic.Patient;
import main.service.PatientRepositoryBinaryImpl;
import main.service.PatientRepositoryJSONImpl;
import main.service.PatientRepositoryTextImpl;
import main.service.PatientService;

import java.util.List;
import java.util.Optional;

public class PatientController {
    private final List<Patient> patients = PatientFactory.createPatient();
    private final PatientService service = new PatientService();
    private final Input input = new Input();
    private final Printer printer = new Printer();
    private final PatientRepositoryTextImpl txtFileService = new PatientRepositoryTextImpl();
    private final PatientRepositoryBinaryImpl binaryFileService = new PatientRepositoryBinaryImpl();
    private final PatientRepositoryJSONImpl jsonFileService = new PatientRepositoryJSONImpl();

    @FXML
    private ChoiceBox<String> operationsChoiceBox;
    @FXML
    private TableView<Patient> patientTable;
    @FXML
    private TableColumn<Patient, Integer> idColumn, medicalRecordNumberColumn, currentYearVisitCountColumn;
    @FXML
    private TableColumn<Patient, String> fullNameColumn, addressColumn, phoneNumberColumn, medicalDiagnosisColumn, lastDateVisitColumn;

    // Table initialize
    public void initialize() {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        fullNameColumn.setCellValueFactory(new PropertyValueFactory<>("fullName"));
        addressColumn.setCellValueFactory(new PropertyValueFactory<>("address"));
        phoneNumberColumn.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
        medicalRecordNumberColumn.setCellValueFactory(new PropertyValueFactory<>("medicalRecordNumber"));
        medicalDiagnosisColumn.setCellValueFactory(new PropertyValueFactory<>("medicalDiagnosis"));
        lastDateVisitColumn.setCellValueFactory(new PropertyValueFactory<>("lastDateVisit"));
        currentYearVisitCountColumn.setCellValueFactory(new PropertyValueFactory<>("currentYearVisitCount"));
    }
    public void updateTable(List<Patient> patientsList){
        patientTable.setItems(FXCollections.observableList(patientsList));
    }

    // Menu - Files
    public void close() {
        Platform.exit();
    }

    public void saveAs(){
        String fileName = chosenFile("Save list of patients to file");
        if(fileName.matches(".*\\.txt$")){
            txtFileService.outputList(patients, fileName);
        }else if(fileName.matches(".*\\.bin$")){
            binaryFileService.outputList(patients, fileName);
        }else if(fileName.matches(".*\\.json$")){
            jsonFileService.outputList(patients, fileName);
        }
    }

    public void readFrom(){
        String fileName = chosenFile("Read list of patients from file");
        if(fileName.matches(".*\\.txt$")){
            patients.clear();
            patients.addAll(txtFileService.readList(fileName));
        }else if(fileName.matches(".*\\.bin$")){
            patients.clear();
            patients.addAll(binaryFileService.readList(fileName));
        }else if(fileName.matches(".*\\.json$")){
            patients.clear();
            patients.addAll(jsonFileService.readList(fileName));
        }
    }

    public String chosenFile(String title){
        Dialog<String> dialog = new Dialog<>();
        dialog.setTitle(title);
        dialog.setHeaderText("Enter the name of file and choose the type");

        ButtonType okButtonType = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(okButtonType, ButtonType.CANCEL);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        ComboBox<String> choice = new ComboBox<>();
        choice.getItems().addAll(".txt", ".bin", ".json");

        TextField fileName = new TextField();
        fileName.setPromptText("Enter the name of file");

        grid.add(new Label("Name:"), 0, 0);
        grid.add(fileName, 1, 0);
        grid.add(new Label("Type:"), 0, 1);
        grid.add(choice, 1, 1);

        dialog.getDialogPane().setContent(grid);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == okButtonType) {
                return fileName.getText() + choice.getValue();
            }
            return null;
        });

        Optional<String> result = dialog.showAndWait();
        return result.orElse(null);
    }

    // Menu - Editing
    public void show() {
        updateTable(patients);
    }

    public void add(){
        service.addNewPatient(patients);
    }

    public void delete(){
        Patient patientForDelete = patientTable.getSelectionModel().getSelectedItem();
        if(patientForDelete == null) return;
        service.deletePatientFromList(patients, patientForDelete.getId());
    }

    // Menu - Help
    public void about(){
        printer.showText("About", """
                Лабораторна робота №9
                Предмет: Технології об'єктно орієнтованого програмування
                Виконав: Федосюк О.О.
                Группа: 2141
                Викладач: Беркунський Є.Ю.
                """);
    }

    // Controller
    public void runOperation() {
        String operation = operationsChoiceBox.getValue();
        if(operation == null) return;

        switch (operation) {
            case "1. List of Patients with the specified diagnosis" ->{
                updateTable(service.listOfPatientsWithTheSpecifiedDiagnosis(patients, input.enterText("Diagnose")));
            }
            case "2. List of Patients whose medical record number is within the specified interval" ->{
                updateTable(service.listOfPatientsWhoseMedicalRecordNumberIsWithinTheSpecifiedInterval(patients, input.enterInterval()));
            }
            case "3. Quantity and list of Patients whose phone number begins with the specified digit" ->{
                updateTable(service.quantityAndListOfPatientsWhosePhoneNumberBeginsWithTheSpecifiedDigit(patients, input.enterText("First phone number")));
            }
            case "4. The list of patients in descending order of the number of visits in the current year. If it is the same, in ascending order of medical record numbers" ->{
                updateTable(service.listOfPatientsByCurrentYearVisitCount(patients));
            }
            case "5. Patient's medical record number by id" ->{
                printer.showText("The medical record number of the person", String.valueOf(service.findPatientById(patients, input.enterInteger("Id"))));
            }
            case "6. A list of patients with this diagnosis in ascending order of the medical record number" ->{}
            case "7. For each patient diagnosis with the highest number of visits" ->{}
        }
    }
}
