package com.openclassrooms.course.springboot.activitych3.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class CurrencyService {


    /**
     *  This calls an external API on the internet from https://currencylayer.com
     *  It's free and you can get your own access_key from the website
     *  to see and example of the API output go to:
     *  http://apilayer.net/api/live?access_key=e8f742c7609c8d94f4d40f7d1fd104d9&currencies=AUD&format=1
     *
     * @param currency that we want to see the exchange rate of
     * @return the exchange rate USD -> currency
     */
    public Double getConversionRateToUsd(String currency) {

        String apiUrl = "http://apilayer.net/api/live?access_key=e8f742c7609c8d94f4d40f7d1fd104d9&currencies=";
        RestTemplate template = new RestTemplate();
        try {
            ResponseEntity<ObjectNode> response = template.getForEntity(apiUrl + currency.toUpperCase(), ObjectNode.class);

            JsonNode rateNode = response.getBody().path("quotes").path("USD" + currency.toUpperCase());
            if (rateNode.isMissingNode()) {
                return null;
            }
            return rateNode.asDouble();
        } catch (Exception e) {
            System.out.println("ERROR! Exception happened while trying to get currency!" + e.getMessage());
            return null;
        }
    }
}
