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
@Table(name="answer")
@Entity
public class Answer extends BaseEntity {
    @Column(name = "value", nullable = false)
    private String value;

    @Column(name = "is_correct", nullable = false)
    private Boolean isCorrect;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "lesson_id")
    private Lesson lesson;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "question_id")
    private Question question;
}
