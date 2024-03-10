package com.poly.easylearning.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.ColumnDefault;

import java.util.Collection;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder(toBuilder = true)
@Table(name="lesson")
@Entity
public class Lesson extends BaseEntity
{
    @ManyToOne
    @JoinColumn(name = "user_info")
    private UserInfo userInfo;

    @Column(name = "name", length = 255, nullable = false)
    private String name;
    private String description;

    @Column(name = "is_public", nullable = false)
    private boolean isPublic;

    @ManyToOne
    @JoinColumn(name = "image_id")
    private Image image;

    @OneToMany(mappedBy = "lesson")
    private Collection<Question> questions;

    @Column(name = "access_times", nullable = false)
    private Integer accessTimes;
}
