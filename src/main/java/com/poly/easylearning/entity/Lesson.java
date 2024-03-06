package com.poly.easylearning.entity;

import com.poly.easylearning.utils.SecurityContextUtils;
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

    @Column(name = "image_url")
    private String imageUrl;

    @OneToMany(mappedBy = "lesson")
    private Collection<Question> questions;
}
