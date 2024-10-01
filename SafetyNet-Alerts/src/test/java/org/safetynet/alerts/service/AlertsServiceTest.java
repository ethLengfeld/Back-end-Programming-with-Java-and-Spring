package org.safetynet.alerts.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.safetynet.alerts.exception.FireStationException;
import org.safetynet.alerts.exception.MedicalRecordException;
import org.safetynet.alerts.exception.PersonException;
import org.safetynet.alerts.model.FireStation;
import org.safetynet.alerts.model.MedicalRecord;
import org.safetynet.alerts.model.Person;
import org.safetynet.alerts.repository.AlertsRepository;
import org.safetynet.alerts.utils.JsonDataUtil;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

public class AlertsServiceTest {

    private static Map<String, Person> personsMap;
    private static Map<String, FireStation> fireStationsMap;
    private static Map<String, MedicalRecord> medicalRecordsMap;

    private static AlertsService alertsService;

    @BeforeAll
    static void setUp() {
        alertsService = new AlertsService(mock(AlertsRepository.class));
        populateMapsWithTestData();
        ReflectionTestUtils.setField(alertsService, "personsMap", personsMap);
        ReflectionTestUtils.setField(alertsService, "fireStationsMap", fireStationsMap);
        ReflectionTestUtils.setField(alertsService, "medicalRecordsMap", medicalRecordsMap);
    }

    @Test
    public void testGetStationAddressesFromStationNumber() {
        List<String> addresses = alertsService.getStationAddressesFromStationNumber(1);
        assertEquals(1, addresses.size());
    }

    @Test
    public void testGetPersonsFromAddresses() {
        List<Person> persons = alertsService.getPersonsFromAddresses(List.of("123 Rainy St"));
        assertEquals(2, persons.size());

        persons = alertsService.getPersonsFromAddresses(List.of("10 Unknown Dr"));
        assertEquals(0, persons.size());
    }

    @Test
    public void testCreatePersonsServicedByStationNumberResponse() throws JsonProcessingException {
        List<Person> persons = new ArrayList<>();
        persons.add(personsMap.get("Hank-Hill"));
        persons.add(personsMap.get("Bobby-Hill"));
        String response = alertsService.createPersonsServicedByStationNumberResponse(persons);
        assertEquals("{\"persons\":[{\"firstName\":\"Hank\",\"lastName\":\"Hill\"," +
                "\"address\":\"123 Rainy St\",\"phone\":\"312-333-6789\"}," +
                "{\"firstName\":\"Bobby\",\"lastName\":\"Hill\",\"address\":\"123 Rainy St\"," +
                "\"phone\":\"312-333-6789\"}],\"numberOfAdults\":1,\"numberOfChildren\":1}", response);
    }

    @Test
    public void testCreateChildrenFromAddressResponse() throws JsonProcessingException {
        List<Person> persons = new ArrayList<>();
        persons.add(personsMap.get("Hank-Hill"));
        persons.add(personsMap.get("Bobby-Hill"));
        String response = alertsService.createChildrenFromAddressResponse(persons);
        assertEquals("{\"children\":[{\"firstName\":\"Bobby\",\"lastName\":\"Hill\"," +
        "\"age\":6}],\"relative\":[{\"firstName\":\"Hank\",\"lastName\":\"Hill\",\"age\":26}]}", response);
    }

    @Test
    public void testCreatePhoneNumbersForPeopleByStationNumberResponse() throws JsonProcessingException {
        List<Person> persons = new ArrayList<>();
        persons.add(personsMap.get("Hank-Hill"));
        String response = alertsService.createPhoneNumbersForPeopleByStationNumberResponse(persons);
        assertEquals("{\"phoneNumbers\":[\"312-333-6789\"]}", response);
    }

    @Test
    public void testCreateStationNumberAndPeopleByAddressResponse() throws JsonProcessingException {
        List<Person> persons = new ArrayList<>();
        persons.add(personsMap.get("Hank-Hill"));
        persons.add(personsMap.get("Bobby-Hill"));
        String response = alertsService.createStationNumberAndPeopleByAddressResponse(persons);
        assertEquals("{\"stationNumber\":1,\"persons\":[{\"firstName\":\"Hank\",\"lastName\":\"Hill\",\"phoneNumber\":\"312-333-6789\",\"age\":26,\"medications\":\"[]\",\"allergies\":\"[]\"},{\"firstName\":\"Bobby\",\"lastName\":\"Hill\",\"phoneNumber\":\"312-333-6789\",\"age\":6,\"medications\":\"[aznol:350mg, hydrapermazol:100mg]\",\"allergies\":\"[nillacilan]\"}]}", response);
    }

