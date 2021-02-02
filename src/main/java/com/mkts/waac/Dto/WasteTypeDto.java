package com.mkts.waac.Dto;


import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Comparator;
import java.util.Objects;

@Getter
@Setter
public class WasteTypeDto implements Comparable<WasteTypeDto> {

    private Integer id;

    @NotNull(message = "Заполните поле (только цифры)")
    @Digits(integer = 8, fraction = 0, message = "Введите правильный номер")
    private Integer code = 0;

    @NotBlank(message = "Заполните поле")
    @Length(max = 200, message = "Введите менее 200 символов")
    private String name = "";

    private Integer dangerousPowId;

    private String dangerousPowName;

    private Integer dangerousClassId;

    private String dangerousClassName;

    private Integer activityKindId;

    private String activityKindName = "";

    @NotBlank(message = "Заполните поле")
    @Length(max = 200, message = "Введите менее 200 символов")
    private String wasteNorm = "";

    @Override
    public int compareTo(WasteTypeDto wasteTypeDto) {
        return (this.code - wasteTypeDto.getCode());
    }

    public static Comparator<WasteTypeDto> wastTypeComparator = new Comparator<WasteTypeDto>() {

        @Override
        public int compare(WasteTypeDto firstSource, WasteTypeDto secondSource) {
            return firstSource.getName().compareTo(secondSource.getName());
        }
    };

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WasteTypeDto that = (WasteTypeDto) o;
        return code.equals(that.code) &&
                name.equals(that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(code, name);
    }

    public String getDangerousPowAndClassName()
    {
        return dangerousPowName+" / "+dangerousClassName;
    }


}
