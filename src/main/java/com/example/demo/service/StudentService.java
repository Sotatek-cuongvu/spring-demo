package com.example.demo.service;

import com.example.demo.model.Gender;
import com.example.demo.model.Student;
import org.springframework.data.domain.Page;

import java.util.List;

public interface StudentService {
    boolean addStudent(String name, int age, String province, String district, String village, Gender gender);

    Page<Student> getStudents(Integer page, Integer pageSize);

    Page<Student> getStudentsByProvince(String province, Integer page, Integer pageSize);

    int countStudentByGenderAndProvince(String province, Gender gender);

    Double getAverageAgeByGender(Gender gender);

    boolean checkStudentIsExisted(String name, int age, Gender gender, String province);

    int countWithoutAddress(Gender gender);

    List<Integer> getAgeByPrefixNameAndGender(String prefixName, Gender gender, Integer page, Integer pageSize);

    Page<Student> getStudentsByAddress(String province, String district, String village, Integer page, Integer pageSize);
}
