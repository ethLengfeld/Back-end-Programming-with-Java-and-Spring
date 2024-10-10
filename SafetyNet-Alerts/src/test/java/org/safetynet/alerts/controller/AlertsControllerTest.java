package org.safetynet.alerts.controller;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.safetynet.alerts.service.AlertsService;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class AlertsControllerTest {

    private static AlertsService alertsService;
    private static AlertsController alertsController;

    @BeforeAll
    static void setUp() {
        alertsService = mock(AlertsService.class);
        alertsController = new AlertsController(alertsService);
    }

    @Test
    public void getPersonsServicedByStationNumber() {
        //TODO fix
        when(alertsService.getStationAddressesFromStationNumber(1)).thenReturn(null);
        alertsController.getPersonsServicedByStationNumber(1);
    }

    @Test
    public void getChildrenFromAddress() {
        alertsController.getChildrenFromAddress("address");
    }

    @Test
    public void getPhoneNumbersForPeopleByStationNumber() {
        alertsController.getPhoneNumbersForPeopleByStationNumber(1);
    }

    @Test
    public void getStationNumberAndPeopleByAddress() {
        alertsController.getStationNumberAndPeopleByAddress("address");
    }

    @Test
    public void getHouseholdsForEachStationNumber() {
        alertsController.getHouseholdsForEachStationNumber(new Integer[]{1, 2});
    }

    @Test
    public void getPersonInfo() {
        alertsController.getPersonInfo("firstName", "lastName");
    }

    @Test
    public void getEmailAddressesByCity() {
        alertsController.getEmailAddressesByCity("city");
    }

    @Test
    public void addNewPerson() {
        alertsController.addNewPerson("firstName", "lastName", "address", "city", "zip", "phone_number", "email");
    }

    @Test
    public void updateExistingPerson() {
        alertsController.updateExistingPerson("firstName", "lastName", "address", "city", "zip", "phone_number", "email");
    }

    @Test
    public void deletePerson() {
        alertsController.deletePerson("firstName", "lastName");
    }

    @Test
    public void addFirestation() {
        alertsController.addFirestation("address", 1);
    }

    @Test
    public void updateExistingFirestation() {
        alertsController.updateExistingFirestation("address", 1);
    }
    @Test
    public void deleteFirestation() {
        alertsController.deleteFirestation("address");
    }

    @Test
    public void addMedicalRecord() {
        alertsController.addMedicalRecord("firstName", "lastName", "birthdate", new String[]{"medications"}, new String[]{"allergies"});
    }

    @Test
    public void updateExistingMedicalRecord() {
        alertsController.updateExistingMedicalRecord("firstName", "lastName", "birthdate", new String[]{"medications"}, new String[]{"allergies"});
    }

    @Test
    public void deleteMedicalRecord() {
        alertsController.deleteMedicalRecord("firstName", "lastName");
    }

}
