package com.example.demo.model;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Address {
    @Column
    private String province;
    @Column
    private String district;
    @Column
    private String village;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;


    public Address(String province, String district, String village) {
        this.province = province;
        this.district = district;
        this.village = village;
    }

    public Address() {
    }

    @Override
    public String toString() {
        return String.format(
                "%s, %s, %s", this.getVillage(), this.getDistrict(), this.getProvince()
        );
    }

}