    @Test
    public void testCreateHouseholdsForEachStationNumberResponse() throws JsonProcessingException {
        List<String> addresses = List.of("123 Rainy St", "100 Unknown Dr");
        String response = alertsService.createHouseholdsForEachStationNumberResponse(addresses);
        assertEquals("{\"addresses\":[{\"address\":\"123 Rainy St\",\"persons\":[{\"firstName\":\"Bobby\",\"lastName\":\"Hill\",\"phoneNumber\":\"312-333-6789\",\"age\":6,\"medications\":\"[aznol:350mg, hydrapermazol:100mg]\",\"allergies\":\"[nillacilan]\"},{\"firstName\":\"Hank\",\"lastName\":\"Hill\",\"phoneNumber\":\"312-333-6789\",\"age\":26,\"medications\":\"[]\",\"allergies\":\"[]\"}]},{\"address\":\"100 Unknown Dr\",\"persons\":[]}]}", response);
    }

    @Test
    public void testCreatePersonInfoResponse() throws JsonProcessingException {
        String response = alertsService.createPersonInfoResponse("Hank-Hill");
        assertEquals("{\"firstName\":\"Hank\",\"lastName\":\"Hill\",\"address\":\"123 Rainy St\",\"email\":\"123 Rainy St\",\"age\":26,\"medications\":\"[]\",\"allergies\":\"[]\"}", response);
    }

    @Test
    public void testCreateEmailAddressesByCityResponse() throws JsonProcessingException {
        String response = alertsService.createEmailAddressesByCityResponse("Arlen");
        assertEquals("{\"emails\":[\"bhill@mail.com\",\"hhill@mail.com\"]}", response);
    }

    @Test
    public void testAddPerson() throws PersonException {
        Person newPerson = new Person("Ethan", "Lengfeld",
                "1010 Null St", "Chicago", "60611",
                "912-340-9800", "elengfeld@mail.com");
        alertsService.addPerson(newPerson);
        assertNotNull(personsMap.get("Ethan-Lengfeld"));

        assertThrows(PersonException.class, () -> {
            Person existingPerson = new Person("Hank", "Hill",
                    "123 Rainy St", "Arlen","60610", "312-333-6789",
                    "hhill@mail.com");
            alertsService.addPerson(existingPerson);
        });
    }

    @Test
    public void testUpdatePerson() throws PersonException {
        Person existingPerson = new Person("Hank", "Hill",
                "123 Rainy St", "Arlen","60610", "312-333-6789",
                "hhill@gmail.com");
        alertsService.updateExistingPerson(existingPerson);
        assertNotNull(personsMap.get("Hank-Hill"));
        assertEquals("hhill@gmail.com", personsMap.get("Hank-Hill").getEmail());

        assertThrows(PersonException.class, () -> {
            Person newPerson = new Person("Ethan", "Lengfeld",
                    "1010 Null St", "Chicago", "60611",
                    "912-340-9800", "elengfeld@mail.com");
            alertsService.updateExistingPerson(newPerson);
        });
    }

    @Test
    public void testDeletePerson() throws PersonException {
        Person newPerson = new Person("Bill", "Bob",
                "3 Integer Ave", "Miami", "74920",
                "739-643-1820", "bbob@mail.com");
        alertsService.addPerson(newPerson);
        alertsService.deletePerson("Bill", "Bob");
        assertNull(personsMap.get("Bill-Bob"));

        assertThrows(PersonException.class, () -> {
            alertsService.deletePerson("Unknown", "Person");
        });
    }

    @Test
    public void testAddFirestation() throws FireStationException {
        FireStation newFireStation = new FireStation("21 Fire Rd", 5);
        alertsService.addFirestation(newFireStation);
        assertNotNull(fireStationsMap.get("21 Fire Rd"));

        assertThrows(FireStationException.class, () -> {
            FireStation existingFirestation = new FireStation("123 Rainy St", 1);
            alertsService.addFirestation(existingFirestation);
        });
    }

    @Test
    public void testUpdateFirestation() throws FireStationException {
        FireStation existingFirestation = new FireStation("124 Conch St", 4);
        alertsService.updateExistingFirestation(existingFirestation);
        assertNotNull(fireStationsMap.get("124 Conch St"));
        assertEquals(4, fireStationsMap.get("124 Conch St").getStation());

        assertThrows(FireStationException.class, () -> {
            FireStation newFirestation = new FireStation("8653 New Ave", 17);
            alertsService.updateExistingFirestation(newFirestation);
        });
    }

