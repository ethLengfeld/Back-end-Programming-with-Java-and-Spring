package org.safetynet.alerts.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;

@Service
public class DataLoader {

    @PostConstruct
    public void init() {
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            JsonNode currObject = objectMapper.readTree(new File("./src/main/resources/data_file.json"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
