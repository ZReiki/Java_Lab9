package main.logic;

import java.io.Serializable;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor

public class Patient implements Serializable {
    private int id;
    private String fullName;
    private String address;
    private String phoneNumber;
    private int medicalRecordNumber;
    private String medicalDiagnosis;
    private String lastDateVisit;
    private int currentYearVisitCount;

    @Override
    public String toString() {
        return "\nPatient {" +
                "\nid = " + id +
                "\nfullName = " + fullName +
                "\naddress = " + address +
                "\nphoneNumber = " + phoneNumber +
                "\nmedicalRecordNumber = " + medicalRecordNumber +
                "\nmedicalDiagnosis = " + medicalDiagnosis +
                "\nlastDateVisit = " + lastDateVisit +
                "\ncurrentYearVisitCount = " + currentYearVisitCount +
                "\n}";
    }
}
