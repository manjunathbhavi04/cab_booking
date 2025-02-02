package com.example.bookYourCab.repository;

import com.example.bookYourCab.Enum.Gender;
import com.example.bookYourCab.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    // these are like queries
    List<Customer> findByEmail(String email); // query to find customer by particular email
    List<Customer> findByGender(Gender gender); // query to find customer by gender
    List<Customer> findByAgeAndGender(int age, Gender gender); // query to find customer with particular age and gender

    @Query("Select c from Customer c where c.gender = :gender and c.age > :age") //Hibernate Query Language
    List<Customer> findWithAgeAndGender(@Param("gender") Gender gender, @Param("age") int age);

    //if we want to use sql query and not hql query because JPA by default only understand hql
    // to make JPA use sql query we just have to pass a parameter
    @Query(value = "select COUNT(*) from customer where age > :age", nativeQuery = true) // here native query is true this means now jpa will understand sql queries also
    int countAge(@Param("age") int age);

    //when we use sql database doesn't understand enum so we have to pass enum as string because there it is varchar

}
