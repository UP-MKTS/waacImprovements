package com.mkts.waac.mappers;

import com.mkts.waac.Dto.Pod9Dto;
import com.mkts.waac.Dto.Pod9SaveDto;
import com.mkts.waac.models.Pod9OwnWaste;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface Pod9SaveMapper {

//    @Mapping(source = "transparentDate",target = "date", dateFormat="dd.MM.yyyy")
//    @Mapping(source = "department.id",target = "departmentId")
//    @Mapping(source = "wasteType.id",target = "wasteTypeId")
//    Pod9SaveDto convertToDto(Pod9OwnWaste entity);
//
//    @InheritInverseConfiguration( name = "convertToDto" )
//    Pod9OwnWaste convertToEntity (Pod9SaveDto dto);
//
//    List<Pod9SaveDto> convertToDtoList(List<Pod9OwnWaste> entityList);
//
//    List<Pod9OwnWaste> convertToEntityList(List<Pod9SaveDto> dtoList);

}