    @Test
    public void testDeleteFirestation() throws FireStationException {
        FireStation newFirestation = new FireStation("35 New Address Dr", 10);
        alertsService.addFirestation(newFirestation);
        alertsService.deleteFirestation("35 New Address Dr");
        assertNull(fireStationsMap.get("35 New Address Dr"));

        assertThrows(FireStationException.class, () -> {
            alertsService.deleteFirestation("10 Fake Ct");
        });
    }

    @Test
    public void testAddMedicalRecord() throws MedicalRecordException {
        MedicalRecord newMedicalRecord = new MedicalRecord("Ethan", "Lengfeld",
                "07/11/1992", new String[]{}, new String[]{});
        alertsService.addMedicalRecord(newMedicalRecord);
        assertNotNull(medicalRecordsMap.get("Ethan-Lengfeld"));

        assertThrows(MedicalRecordException.class, () -> {
            MedicalRecord existingMedicalRecord = new MedicalRecord("Hank", "Hill",
                    "04/13/1998", new String[]{}, new String[]{});
            alertsService.addMedicalRecord(existingMedicalRecord);
        });
    }

    @Test
    public void testUpdateMedicalRecord() throws MedicalRecordException {
        MedicalRecord existingMedicalRecord = new MedicalRecord("Hank", "Hill",
                "12/06/1954", new String[]{}, new String[]{});
        alertsService.updateExistingMedicalRecord(existingMedicalRecord);
        assertNotNull(medicalRecordsMap.get("Hank-Hill"));
        assertEquals("12/06/1954", medicalRecordsMap.get("Hank-Hill").getBirthdate());

        assertThrows(MedicalRecordException.class, () -> {
            MedicalRecord newMedicalRecord = new MedicalRecord("Ethan", "Lengfeld",
                    "03/03/1993", new String[]{}, new String[]{});
            alertsService.updateExistingMedicalRecord(newMedicalRecord);
        });
    }

    @Test
    public void testDeleteMedicalRecord() throws MedicalRecordException {
        MedicalRecord newMedicalRecord = new MedicalRecord("Bill", "Bob",
                "03/03/1993", new String[]{}, new String[]{});
        alertsService.addMedicalRecord(newMedicalRecord);
        alertsService.deleteMedicalRecord("Bill", "Bob");
        assertNull(medicalRecordsMap.get("Bill-Bob"));

        assertThrows(MedicalRecordException.class, () -> {
            alertsService.deleteMedicalRecord("Unknown", "Person");
        });
    }



    private static void populateMapsWithTestData() {
        personsMap = new HashMap<>();

        String adultFirstName = "Hank";
        String adultLastName = "Hill";
        String address = "123 Rainy St";
        Person adultPerson = new Person(adultFirstName, adultLastName, address, "Arlen",
                "60610", "312-333-6789", "hhill@mail.com");
        personsMap.put(JsonDataUtil.createPersonMapKey(adultFirstName, adultLastName), adultPerson);

        String childFirstName = "Bobby";
        String childLastName = "Hill";
        Person childPerson = new Person(childFirstName, childLastName, address, "Arlen",
                    "60610", "312-333-6789", "bhill@mail.com");
        personsMap.put(JsonDataUtil.createPersonMapKey(childFirstName, childLastName), childPerson);

        String firstName = "Mike";
        String lastName = "Smith";
        Person person = new Person(firstName, lastName, "3001 Random Ave", "Chicago",
                "60610", "744-739-3889", "msmith@mail.com");
        personsMap.put(JsonDataUtil.createPersonMapKey(firstName, lastName), person);

        fireStationsMap = new HashMap<>();
        fireStationsMap.put(address, new FireStation(address, 1));
        fireStationsMap.put("124 Conch St", new FireStation("124 Conch St", 2));

        medicalRecordsMap = new HashMap<>();
        MedicalRecord adultMedicalRecord = new MedicalRecord(adultFirstName, adultLastName,
                "04/13/1998", new String[]{}, new String[]{});
        MedicalRecord childMedicalRecord = new MedicalRecord(childFirstName, childLastName,
                "01/26/2018", new String[]{"aznol:350mg", "hydrapermazol:100mg"},
                new String[]{"nillacilan"});
        MedicalRecord medicalRecord = new MedicalRecord(firstName, lastName,
                "11/02/2000", new String[]{},
                new String[]{});
        medicalRecordsMap.put(JsonDataUtil.createPersonMapKey(adultFirstName, adultLastName), adultMedicalRecord);
        medicalRecordsMap.put(JsonDataUtil.createPersonMapKey(childFirstName, childLastName), childMedicalRecord);
        medicalRecordsMap.put(JsonDataUtil.createPersonMapKey(firstName, lastName), medicalRecord);
    }
}
