package com.example.demo.model;

import jakarta.persistence.*;
import lombok.Getter;

import java.util.UUID;

@Entity
@Getter
@Table
public class Student {
    @Id
    @Column
    private String id;
    @Column
    private String name;
    @Column
    private int age;
    @Column
    @Enumerated(EnumType.STRING)
    private Gender gender;
    @OneToOne(cascade = CascadeType.ALL)
    private Address address;

    public Student(String name, int age, Address address, Gender gender) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.age = age;
        this.address = address;
        this.gender = gender;
    }

    public Student() {

    }

    @Override
    public String toString() {
        return "{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", address=" + address +
                ", gender=" + gender +
                '}';
    }
}