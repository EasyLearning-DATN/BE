package com.poly.easylearning.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
@Table(name="question_type")
@Entity
public class QuestionType extends BaseEntity {
    @Column(length = 255, nullable = false)
    private String name;

    @Column(length = 5, nullable = false)
    private String code;

    @JsonIgnore
    @OneToMany(mappedBy = "questionType", fetch = FetchType.LAZY)
    private Collection<Question> questions;
}
