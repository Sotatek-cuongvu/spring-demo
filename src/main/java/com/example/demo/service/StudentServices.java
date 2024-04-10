package com.example.demo.service;

import com.example.demo.model.Address;
import com.example.demo.model.Gender;
import com.example.demo.model.Student;
import com.example.demo.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.InvalidParameterException;
import java.util.ArrayList;
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
        List<Student> result = new ArrayList<>();

        return this.getStudents().stream().filter(
                s -> (s.getAddress() != null && s.getAddress().getProvince() != null && Objects.equals(s.getAddress().getProvince().toLowerCase(), province.toLowerCase()))
        ).limit(length).collect(Collectors.toList());
    }

    public int countStudentByGenderAndProvince(String province, Gender gender) {
        return (int) this.getStudents().stream()
                .filter(
                        s -> (s.getGender() == gender && s.getAddress() != null && Objects.equals(s.getAddress().getProvince().toLowerCase(), province.toLowerCase()))
                ).count();
    }

    public int getAverageAgeByGender(Gender gender) {
        Stream<Student> stream = this.getStudents().stream().filter(s -> (s.getGender() == gender));
        return (int) stream.mapToDouble(Student::getAge).average().orElse(0.0);
    }

    public boolean checkStudentIsExisted(String name, int age, Gender gender, String province) {
        return this.getStudents().stream().filter(
                (s) -> (
                        Objects.equals(s.getName().toLowerCase(), name.toLowerCase())
                                && s.getAge() == age
                                && s.getGender() == gender
                                && s.getAddress() != null
                                && Objects.equals(s.getAddress().getProvince().toLowerCase(), province.toLowerCase())
                )
        ).count() > 0.;
    }

    public int countWithoutAddress(Gender gender) {
        return (int) this.getStudents().stream().filter((s) -> (s.getGender() == gender && s.getAddress() == null)).count();
    }

    public List<Integer> getAgeByPrefixNameAndGender(String prefixName, Gender gender) {
        return this.getStudents().stream()
                .filter(s -> (s.getGender() == gender && s.getName().startsWith(prefixName)))
                .map(Student::getAge)
                .toList();
    }

    public List<Student> getStudentsByAddress(String province, String district, String village) {
        return this.getStudents().stream().filter(s -> (s.getAddress() != null
                        && Objects.equals(s.getAddress().getProvince().toLowerCase(), province.toLowerCase())
                        && Objects.equals(s.getAddress().getDistrict().toLowerCase(), district.toLowerCase())
                        && Objects.equals(s.getAddress().getVillage().toLowerCase(), village.toLowerCase())
                )
        ).sorted(Comparator.comparing(Student::getName).reversed().thenComparing(Student::getAge).reversed()).toList();
    }
}