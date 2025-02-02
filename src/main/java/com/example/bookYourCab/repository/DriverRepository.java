package com.example.bookYourCab.repository;

import com.example.bookYourCab.model.Cab;
import com.example.bookYourCab.model.Driver;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DriverRepository extends JpaRepository<Driver, Long> {
    List<Driver> findByEmail(String email);
    Optional<Driver> findByCab(Cab cab);
    void deleteByEmail(String email);
}
