package com.example.reading.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name ="user",
        uniqueConstraints = {
        @UniqueConstraint(columnNames = "username"),
        @UniqueConstraint(columnNames = "email")
})
@Data
public class UserEntity extends BaseEntity {

    @Column
    private String userName;
    @Column
    private String passWord;
    @Column
    private String email;
    @Column(columnDefinition = "varchar(50) default 'Nguyen Van A'")
    private String fullName;
    @Column(columnDefinition = "boolean default true")
    private boolean status;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_role",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<RoleEntity> roles = new HashSet<>();

    @ManyToMany
    @JoinTable(name = "user_new",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "new_id"))
    private List<NewEntity> news = new ArrayList<>();



}
