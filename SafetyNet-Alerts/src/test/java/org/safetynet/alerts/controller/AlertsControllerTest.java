package org.safetynet.alerts.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
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
    public void testGetPersonsServicedByStationNumber() throws JsonProcessingException {
        //TODO fix
        when(alertsService.getStationAddressesFromStationNumber(1)).thenReturn(null);
        alertsController.getPersonsServicedByStationNumber(1);
    }
}
