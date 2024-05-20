package com.example.usermanagement.web;


import com.example.usermanagement.domain.entity.Customer;
import com.example.usermanagement.domain.service.CustomerService;
import com.example.usermanagement.dto.user.input.CustomerInput;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@Slf4j
@RequestMapping("api/v1/customers")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    //Only admin&stafff
    @GetMapping("")
    public ResponseEntity<List<Customer>> getAllCustomers(){
        log.info("Starting get all customers");
        List<Customer> customers = customerService.getAllCustomers();
        return new ResponseEntity<>(customers, HttpStatus.OK);
    }
    @GetMapping("/{customerId}")
    public ResponseEntity<Customer> getCustomerbyID(@PathVariable String customerId){
        log.info("Starting to find customer");
        Customer customer = customerService.getCustomerbyID(customerId);
        return new ResponseEntity<>(customer,HttpStatus.OK);
    }

    //customer can use function by themselves

    @PostMapping("/create")
    public ResponseEntity<Customer> createCustomer(@RequestBody CustomerInput customer){
        log.info("Requesting to create new customer");
        Customer newCustomer = customerService.createCustomer(customer);
        return new ResponseEntity<>(newCustomer, HttpStatus.OK);
    }
    @PutMapping("/{customerId}/update")
    public ResponseEntity<Customer> updateCustomer(@PathVariable String customerId, @RequestBody Customer customer){
        log.info("Requesting to update a customer");
        Customer updateCustomer = customerService.updateCustomer(customerId, customer);
        return new ResponseEntity<>(updateCustomer, HttpStatus.OK);
    }
    @DeleteMapping("/{customerId}/delete")
    public ResponseEntity<Customer> deleteCustomer(@PathVariable String customerId){
        log.info("Requesting to delete a customer");
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
