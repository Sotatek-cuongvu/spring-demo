package com.example.demo.repository;

import com.example.demo.model.Address;
import com.example.demo.model.Gender;
import com.example.demo.model.Student;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
@ActiveProfiles("test")
@Transactional
public class StudentRepositoryTest {

    @Autowired
    StudentRepository studentRepository;

    @BeforeEach
    void setUp() {
        studentRepository.saveAll(List.of(
                new Student("Nguyễn Thị A", 18, new Address("Hải Dương", "test", "test"), Gender.FEMALE),
                new Student("test", 19, new Address("hải dương", "test", "test"), Gender.MALE),
                new Student("test", 20, new Address("hải dương", "test", "test"), Gender.MALE),
                new Student("Nguyễn Văn A", 16, new Address("Hải Dương", "test", "test"), Gender.MALE),
                new Student("Nguyễn Thị B", 18, new Address("Hải Dương", "test", "test"), Gender.FEMALE),
                new Student("test", 18, new Address("test", "Hà nội", "test"), Gender.FEMALE),
                new Student("Nguyễn Thị C", 22, new Address("Hà nội", "test", "test"), Gender.FEMALE),
                new Student("test", 18, new Address("test", "test", "test"), Gender.FEMALE),
                new Student("test", 18, new Address("test", "test", "test"), Gender.FEMALE),
                new Student("test", 18, null, Gender.FEMALE),
                new Student("Nguyễn Thị D", 21, new Address("Hà nội", "test", "test"), Gender.FEMALE),
                new Student("test", 18, null, Gender.MALE)
        ));
    }

    @Test
    void testGetAllStudentWithPaging() {
        Pageable pageable = PageRequest.of(3, 2);

        Page<Student> result = studentRepository.findAll(pageable);

        assertEquals(2, result.getContent().size());
        assertEquals(6, result.getTotalPages());
        assertTrue(result.getPageable().isPaged());
    }

    @Test
    void testGetStudentByProvince() {
        String province = "Hải Dương";

        Page<Student> result = studentRepository.getStudentByProvince(province, null);

        assertEquals(5, result.getContent().size());
    }

    @Test
    void testCountStudentByProvinceAndGender() {
        String province = "hà nội";

        Integer result = studentRepository.countByGenderAndProvince(Gender.FEMALE, province, 20);

        assertEquals(2, result);
    }

    @Test
    void testAverageAgeOfStudentByGender() {
        Double result = studentRepository.findAgeAverageByGender(Gender.MALE);
        assertEquals(18.25, result);
    }

    @Test
    void testAverageAgeOfStudentByGenderFemale() {
        Double result = studentRepository.findAgeAverageByGender(Gender.FEMALE);
        assertEquals(18.875, result);
    }

    @Test
    void testCheckExistedUser() {
        boolean result = studentRepository.checkExistsByNameAgeGenderAndProvince("Nguyễn Văn A", 16, Gender.MALE, "Hải Dương");
        assertTrue(result);
    }

    @Test
    void testCheckExistedUserButDont() {
        boolean result = studentRepository.checkExistsByNameAgeGenderAndProvince("Nguyễn Văn A", 17, Gender.MALE, "Hải Dương");
        assertFalse(result);
    }

    @Test
    void testCountMaleStudentWithoutAddress() {
        int result = studentRepository.countByGenderAndAddressIsNull(Gender.MALE);
        assertEquals(1, result);
    }

    @Test
    void testCountFemaleStudentWithoutAddress() {
        int result = studentRepository.countByGenderAndAddressIsNull(Gender.FEMALE);
        assertEquals(1, result);
    }

    @Test
    void testGetAgeListByPrefixOfName() {
        String prefixName = "Nguyễn Thị";
        List<Integer> result = studentRepository.getAgeByPrefixNameAndGender(prefixName, Gender.FEMALE);
        assertArrayEquals(List.of(18, 18, 22, 21).toArray(), result.toArray());
    }

    @Test
    void testGetStudentByFullAddress() {
        Page<Student> result = studentRepository.findByFullAddress("Hải Dương", "test", "test", null);
        assertEquals(result.getContent().size(), 5);
    }
}
