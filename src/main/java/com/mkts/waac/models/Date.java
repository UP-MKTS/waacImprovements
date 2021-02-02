package com.mkts.waac.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.Year;

@Getter
@Setter
@Entity
@Table(name = "dates")
public class Date {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "date_id")
    private Integer id;

    @Column(name = "month")
    private Integer month;

    @Column(name = "year")
    private Year year;
}
