package com.example.demo.dtos;

import com.example.demo.models.Gender;
import lombok.Getter;

@Getter
public class GetStudentParams {
    private String province;
    private String district;
    private String village;
    private Gender gender;
    private String prefixName;
    private int limit;
}
