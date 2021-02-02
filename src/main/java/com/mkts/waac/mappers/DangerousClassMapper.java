package com.mkts.waac.mappers;

import com.mkts.waac.Dto.DangerousClassDto;
import com.mkts.waac.models.DangerousClass;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Mapper(componentModel = "spring")
@Service
public interface DangerousClassMapper {

    DangerousClassDto convertToDto(DangerousClass entity);

    @InheritInverseConfiguration( name = "convertToDto" )
    DangerousClass convertToEntity (DangerousClassDto dto);

    List<DangerousClassDto> convertToDtoList(List<DangerousClass> entityList);

    List<DangerousClass> convertToEntityList(List<DangerousClassDto> dtoList);
}
