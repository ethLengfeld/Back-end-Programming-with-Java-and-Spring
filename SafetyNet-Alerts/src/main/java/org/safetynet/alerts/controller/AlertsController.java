package org.safetynet.alerts.controller;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.safetynet.alerts.model.Person;
import org.safetynet.alerts.service.AlertsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AlertsController {

    private AlertsService alertsService;

    @Autowired
    public AlertsController(AlertsService alertsService) {
        this.alertsService = alertsService;
    }

    /**
     * <a href="http://localhost:8080/firestation?stationNumber=">...</a>
     * This URL should return a list of people serviced by the corresponding fire station. So if station number = 1,
     * it should return the people serviced by station number 1. The list of people should include these specific
     * pieces of information: first name, last name, address, phone number. As well, it should provide a
     * summary of the number of adults in the service area and the number of children (anyone aged 18 or
     * younger).
     * @param stationNumber - integer representing the station number
     * @return string with first name, last name, address, phone number, number of adults, and number of children
     */
    @GetMapping("/firestation")
    public ResponseEntity<String> getPersonsServicedByStationNumber(@RequestParam Integer stationNumber) throws JsonProcessingException {

        List<String> listOfAddresses = alertsService.getStationAddressesFromStationNumber(stationNumber);
        List<Person> servicedPersons = alertsService.getPersonsFromAddresses(listOfAddresses);
        String jsonResponse = alertsService.createPersonsServicedByStationNumberResponse(servicedPersons);

        return new ResponseEntity<>(jsonResponse, HttpStatus.OK);
    }

    /**
     * <a href="http://localhost:8080/childAlert?address=">...</a>
     * This URL should return a list of children (anyone under the age of 18) at that address. The list should
     * include the first and last name of each child, the child’s age, and a list of other persons living at that
     * address. If there are no children at the address, then this URL can return an empty string.
     * @param address - string representing the address
     * @return list of children with first name, last name, age, and address
     */
    @GetMapping("/childAlert")
    public ResponseEntity<String> getChildrenFromAddress(@RequestParam String address) throws JsonProcessingException {
        List<String> listOfAddress = List.of(address);
        List<Person> servicedPersons = alertsService.getPersonsFromAddresses(listOfAddress);
        String jsonResponse = alertsService.createChildrenFromAddressResponse(servicedPersons);
        return new ResponseEntity<>(jsonResponse, HttpStatus.OK);
    }

    /**
     * <a href="http://localhost:8080/phoneAlert?firestation=">...</a>
     * This URL should return a list of phone numbers of each person within the fire station’s jurisdiction.
     * We’ll use this to send emergency text messages to specific households.
     * @param stationNumber - integer representing the station number
     * @return list of phone numbers
     */
    @GetMapping("/phoneAlert")
    public ResponseEntity<String> getPhoneNumbersForPeopleByStationNumber(@RequestParam Integer stationNumber) throws JsonProcessingException {
        List<String> listOfAddresses = alertsService.getStationAddressesFromStationNumber(stationNumber);
        List<Person> servicedPersons = alertsService.getPersonsFromAddresses(listOfAddresses);
        String jsonResponse = alertsService.createPhoneNumbersForPeopleByStationNumberResponse(servicedPersons);

        return new ResponseEntity<>(jsonResponse, HttpStatus.OK);
    }

    /**
     * <a href="http://localhost:8080/fire?address=">...</a>
     * This URL should return the fire station number that services the provided address as well as
     * a list of all of the people living at the address. This list should include each person’s name,
     * phone number, age, medications with dosage, and allergies.
     * @param address - string representing firestation address
     * @return list of all people living at address
     */
    @GetMapping("/fire")
    public ResponseEntity<String> getStationNumberAndPeopleByAddress(@RequestParam String address) throws JsonProcessingException {
        // TODO multiple fire stations returned by address '112 Steppes Pl'
        List<String> listOfAddress = List.of(address);
        List<Person> servicedPersons = alertsService.getPersonsFromAddresses(listOfAddress);
        String jsonResponse = alertsService.createStationNumberAndPeopleByAddressResponse(servicedPersons);

        return new ResponseEntity<>(jsonResponse, HttpStatus.OK);
    }

    /**
     * <a href="http://localhost:8080/flood/stations?stations=">...</a>
     * This should return a list of all the households in each fire station’s jurisdiction. This list needs to group
     * people by household address, include name, phone number, and age of each person, and any
     * medications (with dosages) and allergies beside each person’s name.
     * @param stationNumbers - list of stationNumbers
     * @return list of all address and persons
     */
    @GetMapping("/flood/stations")
    public ResponseEntity<String> getHouseholdsForEachStationNumber(@RequestParam Integer[] stationNumbers) throws JsonProcessingException {
        List<String> addresses = new ArrayList<>();
        for (Integer stationNumber: stationNumbers) {
            addresses.addAll(alertsService.getStationAddressesFromStationNumber(stationNumber));
        }
        String jsonResponse = alertsService.createHouseholdsForEachStationNumberResponse(addresses);
        return new ResponseEntity<>(jsonResponse, HttpStatus.OK);
    }

    /**
     * <a href="http://localhost:8080/personInfo?firstName=&lastName=">...</a>
     * This should return the person’s name, address, age, email, list of medications with dosages and allergies.
     * If there is more than one person with the same name, this URL should return all of them.
     * @param firstName - String representing first name
     * @param lastName - String representing last name
     * @return person information
     */
    @GetMapping("/personInfo")
    public ResponseEntity<String> getPersonInfo(@RequestParam String firstName, @RequestParam String lastName) throws JsonProcessingException {
        String jsonResponse = this.alertsService.createPersonInfoResponse(firstName + "-" + lastName);
        return new ResponseEntity<>(jsonResponse, HttpStatus.OK);
    }

    /**
     * <a href="http://localhost:8080/communityEmail?city=">...</a>
     * This will return the email addresses of all of the people in the city.
     * @param city - String representing city
     * @return emails for resident in city
     */
    @GetMapping("/communityEmail")
    public ResponseEntity<String> getEmailAddressesByCity(@RequestParam String city) throws JsonProcessingException {
        String jsonResponse = this.alertsService.createEmailAddressesByCityResponse(city);
        return new ResponseEntity<>(jsonResponse, HttpStatus.OK);
    }
}
