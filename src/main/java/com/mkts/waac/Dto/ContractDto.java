package com.mkts.waac.Dto;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Getter
@Setter
public class ContractDto {

    private Integer id;

    @NotBlank(message = "Заполните поле")
    @Length(max = 50, message = "Введите менее 50 символов")
    private String number;

    @NotBlank(message = "Выберите дату")
    @Pattern(regexp = "[0-9][0-9].[0-9][0-9].[1-2][0-9][0-9][0-9]", message = "Выберите правильную дату")
    private String contractDate;

    private Integer organizationId;

    private String organizationName;

    private String wasteDestination;
}
