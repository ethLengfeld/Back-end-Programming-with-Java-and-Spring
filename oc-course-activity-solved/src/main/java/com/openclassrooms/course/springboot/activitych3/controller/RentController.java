package com.openclassrooms.course.springboot.activitych3.controller;

import com.openclassrooms.course.springboot.activitych3.service.RentService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.Map;

/**
 * This controller provides one HTTP end-point that returns the cost of renting a one bedroom apartment
 * in the city of major cities in the world in any desired currency.
 *
 * The rental cost in those cities in USD are stored in rents.csv in CSV format.
 * The exchange rate however is retrieved dynamically on the fly from an external API
 *
 * Start the application and go to http://localhost:8080/rent?city=sydney&currency=AUD on your browser
 * to see how it works
 */


@RestController
public class RentController {

    RentService rentService;

    public RentController(RentService rentService) {
        this.rentService = rentService;
    }

    /**
     * e.g. http://localhost:8080/rent?city=sydney&currency=AUD
     *
     * @param city the city that the apartment is located in
     * @param currency the currency we want to see the rental cost in
     * @return the calculated rental cost of a 1 BR apartment
     */
    @GetMapping("/rent")
    public Map<String, Object> getRentForCityInCurrency(@RequestParam("city") String city, @RequestParam("currency") String currency) {

        return Collections.singletonMap("result", rentService.getCityRent(city, currency));
    }
}
