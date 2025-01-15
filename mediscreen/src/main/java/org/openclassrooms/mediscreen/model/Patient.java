package org.openclassrooms.mediscreen.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "patients")
public class Patient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "family", nullable = false)
    private String family;
    @Column(name = "given", nullable = false)
    private String given;
    @Column(name = "dob", nullable = false)
    private String dob;
    @Column(name = "sex", nullable = false)
    private char sex;
    @Column(name = "address", nullable = false)
    private String address;
    @PhoneNumber
    @Column(name = "phone", nullable = false)
    private String phone;

}
