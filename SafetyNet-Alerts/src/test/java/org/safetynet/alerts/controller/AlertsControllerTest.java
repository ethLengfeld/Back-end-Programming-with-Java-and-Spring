package org.safetynet.alerts.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.safetynet.alerts.exception.FireStationException;
import org.safetynet.alerts.exception.MedicalRecordException;
import org.safetynet.alerts.exception.PersonException;
import org.safetynet.alerts.model.Person;
import org.safetynet.alerts.service.AlertsService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.mockito.Mockito.*;

public class AlertsControllerTest {

    private static AlertsService alertsService;
    private static AlertsController alertsController;
    private static List<String> addresses;
    private static List<Person> persons;

    @BeforeAll
    static void setUp() {
        alertsService = mock(AlertsService.class);
        alertsController = new AlertsController(alertsService);
        addresses = List.of("123 Apple Street");
        persons = List.of(new Person(), new Person(), new Person());
    }

    @Test
    public void getPersonsServicedByStationNumber() throws JsonProcessingException {
        //positive case
        when(alertsService.getStationAddressesFromStationNumber(ArgumentMatchers.anyInt())).thenReturn(addresses);
        when(alertsService.getPersonsFromAddresses(addresses)).thenReturn(persons);
        when(alertsService.createPersonsServicedByStationNumberResponse(persons)).thenReturn("{persons}");
        ResponseEntity<String> response = alertsController.getPersonsServicedByStationNumber(1);
        Assertions.assertNotNull(response);
        Assertions.assertEquals("{persons}", response.getBody());
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());

        //negative case
        when(alertsService.createPersonsServicedByStationNumberResponse(persons)).thenThrow(JsonProcessingException.class);
        response = alertsController.getPersonsServicedByStationNumber(-1);
        Assertions.assertNotNull(response);
        Assertions.assertEquals("{}", response.getBody());
        Assertions.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    public void getChildrenFromAddress() throws JsonProcessingException {
        //positive case
        when(alertsService.getPersonsFromAddresses(ArgumentMatchers.any())).thenReturn(persons);
        when(alertsService.createChildrenFromAddressResponse(persons)).thenReturn("{children}");
        ResponseEntity<String> response = alertsController.getChildrenFromAddress("address");
        Assertions.assertNotNull(response);
        Assertions.assertEquals("{children}", response.getBody());
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());

        //negative case
        when(alertsService.createChildrenFromAddressResponse(persons)).thenThrow(JsonProcessingException.class);
        response = alertsController.getChildrenFromAddress("bad address");
        Assertions.assertNotNull(response);
        Assertions.assertEquals("{}", response.getBody());
        Assertions.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    public void getPhoneNumbersForPeopleByStationNumber() throws JsonProcessingException {
        //positive case
        when(alertsService.getStationAddressesFromStationNumber(ArgumentMatchers.anyInt())).thenReturn(addresses);
        when(alertsService.getPersonsFromAddresses(addresses)).thenReturn(persons);
        when(alertsService.createPhoneNumbersForPeopleByStationNumberResponse(persons)).thenReturn("{persons}");
        ResponseEntity<String> response = alertsController.getPhoneNumbersForPeopleByStationNumber(1);
        Assertions.assertNotNull(response);
        Assertions.assertEquals("{persons}", response.getBody());
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());

        //negative case
        when(alertsService.createPhoneNumbersForPeopleByStationNumberResponse(persons)).thenThrow(JsonProcessingException.class);
        response = alertsController.getPhoneNumbersForPeopleByStationNumber(-1);
        Assertions.assertNotNull(response);
        Assertions.assertEquals("{}", response.getBody());
        Assertions.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    public void getStationNumberAndPeopleByAddress() throws JsonProcessingException {
        //positive case
        when(alertsService.getPersonsFromAddresses(ArgumentMatchers.any())).thenReturn(persons);
        when(alertsService.createStationNumberAndPeopleByAddressResponse(persons)).thenReturn("{station and persons}");
        ResponseEntity<String> response = alertsController.getStationNumberAndPeopleByAddress("address");
        Assertions.assertNotNull(response);
        Assertions.assertEquals("{station and persons}", response.getBody());
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());

