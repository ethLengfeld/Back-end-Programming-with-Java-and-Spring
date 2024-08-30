package org.safetynet.alerts.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FireStation {
    /**
     *     {
     *       "address": "1509 Culver St",
     *       "station": "3"
     *     }
     */
    private String address;
    private int station;
}
