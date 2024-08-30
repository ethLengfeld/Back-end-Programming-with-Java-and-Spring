package org.safetynet.alerts.controller;

import java.util.List;

import org.safetynet.alerts.model.FireStation;
import org.safetynet.alerts.service.DataLoaderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AlertsController {

    private DataLoaderService dataLoaderService;

    @GetMapping("/hello")
    public ResponseEntity<String> hello() {
        return new ResponseEntity<>("Hello there, friend.", HttpStatus.I_AM_A_TEAPOT);
    }

    /**
     * <a href="http://localhost:8080/firestation?stationNumber=">...</a>
     * This URL should return a list of people serviced by the corresponding fire station. So if station number = 1,
     * it should return the people serviced by station number 1. The list of people should include these specific
     * pieces of information: first name, last name, address, phone number. As well, it should provide a
     * summary of the number of adults in the service area and the number of children (anyone aged 18 or
     * younger).
     * @param stationNumber
     * @return
     */
    @GetMapping("/firestation")
    public ResponseEntity<String> getPeopleServicedByStationNumber(@RequestParam Integer stationNumber) {
        List<FireStation> stations = dataLoaderService.getFireStations();
        for (FireStation station : stations) {

        }
        return new ResponseEntity<>("Here is the list of People serviced by that Station Number-" + stationNumber, HttpStatus.OK);
    }

    /**
     * <a href="http://localhost:8080/childAlert?address=">...</a>
     * This URL should return a list of children (anyone under the age of 18) at that address. The list should
     * include the first and last name of each child, the child’s age, and a list of other persons living at that
     * address. If there are no children at the address, then this URL can return an empty string.
     * @param address
     * @return
     */
    @GetMapping("/childAlert")
    public ResponseEntity<String> getListOfChildrenFromAddress(@RequestParam String address) {
        return new ResponseEntity<>("Here is the list of Children based on the Address-" + address, HttpStatus.OK);
    }

    /**
     * <a href="http://localhost:8080/phoneAlert?firestation=">...</a>
     * This URL should return a list of phone numbers of each person within the fire station’s jurisdiction.We’ll
     * use this to send emergency text messages to specific households.
     * @param stationNumber
     * @return
     */
    @GetMapping("/phoneAlert")
    public ResponseEntity<String> getPhoneNumbersForPeopleByStationNumber(@RequestParam Integer stationNumber) {
        return new ResponseEntity<>("Here is the list of Phone Numbers for each person serviced by this Station Number-" + stationNumber, HttpStatus.OK);
    }

    /**
     * <a href="http://localhost:8080/fire?address=">...</a>
     * This URL should return the fire station number that services the provided address as well as a list of all of
     * the people living at the address. This list should include each person’s name, phone number, age,
     * medications with dosage, and allergies.
     * @param address
     * @return
     */
    @GetMapping("/fire")
    public ResponseEntity<String> getStationNumberAndPeopleByAddress(@RequestParam String address) {
        return new ResponseEntity<>("Here is the Station Number serviced People by Address-" + address, HttpStatus.OK);
    }

    /**
     * <a href="http://localhost:8080/flood/stations?stations=">...</a>
     * This should return a list of all the households in each fire station’s jurisdiction. This list needs to group
     * people by household address, include name, phone number, and age of each person, and any
     * medications (with dosages) and allergies beside each person’s name.
     * @param stationNumbers
     * @return
     */
    @GetMapping("/flood/stations")
    public ResponseEntity<String> getHouseholdsForEachStationNumber(@RequestParam Integer[] stationNumbers) {
        return new ResponseEntity<>("Here is List of all Households serviced by each Station Number", HttpStatus.OK);
    }

    /**
     * <a href="http://localhost:8080/personInfo?firstName=&lastName=">...</a>
     * This should return the person’s name, address, age, email, list of medications with dosages and allergies.
     * If there is more than one person with the same name, this URL should return all of them.
     * @param firstName
     * @param lastName
     * @return
     */
    @GetMapping("/personInfo")
    public ResponseEntity<String> getPersonInfo(@RequestParam String firstName, @RequestParam String lastName) {
        return new ResponseEntity<>("Here is the Person Info for First Name-" + firstName + " Last Name-" + lastName, HttpStatus.OK);
    }

    /**
     * <a href="http://localhost:8080/communityEmail?city=">...</a>
     * This will return the email addresses of all of the people in the city.
     * @param city
     * @return
     */
    @GetMapping("/communityEmail")
    public ResponseEntity<String> getEmailAddressesByCity(@RequestParam String city) {
        return new ResponseEntity<>("Here are the email addresses for People in the City-" + city, HttpStatus.OK);
    }
}
