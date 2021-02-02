package com.mkts.waac.mappers;

import com.mkts.waac.Dto.AccompPasspWasteDto;
import com.mkts.waac.Dto.Pod9OwnWasteDto;
import com.mkts.waac.Dto.Pod9SaveDto;
import com.mkts.waac.models.AccompPasspWaste;
import com.mkts.waac.models.Pod9OwnWaste;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface Pod9OwnWasteMapper {

    @Mapping(source = "accompPasspWaste",target = "accompPasspWasteDto")
    @Mapping(source = "transparentDate",target = "transparentDate",dateFormat="dd.MM.yyyy")
    Pod9OwnWasteDto convertToDto(Pod9OwnWaste entity);

    @InheritInverseConfiguration( name = "convertToDto" )
    Pod9OwnWaste convertToEntity (Pod9OwnWasteDto dto);

    List<Pod9OwnWasteDto> convertToDtoList(List<Pod9OwnWaste> entityList);

    List<Pod9OwnWaste> convertToEntityList(List<Pod9OwnWasteDto> dtoList);

    @Mapping(source = "transparentDate",target = "date", dateFormat="dd.MM.yyyy")
    @Mapping(source = "department.id",target = "departmentId")
    @Mapping(source = "wasteType.id",target = "wasteTypeId")
    Pod9SaveDto POD_9_OWN_WASTE_TO_SAVE(Pod9OwnWaste entity);

    List<Pod9SaveDto> POD_9_OWN_WASTE_TO_SAVES(List<Pod9OwnWaste> entityList);


}
