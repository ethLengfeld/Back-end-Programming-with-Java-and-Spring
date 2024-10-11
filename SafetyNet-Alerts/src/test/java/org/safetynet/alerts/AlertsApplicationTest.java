package org.safetynet.alerts;

import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.springframework.boot.SpringApplication;

import javax.swing.*;

import static org.mockito.Mockito.mockStatic;

public class AlertsApplicationTest {

    @Test
    public void testMain() {
        try (MockedStatic<SpringApplication> mockedStatic = mockStatic(SpringApplication.class)) {
            AlertsApplication.main(new String[]{});
            mockedStatic.verify(() -> SpringApplication.run(AlertsApplication.class, new String[]{}));
        }
    }
}
