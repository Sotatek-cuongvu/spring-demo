package com.example.demo.service;

import com.example.demo.model.Address;
import com.example.demo.model.Gender;
import com.example.demo.model.Student;
import com.example.demo.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.security.InvalidParameterException;
import java.util.List;
import java.util.Objects;

@Service
public class StudentService {

    @Autowired
    StudentRepository studentRepository;

    public boolean addStudent(String name, int age, Address address, Gender gender) {
        if (age >= 100 || age <= 0) throw new InvalidParameterException("Age is invalid");
        if (Objects.equals(name, "") || name == null) throw new InvalidParameterException("Name is invalid");
        studentRepository.save(new Student(name, age, address, gender));
        return true;
    }

    public Page<Student> getStudents(Pageable pageRequest) {
        return studentRepository.findAll(pageRequest);
    }

    public Page<Student> getStudentsByProvince(String province, Pageable pageable) {
        return studentRepository.findByAddress_ProvinceIgnoreCase(province, pageable);
    }

    public int countStudentByGenderAndProvince(String province, Gender gender) {
        return studentRepository.countByGenderAndAddress_ProvinceIgnoreCase(gender, province);
    }

    public Double getAverageAgeByGender(Gender gender) {
        return studentRepository.findAgeAverageByGender(gender);
    }

    public boolean checkStudentIsExisted(String name, int age, Gender gender, String province) {
        return studentRepository.existsByNameIgnoreCaseAndAgeAndGenderAndAddress_ProvinceIgnoreCase(name, age, gender, province);
    }

    public int countWithoutAddress(Gender gender) {
        return studentRepository.countByGenderAndAddressIsNull(gender);
    }

    public List<Integer> getAgeByPrefixNameAndGender(String prefixName, Gender gender, Pageable pageable) {
        return studentRepository.findByNameStartingWithIgnoreCaseAndGender(prefixName, gender, pageable).stream().map(Student::getAge).toList();
    }

    public Page<Student> getStudentsByAddress(String province, String district, String village, Pageable pageable) {
        return studentRepository.findByAddress_ProvinceIgnoreCaseAndAddress_DistrictIgnoreCaseAndAddress_villageIgnoreCase(province, district, village, pageable);
    }
}