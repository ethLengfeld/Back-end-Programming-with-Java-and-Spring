package org.safetynet.alerts.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.safetynet.alerts.exception.FireStationException;
import org.safetynet.alerts.exception.MedicalRecordException;
import org.safetynet.alerts.model.FireStation;
import org.safetynet.alerts.model.MedicalRecord;
import org.safetynet.alerts.model.Person;
import org.safetynet.alerts.repository.AlertsRepository;
import org.safetynet.alerts.utils.JsonDataUtil;
import org.safetynet.alerts.exception.PersonException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Service
public class AlertsService {

    private final static int ADULT_AGE = 18;
    private final static ObjectMapper MAPPER = new ObjectMapper();

    private final Map<String, Person> personsMap;
    private final Map<String, FireStation> fireStationsMap;
    private final Map<String, MedicalRecord> medicalRecordsMap;
    private final AlertsRepository alertsRepository;

    public AlertsService(AlertsRepository alertsRepository) {
        this.alertsRepository = alertsRepository;
        this.personsMap = this.alertsRepository.getPersons();
        this.fireStationsMap = this.alertsRepository.getFireStations();
        this.medicalRecordsMap = this.alertsRepository.getMedicalRecords();
    }

    public List<String> getStationAddressesFromStationNumber(int stationNumber) {
        List<String> servicedAddresses = new ArrayList<>();
        for (String stationAddress : this.fireStationsMap.keySet()) {
            if (this.fireStationsMap.get(stationAddress).getStation() == stationNumber) {
                servicedAddresses.add(stationAddress);
            }
        }
        return servicedAddresses;
    }

