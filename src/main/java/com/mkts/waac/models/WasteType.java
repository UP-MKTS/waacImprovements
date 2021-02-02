package com.mkts.waac.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "waste_types")
public class WasteType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "waste_type_id")
    private Integer id;

    @Column(name = "waste_code")
    private Integer code;

    @Column(name = "waste_name")
    private String name;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "dangerous_pow_id")
    private DangerousPow dangerousPow;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "dangerous_class_id")
    private DangerousClass dangerousClass;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "activity_kind_id")
    private ActivityKind activityKinds;

    @Column(name = "waste_norm")
    private String wasteNorm;




}
