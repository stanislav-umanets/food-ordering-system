package com.food.ordering.system.customer.service.domain;

import com.food.ordering.system.customer.service.domain.dto.CreateCustomerCommand;
import com.food.ordering.system.customer.service.domain.dto.CreateCustomerResponse;
import com.food.ordering.system.customer.service.domain.event.CustomerCreateEvent;
import com.food.ordering.system.customer.service.domain.mapper.CustomerDataMapper;
import com.food.ordering.system.customer.service.domain.ports.input.service.CustomerApplicationService;
import com.food.ordering.system.customer.service.domain.ports.output.message.publisher.CustomerRequestMessagePublisher;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

@Slf4j
@Validated
@Service
public class CustomerApplicationServiceImpl implements CustomerApplicationService {

    private final CustomerCreateCommandHandler customerCreateCommandHandler;
    private final CustomerRequestMessagePublisher customerRequestMessagePublisher;
    private final CustomerDataMapper customerDataMapper;

    public CustomerApplicationServiceImpl(CustomerCreateCommandHandler customerCreateCommandHandler,
                                          CustomerRequestMessagePublisher customerRequestMessagePublisher,
                                          CustomerDataMapper customerDataMapper) {
        this.customerCreateCommandHandler = customerCreateCommandHandler;
        this.customerRequestMessagePublisher = customerRequestMessagePublisher;
        this.customerDataMapper = customerDataMapper;
    }

    @Override
    public CreateCustomerResponse createCustomer(CreateCustomerCommand createCustomerCommand) {
        CustomerCreateEvent customerCreateEvent = customerCreateCommandHandler.createCustomer(createCustomerCommand);
        customerRequestMessagePublisher.publish(customerCreateEvent);
        CreateCustomerResponse customerResponse = customerDataMapper.customerToCreateCustomerResponse(customerCreateEvent.getCustomer(), "Customer created successfully");
        log.info("Returning CreateCustomerResponse with order id: {}", customerResponse.getCustomerId());
        return customerResponse;
    }
}
