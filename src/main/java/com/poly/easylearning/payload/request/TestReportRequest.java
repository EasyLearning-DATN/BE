package com.poly.easylearning.payload.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.poly.easylearning.entity.ReportItem;
import com.poly.easylearning.entity.Test;
import com.poly.easylearning.entity.UserInfo;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder(toBuilder = true)
public class TestReportRequest {
    @NotBlank
    @JsonProperty("test_id")
    private UUID testId;

    @NotBlank
    @JsonProperty("user_info_id")
    private Integer userInfoId;

    @JsonProperty("report_items")
    private List<ReportItemRequest> reportItems;
}
