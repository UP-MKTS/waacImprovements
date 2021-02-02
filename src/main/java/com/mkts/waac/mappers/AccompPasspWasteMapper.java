package com.mkts.waac.mappers;

import com.mkts.waac.Dto.AccompPasspWasteDto;
import com.mkts.waac.Dto.ContractDto;
import com.mkts.waac.models.AccompPasspWaste;
import com.mkts.waac.models.Contract;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AccompPasspWasteMapper {

    @Mapping(source = "wasteTypes",target = "wasteTypeId")
    @Mapping(source = "accompPassps",target = "accompPassps",ignore = true)
    @Mapping(source = "wasteWeight",target = "wasteWeight")
    @Mapping(source = "goal",target = "goal")
    AccompPasspWasteDto convertToDto(AccompPasspWaste entity);

    @InheritInverseConfiguration( name = "convertToDto" )
    AccompPasspWaste convertToEntity (AccompPasspWasteDto dto);

    List<AccompPasspWasteDto> convertToDtoList(List<AccompPasspWaste> entityList);

    List<AccompPasspWaste> convertToEntityList(List<AccompPasspWasteDto> dtoList);
}
