package com.mkts.waac.services.helpers;

import com.mkts.waac.Dto.AccompPasspDto;
import com.mkts.waac.Dto.AccompPasspWasteDto;
import com.mkts.waac.Dto.GoalDto;
import lombok.Getter;
import lombok.Setter;


import java.util.List;

@Getter
@Setter
public class AccompPasspWeightUpdateHelper {

    AccompPasspWasteDto accompPasspWasteDto;
    List<GoalDto> goalDtos;
    AccompPasspDto accompPasspDto;

    public AccompPasspWeightUpdateHelper(AccompPasspWasteDto accompPasspWasteDto, AccompPasspDto accompPasspDto, List<GoalDto> goalDtos) {
        this.accompPasspWasteDto = accompPasspWasteDto;
        this.goalDtos = goalDtos;
        this.accompPasspDto = accompPasspDto;
    }
}
