package com.poly.easylearning.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.poly.easylearning.enums.RoleName;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "role_app")
public class RoleApp extends BaseEntity {

    @Enumerated(EnumType.STRING)
    private RoleName name;

    @JsonIgnore
    @OneToMany(mappedBy = "role", fetch = FetchType.EAGER)
    private List<UserRole> userRoles;
}