        //negative case
        when(alertsService.getPersonsFromAddresses(ArgumentMatchers.any())).thenReturn(persons);
        when(alertsService.createStationNumberAndPeopleByAddressResponse(persons)).thenThrow(JsonProcessingException.class);
        response = alertsController.getStationNumberAndPeopleByAddress("bad address");
        Assertions.assertNotNull(response);
        Assertions.assertEquals("{}", response.getBody());
        Assertions.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    public void getHouseholdsForEachStationNumber() throws JsonProcessingException {
        //positive case
        when(alertsService.getStationAddressesFromStationNumber(ArgumentMatchers.anyInt())).thenReturn(addresses);
        when(alertsService.createHouseholdsForEachStationNumberResponse(ArgumentMatchers.any())).thenReturn("{households}");
        ResponseEntity<String> response = alertsController.getHouseholdsForEachStationNumber(new Integer[]{1, 2});
        Assertions.assertNotNull(response);
        Assertions.assertEquals("{households}", response.getBody());
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());

        //negative case
        when(alertsService.getStationAddressesFromStationNumber(ArgumentMatchers.anyInt())).thenReturn(addresses);
        when(alertsService.createHouseholdsForEachStationNumberResponse(ArgumentMatchers.any())).thenThrow(JsonProcessingException.class);
        when(alertsService.getStationAddressesFromStationNumber(ArgumentMatchers.anyInt())).thenReturn(addresses);
        response = alertsController.getHouseholdsForEachStationNumber(new Integer[]{});
        Assertions.assertNotNull(response);
        Assertions.assertEquals("{}", response.getBody());
        Assertions.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    public void getPersonInfo() throws JsonProcessingException {
        //positive case
        when(alertsService.createPersonInfoResponse(ArgumentMatchers.anyString())).thenReturn("{person info}");
        ResponseEntity<String> response = alertsController.getPersonInfo("firstName", "lastName");
        Assertions.assertNotNull(response);
        Assertions.assertEquals("{person info}", response.getBody());
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());

        //negative case
        when(alertsService.createPersonInfoResponse(ArgumentMatchers.anyString())).thenThrow(JsonProcessingException.class);
        response = alertsController.getPersonInfo("firstName", "lastName");
        Assertions.assertNotNull(response);
        Assertions.assertEquals("{}", response.getBody());
        Assertions.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    public void getEmailAddressesByCity() throws JsonProcessingException {
        //positive case
        when(alertsService.createEmailAddressesByCityResponse("city")).thenReturn("{emails}");
        ResponseEntity<String> response = alertsController.getEmailAddressesByCity("city");
        Assertions.assertNotNull(response);
        Assertions.assertEquals("{emails}", response.getBody());
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());

        //negative case
        when(alertsService.createEmailAddressesByCityResponse("bad city")).thenThrow(JsonProcessingException.class);
        response = alertsController.getEmailAddressesByCity("bad city");
        Assertions.assertNotNull(response);
        Assertions.assertEquals("{}", response.getBody());
        Assertions.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    public void addNewPerson() throws PersonException {
        //positive case
        doNothing().when(alertsService).addPerson(ArgumentMatchers.any());
        ResponseEntity<String> response = alertsController.addNewPerson("firstName", "lastName", "address", "city", "zip", "phone_number", "email");
        Assertions.assertNotNull(response);
        Assertions.assertEquals("SUCCESS", response.getBody());
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());

        //negative case
        doThrow(PersonException.class).when(alertsService).addPerson(ArgumentMatchers.any());
        response = alertsController.addNewPerson("firstName", "lastName", "address", "city", "zip", "phone_number", "email");
        Assertions.assertNotNull(response);
        Assertions.assertEquals("FAILURE", response.getBody());
        Assertions.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    public void updateExistingPerson() throws PersonException {
        //positive case
        doNothing().when(alertsService).updateExistingPerson(ArgumentMatchers.any());
        ResponseEntity<String> response = alertsController.updateExistingPerson("firstName", "lastName", "address", "city", "zip", "phone_number", "email");
        Assertions.assertNotNull(response);
        Assertions.assertEquals("SUCCESS", response.getBody());
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());

        //negative case
        doThrow(PersonException.class).when(alertsService).updateExistingPerson(ArgumentMatchers.any());
        response = alertsController.updateExistingPerson("firstName", "lastName", "address", "city", "zip", "phone_number", "email");
        Assertions.assertNotNull(response);
        Assertions.assertEquals("FAILURE", response.getBody());
        Assertions.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    public void deletePerson() throws PersonException {
        //positive case
        doNothing().when(alertsService).deletePerson(ArgumentMatchers.anyString(), ArgumentMatchers.anyString());
        ResponseEntity<String> response = alertsController.deletePerson("firstName", "lastName");
        Assertions.assertNotNull(response);
        Assertions.assertEquals("SUCCESS", response.getBody());
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());

