package com.example.demo.controller;

import com.example.demo.dto.StudentRequest;
import com.example.demo.model.Gender;
import com.example.demo.model.Student;
import com.example.demo.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/students")
public class StudentController {
    @Autowired
    StudentService studentService;

    // Get method
    @GetMapping("/")
    public ResponseEntity<Page<Student>> getStudentList(
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer pageSize
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(studentService.getStudents(page, pageSize)
        );
    }

    @GetMapping("/address/")
    public Page<Student> getStudentByProvince(
            @RequestParam String province,
            @RequestParam(required = false) String district,
            @RequestParam(required = false) String village,
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer pageSize
    ) {
        if (district != null && village != null) {
            return studentService.getStudentsByAddress(province, district, village, page, pageSize);
        }
        return studentService.getStudentsByProvince(province, page, pageSize);
    }

    @GetMapping("/province/count/")
    public Integer countStudentByProvinceAndGender(@RequestParam Gender gender, @RequestParam String province, @RequestParam Integer age) {
        return studentService.countStudentByGenderAndProvince(province, gender, age);
    }

    @GetMapping("/noAddress/count/")
    public Integer countStudentWithoutAddress(@RequestParam Gender gender) {
        return studentService.countWithoutAddress(gender);
    }

    @GetMapping("/age/average/")
    public Double getAgeAverage(@RequestParam Gender gender) {
        return studentService.getAverageAgeByGender(gender);
    }

    @GetMapping("/existed/check/")
    public boolean checkUserIsExisted(
            @RequestParam String name,
            @RequestParam Integer age,
            @RequestParam Gender gender,
            @RequestParam String province
    ) {
        return studentService.checkStudentIsExisted(name, age, gender, province);
    }

    @GetMapping("/age/list/")
    public List<Integer> getListAgeByPrefixNameAndGender(@RequestParam String prefixName, @RequestParam Gender gender) {
        return studentService.getAgeByPrefixNameAndGender(prefixName, gender);
    }

    // Post Method
    @PostMapping("/")
    public ResponseEntity<String> createStudent(@RequestBody StudentRequest studentRequest) {
        if (
                studentService.addStudent(
                        studentRequest.getName(),
                        studentRequest.getAge(),
                        studentRequest.getProvince(),
                        studentRequest.getDistrict(),
                        studentRequest.getVillage(),
                        studentRequest.getGender()
                )
        )
            return ResponseEntity.status(HttpStatus.OK).body("Successful!");
        else
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Unsuccessful!");
    }
}
