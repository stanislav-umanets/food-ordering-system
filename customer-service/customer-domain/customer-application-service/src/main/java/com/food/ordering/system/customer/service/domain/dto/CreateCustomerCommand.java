package com.food.ordering.system.customer.service.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotNull;

@Getter
@Builder
@AllArgsConstructor
public class CreateCustomerCommand {

    @NotNull
    private final String userName;
    @NotNull
    private final String firstName;
    @NotNull
    private final String lastName;
}
