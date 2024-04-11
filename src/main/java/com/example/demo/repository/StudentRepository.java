package com.example.demo.repository;

import com.example.demo.model.Gender;
import com.example.demo.model.Student;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentRepository extends JpaRepository<Student, String> {

//    Page<Student> findAll(Pageable pageable);

    @Query("SELECT avg(s.age) from Student s where s.gender= :gender")
    Double findAgeAverageByGender(Gender gender);

    @Query("select s from Student s left join Address a on a.id=s.address.id where upper(a.province)=upper(:province)")
    Page<Student> getStudentByProvince(String province, Pageable pageable);

    @Query("select count(s) from Student s left join Address a on a.id=s.address.id where s.gender=:gender and upper(a.province)=upper(:province)")
    Integer countByGenderAndProvince(Gender gender, String province);

    @Query("select exists(( select s from Student s left join Address a on a.id=s.address.id where upper(s.name)=upper(:name) and s.age=:age and s.gender=:gender and a.province=:province))")
    boolean checkExistsByNameAgeGenderAndProvince(String name, int age, Gender gender, String province);

    Integer countByGenderAndAddressIsNull(Gender gender);

    @Query("select s.age from Student s where upper(s.name) like upper(:name)||'%' and s.gender=(:gender)")
    List<Integer> getAgeByPrefixNameAndGender(String name, Gender gender);

    @Query("select s1_0 from Student s1_0 left join Address a1_0 on a1_0.id=s1_0.address.id where upper(a1_0.province)=upper(:province) and upper(a1_0.district)=upper(:district) and upper(a1_0.village)=upper(:village)")
    Page<Student> findByFullAddress(String province, String district, String village, Pageable pageable);

}
