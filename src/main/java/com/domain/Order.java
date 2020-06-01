package com.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@AllArgsConstructor
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Order {

    @JsonProperty
    private Long id;

    @JsonProperty
    @JsonFormat(pattern="yyyy-MM-dd")
    private LocalDate ordered;

    @JsonProperty
    private String comments;

    @JsonProperty
    private Long cartId;

    @JsonProperty
    private Long userId;

    @JsonProperty
    private Double totalValue;

    @JsonProperty
    private boolean isCompleted;

    public Order() {
        this.ordered = LocalDate.now();
    }
}
