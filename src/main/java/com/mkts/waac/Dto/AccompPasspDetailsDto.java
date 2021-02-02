package com.mkts.waac.Dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class AccompPasspDetailsDto {

    private String contractNumber;

    private String contractDate;

    private String transportationDate;

    private String carModel;

    private String carNumber;

    private String driverFio;

    private List<AccompPasspWasteDto> accompPasspWasteDtoList;

    private String boxing;
}
