package com.mkts.waac.mappers;

import com.mkts.waac.Dto.DepartmentDto;
import com.mkts.waac.models.Department;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface DepartmentMapper {

    DepartmentDto convertToDto(Department entity);

    @InheritInverseConfiguration( name = "convertToDto" )
    Department convertToEntity (DepartmentDto dto);

    List<DepartmentDto> convertToDtoList(List<Department> entityList);

    List<Department> convertToEntityList(List<DepartmentDto> dtoList);
}
