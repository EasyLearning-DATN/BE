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
    private String description;
    @Column(name = "time_total")
    private Long timeTotal; //second
    @Column(name = "time_question")
    private Long timeQuestion;
    @Column(name = "total_question", nullable = false)
    private Integer totalQuestion;

    @ManyToOne
    @JoinColumn(name = "image_id")
    private Image image;

    @ManyToOne
    @JoinColumn(name = "view_result_type_id", nullable = false)
    private ViewResultType viewResultType;

    @OneToMany(mappedBy = "test")
    private List<QuestionTest> questionTests;

    @OneToMany(mappedBy = "test")
    private List<TestReport> testReports;

    @ManyToOne
    @JoinColumn(name = "user_info_id")
    private UserInfo userInfo;
}
