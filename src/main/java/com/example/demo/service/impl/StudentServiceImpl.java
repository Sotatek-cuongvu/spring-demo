package com.example.demo.service.impl;

import com.example.demo.model.Address;
import com.example.demo.model.Gender;
import com.example.demo.model.Student;
import com.example.demo.repository.StudentRepository;
import com.example.demo.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.security.InvalidParameterException;
import java.util.List;
import java.util.Objects;

@Service
public class StudentServiceImpl implements StudentService {

    @Autowired
    StudentRepository studentRepository;

    public boolean addStudent(String name, int age, String province, String district, String village, Gender gender) {
        if (age >= 100 || age <= 0) throw new InvalidParameterException("Age is invalid");
        if (Objects.equals(name, "") || name == null) throw new InvalidParameterException("Name is invalid");
        studentRepository.save(new Student(name, age, new Address(province, district, village), gender));
        return true;
    }

    public Page<Student> getStudents(Integer page, Integer pageSize) {
        Pageable pageable = PageRequest.of((page == null) ? 0 : page, (pageSize == null) ? 10 : pageSize);
        return studentRepository.findAll(pageable);
    }

    public Page<Student> getStudentsByProvince(String province, Integer page, Integer pageSize) {
        Pageable pageable = PageRequest.of((page == null) ? 0 : page, (pageSize == null) ? 10 : pageSize);
        return studentRepository.getStudentByProvince(province, pageable);
    }

    public int countStudentByGenderAndProvince(String province, Gender gender) {
        return studentRepository.countByGenderAndProvince(gender, province);
    }

    public Double getAverageAgeByGender(Gender gender) {
        return studentRepository.findAgeAverageByGender(gender);
    }

    public boolean checkStudentIsExisted(String name, int age, Gender gender, String province) {
        return studentRepository.checkExistsByNameAgeGenderAndProvince(name, age, gender, province);
    }

    public int countWithoutAddress(Gender gender) {
        return studentRepository.countByGenderAndAddressIsNull(gender);
    }

    public List<Integer> getAgeByPrefixNameAndGender(String prefixName, Gender gender) {
        return studentRepository.getAgeByPrefixNameAndGender(prefixName, gender);
    }

    public Page<Student> getStudentsByAddress(String province, String district, String village, Integer page, Integer pageSize) {
        Pageable pageable = PageRequest.of(
                (page == null) ? 0 : page,
                (pageSize == null) ? 10 : pageSize,
                Sort.by("name").and(Sort.by("age").descending())
        );
        return studentRepository.findByFullAddress(province, district, village, pageable);
    }
}