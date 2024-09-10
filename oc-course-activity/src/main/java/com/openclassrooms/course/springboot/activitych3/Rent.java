package com.openclassrooms.course.springboot.activitych3;

import com.opencsv.bean.CsvBindByName;

public class Rent {

    @CsvBindByName
    private int id;

    @CsvBindByName
    private String city;

    @CsvBindByName
    private String country;

    @CsvBindByName
    private Double rent;

    public Rent() {
    }

    public Rent(int id, String city, String country, Double rent) {
        this.id = id;
        this.city = city;
        this.country = country;
        this.rent = rent;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public Double getRent() {
        return rent;
    }

    public void setRent(Double rent) {
        this.rent = rent;
    }
}
