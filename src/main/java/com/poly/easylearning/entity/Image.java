package com.poly.easylearning.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "`image`")
@Builder
public class Image {
    @Id
    private String publicId;
    private String url;

    public Image(String publicId, String url) {
        this.publicId = publicId;
        this.url = url;
    }

    @JsonIgnore
    @OneToMany(mappedBy = "image")
    private Collection<Lesson> lessons;
}
