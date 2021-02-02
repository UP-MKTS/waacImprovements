package com.mkts.waac.mappers;

import com.mkts.waac.Dto.RoleDto;
import com.mkts.waac.models.Role;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface RoleMapper {

    RoleDto convertToDto(Role entity);

    @InheritInverseConfiguration( name = "convertToDto" )
    Role convertToEntity (RoleDto dto);

    List<RoleDto> convertToDtoList(List<Role> entityList);

    List<Role> convertToEntityList(List<RoleDto> dtoList);
}
