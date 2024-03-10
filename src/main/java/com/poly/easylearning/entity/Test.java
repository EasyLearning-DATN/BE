package com.poly.easylearning.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.Set;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder(toBuilder = true)
@Table(name="test")
@Entity
public class Test extends BaseEntity{
    @Column(length = 255, nullable = false)
    private String name;
    @Column(name = "time_total", nullable = false)
    private Long timeTotal; //second
    @Column(name = "time_question", nullable = false)
    private Long timeQuestion;
    @Column(name = "total_question", nullable = false)
    private Integer totalQuestion;

    @ManyToOne
    @JoinColumn(name = "image_id")
    private Image image;

    @ManyToOne
    @JoinColumn(name = "view_result_type_id")
    private ViewResultType viewResultType;

    @OneToMany(mappedBy = "test")
    private Set<QuestionTest> questionTests;

    @OneToMany(mappedBy = "test")
    private List<TestReport> testReports;
}
