package com.leverx.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.Date;

@Data
@Entity
@Table(name = "users")
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

    @Email
    @NotNull
    @Pattern(regexp = "^[A-Za-z\\d_]{4,}@[a-z]+\\.[a-z]{2,6}$",
            message = "Please provide a valid email address")
    @Column(name = "email", length = 40, nullable = false)
    private String email;

    @NotNull
    @Column(name = "created_at", nullable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createdDate;
}
