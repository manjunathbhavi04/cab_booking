package com.example.bookYourCab.service;

import com.example.bookYourCab.model.Customer;
import com.example.bookYourCab.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {
    @Autowired
    private CustomerRepository customerRepo;

    public List<Customer> getAllCustomer(){
        return customerRepo.findAll();
    }

    public String addCustomer(Customer customer){
        List<Customer> existingCustomer = customerRepo.findByEmail(customer.getEmail());
        if(!existingCustomer.isEmpty()){
            return "Customer already exists";
        }
        try{
            customerRepo.save(customer);
        }catch(Exception e){
            return "Error: "+ e;
        }
        return "Added Successfully";
    }

    public String deleteCustomer(long customerId){
        if(!customerRepo.existsById(customerId)){
            return "Customer Do not Exists";
        }
        try{
            customerRepo.deleteById(customerId);
            return "Customer deleted from database";
        } catch(DataIntegrityViolationException e){
            return "Customer deletion failed due to constraint violation. " +
                    "Please ensure there are no dependent entities referencing this customer.";
        } catch(Exception e){
            return "Error deleting customer: " + e.getMessage();
        }
    }

    public String deleteAllCustomer(){
        try{
            customerRepo.deleteAll();
        } catch(Exception e){
            return "Error: "+ e;
        }
        return "Deleted All Customer";
    }
}
