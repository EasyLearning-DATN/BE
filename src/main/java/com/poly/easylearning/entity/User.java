package com.poly.easylearning.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.poly.easylearning.enums.Provider;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;

@Getter
@Setter
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "`user`")
@Entity
public class User extends BaseEntity implements UserDetails {

    private String  username;

    @JsonIgnore
    @Column(name="password" ,length = 1024)
    private String  password;

    @Enumerated(EnumType.STRING)
    private Provider provider;

    private boolean locked;
    private boolean enable;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private UserInfo userInfo;

    @JsonIgnore
    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<UserRole> userRoles;

    @JsonIgnore
    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<Token> tokens;

    public User(String username, String password, Provider provider) {
        this.username = username;
        this.password = password;
        this.provider = provider;
    }

    @JsonIgnore
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorityList = new ArrayList<>();
        for (UserRole userRole: this.userRoles) {
            authorityList.add(new SimpleGrantedAuthority(userRole.getRole().getName().getValue()));
        }
        return authorityList;
    }
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
