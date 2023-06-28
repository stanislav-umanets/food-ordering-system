package com.food.ordering.system.customer.service.domain;

import com.food.ordering.system.customer.service.domain.dto.CreateCustomerCommand;
import com.food.ordering.system.customer.service.domain.entity.Customer;
import com.food.ordering.system.customer.service.domain.event.CustomerCreateEvent;
import com.food.ordering.system.customer.service.domain.exception.CustomerDomainException;
import com.food.ordering.system.customer.service.domain.mapper.CustomerDataMapper;
import com.food.ordering.system.customer.service.domain.ports.output.repository.CustomerRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@Component
public class CustomerCreateCommandHandler {

    private final CustomerDomainService customerDomainService;
    private final CustomerRepository customerRepository;
    private final CustomerDataMapper customerDataMapper;


    public CustomerCreateCommandHandler(CustomerDomainService customerDomainService,
                                        CustomerRepository customerRepository,
                                        CustomerDataMapper customerDataMapper) {
        this.customerDomainService = customerDomainService;
        this.customerRepository = customerRepository;
        this.customerDataMapper = customerDataMapper;
    }

    @Transactional
    public CustomerCreateEvent createCustomer(CreateCustomerCommand createCustomerCommand) {
        Customer customer = customerDataMapper.customerCommandToCustomer(createCustomerCommand);
        checkCustomer(customer);
        saveCustomer(customer);
        return customerDomainService.validateAndInitiateCustomer(customer);
    }

    private void saveCustomer(Customer customer) {
        log.info("Saving customer {} to database", customer);
        Customer newCustomer = customerRepository.saveCustomer(customer);
        if (newCustomer == null) {
            log.error("Could not save customer!");
            throw new CustomerDomainException("Could not save customer!");
        }
        log.info("Customer saved with id: {}", newCustomer.getId().getValue());
    }

    private void checkCustomer(Customer customer) {
        log.info("Checking customer {} for duplicates", customer);
        Optional<Customer> persistedCustomer = customerRepository.findById(customer.getId().getValue().toString());
        if (persistedCustomer.isPresent()) {
            log.error("Customer with id {} already exists", customer.getId().getValue().toString());
            throw new CustomerDomainException("Customer with id " + customer.getId().getValue().toString() + " already exists");
        }
    }

}
