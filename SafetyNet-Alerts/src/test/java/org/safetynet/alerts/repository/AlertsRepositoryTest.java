package org.safetynet.alerts.repository;

import org.junit.jupiter.api.Test;
import org.safetynet.alerts.model.FireStation;
import org.safetynet.alerts.model.MedicalRecord;
import org.safetynet.alerts.model.Person;
import org.springframework.test.util.ReflectionTestUtils;

import static org.junit.jupiter.api.Assertions.*;

public class AlertsRepositoryTest {

    @Test
    public void testInit() {
        AlertsRepository alertsRepository = new AlertsRepository();
        ReflectionTestUtils.setField(alertsRepository, "jsonFilePath", "src/test/resources/test_data_file.json");
        alertsRepository.init();

        assertNotNull(alertsRepository.getPersons());
        assertNotNull(alertsRepository.getFireStations());
        assertNotNull(alertsRepository.getMedicalRecords());
        assertEquals(1, alertsRepository.getPersons().size());
        assertEquals(1, alertsRepository.getFireStations().size());
        assertEquals(1, alertsRepository.getMedicalRecords().size());
        //assert Person data
        Person person = alertsRepository.getPersons().get("John-Boyd");
        assertEquals("John", person.getFirstName());
        assertEquals("Boyd", person.getLastName());
        assertEquals("1509 Culver St", person.getAddress());
        assertEquals("Culver", person.getCity());
        assertEquals("97451", person.getZip());
        assertEquals("841-874-6512", person.getPhone());
        assertEquals("jaboyd@email.com", person.getEmail());
        //assert FireStation data
        FireStation fireStation = alertsRepository.getFireStations().get("1509 Culver St");
        assertEquals("1509 Culver St", fireStation.getAddress());
        assertEquals(3, fireStation.getStation());
        //assert MedicalRecord data
        MedicalRecord medicalRecord = alertsRepository.getMedicalRecords().get("John-Boyd");
        assertEquals("John", medicalRecord.getFirstName());
        assertEquals("Boyd", medicalRecord.getLastName());
        assertEquals("03/06/1984", medicalRecord.getBirthdate());
        assertEquals(2, medicalRecord.getMedications().length);
        assertEquals(1, medicalRecord.getAllergies().length);
    }

    @Test
    public void testInitInvalidFile() {
        AlertsRepository alertsRepository = new AlertsRepository();
        ReflectionTestUtils.setField(alertsRepository, "jsonFilePath", "src/test/resources/invalid_file.json");
        assertThrows(RuntimeException.class, alertsRepository::init);
    }
}
