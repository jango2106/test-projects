package com.fakeperson.client.model.dto;

import lombok.Value;

import java.util.ArrayList;
import java.util.List;

public record Person(String name, Integer age, String job, Integer incomeUSD, Integer creditScore, String ccNumber,
                     Boolean married, Boolean hasChildren, Float height, Float weight, String eyeColor, String email,
                     String gender, Boolean hasDegree, String bloodType, String username, Float politicalLeaning,
                     String religion, Address address, String doB, Integer gpa) {
}

