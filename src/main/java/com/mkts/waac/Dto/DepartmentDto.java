package com.mkts.waac.Dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mkts.waac.models.AccompPassp;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class DepartmentDto {

    private Integer id;

    @NotBlank(message = "Заполните поле")
    @Length(max = 100, message = "Введите менее 100 символов")
    private String fullName;

    @NotBlank(message = "Заполните поле")
    @Length(max = 100, message = "Введите менее 100 символов")
    private String shortName;

    @NotBlank(message = "Заполните поле")
    @Length(max = 100, message = "Введите менее 100 символов")
    private String chiefFio;

    @NotBlank(message = "Заполните поле")
    @Length(max = 100, message = "Введите менее 100 символов")
    private String chiefPosition;


}