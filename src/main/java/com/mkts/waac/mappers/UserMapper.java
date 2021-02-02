package com.mkts.waac.mappers;

import com.mkts.waac.Dto.UserDto;
import com.mkts.waac.models.User;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(source = "department.id", target = "departmentId")
    @Mapping(source = "department.fullName", target = "departmentFullName")
    @Mapping(source = "department.shortName", target = "departmentShortName")
    @Mapping(source = "role.id", target = "roleId")
    @Mapping(source = "role.name", target = "roleName")
    @Mapping(target = "dateLogin", dateFormat="dd.MM.yyyy (HH:mm)")
    @Mapping(target = "dateDestroy", dateFormat="dd.MM.yyyy (HH:mm)")
    UserDto convertToDto(User entity);

    @InheritInverseConfiguration( name = "convertToDto" )
    User convertToEntity (UserDto dto);

    List<UserDto> convertToDtoList(List<User> entityList);

    List<User> convertToEntityList(List<UserDto> dtoList);
}
