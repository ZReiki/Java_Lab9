package main.service;

import main.logic.Patient;
import main.io.*;

import java.util.*;
import java.util.stream.*;
import javafx.util.Pair;

public class PatientService {
    private final Input input = new Input();
    private final Printer printer = new Printer();

    // List operations
    public List<Patient> listOfPatientsWithTheSpecifiedDiagnosis(List<Patient> patients, String diagnosis){
        return patients.stream()
                .filter(p -> p.getMedicalDiagnosis().equals(diagnosis))
                .collect(Collectors.toList());
    }

    public List<Patient> listOfPatientsWhoseMedicalRecordNumberIsWithinTheSpecifiedInterval(List<Patient> patients, Pair<Integer, Integer> interval){
        return patients.stream()
                .filter(p -> p.getMedicalRecordNumber() >= interval.getKey() && p.getMedicalRecordNumber() <= interval.getValue())
                .collect(Collectors.toList());
    }

    public List<Patient> quantityAndListOfPatientsWhosePhoneNumberBeginsWithTheSpecifiedDigit(List<Patient> patients, String phoneNumberDigit){
        return patients.stream()
                .filter(p -> p.getPhoneNumber().substring(4, 5).equals(phoneNumberDigit))
                .collect(Collectors.toList());
    }

    // Changing the list
    public void addNewPatient(List<Patient> patients){
        Patient patient = input.newPatient();
        if(patient == null){
            printer.showError("Patient cannot be null");
        }else {
            patients.add(patient);
        }
    }

    public void deletePatientFromList(List<Patient> patients, int id){
        patients.removeIf(p -> p.getId() == id);
    }

    //Список пацієнтів у порядку спадання кількості візитів у поточному році. Якщо вона
    //однакова - за зростанням номерів медичної картки.
    public List<Patient> listOfPatientsByCurrentYearVisitCount(List<Patient> patients){
        return patients.stream()
                .sorted(Comparator.comparingInt(Patient::getCurrentYearVisitCount).reversed()
                        .thenComparingInt(Patient::getMedicalRecordNumber))
                .collect(Collectors.toList());
    }

    public int findPatientById(List<Patient> patients, int id){
        return patients.stream()
                .filter(p -> p.getId() == id)
                .map(Patient::getMedicalRecordNumber)
                .findFirst()
                .orElse(-1);
    }

    // Map operations
    public Map<String, List<Patient>> createMap(List<Patient> patients){
        return patients.stream()
                .collect(Collectors.groupingBy(Patient::getMedicalDiagnosis));
    }

    public List<Patient> findValueByKey(Map<String, List<Patient>> map, String key){
            Map<String, List<Patient>> p = mapOfPatientsByDiagnosisWithMedicalRecordNumber(map);
            return p.entrySet().stream()
                    .filter(t -> t.getKey().equals(key))
                    .map(Map.Entry::getValue)
                    .findFirst()
                    .orElse(Collections.emptyList());
    }

    public List<Patient> findValueByKey(List<Patient> patients, String key){
        Map<String, Patient> p = mapOfPatientsByDiagnosisWithMaxVisitCount(patients);
        return p.entrySet().stream().filter(t -> t.getKey().equals(key)).map(Map.Entry::getValue).findFirst().stream().toList();
    }

    public Map<String, List<Patient>> mapOfPatientsByDiagnosisWithMedicalRecordNumber(Map<String, List<Patient>> patients){
        return patients.entrySet().stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        en -> en.getValue().stream()
                                .sorted(Comparator.comparingInt(Patient::getMedicalRecordNumber))
                                .collect(Collectors.toList())
                ));
    }

    public Map<String, Patient> mapOfPatientsByDiagnosisWithMaxVisitCount(List<Patient> patients){
        return patients.stream()
                .collect(Collectors.toMap(
                        Patient::getMedicalDiagnosis,
                        p -> p,
                        (existing, replacement) -> existing.getCurrentYearVisitCount() > replacement.getCurrentYearVisitCount() ? existing : replacement
                ));
    }
}
