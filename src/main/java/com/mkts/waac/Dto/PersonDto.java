package com.mkts.waac.Dto;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Getter
@Setter
public class PersonDto {

    private Integer id;

    @NotBlank(message = "Заполните поле")
    @Length(max = 100, message = "Введите менее 100 символов")
    private String fio;

    @NotBlank(message = "Заполните поле")
    @Length(max = 200, message = "Введите менее 200 символов")
    private String position;

    private Integer departmentId;

    private String departmentShortName;

    @NotBlank(message = "Заполните поле")
    @Length(max = 50, message = "Введите менее 50 символов")
    private String orderNumber;

    @NotBlank(message = "Выберите дату")
    @Pattern(regexp = "[0-9][0-9].[0-9][0-9].[1-2][0-9][0-9][0-9]", message = "Выберите правильную дату")
    private String orderDate;
}
