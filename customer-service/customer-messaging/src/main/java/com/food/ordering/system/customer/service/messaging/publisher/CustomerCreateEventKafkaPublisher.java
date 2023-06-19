package com.food.ordering.system.customer.service.messaging.publisher;

import com.food.ordering.system.customer.service.domain.config.CustomerServiceConfig;
import com.food.ordering.system.customer.service.domain.event.CustomerCreateEvent;
import com.food.ordering.system.customer.service.domain.ports.output.message.publisher.CustomerRequestMessagePublisher;
import com.food.ordering.system.customer.service.messaging.mapper.CustomerMessagingDataMapper;
import com.food.ordering.system.kafka.order.avro.model.CustomerAvroModel;
import com.food.ordering.system.kafka.producer.service.KafkaProducer;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFutureCallback;

@Slf4j
@Component
public class CustomerCreateEventKafkaPublisher implements CustomerRequestMessagePublisher {

    private final CustomerMessagingDataMapper customerMessagingDataMapper;
    private final KafkaProducer<String, CustomerAvroModel> kafkaProducer;
    private final CustomerServiceConfig customerServiceConfig;

    public CustomerCreateEventKafkaPublisher(CustomerMessagingDataMapper customerMessagingDataMapper,
                                             KafkaProducer<String, CustomerAvroModel> kafkaProducer,
                                             CustomerServiceConfig customerServiceConfig) {
        this.customerMessagingDataMapper = customerMessagingDataMapper;
        this.kafkaProducer = kafkaProducer;
        this.customerServiceConfig = customerServiceConfig;
    }

    @Override
    public void publish(CustomerCreateEvent customerCreateEvent) {
        String customerId = customerCreateEvent.getCustomer().getId().getValue().toString();
        try {
            CustomerAvroModel customerAvroModel = customerMessagingDataMapper.customerCreateEventToCustomerAvroModel(customerCreateEvent);

            kafkaProducer.send(
                    customerServiceConfig.getCustomerTopicName(),
                    customerId,
                    customerAvroModel,
                    getKafkaCallback(
                            customerServiceConfig.getCustomerTopicName(),
                            customerAvroModel,
                            customerId,
                            "CustomerAvroModel"));
            log.info("CustomerAvroModel sent to topic name: {} for customer id: {}", customerServiceConfig.getCustomerTopicName(), customerId);
        } catch (Exception e) {
            log.error("Error while sending CustomerAvroModel message to Kafka with customer id: {}, error: {}", customerId, e.getMessage());
        }
    }

    public <T> ListenableFutureCallback<SendResult<String, T>> getKafkaCallback(String responseTopicName, T avroModel, String id, String avroModelName) {
        return new ListenableFutureCallback<>() {
            @Override
            public void onFailure(Throwable ex) {
                log.error("Error while sending " + avroModelName + " message {} to topic {}", avroModel.toString(), responseTopicName, ex);
            }

            @Override
            public void onSuccess(SendResult<String, T> result) {
                RecordMetadata metadata = result.getRecordMetadata();
                log.info("Received successful response from Kafka for order id: {} Topic: {} Partition: {} Offset: {} Timestamp: {}",
                        id,
                        metadata.topic(),
                        metadata.partition(),
                        metadata.offset(),
                        metadata.timestamp());
            }
        };
    }
}
