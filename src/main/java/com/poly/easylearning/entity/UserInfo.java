package com.poly.easylearning.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "`user_info`")
public class UserInfo implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String email;

    @Column(name = "full_name")
    private String fullName;

    @OneToOne
    @JoinColumn(name = "avatar")
    private Image avatar;

    private LocalDate dayOfBirth;
    private boolean isDeleted;

    @JsonIgnore
    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "userInfo")
    private List<TestReport> reports;

    @OneToMany(mappedBy = "userInfo")
    private List<Test> tests;

    @OneToMany(mappedBy = "userInfo")
    private List<Invoice> invoices;
}