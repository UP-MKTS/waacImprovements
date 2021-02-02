package com.mkts.waac.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "activity_kinds")
public class ActivityKind {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "activity_kind_id")
    private Integer id;

    @Column(name = "activity_kind_name")
    private String name;
}
