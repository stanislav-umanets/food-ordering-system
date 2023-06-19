package com.food.ordering.system.customer.service.dataaccess.customer.mapper;

import com.food.ordering.system.customer.service.dataaccess.customer.entity.CustomerEntity;
import com.food.ordering.system.customer.service.domain.entity.Customer;
import com.food.ordering.system.domain.valueobject.CustomerId;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class CustomerDataAccessMapper {
    public CustomerEntity customerToCustomerEntity(Customer customer) {
        return CustomerEntity.builder()
                .id(customer.getId().getValue())
                .username(customer.getUsername())
                .firstName(customer.getFirstName())
                .lastName(customer.getLastName())
                .build();
    }

    public Customer customerEntityToCustomer(CustomerEntity customerEntity) {
        return new Customer(new CustomerId(UUID.fromString(customerEntity.getId().toString())),
                customerEntity.getUsername(), customerEntity.getFirstName(), customerEntity.getLastName());
    }
}
