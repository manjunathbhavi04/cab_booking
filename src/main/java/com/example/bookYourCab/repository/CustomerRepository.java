package com.example.bookYourCab.repository;

import com.example.bookYourCab.Enum.Gender;
import com.example.bookYourCab.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    List<Customer> findByEmail(String email);
    List<Customer> findByGender(Gender gender);
    List<Customer> findByAgeAndGender(int age, Gender gender);
}
