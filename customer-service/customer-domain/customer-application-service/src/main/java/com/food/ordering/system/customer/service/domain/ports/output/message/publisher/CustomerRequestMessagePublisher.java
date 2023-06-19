package com.food.ordering.system.customer.service.domain.ports.output.message.publisher;

import com.food.ordering.system.customer.service.domain.event.CustomerCreateEvent;
import com.food.ordering.system.domain.event.publisher.DomainEventPublisher;

public interface CustomerRequestMessagePublisher extends DomainEventPublisher<CustomerCreateEvent> {
}
