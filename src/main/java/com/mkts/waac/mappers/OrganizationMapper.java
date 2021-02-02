package com.mkts.waac.mappers;

import com.mkts.waac.Dto.OrganizationDto;
import com.mkts.waac.models.Organization;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Mapper(componentModel = "spring")
@Service
public interface OrganizationMapper {

    OrganizationDto convertToDto(Organization entity);


    @InheritInverseConfiguration( name = "convertToDto" )
    Organization convertToEntity (OrganizationDto dto);

    List<OrganizationDto> convertToDtoList(List<Organization> entityList);

    List<Organization> convertToEntityList(List<OrganizationDto> dtoList);
}
