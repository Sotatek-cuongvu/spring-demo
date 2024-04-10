package com.example.demo.dtos;

import com.example.demo.models.Address;
import com.example.demo.models.Gender;
import lombok.Getter;

@Getter
public class StudentRequest {
    private String name;
    private int age;
    private String province;
    private String district;
    private String village;
    private Gender gender;

    public Address getAddress() {
        System.out.println(this.getProvince() == null && this.getDistrict() == null && this.getVillage() == null);
        return (this.getProvince() == null && this.getDistrict() == null && this.getVillage() == null) ? null : new Address(this.getProvince(), this.getDistrict(), this.getVillage());
    }
}

