package com.mkts.waac.Dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AccompPasspDepartmentDto {

    private Integer id;

    @JsonIgnore
    private AccompPasspDto accompPassps;

    private DepartmentDto department;

}
