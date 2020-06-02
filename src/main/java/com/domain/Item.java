package com.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Item {

    @JsonProperty
    private Long id;

    @JsonProperty
    private Long productId;

    @JsonProperty
    private Integer quantity;
}
