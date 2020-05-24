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
    private List<Item> items;

    public Cart() {
        this.items = new ArrayList<>();
    }
}
