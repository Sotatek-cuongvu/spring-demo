package com.example.demo.repository;

import com.example.demo.model.Gender;
import com.example.demo.model.Student;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepository extends JpaRepository<Student, String> {

//    Page<Student> findAll(Pageable pageable);

    @Query("SELECT avg(s.age) from Student s where s.gender= :gender")
    Double findAgeAverageByGender(Gender gender);

    Page<Student> findByAddress_ProvinceIgnoreCase(String address_province, Pageable pageable);

    Integer countByGenderAndAddress_ProvinceIgnoreCase(Gender gender, String address_province);

    boolean existsByNameIgnoreCaseAndAgeAndGenderAndAddress_ProvinceIgnoreCase(String name, int age, Gender gender, String province);

    Integer countByGenderAndAddressIsNull(Gender gender);

    Page<Student> findByNameStartingWithIgnoreCaseAndGender(String name, Gender gender, Pageable pageable);

    Page<Student> findByAddress_ProvinceIgnoreCaseAndAddress_DistrictIgnoreCaseAndAddress_villageIgnoreCase(String province, String district, String village, Pageable pageable);

}
