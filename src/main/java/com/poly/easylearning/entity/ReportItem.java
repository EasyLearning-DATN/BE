package com.poly.easylearning.entity;

import jakarta.persistence.*;
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
@Table(name="report_item")
@Entity
public class ReportItem extends BaseEntity{
    @ManyToOne
    @JoinColumn(name = "test_report_id")
    private TestReport testReport;

    @ManyToOne
    @JoinColumn(name = "question_test_id")
    private QuestionTest questionTest;

    @Column(name = "answer_of_user")
    private String answerOfUser;
}
