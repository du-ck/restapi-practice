package com.gritstandard.project.biz.entity;



import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id = 0L;

    @Column(name = "login_id", length = 15, nullable = false, unique = true)
    private String loginId;

    @Column(length = 20, nullable = false)
    private String password;

    @Column(name = "group_number", length = 5, nullable = false)
    private String groupNumber;

    @Column(length = 200, nullable = false)
    private String name;

    @Column(name = "phone_number", length = 11, nullable = false)
    private String phoneNumber;

    @Column(length = 200, nullable = false)
    private String companyName;

    @Column(length = 10, nullable = false)
    private String auth = "";
}
