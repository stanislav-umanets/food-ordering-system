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
        checkCustomer(createCustomerCommand.getUserName());
        Customer customer = customerDataMapper.customerCommandToCustomer(createCustomerCommand);
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

    private void checkCustomer(String userName) {
        Optional<Customer> customer = customerRepository.findByUserName(userName);
        if (customer.isPresent()) {
            log.error("Customer with user name {} already exists", userName);
            throw new CustomerDomainException("Customer with user name " + userName + " already exists");
        }
    }

}
