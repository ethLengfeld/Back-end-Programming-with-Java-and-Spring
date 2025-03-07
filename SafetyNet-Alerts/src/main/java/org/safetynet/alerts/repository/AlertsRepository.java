package org.safetynet.alerts.repository;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import lombok.Getter;
import org.safetynet.alerts.model.FireStation;
import org.safetynet.alerts.model.MedicalRecord;
import org.safetynet.alerts.model.Person;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Value;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.HashMap;

@Getter
@Service
public class AlertsRepository {

    @Value("${json.file.path}")
    private String jsonFilePath;

    private Map<String, Person> persons;
    private Map<String, FireStation> fireStations;
    private Map<String, MedicalRecord> medicalRecords;

    @PostConstruct
    public void init() {

        this.persons = new HashMap<>();
        this.fireStations = new HashMap<>();
        this.medicalRecords = new HashMap<>();

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode rootNode;
        try {
            rootNode = objectMapper.readTree(new File(jsonFilePath));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        JsonNode personsArray =   rootNode.get("persons");
        JsonNode fireStationsArray = rootNode.get("firestations");
        JsonNode medicalRecordsArray = rootNode.get("medicalrecords");

        for(JsonNode currNode : personsArray) {
            Person person = objectMapper.convertValue(currNode, Person.class);
            persons.put(person.getFirstName()+"-"+person.getLastName(), person);
        }
        for(JsonNode currNode : fireStationsArray) {
            FireStation station = objectMapper.convertValue(currNode, FireStation.class);
            fireStations.put(station.getAddress(), station);
        }
        for(JsonNode currNode : medicalRecordsArray) {
            MedicalRecord record = objectMapper.convertValue(currNode, MedicalRecord.class);
            medicalRecords.put(record.getFirstName()+"-"+record.getLastName(), record);
        }
    }

}
