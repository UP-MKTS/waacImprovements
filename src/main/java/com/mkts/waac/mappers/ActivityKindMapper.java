package com.mkts.waac.mappers;

import com.mkts.waac.Dto.ActivityKindDto;
import com.mkts.waac.models.ActivityKind;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Mapper(componentModel = "spring")
@Service
public interface ActivityKindMapper {

    ActivityKindDto convertToDto(ActivityKind entity);

    @InheritInverseConfiguration( name = "convertToDto" )
    ActivityKind convertToEntity (ActivityKindDto dto);

    List<ActivityKindDto> convertToDtoList(List<ActivityKind> entityList);

    List<ActivityKind> convertToEntityList(List<ActivityKindDto> dtoList);
}
