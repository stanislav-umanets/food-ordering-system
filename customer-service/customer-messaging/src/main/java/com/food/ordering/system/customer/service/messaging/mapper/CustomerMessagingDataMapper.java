package com.food.ordering.system.customer.service.messaging.mapper;

import com.food.ordering.system.customer.service.domain.event.CustomerCreateEvent;
import com.food.ordering.system.kafka.order.avro.model.CustomerAvroModel;
import org.springframework.stereotype.Component;

@Component
public class CustomerMessagingDataMapper {
    public CustomerAvroModel customerCreateEventToCustomerAvroModel(CustomerCreateEvent customerCreateEvent) {
        return CustomerAvroModel.newBuilder()
                .setId(customerCreateEvent.getCustomer().getId().getValue().toString())
                .setUsername(customerCreateEvent.getCustomer().getUsername())
                .setFirstName(customerCreateEvent.getCustomer().getFirstName())
                .setLastName(customerCreateEvent.getCustomer().getLastName())
                .build();
    }
}
