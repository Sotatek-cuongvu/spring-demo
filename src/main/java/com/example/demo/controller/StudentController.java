package com.example.demo.controller;

import com.example.demo.dto.StudentRequest;
import com.example.demo.model.Gender;
import com.example.demo.model.Student;
import com.example.demo.service.StudentServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/students")
public class StudentController {
    @Autowired
    StudentServices studentServices;

    // Get method
    @GetMapping("/")
    public ResponseEntity<List<Student>> getStudentList() {
        return ResponseEntity.status(HttpStatus.OK).body(studentServices.getStudents());
    }

    @GetMapping("/address/")
    public List<Student> getStudentByProvince(
            @RequestParam String province,
            @RequestParam(required = false) Integer limit,
            @RequestParam(required = false) String district,
            @RequestParam(required = false) String village
    ) {
        if (district != null && village != null)
            return studentServices.getStudentsByAddress(province, district, village);
        return studentServices.getStudentsByProvince(province, limit == null ? 10 : limit);
    }

    @GetMapping("/province/count/")
    public Integer countStudentByProvinceAndGender(@RequestParam Gender gender, @RequestParam String province) {
        return studentServices.countStudentByGenderAndProvince(province, gender);
    }

    @GetMapping("/no_address/count/")
    public Integer countStudentWithoutAddress(@RequestParam Gender gender) {
        return studentServices.countWithoutAddress(gender);
    }

    @GetMapping("/age/average/")
    public Integer getAgeAverage(@RequestParam Gender gender) {
        return studentServices.getAverageAgeByGender(gender);
    }

    @GetMapping("/existed/check/")
    public boolean checkUserIsExisted(
            @RequestParam String name,
            @RequestParam Integer age,
            @RequestParam Gender gender,
            @RequestParam String province
    ) {
        return studentServices.checkStudentIsExisted(name, age, gender, province);
    }

    @GetMapping("/age/list/")
    public List<Integer> getListAgeByPrefixNameAndGender(@RequestParam String prefixName, @RequestParam Gender gender) {
        return studentServices.getAgeByPrefixNameAndGender(prefixName, gender);
    }

    // Post Method
    @PostMapping("/")
    public ResponseEntity<String> createStudent(@RequestBody StudentRequest studentRequest) {
        if (studentServices.addStudent(studentRequest.getName(), studentRequest.getAge(), studentRequest.getAddress(), studentRequest.getGender()))
            return ResponseEntity.status(HttpStatus.OK).body("Successful!");
        else
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Unsuccessful!");
    }
}
