package com.poly.easylearning.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
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
@Table(name="question")
@Entity
public class Question extends BaseEntity {
    @Column(length = 255,nullable = false)
    private String title;

    private String description;

    @Column(name = "image_path")
    private String imagePath;

    @Column(nullable = false)
    private Double weighted;
}
