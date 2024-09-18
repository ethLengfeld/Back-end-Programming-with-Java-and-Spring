package org.safetynet.alerts.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Person {
    /**
     *     {
     *       "firstName": "John",
     *       "lastName": "Boyd",
     *       "address": "1509 Culver St",
     *       "city": "Culver",
     *       "zip": "97451",
     *       "phone": "841-874-6512",
     *       "email": "jaboyd@email.com"
     *     },
     */
    private String firstName;
    private String lastName;
    private String address;
    private String city;
    private String zip;
    private String phone;
    private String email;

    public Person() {
        //default constructor
    }

    public Person(String firstName, String lastName, String address, String city, String zip, String phone, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.city = city;
        this.zip = zip;
        this.phone = phone;
        this.email = email;
    }
}