        //negative case
        doThrow(PersonException.class).when(alertsService).deletePerson(ArgumentMatchers.anyString(), ArgumentMatchers.anyString());
        response = alertsController.deletePerson("firstName", "lastName");
        Assertions.assertNotNull(response);
        Assertions.assertEquals("FAILURE", response.getBody());
        Assertions.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    public void addFirestation() throws FireStationException {
        //positive case
        doNothing().when(alertsService).addFirestation(ArgumentMatchers.any());
        ResponseEntity<String> response = alertsController.addFirestation("address", 1);
        Assertions.assertNotNull(response);
        Assertions.assertEquals("SUCCESS", response.getBody());
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());

        //negative case
        doThrow(FireStationException.class).when(alertsService).addFirestation(ArgumentMatchers.any());
        response = alertsController.addFirestation("address", 1);
        Assertions.assertNotNull(response);
        Assertions.assertEquals("FAILURE", response.getBody());
        Assertions.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    public void updateExistingFirestation() throws FireStationException {
        //positive case
        doNothing().when(alertsService).updateExistingFirestation(ArgumentMatchers.any());
        ResponseEntity<String> response = alertsController.updateExistingFirestation("address", 1);
        Assertions.assertNotNull(response);
        Assertions.assertEquals("SUCCESS", response.getBody());
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());

        //negative case
        doThrow(FireStationException.class).when(alertsService).updateExistingFirestation(ArgumentMatchers.any());
        response = alertsController.updateExistingFirestation("address", 1);
        Assertions.assertNotNull(response);
        Assertions.assertEquals("FAILURE", response.getBody());
        Assertions.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }
    @Test
    public void deleteFirestation() throws FireStationException {
        //positive case
        doNothing().when(alertsService).deleteFirestation(ArgumentMatchers.anyString());
        ResponseEntity<String> response = alertsController.deleteFirestation("address");
        Assertions.assertNotNull(response);
        Assertions.assertEquals("SUCCESS", response.getBody());
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());

        //negative case
        doThrow(FireStationException.class).when(alertsService).deleteFirestation(ArgumentMatchers.anyString());
        response = alertsController.deleteFirestation("address");
        Assertions.assertNotNull(response);
        Assertions.assertEquals("FAILURE", response.getBody());
        Assertions.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    public void addMedicalRecord() throws MedicalRecordException {
        //positive case
        doNothing().when(alertsService).addMedicalRecord(ArgumentMatchers.any());
        ResponseEntity<String> response = alertsController.addMedicalRecord("firstName", "lastName", "birthdate", new String[]{"medications"}, new String[]{"allergies"});
        Assertions.assertNotNull(response);
        Assertions.assertEquals("SUCCESS", response.getBody());
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());

        //negative case
        doThrow(MedicalRecordException.class).when(alertsService).addMedicalRecord(ArgumentMatchers.any());
        response = alertsController.addMedicalRecord("firstName", "lastName", "birthdate", new String[]{"medications"}, new String[]{"allergies"});
        Assertions.assertNotNull(response);
        Assertions.assertEquals("FAILURE", response.getBody());
        Assertions.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    public void updateExistingMedicalRecord() throws MedicalRecordException {
        //positive case
        doNothing().when(alertsService).updateExistingMedicalRecord(ArgumentMatchers.any());
        ResponseEntity<String> response = alertsController.updateExistingMedicalRecord("firstName", "lastName", "birthdate", new String[]{"medications"}, new String[]{"allergies"});
        Assertions.assertNotNull(response);
        Assertions.assertEquals("SUCCESS", response.getBody());
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());

        //negative case
        doThrow(MedicalRecordException.class).when(alertsService).updateExistingMedicalRecord(ArgumentMatchers.any());
        response = alertsController.updateExistingMedicalRecord("firstName", "lastName", "birthdate", new String[]{"medications"}, new String[]{"allergies"});
        Assertions.assertNotNull(response);
        Assertions.assertEquals("FAILURE", response.getBody());
        Assertions.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    public void deleteMedicalRecord() throws MedicalRecordException {
        //positive case
        doNothing().when(alertsService).deleteMedicalRecord(ArgumentMatchers.anyString(), ArgumentMatchers.anyString());
        ResponseEntity<String> response = alertsController.deleteMedicalRecord("firstName", "lastName");
        Assertions.assertNotNull(response);
        Assertions.assertEquals("SUCCESS", response.getBody());
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());

        //negative case
        doThrow(MedicalRecordException.class).when(alertsService).deleteMedicalRecord(ArgumentMatchers.anyString(), ArgumentMatchers.anyString());
        response = alertsController.deleteMedicalRecord("firstName", "lastName");
        Assertions.assertNotNull(response);
        Assertions.assertEquals("FAILURE", response.getBody());
        Assertions.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

}
