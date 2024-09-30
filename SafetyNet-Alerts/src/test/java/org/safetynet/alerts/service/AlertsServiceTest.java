package org.safetynet.alerts.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
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

        fireStationsMap = new HashMap<>();
        fireStationsMap.put(address, new FireStation(address, 1));
        fireStationsMap.put("124 Conch St", new FireStation("124 Conch St", 2));

        medicalRecordsMap = new HashMap<>();
        MedicalRecord adultMedicalRecord = new MedicalRecord(adultFirstName, adultLastName,
                "04/13/1998", new String[]{}, new String[]{});
        MedicalRecord childMedicalRecord = new MedicalRecord(childFirstName, childLastName,
                "01/26/2018", new String[]{"aznol:350mg", "hydrapermazol:100mg"},
                new String[]{"nillacilan"});
        medicalRecordsMap.put(JsonDataUtil.createPersonMapKey(adultFirstName, adultLastName), adultMedicalRecord);
        medicalRecordsMap.put(JsonDataUtil.createPersonMapKey(childFirstName, childLastName), childMedicalRecord);
    }
}
