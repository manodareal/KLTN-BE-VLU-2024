package com.example.usermanagement.domain.service;

import com.example.usermanagement.domain.entity.Customer;
import com.example.usermanagement.domain.repo.CustomerRepository;
import com.example.usermanagement.dto.CustomerDTO;
import com.example.usermanagement.dto.user.input.CustomerInput;
import com.example.usermanagement.mapper.CustomerMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class CustomerService {
    private final CustomerRepository customerRepository;

    public List<CustomerDTO> getAllCustomers(){
        log.info("Get all customers success");
        List<Customer> customers = customerRepository.findAll();
        return customers.stream().map(CustomerMapper::toDTO).collect(Collectors.toList());
    }

    public CustomerDTO getCustomerById(String customerId){
        Customer existCustomer = customerRepository.findById(customerId).orElseThrow(
                () -> new RuntimeException("Not found this customer " + customerId)
        );
        log.info("Get customer success");
        return CustomerMapper.toDTO(existCustomer);
    }

    public CustomerDTO createCustomer(CustomerInput customerInput){
        Customer customer = new Customer();
        customer.setUsername(customerInput.getUsername());
        customer.setEmail(customerInput.getEmail());
        customer.setFullName(customerInput.getFullName());
        customer.setAddress(customerInput.getAddress());
        customer.setBirthday(customerInput.getBirthday());
        customer.setPhoneNumber(customerInput.getPhoneNumber());
        customerRepository.save(customer);
        log.info("Create customer successfully");
        return CustomerMapper.toDTO(customer);
    }

    public CustomerDTO updateCustomer(String customerId, CustomerInput customerInput) {
        Customer existCustomer = customerRepository.findById(customerId).orElseThrow(
                () -> new RuntimeException("Customer not exist")
        );

        existCustomer.setUsername(customerInput.getUsername());
        existCustomer.setFullName(customerInput.getFullName());
        existCustomer.setEmail(customerInput.getEmail());
        existCustomer.setAddress(customerInput.getAddress());
        existCustomer.setBirthday(customerInput.getBirthday());
        existCustomer.setPhoneNumber(customerInput.getPhoneNumber());
        customerRepository.save(existCustomer);

        log.info("Customer's information updated");
        return CustomerMapper.toDTO(existCustomer);
    }

    public void deleteCustomer(String customerId){
        log.info("Delete successfully");
        customerRepository.deleteById(customerId);
    }
}
