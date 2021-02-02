package com.mkts.waac.mappers;

import com.mkts.waac.Dto.ContractDto;
import com.mkts.waac.Dto.DepartmentDto;
import com.mkts.waac.models.Contract;
import com.mkts.waac.models.Department;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ContractMapper {


    @Mapping(source = "organization.id", target = "organizationId")
    @Mapping(source = "organization.name", target = "organizationName")
    @Mapping(source = "organization.wasteDestination", target = "wasteDestination")
    @Mapping(source = "contractDate", target = "contractDate", dateFormat="dd.MM.yyyy")
    ContractDto convertToDto(Contract entity);

    @InheritInverseConfiguration( name = "convertToDto" )
    Contract convertToEntity (ContractDto dto);

    List<ContractDto> convertToDtoList(List<Contract> entityList);

    List<Contract> convertToEntityList(List<ContractDto> dtoList);
}
