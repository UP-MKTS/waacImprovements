package com.mkts.waac.Dto;

import lombok.Getter;
import lombok.Setter;

import java.time.Year;

@Getter
@Setter
public class DateDto {

    private Integer id;

    private Integer month;

    private Year year;
}
