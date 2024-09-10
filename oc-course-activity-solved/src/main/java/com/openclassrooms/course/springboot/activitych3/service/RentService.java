package com.openclassrooms.course.springboot.activitych3.service;

import com.openclassrooms.course.springboot.activitych3.domain.Rent;
import com.openclassrooms.course.springboot.activitych3.repository.RentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

@Service
public class RentService {

    RentRepository rentRepository;

    CurrencyService currencyService;

    @Autowired
    public RentService(RentRepository rentRepository, CurrencyService currencyService) {
        this.rentRepository = rentRepository;
        this.currencyService = currencyService;
    }

    public String getCityRent(@RequestParam("city") String city, @RequestParam("currency") String currency) {

        String result;
        Rent rent = rentRepository.findByCity(city);

        if (rent == null) {
            System.out.println("Error: No rental data found for the city "+city);
            result = "No rental data found for the city: "+city;
        } else {
            Double conversionRateToUsd = currencyService.getConversionRateToUsd(currency);
            if (conversionRateToUsd == null) {
                result = "No exchange rate found for the currency "+currency;
            } else {
                double rentInCurrency = rent.getRent() * conversionRateToUsd;
                result = rentInCurrency + " " + currency;
            }
        }
        return result;
    }
}
