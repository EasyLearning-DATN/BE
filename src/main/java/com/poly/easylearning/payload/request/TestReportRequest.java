package com.poly.easylearning.payload.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.JoinColumn;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder(toBuilder = true)
public class TestReportRequest {
    @NotNull
    @JsonProperty("test_id")
    private UUID testId;
    @NotNull
    @JsonProperty("user_info_id")
    private Integer userInfoId;
    @JsonProperty("report_items")
    private List<ReportItemRequest> reportItems;
    @JsonProperty("total_point")
    private double totalPoint;
}
