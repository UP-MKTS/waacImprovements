package com.mkts.waac.Dto;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
public class AccompPasspJournalDto {

    private Integer id;

    private Integer accompPasspNumber;

    private String accompPasspDate;

    private String transportationDate;

    private String contractNumber;

    private String contractDate;

    private String recipientOrganizationName;

    private String wasteDangerousClassName;

    private List<AccompPasspWasteDto> accompPasspWasteDtoList;

    public String getDepartmentsShortName()
    {
        String result = "";
        List<String> departments = new ArrayList<>();
        for (AccompPasspWasteDto temp:accompPasspWasteDtoList){
            departments.add(temp.getDepartment().getShortName());
        }
        departments = departments.stream().distinct().collect(Collectors.toList());
        result = departments.toString().replace("[","");
        return result.replace("]","");
    }


}
