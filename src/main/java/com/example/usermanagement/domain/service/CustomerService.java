package com.example.usermanagement.domain.service;


import com.example.usermanagement.domain.entity.Customer;
import com.example.usermanagement.domain.repo.CustomerRepository;
import com.example.usermanagement.dto.user.input.CustomerInput;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class CustomerService {
    private final CustomerRepository customerRepository;

    public List<Customer> getAllCustomers(){
        log.info("Get all customers success");
        return customerRepository.findAll();
    }

    public Optional<Customer> getCustomerbyID(String customerId){
        log.info("Get customer success");
        return customerRepository.findById(customerId);
    }

    public Customer createCustomer(CustomerInput customerInput){
        Customer customer = new Customer();
        customer.setUsername(customerInput.getUsername());
        customer.setEmail(customerInput.getEmail());
        customer.setPassword(customerInput.getPassword());
        customer.setFullName(customerInput.getFullName());
        customer.setAddress(customerInput.getAddress());
        customer.setBirthday(customerInput.getBirthday());

        customerRepository.save(customer);
        log.info("Create customer successfully");
        return customer;
    }

    public Customer updateCustomer(String customerId, Customer customer) {
        Customer existCustomer = getCustomerbyID(customerId).orElse((null));
        if (existCustomer == null){
            log.error("Customer not exist");

        } else {
            existCustomer.setUsername(customer.getUsername());
            existCustomer.setEmail(customer.getEmail());
            existCustomer.setAddress(customer.getAddress());
            existCustomer.setBirthday(customer.getBirthday());

            existCustomer.setPassword(customer.getPassword());
            log.info("Customer's information updated");

        }
        return existCustomer;
    }

    public void deleteCustomer(String customerId){
        log.info("Delete successfully");
        customerRepository.deleteById(customerId);
    }
}
