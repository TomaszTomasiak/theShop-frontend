package com.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Product {

    @JsonProperty
    private Long id;

    @JsonProperty
    private String name;

    @JsonProperty
    private double price;

    @JsonProperty
    private String description;

    @JsonProperty
    private Long groupId;

    @JsonProperty
    private boolean available;
}
