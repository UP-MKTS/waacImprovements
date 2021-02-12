package com.mkts.waac.Dto;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
public class AccompPasspDto {

    private Integer id;

    private String address;

    @NotNull(message = "Заполните правильно (только цифры)")
    @Positive(message = "Введите число больше 0")
    @Max(value = 9999, message = "Введите число меньше 10 000")
    private Integer number;

    @NotBlank(message = "Выберите дату")
    @Pattern(regexp = "[0-9][0-9].[0-9][0-9].[1-2][0-9][0-9][0-9]", message = "Выберите правильную дату")
    private String accompPasspDate;

    @Size(min = 1, message = "Добавьте код отход в список")
    List<AccompPasspDepartmentDto> departmentDtos;

    private Integer contractId;

    private String contractNumber;

    private String contractDate;

    private String recipientOrganizationName;

    private String recipientOrganizationAddress;

    private String wasteDestination;

    private Integer carrierOrganizationId;

    private String carrierOrganizationName;

    private String carrierOrganizationAddress;

    @NotBlank(message = "Выберите дату")
    @Pattern(regexp = "[0-9][0-9].[0-9][0-9].[1-2][0-9][0-9][0-9]", message = "Выберите правильную дату")
    private String transportationDate;

    @NotBlank(message = "Заполните поле")
    @Length(max = 50, message = "Введите менее 50 символов")
    private String carModel;

    @NotBlank(message = "Заполните поле")
    @Length(max = 9, message = "Введите менее 9 символов")
    private String carNumber;

    @NotBlank(message = "Заполните поле")
    @Length(max = 100, message = "Введите менее 100 символов")
    private String driverFio;

    @Length(max = 100, message = "Введите менее 100 символов")
    private String boxing;

    private Double countStored;

    @Size(min = 1, message = "Добавьте код отход в список")
    private List<AccompPasspWasteDto> wasteTypeIdList;


    public String getDepartmentsShortName()
    {
        String result = "";
        List<String> departments = new ArrayList<>();
        for (AccompPasspWasteDto temp:wasteTypeIdList){
           departments.add(temp.getDepartment().getShortName());
        }
        departments = departments.stream().distinct().collect(Collectors.toList());
        result = departments.toString().replace("[","");
        return result.replace("]","");
    }

    public String getChiefPosition()
    {
        String result = "";
        List<String> chiefPosition = new ArrayList<>();
        for (AccompPasspWasteDto temp:wasteTypeIdList){
            chiefPosition.add(temp.getDepartment().getChiefPosition());
        }
        chiefPosition = chiefPosition.stream().distinct().collect(Collectors.toList());
        result = chiefPosition.toString().replace("[","");
        return result.replace("]","");
    }

    public String getChiefFio()
    {
        String result = "";
        List<String> chiefPosition = new ArrayList<>();
        for (AccompPasspWasteDto temp:wasteTypeIdList){
            chiefPosition.add(temp.getDepartment().getChiefFio());
        }
        chiefPosition = chiefPosition.stream().distinct().collect(Collectors.toList());
        result = chiefPosition.toString().replace("[","");
        return result.replace("]","");
    }

    public String getWasteCode(){
        String result = "";
        List<String> wasteCode = new ArrayList<>();
        for (AccompPasspWasteDto temp:wasteTypeIdList){
            wasteCode.add(temp.getWasteTypeId().getCode().toString());
        }
        wasteCode = wasteCode.stream().distinct().collect(Collectors.toList());
        result = wasteCode.toString().replace("[","");
        return result.replace("]","");
    }

}
