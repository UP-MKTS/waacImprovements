package com.mkts.waac.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;


@Getter
@Setter
@Entity
@Table(name = "accomp_passp_department")
public class AccompPasspDepartment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "accomp_passp_department_id")
    private Integer id;

    @LazyCollection(LazyCollectionOption.FALSE)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "accomp_passp_id")
    private AccompPassp accompPassps;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "department_id")
    private Department department;
}
