package com.example.bookYourCab.model;

import com.example.bookYourCab.enums.Gender;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.awt.print.Book;
import java.util.ArrayList;
import java.util.List;

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

    // one customer can have many unique bookings so the relation is one to many
    // one customer can have many booking
    @OneToMany(cascade = CascadeType.ALL) // here one represents the Customer and many represents the Booking
    @JoinColumn(name = "customer_id")
    private List<Booking> bookings = new ArrayList<>();


}
