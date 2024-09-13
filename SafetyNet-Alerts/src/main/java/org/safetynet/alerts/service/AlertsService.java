package org.safetynet.alerts.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.safetynet.alerts.model.FireStation;
import org.safetynet.alerts.model.MedicalRecord;
import org.safetynet.alerts.model.Person;
import org.safetynet.alerts.repository.AlertsRepository;
import org.safetynet.alerts.utils.DataValidationUtil;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class AlertsService {

    private final static int ADULT_AGE = 18;
    private final static ObjectMapper MAPPER = new ObjectMapper();

    private AlertsRepository alertsRepository;

    public AlertsService(AlertsRepository alertsRepository) {
        this.alertsRepository = alertsRepository;
    }

    public List<String> getStationAddressesFromStationNumber(int stationNumber) {
        Map<String, FireStation> stationsMap = this.alertsRepository.getFireStations();
        List<String> servicedAddresses = new ArrayList<>();
        for (String stationAddress : stationsMap.keySet()) {
            if (stationsMap.get(stationAddress).getStation() == stationNumber) {
                servicedAddresses.add(stationAddress);
            }
        }
        return servicedAddresses;
    }

    public List<Person> getPersonsFromAddresses(List<String> servicedAddresses) {
        List<Person> servicedPersons = new ArrayList<>();
        Map<String, Person> personsMap = alertsRepository.getPersons();
        for (String person : personsMap.keySet()) {
            if (servicedAddresses.contains(personsMap.get(person).getAddress())) {
                servicedPersons.add(personsMap.get(person));
            }
        }
        return servicedPersons;
    }

    public String createPersonsResponse(List<Person> persons) throws JsonProcessingException {
        ObjectNode rootNode = MAPPER.createObjectNode();
        ArrayNode arrayNode = MAPPER.createArrayNode();

        Map<String, MedicalRecord> medicalRecordMap = this.alertsRepository.getMedicalRecords();
        int numberOfAdults = 0, numberOfChildren = 0;
        for (Person person : persons) {
            ObjectNode personNode = MAPPER.createObjectNode();
            personNode.put("firstName", person.getFirstName());
            personNode.put("lastName", person.getLastName());
            personNode.put("address", person.getAddress());
            personNode.put("phone", person.getPhone());
            arrayNode.add(personNode);

            if(DataValidationUtil.getAgeInYears(medicalRecordMap.get(person.getFirstName()+"-"+person.getLastName()).getBirthdate()) >= ADULT_AGE) {
                numberOfAdults++;
            } else {
                numberOfChildren++;
            }
        }

        rootNode.set("persons", arrayNode);
        rootNode.put("numberOfAdults", numberOfAdults);
        rootNode.put("numberOfChildren", numberOfChildren);

        return MAPPER.writeValueAsString(rootNode);
    }

    public String createChildrenResponse(List<Person> persons) throws JsonProcessingException {
        ObjectNode rootNode = MAPPER.createObjectNode();

        ArrayNode childrenArrayNode = MAPPER.createArrayNode();
        ArrayNode relativeArrayNode = MAPPER.createArrayNode();
        Map<String, MedicalRecord> medicalRecordMap = this.alertsRepository.getMedicalRecords();
        for (Person person : persons) {
            ObjectNode personNode = MAPPER.createObjectNode();
            personNode.put("firstName", person.getFirstName());
            personNode.put("lastName", person.getLastName());

            int age = DataValidationUtil.getAgeInYears(medicalRecordMap.get(person.getFirstName()+"-"+person.getLastName()).getBirthdate());
            personNode.put("age", age);
            if(age < ADULT_AGE) {
                childrenArrayNode.add(personNode);
            } else {
                relativeArrayNode.add(personNode);
            }
        }

        rootNode.set("children", childrenArrayNode);
        rootNode.set("relative", relativeArrayNode);

        return MAPPER.writeValueAsString(rootNode);
    }
}
