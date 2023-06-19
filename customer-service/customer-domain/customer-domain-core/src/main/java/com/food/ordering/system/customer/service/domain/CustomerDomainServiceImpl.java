package com.food.ordering.system.customer.service.domain;

import com.food.ordering.system.customer.service.domain.entity.Customer;
import com.food.ordering.system.customer.service.domain.event.CustomerCreateEvent;
import lombok.extern.slf4j.Slf4j;

import java.time.ZoneId;
import java.time.ZonedDateTime;

@Slf4j
public class CustomerDomainServiceImpl implements CustomerDomainService {


    @Override
    public CustomerCreateEvent validateAndInitiateCustomer(Customer customer) {
        log.debug("Customer with id {} initiated", customer.getId().getValue());
        return new CustomerCreateEvent(customer, ZonedDateTime.now(ZoneId.of("UTC")));
    }
}
