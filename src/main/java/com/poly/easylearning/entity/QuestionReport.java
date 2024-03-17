package com.poly.easylearning.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder(toBuilder = true)
@Table(name = "question_report")
@Entity
public class QuestionReport extends BaseEntity {
    @Column(length = 255, nullable = false)
    private String title;
    private String description;

    @Column(nullable = false)
    private Double weighted;
    @OneToMany(mappedBy = "questionReport")
    private List<AnswerReport> answerReports;
    @JoinColumn(name = "question_type_code")
    private String questionTypeCode;
    @ManyToOne
    @JoinColumn(name = "test_report_id")
    private TestReport testReport;
    @JoinColumn(name = "answerOfUser")
    private String answerOfUser;
    private double point;
}
