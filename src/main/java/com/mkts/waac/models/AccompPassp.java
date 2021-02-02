package com.mkts.waac.models;

import com.mkts.waac.Dto.AccompPasspDepartmentDto;
import com.mkts.waac.models.Department;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "accomp_passp")
public class AccompPassp {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "accomp_passp_id")
    private Integer id;

    @Column(name = "address")
    private String address;

    @Column(name = "accomp_passp_number")
    private Integer number;

    @Column(name = "accomp_passp_date")
    private LocalDate accompPasspDate;

    @Fetch(value = FetchMode.SUBSELECT)
    @OneToMany(mappedBy = "accompPassps", fetch = FetchType.EAGER)
    List<AccompPasspDepartment> accompPasspDepartments;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "contract_id")
    private Contract contract;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "carrier_organization_id")
    private Organization carrierOrganization;

    @Column(name = "transportation_date")
    private LocalDate transportationDate;

    @Column(name = "car_model")
    private String carModel;

    @Column(name = "car_number")
    private String carNumber;

    @Column(name = "driver_fio")
    private String driverFio;

    @Fetch(value = FetchMode.SUBSELECT)
    @OneToMany(mappedBy = "accompPassps", fetch = FetchType.EAGER)
    List<AccompPasspWaste> accompPasspWastes;

//    @OneToMany(mappedBy = "accompPassp", fetch = FetchType.LAZY)
//    List<AccompPasspDepartments> accompPasspDepartments;

    @Column
    private String boxing;

    @Column(name = "count_stored")
    private Double countStored;

    public String getDepartmentsShortName(String separator)
    {
        String result = "";
        String flag = "";
        for (AccompPasspWaste temp:accompPasspWastes){
            if(flag != temp.getDepartment().getShortName()){
                result+=temp.getDepartment().getShortName()+separator;
            }
            flag = temp.getDepartment().getShortName();
        }
        return result;
    }


    public String getWastesCodes(String separator)
    {
        String result = "";
        String flag = "";
        for (AccompPasspWaste temp:accompPasspWastes){
            if(flag != temp.getWasteTypes().getCode().toString()){
                result+=temp.getWasteTypes().getCode().toString()+separator;
            }
            flag = temp.getWasteTypes().getCode().toString();
        }
        return result;
    }

    public String getWastesDangerousPow(String separator)
    {
        String result = "";
        String flag = "";
        for (AccompPasspWaste temp:accompPasspWastes){
            if(flag != temp.getWasteTypes().getDangerousPow().getName()){
                result+=temp.getWasteTypes().getDangerousPow().getName()+separator;
            }
            flag = temp.getWasteTypes().getDangerousPow().getName();
        }
        return result;
    }

}
