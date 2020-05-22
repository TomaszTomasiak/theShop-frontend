package com.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Item {

    @JsonProperty
    private Long id;

    @JsonProperty
    private Long productId;

    @JsonProperty
    private int quantity;

    @JsonProperty
    private Long orderId;
}
