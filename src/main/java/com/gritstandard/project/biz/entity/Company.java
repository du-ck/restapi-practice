package com.gritstandard.project.biz.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table
public class Company {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id = 0L;

    @Column(length = 200, nullable = false, unique = true)
    private String name;

    /**
     * Company 는 groupNumber 를 여러개 가질 수 있으며, 그 구분은 콤마(,) 로 한다.
     * 단, default groupNumber 는 첫번째로 정한다.
     */
    @Column(name = "group_number", length = 200, nullable = false)
    private String groupNumber;
}
