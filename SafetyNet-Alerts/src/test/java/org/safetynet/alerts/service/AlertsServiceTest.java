package org.safetynet.alerts.service;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.safetynet.alerts.repository.AlertsRepository;

import static org.mockito.Mockito.mock;

public class AlertsServiceTest {

    private static AlertsRepository alertsRepository;
    private static AlertsService alertsService;

    @BeforeAll
    static void setUp() {
        alertsRepository = mock(AlertsRepository.class);
        alertsService = new AlertsService(alertsRepository);
    }

    @Test
    public void testGetStationAddressesFromStationNumber() {

    }
}
