package com.leverx.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.Date;

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
    @Pattern(regexp = "^[A-Za-z\\d_]{4,}@[a-z]+\\.[a-z]{2,6}$", message = "Please provide a valid email address")
    @Column(name = "email", length = 40, nullable = false)
    private String email;

    @NotNull
    @Column(name = "created_at", nullable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createdDate;

    @Column(name = "code")
    private String code;

    @NotNull
    @Column(name = "code_created_at")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createdCodeDate;

    public void setId(int id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Date getCreatedCodeDate() {
        return createdCodeDate;
    }

    public void setCreatedCodeDate(Date createdCodeDate) {
        this.createdCodeDate = createdCodeDate;
    }

    public int getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }
}
