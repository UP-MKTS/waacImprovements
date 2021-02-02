package com.mkts.waac.models;

import com.mkts.waac.Dto.WasteTypeDto;
import com.mkts.waac.mappers.WasteTypeMapper;
import com.mkts.waac.models.Department;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "pod9_own_waste")
public class Pod9OwnWaste implements Comparable<Pod9OwnWaste>{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pod9_own_waste_id")
    private Integer id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "accomp_passp_waste_id")
    private AccompPasspWaste accompPasspWaste;

    @Column(name = "date")
    private LocalDate transparentDate;

    @Column(name = "waste_generate")
    private Double wasteGenerate;

    @Column(name = "count_from_other")
    private Double countFromOther;

    @Column(name = "name_other")
    private String nameOther;

    @Column(name = "count_from_people")
    private Double countFromPeople;

    @Column(name = "count_used")
    private Double countUsed;

    @Column(name = "count_neutralized")
    private Double countNeutralized;

    @Column(name = "count_keeping")
    private Double countKeeping;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "department_id")
    private Department department;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "waste_type_id")
    private WasteType wasteType;

    public int compareTo(Pod9OwnWaste pod9) {


        int compareCode = 0;

        if(pod9.getAccompPasspWaste()!=null){
            compareCode = pod9.getAccompPasspWaste().getWasteTypes().getCode();
        }
        else
        {
            compareCode = pod9.getWasteType().getCode();
        }
        int result = 0;

        if(this.getAccompPasspWaste()!=null){
            result = this.getAccompPasspWaste().getWasteTypes().getCode() - compareCode;
        }
        else
        {
            result = this.getWasteType().getCode() - compareCode;
        }

        return result;


    }


}
