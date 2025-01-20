package com.example.bookYourCab.service;

import com.example.bookYourCab.model.Customer;
import com.example.bookYourCab.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;

import java.nio.channels.AcceptPendingException;
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

    public String updateCustomer(Customer customer){
        try{
            Customer cust = customerRepo.findById(customer.getCustomerId()).orElseThrow(() -> new ResourceAccessException("Customer not found with id: " + customer.getCustomerId()));
            cust.setAge(customer.getAge());
            cust.setEmail(customer.getEmail());
            cust.setGender(customer.getGender());
            cust.setName(customer.getName());
            customerRepo.save(cust);
        } catch (Exception e){
            return "Error: "+ e;
        }
        return "Updated Successfully";


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

    public Optional<Customer> getCustomer(long customerId) {
        // we return Optional because some time the customer id might not be present so optional can be empty when there is no customer present,
        // and we  can handle the app without crashing of NullPointerException
        return customerRepo.findById(customerId);
    }
}
