package com.mkts.waac.Dto;


import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.*;

@Getter
@Setter
public class Pod9SaveDto {

    private Integer id;

    @NotBlank(message = "Выберите дату")
    @Pattern(regexp = "[0-9][0-9].[0-9][0-9].[1-2][0-9][0-9][0-9]", message = "Выберите правильную дату")
    private String date;

    @NotNull(message = "Заполните правильно (только цифры)")
    @Positive(message = "Введите число больше 0")
    @Max(value = 9999, message = "Введите число меньше 10 000")
    private Double wasteGenerate;

    private Double countFromOther;

    private String nameOther;

    private Double countFromPeople;

    private Double countUsed;

    private Double countNeutralized;

    private Double wasteWeight;

    private Integer departmentId;

    @NotNull(message = "Заполните значение")
    private Integer wasteTypeId;


}
