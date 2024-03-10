package com.poly.easylearning.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder(toBuilder = true)
@Table(name="view_result_type")
@Entity
public class ViewResultType extends BaseEntity {
    @Column(nullable = false)
    private String code;
    @Column(nullable = false)
    private String name;

    @OneToMany(mappedBy = "viewResultType")
    private List<Test> test;
}
