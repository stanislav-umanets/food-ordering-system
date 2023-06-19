package com.food.ordering.system.customer.service.domain;

import com.food.ordering.system.customer.service.domain.entity.Customer;
import com.food.ordering.system.customer.service.domain.event.CustomerCreateEvent;

public interface CustomerDomainService {

    CustomerCreateEvent validateAndInitiateCustomer(Customer customer);

}
