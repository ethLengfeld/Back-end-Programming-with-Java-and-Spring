package org.safetynet.alerts.controller;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.slf4j.Slf4j;
import org.safetynet.alerts.exception.FireStationException;
import org.safetynet.alerts.exception.MedicalRecordException;
import org.safetynet.alerts.model.FireStation;
import org.safetynet.alerts.model.MedicalRecord;
import org.safetynet.alerts.model.Person;
import org.safetynet.alerts.service.AlertsService;
import org.safetynet.alerts.exception.PersonException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@Slf4j
public class AlertsController {

    private final AlertsService alertsService;

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
     *
     * @param stationNumber - integer representing the station number
     * @return string with first name, last name, address, phone number, number of adults, and number of children
     */
    @GetMapping("/firestation")
    public ResponseEntity<String> getPersonsServicedByStationNumber(@RequestParam Integer stationNumber) {
        log.info("REQUEST GET /firestation");
        String jsonResponse;
        try {
            List<String> listOfAddresses = alertsService.getStationAddressesFromStationNumber(stationNumber);
            List<Person> servicedPersons = alertsService.getPersonsFromAddresses(listOfAddresses);
            jsonResponse = alertsService.createPersonsServicedByStationNumberResponse(servicedPersons);
        } catch (JsonProcessingException e) {
            log.error("ERROR GET /personInfo", e);
            return new ResponseEntity<>("{}", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(jsonResponse, HttpStatus.OK);
    }

    /**
     * <a href="http://localhost:8080/childAlert?address=">...</a>
     * This URL should return a list of children (anyone under the age of 18) at that address. The list should
     * include the first and last name of each child, the child’s age, and a list of other persons living at that
     * address. If there are no children at the address, then this URL can return an empty string.
     *
     * @param address - string representing the address
     * @return list of children with first name, last name, age, and address
     */
    @GetMapping("/childAlert")
    public ResponseEntity<String> getChildrenFromAddress(@RequestParam String address) {
        log.info("REQUEST GET /childAlert");
        String jsonResponse;
        try {
            List<String> listOfAddress = List.of(address);
            List<Person> servicedPersons = alertsService.getPersonsFromAddresses(listOfAddress);
            jsonResponse = alertsService.createChildrenFromAddressResponse(servicedPersons);
        } catch (JsonProcessingException e) {
            log.error("ERROR GET /personInfo", e);
            return new ResponseEntity<>("{}", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(jsonResponse, HttpStatus.OK);
    }

    /**
     * <a href="http://localhost:8080/phoneAlert?firestation=">...</a>
     * This URL should return a list of phone numbers of each person within the fire station’s jurisdiction.
     * We’ll use this to send emergency text messages to specific households.
     *
     * @param stationNumber - integer representing the station number
     * @return list of phone numbers
     */
    @GetMapping("/phoneAlert")
    public ResponseEntity<String> getPhoneNumbersForPeopleByStationNumber(@RequestParam Integer stationNumber) {
        log.info("REQUEST GET /phoneAlert");
        String jsonResponse;
        try {
            List<String> listOfAddresses = alertsService.getStationAddressesFromStationNumber(stationNumber);
            List<Person> servicedPersons = alertsService.getPersonsFromAddresses(listOfAddresses);
            jsonResponse = alertsService.createPhoneNumbersForPeopleByStationNumberResponse(servicedPersons);
        } catch (JsonProcessingException e) {
            log.error("ERROR GET /personInfo", e);
            return new ResponseEntity<>("{}", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(jsonResponse, HttpStatus.OK);
    }

    /**
     * <a href="http://localhost:8080/fire?address=">...</a>
     * This URL should return the fire station number that services the provided address as well as
     * a list of all of the people living at the address. This list should include each person’s name,
     * phone number, age, medications with dosage, and allergies.
     *
     * @param address - string representing firestation address
     * @return list of all people living at address
     */
    @GetMapping("/fire")
    public ResponseEntity<String> getStationNumberAndPeopleByAddress(@RequestParam String address) {
        log.info("REQUEST GET /fire");
        String jsonResponse;
        try {
            List<String> listOfAddress = List.of(address);
            List<Person> servicedPersons = alertsService.getPersonsFromAddresses(listOfAddress);
            jsonResponse = alertsService.createStationNumberAndPeopleByAddressResponse(servicedPersons);
        } catch (JsonProcessingException e) {
            log.error("ERROR GET /personInfo", e);
            return new ResponseEntity<>("{}", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(jsonResponse, HttpStatus.OK);
    }

    /**
     * <a href="http://localhost:8080/flood/stations?stations=">...</a>
     * This should return a list of all the households in each fire station’s jurisdiction. This list needs to group
     * people by household address, include name, phone number, and age of each person, and any
     * medications (with dosages) and allergies beside each person’s name.
     *
     * @param stationNumbers - list of stationNumbers
     * @return list of all address and persons
     */
    @GetMapping("/flood/stations")
    public ResponseEntity<String> getHouseholdsForEachStationNumber(@RequestParam Integer[] stationNumbers) {
        log.info("REQUEST GET /flood/stations");
        String jsonResponse;
        try {
            List<String> addresses = new ArrayList<>();
            for (Integer stationNumber : stationNumbers) {
                addresses.addAll(alertsService.getStationAddressesFromStationNumber(stationNumber));
            }
            jsonResponse = alertsService.createHouseholdsForEachStationNumberResponse(addresses);
        } catch (JsonProcessingException e) {
            log.error("ERROR GET /personInfo", e);
            return new ResponseEntity<>("{}", HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(jsonResponse, HttpStatus.OK);
    }

    /**
     * <a href="http://localhost:8080/personInfo?firstName=&lastName=">...</a>
     * This should return the person’s name, address, age, email, list of medications with dosages and allergies.
     * If there is more than one person with the same name, this URL should return all of them.
     *
     * @param firstName - String representing first name
     * @param lastName  - String representing last name
     * @return person information
     */
    @GetMapping("/personInfo")
    public ResponseEntity<String> getPersonInfo(@RequestParam String firstName, @RequestParam String lastName) {
        log.info("REQUEST GET /personInfo");
        String jsonResponse;
        try {
            jsonResponse = this.alertsService.createPersonInfoResponse(firstName + "-" + lastName);
        } catch (JsonProcessingException e) {
            log.error("ERROR GET /personInfo", e);
            return new ResponseEntity<>("{}", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(jsonResponse, HttpStatus.OK);
    }

    /**
     * <a href="http://localhost:8080/communityEmail?city=">...</a>
     * This will return the email addresses of all of the people in the city.
     *
     * @param city - String representing city
     * @return emails for resident in city
     */
    @GetMapping("/communityEmail")
    public ResponseEntity<String> getEmailAddressesByCity(@RequestParam String city) {
        log.info("REQUEST GET /communityEmail");
        String jsonResponse;
        try {
            jsonResponse = this.alertsService.createEmailAddressesByCityResponse(city);
        } catch (JsonProcessingException e) {
            log.error("ERROR GET /personInfo", e);
            return new ResponseEntity<>("{}", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(jsonResponse, HttpStatus.OK);
    }

    @PostMapping("/person")
    public ResponseEntity<String> addNewPerson(@RequestParam String firstName,
                                               @RequestParam String lastName,
                                               @RequestParam String address,
                                               @RequestParam String city,
                                               @RequestParam String zip,
                                               @RequestParam String phone,
                                               @RequestParam String email) {

        log.info("REQUEST POST /person");
        Person person = new Person(firstName, lastName, address, city, zip, phone, email);
        try {
            this.alertsService.addPerson(person);
        } catch (PersonException e) {
            log.error("FAILURE ADDING PERSON", e);
            return new ResponseEntity<>("FAILURE", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("SUCCESS", HttpStatus.OK);
    }

    @PutMapping("/person")
    public ResponseEntity<String> updateExistingPerson(@RequestParam String firstName,
                                                       @RequestParam String lastName,
                                                       @RequestParam String address,
                                                       @RequestParam String city,
                                                       @RequestParam String zip,
                                                       @RequestParam String phone,
                                                       @RequestParam String email) {
        log.info("REQUEST PUT /person");
        Person person = new Person(firstName, lastName, address, city, zip, phone, email);
        try {
            this.alertsService.updateExistingPerson(person);
        } catch (PersonException e) {
            log.error("FAILURE UPDATING PERSON", e);
            return new ResponseEntity<>("FAILURE", HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>("SUCCESS", HttpStatus.OK);
    }

    @DeleteMapping("/person")
    public ResponseEntity<String> deletePerson(@RequestParam String firstName, @RequestParam String lastName) {
        log.info("REQUEST DELETE /person");
        try {
            this.alertsService.deletePerson(firstName, lastName);
        } catch (PersonException e) {
            log.error("FAILURE DELETING PERSON", e);
            return new ResponseEntity<>("FAILURE", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("SUCCESS", HttpStatus.OK);
    }

    @PostMapping("/firestation")
    public ResponseEntity<String> addFirestation(@RequestParam String address, @RequestParam int station) {
        log.info("REQUEST POST /firestation");
        FireStation fireStation = new FireStation(address, station);
        try {
            this.alertsService.addFirestation(fireStation);
        } catch (FireStationException e) {
            log.error("FAILURE ADDING FIRE STATION", e);
            return new ResponseEntity<>("FAILURE", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("SUCCESS", HttpStatus.OK);
    }

    @PutMapping("/firestation")
    public ResponseEntity<String> updateExistingFirestation(@RequestParam String address, @RequestParam int station) {
        log.info("REQUEST PUT /firestation");
        FireStation fireStation = new FireStation(address, station);
        try {
            this.alertsService.updateExistingFirestation(fireStation);
        } catch (FireStationException e) {
            log.error("FAILURE UPDATING FIRE STATION", e);
            return new ResponseEntity<>("FAILURE", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("SUCCESS", HttpStatus.OK);
    }

    @DeleteMapping("/firestation")
    public ResponseEntity<String> deleteFirestation(@RequestParam String address) {
        log.info("REQUEST DELETE /firestation");
        try {
            this.alertsService.deleteFirestation(address);
        } catch (FireStationException e) {
            log.error("FAILURE DELETING FIRE STATION", e);
            return new ResponseEntity<>("FAILURE", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("SUCCESS", HttpStatus.OK);
    }

    @PostMapping("/medicalRecord")
    public ResponseEntity<String> addMedicalRecord(@RequestParam String firstName,
                                                   @RequestParam String lastName,
                                                   @RequestParam String birthdate,
                                                   @RequestParam String[] medications,
                                                   @RequestParam String[] allergies) {
        log.info("REQUEST POST /medicalRecord");
        MedicalRecord medicalRecord = new MedicalRecord(firstName, lastName, birthdate, medications, allergies);
        try {
            this.alertsService.addMedicalRecord(medicalRecord);
        } catch (MedicalRecordException e) {
            log.error("FAILURE ADDING MEDICAL RECORD", e);
            return new ResponseEntity<>("FAILURE", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("SUCCESS", HttpStatus.OK);
    }

    @PutMapping("/medicalRecord")
    public ResponseEntity<String> updateExistingMedicalRecord(@RequestParam String firstName,
                                                              @RequestParam String lastName,
                                                              @RequestParam String birthdate,
                                                              @RequestParam String[] medications,
                                                              @RequestParam String[] allergies) {
        log.info("REQUEST PUT /medicalRecord");
        MedicalRecord medicalRecord = new MedicalRecord(firstName, lastName, birthdate, medications, allergies);
        try {
            this.alertsService.updateExistingMedicalRecord(medicalRecord);
        } catch (MedicalRecordException e) {
            log.error("FAILURE UPDATING MEDICAL RECORD", e);
            return new ResponseEntity<>("FAILURE", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("SUCCESS", HttpStatus.OK);
    }

    @DeleteMapping("/medicalRecord")
    public ResponseEntity<String> deleteMedicalRecord(@RequestParam String firstName, @RequestParam String lastName) {
        log.info("REQUEST DELETE /medicalRecord");
        try {
            this.alertsService.deleteMedicalRecord(firstName, lastName);
        } catch (MedicalRecordException e) {
            log.error("FAILURE DELETING MEDICAL RECORD", e);
            return new ResponseEntity<>("FAILURE", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("SUCCESS", HttpStatus.OK);
    }
}
