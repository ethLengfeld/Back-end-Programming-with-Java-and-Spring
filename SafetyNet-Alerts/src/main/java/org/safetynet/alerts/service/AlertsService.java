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
import java.util.Arrays;
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
        Map<String, Person> personsMap = this.alertsRepository.getPersons();
        for (String person : personsMap.keySet()) {
            if (servicedAddresses.contains(personsMap.get(person).getAddress())) {
                servicedPersons.add(personsMap.get(person));
            }
        }
        return servicedPersons;
    }

    public String createPersonsServicedByStationNumberResponse(List<Person> persons) throws JsonProcessingException {
        ObjectNode rootNode = MAPPER.createObjectNode();
        ArrayNode arrayNode = MAPPER.createArrayNode();

        Map<String, MedicalRecord> medicalRecordMap = this.alertsRepository.getMedicalRecords();
        int numberOfAdults = 0, numberOfChildren = 0;
        for (Person person : persons) {
            ObjectNode personNode = createPersonNode(person);
            personNode.put("address", person.getAddress());
            personNode.put("phone", person.getPhone());
            arrayNode.add(personNode);

            if(DataValidationUtil.getAgeInYears(medicalRecordMap.get(person.getFirstName() + "-" + person.getLastName()).getBirthdate()) >= ADULT_AGE) {
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

    public String createChildrenFromAddressResponse(List<Person> persons) throws JsonProcessingException {
        ObjectNode rootNode = MAPPER.createObjectNode();

        ArrayNode childrenArrayNode = MAPPER.createArrayNode();
        ArrayNode relativeArrayNode = MAPPER.createArrayNode();
        Map<String, MedicalRecord> medicalRecordMap = this.alertsRepository.getMedicalRecords();
        for (Person person : persons) {
            ObjectNode personNode = createPersonNode(person);

            int age = DataValidationUtil.getAgeInYears(medicalRecordMap.get(person.getFirstName() + "-" + person.getLastName()).getBirthdate());
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

    public String createPhoneNumbersForPeopleByStationNumberResponse(List<Person> persons) throws JsonProcessingException {
        ObjectNode rootNode = MAPPER.createObjectNode();

        ArrayNode phoneArrayNode = MAPPER.createArrayNode();
        for (Person person : persons) {
            phoneArrayNode.add(person.getPhone());
        }

        rootNode.set("phoneNumbers", phoneArrayNode);
        return MAPPER.writeValueAsString(rootNode);
    }

    public String createStationNumberAndPeopleByAddressResponse(List<Person> persons) throws JsonProcessingException {
        ObjectNode rootNode = MAPPER.createObjectNode();
        ArrayNode arrayNode = MAPPER.createArrayNode();

        Map<String, MedicalRecord> medicalRecordMap = this.alertsRepository.getMedicalRecords();
        for (Person person : persons) {
            ObjectNode personNode = createPersonNode(person);
            personNode.put("phoneNumber", person.getPhone());

            MedicalRecord medicalRecord = medicalRecordMap.get(person.getFirstName() + "-" + person.getLastName());
            personNode.put("age", DataValidationUtil.getAgeInYears(medicalRecord.getBirthdate()));
            personNode.put("medications", Arrays.toString(medicalRecord.getMedications()));
            personNode.put("allergies", Arrays.toString(medicalRecord.getAllergies()));
            arrayNode.add(personNode);
        }

        Map<String, FireStation> stationsMap = this.alertsRepository.getFireStations();
        rootNode.put("stationNumber", stationsMap.get(persons.get(0).getAddress()).getStation());
        rootNode.set("persons", arrayNode);

        return MAPPER.writeValueAsString(rootNode);
    }

    public String createHouseholdsForEachStationNumberResponse(List<String> addresses) throws JsonProcessingException {
        ObjectNode rootNode = MAPPER.createObjectNode();
        ArrayNode arrayNode = MAPPER.createArrayNode();

        Map<String, MedicalRecord> medicalRecordMap = this.alertsRepository.getMedicalRecords();
        for (String address : addresses) {
            ObjectNode addressNode = MAPPER.createObjectNode();
            addressNode.put("address", address);
            ArrayNode personsArrayNode = MAPPER.createArrayNode();
            List<Person> persons = this.getPersonsFromAddresses(List.of(address));
            for (Person person : persons) {
                ObjectNode personNode = createPersonNode(person);
                personNode.put("phoneNumber", person.getPhone());

                MedicalRecord medicalRecord = medicalRecordMap.get(person.getFirstName() + "-" + person.getLastName());
                personNode.put("age", DataValidationUtil.getAgeInYears(medicalRecord.getBirthdate()));
                personNode.put("medications", Arrays.toString(medicalRecord.getMedications()));
                personNode.put("allergies", Arrays.toString(medicalRecord.getAllergies()));
                personsArrayNode.add(personNode);
            }
            addressNode.set("persons", personsArrayNode);
            arrayNode.add(addressNode);
        }
        rootNode.set("addresses", arrayNode);

        return MAPPER.writeValueAsString(rootNode);
    }

    public String createPersonInfoResponse(String firstLastNameKey) throws JsonProcessingException {

        Map<String, Person> personsMap = this.alertsRepository.getPersons();
        Map<String, MedicalRecord> medicalRecordMap = this.alertsRepository.getMedicalRecords();

        Person person = personsMap.get(firstLastNameKey);
        ObjectNode rootNode = createPersonNode(person);
        rootNode.put("address", person.getAddress());
        rootNode.put("email", person.getAddress());


        MedicalRecord medicalRecord = medicalRecordMap.get(firstLastNameKey);
        rootNode.put("age", DataValidationUtil.getAgeInYears(medicalRecord.getBirthdate()));
        rootNode.put("medications", Arrays.toString(medicalRecord.getMedications()));
        rootNode.put("allergies", Arrays.toString(medicalRecord.getAllergies()));

        return MAPPER.writeValueAsString(rootNode);
    }

    public String createEmailAddressesByCityResponse(String city) throws JsonProcessingException {
        ObjectNode rootNode = MAPPER.createObjectNode();
        ArrayNode emailArrayNode = MAPPER.createArrayNode();
        Map<String, Person> personsMap = this.alertsRepository.getPersons();
        for (String personKey : personsMap.keySet()) {
            if (personsMap.get(personKey).getCity().equals(city)) {
                emailArrayNode.add(personsMap.get(personKey).getEmail());
            }
        }
        rootNode.set("emails", emailArrayNode);

        return MAPPER.writeValueAsString(rootNode);
    }

    public ObjectNode createPersonNode(Person person) {
        ObjectNode personNode = MAPPER.createObjectNode();
        personNode.put("firstName", person.getFirstName());
        personNode.put("lastName", person.getLastName());
        return personNode;
    }

}
