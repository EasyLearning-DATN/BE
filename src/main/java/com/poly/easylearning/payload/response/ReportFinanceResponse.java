package com.poly.easylearning.payload.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder(toBuilder = true)
public class ReportFinanceResponse {
    @JsonProperty("revenue_quarter")
    private Double revenueQuarter;
    @JsonProperty("revenue_total")
    private Double revenueTotal;
}
