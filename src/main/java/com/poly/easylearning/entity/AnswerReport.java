package com.poly.easylearning.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
@Table(name="answer_report")
@Entity
public class AnswerReport extends BaseEntity {
    @Column(name = "value", nullable = false)
    private String value;

    @Column(name = "is_correct", nullable = false)
    private Boolean isCorrect;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "question_report_id")
    private QuestionReport questionReport;
}
