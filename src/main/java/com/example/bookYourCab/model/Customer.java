package com.example.bookYourCab.model;

import com.example.bookYourCab.enums.Gender;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor // Default constructor
@AllArgsConstructor // Constructor with all attributes
@Getter // Getters of the class
@Setter // Setters of the class
@Entity
public class Customer {
    @Id
    private int customerId;
    private String name;
    private int age;
    private String email;
    private Gender gender;

}
