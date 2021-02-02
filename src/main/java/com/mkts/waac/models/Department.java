package com.mkts.waac.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "departments")
public class Department {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "department_id")
    private Integer id;

    @Column(name = "department_full_name")
    private String fullName;

    @Column(name = "department_short_name")
    private String shortName;

    @Column(name = "chief_fio")
    private String chiefFio;

    @Column(name = "chief_position")
    private String chiefPosition;


}
