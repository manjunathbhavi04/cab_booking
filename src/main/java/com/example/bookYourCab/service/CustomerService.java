package com.example.bookYourCab.service;

import com.example.bookYourCab.Enum.Gender;
import com.example.bookYourCab.dto.request.CustomerRequest;
import com.example.bookYourCab.dto.response.CustomerResponse;
import com.example.bookYourCab.exception.CustomerNotFoundException;
import com.example.bookYourCab.model.Customer;
import com.example.bookYourCab.repository.CustomerRepository;
import com.example.bookYourCab.transformer.CustomerTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {
    @Autowired
    private CustomerRepository customerRepo;

    public List<CustomerResponse> getAllCustomer(){
        //At Service layer the Entity are being converted to DTO's
        List<Customer> customers = customerRepo.findAll();
        return customers.stream().map(customer -> new CustomerResponse(
                customer.getName(),
                customer.getAge(),
                customer.getEmail()
        )).toList();
    }

    public CustomerResponse getCustomer(long customerId) {
        // we use Optional because some time the customer id might not be present so optional can be empty when there is no customer present,
        // and we  can handle the app without crashing of NullPointerException
        //Optional can either have one value or not value
        Optional<Customer> customer = customerRepo.findById(customerId);
        if (customer.isEmpty()){
            throw new CustomerNotFoundException("Invalid Customer Id");
        }
        Customer customer1 = customer.get();
        return CustomerTransformer.CustomerToCustomerResponse(customer1);
    }

    public List<CustomerResponse> getCustomerByGender(Gender gender) {
        List<Customer> customers = customerRepo.findByGender(gender);
        return customers.stream().map(CustomerTransformer::CustomerToCustomerResponse).toList();
    }

    public String addCustomer(CustomerRequest customerRequest){
        List<Customer> existingCustomer = customerRepo.findByEmail(customerRequest.getEmail());
        if(!existingCustomer.isEmpty()){ // if the email already exist then this means that user is already customer because email is unique for everyone
            return "Customer already exists";
        }
        try{
            Customer newCustomer = CustomerTransformer.CustomerReqToCustomer(customerRequest);
            customerRepo.save(newCustomer);
        }catch(Exception e){
            return "Error: "+ e;
        }
        return "Added Successfully";
    }

    public CustomerResponse updateCustomer(long customerId, CustomerRequest customer){
        // customer request cannot send customer id as input so we take the customer from the path variable
        // to update particular customer
        Optional<Customer> existingCustomer = customerRepo.findById(customerId);
        // even if the customer is not present we will create new customer
        Customer currCustomer;
        if(existingCustomer.isEmpty()){
            System.out.println("Customer was not present so created new customer and added to the database as put can also add not only update");
            currCustomer = new Customer();
        }else{
            // list is not empty which means customer with email is present
            currCustomer = existingCustomer.get();
        }
        currCustomer.setAge(customer.getAge());
        currCustomer.setEmail(customer.getEmail());
        currCustomer.setGender(customer.getGender());
        currCustomer.setName(customer.getName());
        customerRepo.save(currCustomer);
        return CustomerTransformer.CustomerToCustomerResponse(currCustomer);

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

    public List<CustomerResponse> getByAgeAndGender(int age, Gender gender) {
        List<Customer> customers = customerRepo.findByAgeAndGender(age, gender);
        return customers.stream().map(CustomerTransformer::CustomerToCustomerResponse).toList();
    }
}
