package com.mkts.waac.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "dangerous_class")
public class DangerousClass {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "dangerous_class_id")
    private Integer id;

    @Column(name = "class_name")
    private String name;
}
