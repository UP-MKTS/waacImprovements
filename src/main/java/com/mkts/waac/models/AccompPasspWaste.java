package com.mkts.waac.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;


@Getter
@Setter
@Entity
@Table(name = "accomp_passp_waste")
public class AccompPasspWaste {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "accomp_passp_waste_id")
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "waste_type_id")
    private WasteType wasteTypes;


    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "accomp_passp_id")
    private AccompPassp accompPassps;

    @Column(name = "waste_weight")
    private Double wasteWeight;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "goal_id")
    private Goal goal;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "department_id")
    private Department department;

    @Column(name = "accomp_passp_waste_boxing")
    private String boxing;

    @Column(name = "accomp_passp_waste_address")
    private String address;

    public Double getWasteWeightPod9() {
        return wasteWeight!=null?wasteWeight:0d;
    }
}
