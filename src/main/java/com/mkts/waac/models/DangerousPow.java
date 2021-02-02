package com.mkts.waac.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "dangerous_pow")
public class DangerousPow {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "dangerous_pow_id")
    private Integer id;

    @Column(name = "pow_name")
    private String name;
}
