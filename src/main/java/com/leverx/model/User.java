package com.leverx.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private int id;

    @NotNull
    @Column(name = "first_name", length = 20, nullable = false)
    private String firstName;

    @NotNull
    @Column(name = "last_name", length = 20, nullable = false)
    private String lastName;

    @NotNull
    @Column(name = "password", length = 60, nullable = false)
    private String password;

    @NotNull
    @Column(name = "email", length = 40, nullable = false)
    private String email;

    @NotNull
    @Column(name = "created_at", nullable = false)
    private Date createdDate;
}
