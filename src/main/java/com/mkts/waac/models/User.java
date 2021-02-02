package com.mkts.waac.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Integer id;

    @Column(name = "user_fio")
    private String fio;

    @Column(name = "user_name")
    private String userName;

    @Column
    private String password;

    @Column
    private String email;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "department_id")
    private Department department;

    @Column
    private Boolean activated;

    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role;

    @Column(name = "login_status")
    private Boolean loginStatus;

    @Column(name = "date_login")
    private LocalDateTime dateLogin;

    @Column(name = "date_destroy")
    private LocalDateTime dateDestroy;
}
