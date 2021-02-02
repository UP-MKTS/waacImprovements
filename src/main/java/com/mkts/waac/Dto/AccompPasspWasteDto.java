package com.mkts.waac.Dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mkts.waac.models.AccompPassp;
import com.mkts.waac.models.Goal;
import com.mkts.waac.models.WasteType;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
public class AccompPasspWasteDto {

    private Integer id;

    private WasteTypeDto wasteTypeId;

    private AccompPasspDto accompPassps;

    private Double wasteWeight;

    private GoalDto goal;

    private DepartmentDto department;

    private String boxing;

    private String address;

    public String getGoalName(){
        return goal==null?"":goal.getName();
    }

}
