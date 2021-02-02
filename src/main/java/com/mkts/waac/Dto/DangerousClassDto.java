package com.mkts.waac.Dto;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class DangerousClassDto {

    private Integer id;

    @NotBlank(message = "Заполните поле")
    @Length(max = 100, message = "Введите менее 100 символов")
    private String name;
}
