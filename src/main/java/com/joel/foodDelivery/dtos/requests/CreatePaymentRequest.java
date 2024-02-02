package com.joel.foodDelivery.dtos.requests;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@ToString
public class CreatePaymentRequest {

    @JsonProperty("country")
    private String countryCode;
    private String recurrence;
    private String type;

    @JsonProperty("biller_name")
    private String billerName;
    private String reference;
    private String customer;
    private String email;
    private BigDecimal amount;

    public void setAmount(BigDecimal amount) {
        this.amount = amount.multiply(new BigDecimal(100));
    }
}
