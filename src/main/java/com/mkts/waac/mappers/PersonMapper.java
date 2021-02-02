package com.mkts.waac.mappers;

import com.mkts.waac.Dto.PersonDto;
import com.mkts.waac.models.Person;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Service;

import java.util.List;

@Mapper(componentModel = "spring")
@Service
public interface PersonMapper {

    @Mapping(source = "department.id", target = "departmentId")
    @Mapping(source = "department.shortName", target = "departmentShortName")
    @Mapping(source = "orderDate", target = "orderDate", dateFormat="dd.MM.yyyy")
    PersonDto convertToDto(Person entity);

    @InheritInverseConfiguration( name = "convertToDto" )
    Person convertToEntity (PersonDto dto);

    List<PersonDto> convertToDtoList(List<Person> entityList);

    List<Person> convertToEntityList(List<PersonDto> dtoList);
}
