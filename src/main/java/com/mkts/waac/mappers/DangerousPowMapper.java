package com.mkts.waac.mappers;

import com.mkts.waac.Dto.DangerousPowDto;
import com.mkts.waac.models.DangerousPow;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Mapper(componentModel = "spring")
@Service
public interface DangerousPowMapper {

    DangerousPowDto convertToDto(DangerousPow entity);

    @InheritInverseConfiguration( name = "convertToDto" )
    DangerousPow convertToEntity (DangerousPowDto dto);

    List<DangerousPowDto> convertToDtoList(List<DangerousPow> entityList);

    List<DangerousPow> convertToEntityList(List<DangerousPowDto> dtoList);
}
