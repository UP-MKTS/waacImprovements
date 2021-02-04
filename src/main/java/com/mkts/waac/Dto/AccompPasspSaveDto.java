package com.mkts.waac.Dto;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.*;
import java.util.List;
import java.util.Set;

@Getter
@Setter
public class AccompPasspSaveDto {

    @NotBlank(message = "Выберите дату")
    @Pattern(regexp = "[0-9][0-9].[0-9][0-9].[1-2][0-9][0-9][0-9]", message = "Выберите правильную дату")
    private String accompPasspDate;

    private Integer id;

    @NotNull(message = "Заполните правильно (только цифры)")
    @Positive(message = "Введите число больше 0")
    @Max(value = 9999, message = "Введите число меньше 10 000")
    private Integer number;

    private Integer contractId;

    private Integer carrierOrganizationId;

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

    @Size(min = 1, message = "Добавьте код отход в список")
    private List<AccompPasspWasteSaveDto> accompPasspWasteSaveDtos;
}
