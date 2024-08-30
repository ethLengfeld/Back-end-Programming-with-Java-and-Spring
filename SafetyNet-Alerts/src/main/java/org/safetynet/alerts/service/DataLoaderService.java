package org.safetynet.alerts.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import lombok.Getter;
import org.safetynet.alerts.model.FireStation;
import org.safetynet.alerts.model.MedicalRecord;
import org.safetynet.alerts.model.Person;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Getter
@Service
public class DataLoaderService {


    private List<Person> persons;
    private List<FireStation> fireStations;
    private List<MedicalRecord> medicalRecords;

    @PostConstruct
    public void init() {

        this.persons = new ArrayList<>();
        this.fireStations = new ArrayList<>();
        this.medicalRecords = new ArrayList<>();

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode rootNode;
        try {
            rootNode = objectMapper.readTree(new File("SafetyNet-Alerts/src/main/resources/data_file.json"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        JsonNode personsArray =   rootNode.get("persons");
        JsonNode fireStationsArray = rootNode.get("firestations");
        JsonNode medicalRecordsArray = rootNode.get("medicalrecords");

        for(JsonNode currNode : personsArray) {
            persons.add(objectMapper.convertValue(currNode, Person.class));
        }
        for(JsonNode currNode : fireStationsArray) {
            fireStations.add(objectMapper.convertValue(currNode, FireStation.class));
        }
        for(JsonNode currNode : medicalRecordsArray) {
            medicalRecords.add(objectMapper.convertValue(currNode, MedicalRecord.class));
        }
    }

    /**
     * Test DataLoaderService
     * @param args
     */
    public static void main(String[] args) {
        DataLoaderService svc = new DataLoaderService();
        svc.init();

        System.out.println("/------------------PERSONS------------------\\");
        for(Person currPerson : svc.getPersons()) {
            System.out.println(currPerson.getFirstName() + " " + currPerson.getLastName());
        }
        System.out.println("/------------------FIRE STATIONS------------------\\");
        for(FireStation currStation : svc.getFireStations()) {
            System.out.println(currStation.getStation() + " " + currStation.getAddress());
        }
        System.out.println("/------------------MEDICAL RECORDS------------------\\");
        for(MedicalRecord currRecord : svc.getMedicalRecords()) {
            System.out.println(currRecord.getFirstName() + " " + currRecord.getLastName() + " " + currRecord.getBirthdate());
        }
    }


}
