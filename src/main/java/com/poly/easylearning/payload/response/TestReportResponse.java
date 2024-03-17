package com.poly.easylearning.payload.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.poly.easylearning.entity.*;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder(toBuilder = true)
public class TestReportResponse extends BaseResponse {
    @JsonProperty("test")
    private TestOfTestReportResponse testOfTestReportResponse;

    @JsonProperty("user_info")
    private UserInfoResponse userInfoResponse;

    @JsonProperty("question_report")
    private List<QuestionReportResponse> questionReportResponses;

    @Column(name = "doing_date")
    private LocalDateTime doingDate;
    @JsonProperty("total_point")
    private double totalPoint;

    public static TestReportResponse fromTestReport(TestReport testReport) {
        return TestReportResponse.builder()
                .id(testReport.getId())
                .createdDate(testReport.getCreatedDate())
                .createdBy(testReport.getCreatedBy())
                .lastModifiedDate(testReport.getLastModifiedDate())
                .lastModifiedBy(testReport.getLastModifiedBy())
                .testOfTestReportResponse(TestOfTestReportResponse.fromTest(testReport.getTest()))
                .userInfoResponse(UserInfoResponse.fromUserInfo(testReport.getUserInfo()))
                .questionReportResponses(testReport.getQuestionReports() != null ? testReport.getQuestionReports().stream().map(QuestionReportResponse::fromQuestionReport).toList() : null)
                .doingDate(testReport.getDoingDate())
                .totalPoint(testReport.getTotalPoint())
                .build();
    }
}
