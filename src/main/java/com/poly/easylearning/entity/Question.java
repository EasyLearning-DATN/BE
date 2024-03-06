package com.poly.easylearning.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.Collection;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder(toBuilder = true)
@Table(name="question")
@Entity
public class Question extends BaseEntity {
    @Column(length = 255,nullable = false)
    private String title;

    private String description;

    @Column(nullable = false)
    private Double weighted;

    @OneToMany(mappedBy = "question")
    private Collection<Answer> answers;

    @ManyToOne
    @JoinColumn(name="question_type")
    private QuestionType questionType;

    @ManyToOne
    @JoinColumn(name = "lesson_id")
    private Lesson lesson;

    @OneToOne
    @JoinColumn(name = "image_id")
    private Image image;
}
