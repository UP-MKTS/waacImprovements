package com.mkts.waac.services.helpers;

import com.mkts.waac.Dto.Pod9Dto;
import com.mkts.waac.Dto.WasteTypeDto;
import com.mkts.waac.models.WasteType;
import lombok.Getter;
import lombok.Setter;

import java.time.YearMonth;
import java.util.List;


@Getter
@Setter
public class Pod9Helper {

    List<Pod9Dto> pod9DtoList;

    List<WasteTypeDto> wasteTypeDtos;

    YearMonth yearMonth = YearMonth.now();

}
