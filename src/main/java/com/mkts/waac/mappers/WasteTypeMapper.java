package com.mkts.waac.mappers;

import com.mkts.waac.Dto.WasteTypeDto;
import com.mkts.waac.models.WasteType;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Service;

import java.util.List;

@Mapper(componentModel = "spring")
@Service
public interface WasteTypeMapper {

    @Mapping(source = "dangerousPow.id", target = "dangerousPowId")
    @Mapping(source = "dangerousPow.name", target = "dangerousPowName")
    @Mapping(source = "dangerousClass.id", target = "dangerousClassId")
    @Mapping(source = "dangerousClass.name", target = "dangerousClassName")
    @Mapping(source = "activityKinds.id", target = "activityKindId")
    @Mapping(source = "activityKinds.name", target = "activityKindName")
    WasteTypeDto convertToDto(WasteType entity);

    @InheritInverseConfiguration( name = "convertToDto" )
    WasteType convertToEntity (WasteTypeDto dto);

    List<WasteTypeDto> convertToDtoList(List<WasteType> entityList);

    List<WasteType> convertToEntityList(List<WasteTypeDto> dtoList);
}
