package com.food.ordering.system.order.service.domain.ports.output.repository;

import com.food.ordering.system.order.service.domain.entity.Customer;

import java.util.Optional;
import java.util.UUID;

public interface CustomerRepository {

    Customer saveCustomer(Customer customer);

    Optional<Customer> findCustomer(UUID customerId);
}
