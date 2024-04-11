package com.example.demo.dto;

import com.example.demo.model.Gender;
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
