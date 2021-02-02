package com.mkts.waac.Dto;

import com.mkts.waac.mappers.WasteTypeMapper;
import com.mkts.waac.models.AccompPassp;
import com.mkts.waac.models.AccompPasspWaste;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class Pod9OwnWasteDto {



    private Integer id;

    private AccompPasspWasteDto accompPasspWasteDto;

    private String transparentDate;

    private Double wasteGenerate;

    private Double countFromOther;

    private String nameOther;

    private Double countFromPeople;

    private Double countUsed;

    private Double countNeutralized;

    private Double countKeeping;

    private DepartmentDto department;

    private WasteTypeDto wasteType;

    public String getDangerousPowAndClassName()
    {
        return accompPasspWasteDto.getWasteTypeId().getDangerousPowName();
    }

    public String getWasteNorm()
    {
        return accompPasspWasteDto.getWasteTypeId().getWasteNorm();
    }

    public String getActivityKindName()
    {
        return accompPasspWasteDto.getWasteTypeId().getActivityKindName();
    }

}
