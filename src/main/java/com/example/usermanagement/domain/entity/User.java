package com.example.usermanagement.domain.entity;

import com.google.common.collect.Lists;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.util.Collection;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@Table (name = "account")
@Entity
public class User implements UserDetails {
    @Id
    @Column(length = 50, nullable = false)
    private String id;

    @Column(nullable = false)
    private String username;
    @Column(nullable = false)
    private String password;
    @Column(nullable = false)
    private String fullName;
    @Column(nullable = false, unique = true)
    private String email;

    @Column
    private LocalDate createAt;
    @Column
    private LocalDate updateAt;
    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role;

    public User() {
        this.id = "TK-" + UUID.randomUUID();
        this.createAt = LocalDate.now();
        this.updateAt = LocalDate.now();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (StringUtils.hasText(role.getRoleName())) {
            return Lists.newArrayList(new SimpleGrantedAuthority(role.getRoleName()));
        } else {
            return Lists.newArrayList();
        }
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }
}
