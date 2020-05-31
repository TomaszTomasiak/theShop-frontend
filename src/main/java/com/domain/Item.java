package com.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@NoArgsConstructor
@Data
public class Item {

    @JsonProperty
    private Long id;

    @JsonProperty
    private Long productId;

    @JsonProperty
    private Integer quantity;

//    @JsonProperty
//    private Long orderId;

    public Item(Long productId, int quantity) {
        this.productId = productId;
        this.quantity = quantity;
    }


}
