package com.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@AllArgsConstructor
@Data
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
