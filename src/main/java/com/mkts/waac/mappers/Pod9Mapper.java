package com.mkts.waac.mappers;

import com.mkts.waac.Dto.Pod9Dto;
import com.mkts.waac.Dto.Pod9OwnWasteDto;
import com.mkts.waac.models.Pod9OwnWaste;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface Pod9Mapper {

    @Mapping(source = "accompPasspWaste.wasteTypes.name",target = "wasteName")
    @Mapping(source = "accompPasspWaste.wasteTypes.id",target = "wasteId")
    @Mapping(source = "accompPasspWaste.wasteTypes.code",target = "wasteCode")
    @Mapping(source = "accompPasspWaste.wasteTypes.dangerousPow.name",target = "dangerousPowName")
    @Mapping(source = "accompPasspWaste.wasteTypes.dangerousClass.name",target = "dangerousClassName")
    @Mapping(source = "accompPasspWaste.wasteTypes.wasteNorm",target = "wasteNorm")
    @Mapping(source = "accompPasspWaste.wasteTypes.activityKinds.name",target = "activityKindName")
    @Mapping(source = "transparentDate",target = "transparentDate",dateFormat="dd.MM.yyyy")
    @Mapping(source = "accompPasspWaste.wasteWeight",target = "wasteWeight")
    @Mapping(source = "accompPasspWaste.accompPassps.contract.organization.name",target = "recipientOrganizationName")
    @Mapping(source = "accompPasspWaste.goal.name",target = "goalName")
    Pod9Dto convertToDto(Pod9OwnWaste entity);

    @InheritInverseConfiguration( name = "convertToDto" )
    Pod9OwnWaste convertToEntity (Pod9Dto dto);

    List<Pod9Dto> convertToDtoList(List<Pod9OwnWaste> entityList);

    List<Pod9OwnWaste> convertToEntityList(List<Pod9Dto> dtoList);

}
