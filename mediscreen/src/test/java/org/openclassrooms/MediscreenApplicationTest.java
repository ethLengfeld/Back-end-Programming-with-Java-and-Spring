package org.openclassrooms;

import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.openclassrooms.mediscreen.MediscreenApplication;
import org.springframework.boot.SpringApplication;

import static org.mockito.Mockito.mockStatic;

public class MediscreenApplicationTest {

    @Test
    void main() {
        try (MockedStatic<SpringApplication> mockedSpringApplication = mockStatic(SpringApplication.class)) {
            MediscreenApplication.main(null);
        }
    }
}
