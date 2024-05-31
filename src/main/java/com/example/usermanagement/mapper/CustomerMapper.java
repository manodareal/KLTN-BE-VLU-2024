package com.example.usermanagement.mapper;

import com.example.usermanagement.domain.entity.Customer;
import com.example.usermanagement.dto.CustomerDTO;

public class CustomerMapper {
    public static CustomerDTO toDTO(Customer customer) {
        return new CustomerDTO(
                customer.getCustomerId(),
                customer.getUsername(),
                customer.getEmail(),
                customer.getFullName(),
                customer.getAddress(),
                customer.getBirthday(),
                customer.getCreateAt(),
                customer.getUpdateAt()
        );
    }

    public static Customer toEntity(CustomerDTO customerDTO) {
        Customer customer = new Customer();
        customer.setCustomerId(customerDTO.getCustomerId());
        customer.setUsername(customerDTO.getUsername());
        customer.setEmail(customerDTO.getEmail());
        customer.setFullName(customerDTO.getFullName());
        customer.setAddress(customerDTO.getAddress());
        customer.setBirthday(customerDTO.getBirthday());
        customer.setCreateAt(customerDTO.getCreateAt());
        customer.setUpdateAt(customerDTO.getUpdateAt());
        return customer;
    }
}
