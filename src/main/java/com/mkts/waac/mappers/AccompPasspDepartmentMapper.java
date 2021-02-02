package com.mkts.waac.mappers;

import com.mkts.waac.Dto.AccompPasspDepartmentDto;
import com.mkts.waac.Dto.AccompPasspDetailsDto;
import com.mkts.waac.Dto.AccompPasspWasteDto;
import com.mkts.waac.models.AccompPasspDepartment;
import com.mkts.waac.models.AccompPasspWaste;
import com.mkts.waac.models.Contract;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AccompPasspDepartmentMapper {

    @Mapping(source = "accompPassps",target = "accompPassps",ignore = true)
    @Mapping(source = "department",target = "department")
    AccompPasspDepartmentDto convertToDto(AccompPasspDepartment entity);

    @InheritInverseConfiguration( name = "convertToDto" )
    AccompPasspDepartment convertToEntity (AccompPasspDepartmentDto dto);

    List<AccompPasspDepartmentDto> convertToDtoList(List<AccompPasspDepartment> entityList);

    List<AccompPasspDepartment> convertToEntityList(List<AccompPasspDepartmentDto> dtoList);
}
