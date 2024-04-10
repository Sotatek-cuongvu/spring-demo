package com.example.demo.repository;

import com.example.demo.model.Gender;
import com.example.demo.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@EnableJpaRepositories
public interface StudentRepository extends JpaRepository<Student, String> {
    List<Student> findByGender(Gender gender);

    List<Student> findByAddress_ProvinceIgnoreCase(String address_province);

    Integer countByGenderAndAddress_ProvinceIgnoreCase(Gender gender, String address_province);

    boolean existsByNameIgnoreCaseAndAgeAndGenderAndAddress_ProvinceIgnoreCase(String name, int age, Gender gender, String province);

    Integer countByGenderAndAddressIsNull(Gender gender);

    List<Student> findByNameStartingWithIgnoreCaseAndGender(String name, Gender gender);

    List<Student> findByAddress_ProvinceIgnoreCaseAndAddress_DistrictIgnoreCaseAndAddress_villageIgnoreCase(String province, String district, String village);

}