    public List<Person> getPersonsFromAddresses(List<String> servicedAddresses) {
        List<Person> servicedPersons = new ArrayList<>();
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

        int numberOfAdults = 0, numberOfChildren = 0;
        for (Person person : persons) {
            ObjectNode personNode = createPersonNode(person);
            personNode.put("address", person.getAddress());
            personNode.put("phone", person.getPhone());
            arrayNode.add(personNode);

            if(JsonDataUtil.getAgeInYears(this.medicalRecordsMap.get(person.getFirstName() + "-" + person.getLastName()).getBirthdate()) >= ADULT_AGE) {
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
        for (Person person : persons) {
            ObjectNode personNode = createPersonNode(person);

            int age = JsonDataUtil.getAgeInYears(this.medicalRecordsMap.get(person.getFirstName() + "-" + person.getLastName()).getBirthdate());
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

        for (Person person : persons) {
            ObjectNode personNode = createPersonNode(person);
            personNode.put("phoneNumber", person.getPhone());

            MedicalRecord medicalRecord = this.medicalRecordsMap.get(person.getFirstName() + "-" + person.getLastName());
            personNode.put("age", JsonDataUtil.getAgeInYears(medicalRecord.getBirthdate()));
            personNode.put("medications", Arrays.toString(medicalRecord.getMedications()));
            personNode.put("allergies", Arrays.toString(medicalRecord.getAllergies()));
            arrayNode.add(personNode);
        }

        rootNode.put("stationNumber", this.fireStationsMap.get(persons.get(0).getAddress()).getStation());
        rootNode.set("persons", arrayNode);

        return MAPPER.writeValueAsString(rootNode);
    }

    public String createHouseholdsForEachStationNumberResponse(List<String> addresses) throws JsonProcessingException {
        ObjectNode rootNode = MAPPER.createObjectNode();
        ArrayNode arrayNode = MAPPER.createArrayNode();

        for (String address : addresses) {
            ObjectNode addressNode = MAPPER.createObjectNode();
            addressNode.put("address", address);
            ArrayNode personsArrayNode = MAPPER.createArrayNode();
            List<Person> persons = this.getPersonsFromAddresses(List.of(address));
            for (Person person : persons) {
                ObjectNode personNode = createPersonNode(person);
                personNode.put("phoneNumber", person.getPhone());

                MedicalRecord medicalRecord = this.medicalRecordsMap.get(person.getFirstName() + "-" + person.getLastName());
                personNode.put("age", JsonDataUtil.getAgeInYears(medicalRecord.getBirthdate()));
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
        Person person = this.personsMap.get(firstLastNameKey);
        ObjectNode rootNode = createPersonNode(person);
        rootNode.put("address", person.getAddress());
        rootNode.put("email", person.getAddress());

        MedicalRecord medicalRecord = this.medicalRecordsMap.get(firstLastNameKey);
        rootNode.put("age", JsonDataUtil.getAgeInYears(medicalRecord.getBirthdate()));
        rootNode.put("medications", Arrays.toString(medicalRecord.getMedications()));
        rootNode.put("allergies", Arrays.toString(medicalRecord.getAllergies()));

        return MAPPER.writeValueAsString(rootNode);
    }

    public String createEmailAddressesByCityResponse(String city) throws JsonProcessingException {
        ObjectNode rootNode = MAPPER.createObjectNode();
        ArrayNode emailArrayNode = MAPPER.createArrayNode();
        for (String personKey : this.personsMap.keySet()) {
            if (this.personsMap.get(personKey).getCity().equals(city)) {
                emailArrayNode.add(this.personsMap.get(personKey).getEmail());
            }
        }
        rootNode.set("emails", emailArrayNode);

        return MAPPER.writeValueAsString(rootNode);
    }

    public void addPerson(Person person) throws PersonException {
        String key = JsonDataUtil.createPersonMapKey(person.getFirstName(), person.getLastName());
        if(this.personsMap.get(key) != null) {
            throw new PersonException("This Person has already been added");
        }
        this.personsMap.put(key, person);
    }

    public void updateExistingPerson(Person person) throws PersonException {
        Person updatePerson = this.personsMap.get(JsonDataUtil.createPersonMapKey(person.getFirstName(), person.getLastName()));
        if (updatePerson == null) {
            throw new PersonException("Person does not exist to update");
        }
        updatePerson.setAddress(person.getAddress());
        updatePerson.setCity(person.getCity());
        updatePerson.setPhone(person.getPhone());
        updatePerson.setZip(person.getZip());
        updatePerson.setEmail(person.getEmail());
    }

    public void deletePerson(String firstName, String lastName) throws PersonException{
        if(this.personsMap.remove(JsonDataUtil.createPersonMapKey(firstName, lastName)) == null) {
            throw new PersonException("Person did not exist to delete");
        }
    }

    public void addFirestation(FireStation fireStation) throws FireStationException {
        if(this.fireStationsMap.get(fireStation.getAddress()) != null) {
            throw new FireStationException("This Fire Station has already been added");
        }
        this.fireStationsMap.put(fireStation.getAddress(), fireStation);
    }

    public void updateExistingFirestation(FireStation fireStation) throws FireStationException {
        FireStation updateFireStation = this.fireStationsMap.get(fireStation.getAddress());
        if (updateFireStation == null) {
            throw new FireStationException("Fire Station does not exist to update");
        }
        updateFireStation.setAddress(fireStation.getAddress());
        updateFireStation.setStation(fireStation.getStation());
    }

    public void deleteFirestation(String address) throws FireStationException {
        if(this.fireStationsMap.remove(address) == null) {
            throw new FireStationException("Fire Station did not exist to delete");
        }
    }

    public void addMedicalRecord(MedicalRecord medicalRecord) throws MedicalRecordException {
        String key = JsonDataUtil.createPersonMapKey(medicalRecord.getFirstName(), medicalRecord.getLastName());
        if(this.medicalRecordsMap.get(key) != null) {
            throw new MedicalRecordException("This Medical Record has already been added");
        }
        this.medicalRecordsMap.put(key, medicalRecord);
    }

    public void updateExistingMedicalRecord(MedicalRecord medicalRecord) throws MedicalRecordException {
        MedicalRecord updateMedicalRecord = this.medicalRecordsMap.get(JsonDataUtil.createPersonMapKey(medicalRecord.getFirstName(), medicalRecord.getLastName()));
        if (updateMedicalRecord == null) {
            throw new MedicalRecordException("Medical Record does not exist to update");
        }
        updateMedicalRecord.setFirstName(medicalRecord.getFirstName());
        updateMedicalRecord.setLastName(medicalRecord.getLastName());
        updateMedicalRecord.setBirthdate(medicalRecord.getBirthdate());
        updateMedicalRecord.setMedications(medicalRecord.getMedications());
        updateMedicalRecord.setAllergies(medicalRecord.getAllergies());
    }

    public void deleteMedicalRecord(String firstName, String lastName) throws MedicalRecordException {
        if(this.medicalRecordsMap.remove(JsonDataUtil.createPersonMapKey(firstName, lastName)) == null) {
            throw new MedicalRecordException("Medical Record did not exist to delete");
        }
    }

    private ObjectNode createPersonNode(Person person) {
        ObjectNode personNode = MAPPER.createObjectNode();
        personNode.put("firstName", person.getFirstName());
        personNode.put("lastName", person.getLastName());
        return personNode;
    }

}
