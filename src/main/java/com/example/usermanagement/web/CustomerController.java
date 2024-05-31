package com.example.usermanagement.web;

import com.example.usermanagement.domain.service.CustomerService;
import com.example.usermanagement.dto.CustomerDTO;
import com.example.usermanagement.dto.user.input.CustomerInput;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("api/v1/customers")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;

    //Only admin&staff
    @GetMapping("")
    public ResponseEntity<List<CustomerDTO>> getAllCustomers(){
        log.info("Starting get all customers");
        List<CustomerDTO> customers = customerService.getAllCustomers();
        return new ResponseEntity<>(customers, HttpStatus.OK);
    }

    @GetMapping("/{customerId}")
    public ResponseEntity<CustomerDTO> getCustomerById(@PathVariable String customerId){
        log.info("Starting to find customer");
        CustomerDTO customer = customerService.getCustomerById(customerId);
        return new ResponseEntity<>(customer, HttpStatus.OK);
    }

    // Customer can use function by themselves
    @PostMapping("/create")
    public ResponseEntity<CustomerDTO> createCustomer(@RequestBody CustomerInput customer){
        log.info("Requesting to create new customer");
        CustomerDTO newCustomer = customerService.createCustomer(customer);
        return new ResponseEntity<>(newCustomer, HttpStatus.OK);
    }

    @PutMapping("/{customerId}/update")
    public ResponseEntity<CustomerDTO> updateCustomer(@PathVariable String customerId, @RequestBody CustomerInput customer){
        log.info("Requesting to update a customer");
        CustomerDTO updateCustomer = customerService.updateCustomer(customerId, customer);
        return new ResponseEntity<>(updateCustomer, HttpStatus.OK);
    }

    @DeleteMapping("/{customerId}/delete")
    public ResponseEntity<Void> deleteCustomer(@PathVariable String customerId){
        log.info("Requesting to delete a customer");
        customerService.deleteCustomer(customerId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
