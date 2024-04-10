package com.example.demo.service;

import com.example.demo.model.Address;
import com.example.demo.model.Gender;
import com.example.demo.model.Student;
import com.example.demo.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.InvalidParameterException;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class StudentServices {

    @Autowired
    StudentRepository studentRepository;

    public boolean addStudent(String name, int age, Address address, Gender gender) {
        if (age >= 100 || age <= 0) throw new InvalidParameterException("Age is invalid");
        if (Objects.equals(name, "") || name == null) throw new InvalidParameterException("Name is invalid");
        studentRepository.save(new Student(name, age, address, gender));
        return true;
    }

    public List<Student> getStudents() {
        return studentRepository.findAll();
    }

    public List<Student> getStudentsByProvince(String province, int length) {
        return studentRepository.findByAddress_ProvinceIgnoreCase(province).stream().limit(length).collect(Collectors.toList());
    }

    public int countStudentByGenderAndProvince(String province, Gender gender) {
        return studentRepository.countByGenderAndAddress_ProvinceIgnoreCase(gender, province);
    }

    public int getAverageAgeByGender(Gender gender) {
        Stream<Student> stream = studentRepository.findByGender(gender).stream();
        return (int) stream.mapToDouble(Student::getAge).average().orElse(0.0);
    }

    public boolean checkStudentIsExisted(String name, int age, Gender gender, String province) {
        return studentRepository.existsByNameIgnoreCaseAndAgeAndGenderAndAddress_ProvinceIgnoreCase(name, age, gender, province);
    }

    public int countWithoutAddress(Gender gender) {
        return studentRepository.countByGenderAndAddressIsNull(gender);
    }

    public List<Integer> getAgeByPrefixNameAndGender(String prefixName, Gender gender) {
        return studentRepository.findByNameStartingWithIgnoreCaseAndGender(prefixName, gender).stream().map(Student::getAge).toList();
    }

    public List<Student> getStudentsByAddress(String province, String district, String village) {
        return studentRepository.findByAddress_ProvinceIgnoreCaseAndAddress_DistrictIgnoreCaseAndAddress_villageIgnoreCase(province, district, village)
                .stream()
                .sorted(Comparator.comparing(Student::getName).reversed().thenComparing(Student::getAge).reversed()).toList();
    }
}