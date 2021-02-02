package com.mkts.waac.mappers;

import com.mkts.waac.Dto.GoalDto;
import com.mkts.waac.models.Goal;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Mapper(componentModel = "spring")
@Service
public interface GoalMapper {

    GoalDto convertToDto(Goal entity);

    @InheritInverseConfiguration( name = "convertToDto" )
    Goal convertToEntity (GoalDto dto);

    List<GoalDto> convertToDtoList(List<Goal> entityList);

    List<Goal> convertToEntityList(List<GoalDto> dtoList);
}
