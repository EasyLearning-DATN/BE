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
public class ReportActivityResponse {
    @JsonProperty("sale_total")
    private Long saleTotal;
    @JsonProperty("sale_quarter")
    private Long saleQuarter;
    @JsonProperty("user_amount_quarter")
    private Long userAmoutQuarter;
    @JsonProperty("doing_test_amount_quarter")
    private Long doingTestAmountQuarter;
    @JsonProperty("create_lesson_amount_quarter")
    private Long createLessonAmountQuarter;
}
