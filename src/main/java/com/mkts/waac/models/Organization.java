package com.mkts.waac.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "organizations")
public class Organization {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "organization_id")
    private Integer id;

    @Column(name = "organization_name")
    private String name;

    @Column(name = "organization_address")
    private String address;

    @Column(name = "waste_destination")
    private String wasteDestination;
}
