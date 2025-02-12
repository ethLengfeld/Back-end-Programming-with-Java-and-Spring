package org.openclassrooms.mediscreen.model;

import org.openclassrooms.mediscreen.annotations.PhoneNumber;
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

    @Column(name = "phone", nullable = false)
    @PhoneNumber
    private String phone;

    public Patient() {}

    public Patient(Long id, String family, String given, String dob, char sex, String address, String phone) {
        this.id = id;
        this.family = family;
        this.given = given;
        this.dob = dob;
        this.sex = sex;
        this.address = address;
        this.phone = phone;
    }
}
