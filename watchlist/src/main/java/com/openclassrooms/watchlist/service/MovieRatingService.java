package com.openclassrooms.watchlist.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.node.ObjectNode;

public class MovieRatingService {

    private final ObjectMapper mapper = new ObjectMapper();
    private final String apiUrl = "http://www.omdbapi.com/?apikey=" + System.getenv("apikey") + "&t=";

    public String getMovieRating(String title) {
        try {
            RestTemplate template = new RestTemplate();

            String completeUrl = apiUrl + title;
            ResponseEntity<ObjectNode> response =
                    template.getForEntity(completeUrl, ObjectNode.class);
            System.out.println("Getting movie request from url " + completeUrl);

            ObjectNode jsonObject = response.getBody();
            System.out.println(mapper.writeValueAsString(jsonObject));

            return jsonObject.path("imdbRating").asText();
        } catch (Exception e) {
            System.out.println("Something went wront while calling OMDb API" + e.getMessage());
            return null;
        }
    }

}
