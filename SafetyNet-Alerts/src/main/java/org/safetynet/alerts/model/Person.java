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
}
