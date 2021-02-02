package com.mkts.waac.mappers;

import com.mkts.waac.Dto.DateDto;
import com.mkts.waac.models.Date;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Mapper(componentModel = "spring")
@Service
public interface DateMapper {

    DateDto convertToDto(Date entity);

    @InheritInverseConfiguration( name = "convertToDto" )
    Date convertToEntity (DateDto dto);

    List<DateDto> convertToDtoList(List<Date> entityList);

    List<Date> convertToEntityList(List<DateDto> dtoList);
}
