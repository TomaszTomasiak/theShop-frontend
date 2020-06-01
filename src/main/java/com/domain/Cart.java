package com.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class Cart {

    @JsonProperty
    private Long id;

    @JsonProperty
    private List<Item> items = new ArrayList<>();

    @JsonProperty
    private Long orderId;

    @JsonProperty
    private Long userId;

}
