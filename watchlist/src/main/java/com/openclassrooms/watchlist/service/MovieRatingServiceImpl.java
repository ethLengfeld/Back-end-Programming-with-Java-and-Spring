package com.openclassrooms.watchlist.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.node.ObjectNode;

@Slf4j
@Service
@ConditionalOnProperty(name = "app.environment", havingValue = "prod")
public class MovieRatingServiceImpl implements MovieRatingService {

    private final ObjectMapper mapper = new ObjectMapper();
    private final String apiUrl = "http://www.omdbapi.com/?apikey=" + System.getenv("apikey") + "&t=";

    @Override
    public String getMovieRating(String title) {
        try {
            RestTemplate template = new RestTemplate();

            String completeUrl = apiUrl + title;
            ResponseEntity<ObjectNode> response =
                    template.getForEntity(completeUrl, ObjectNode.class);
            log.debug("Getting movie request from url " + completeUrl);

            ObjectNode jsonObject = response.getBody();
            log.debug(mapper.writeValueAsString(jsonObject));

            assert jsonObject != null;
            return jsonObject.path("imdbRating").asText();
        } catch (Exception e) {
            log.error("Something went wrong while calling OMDb API, {}", e.getMessage());
            return null;
        }
    }

}
