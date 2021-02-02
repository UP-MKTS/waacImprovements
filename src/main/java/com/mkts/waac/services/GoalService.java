package com.mkts.waac.services;

import com.mkts.waac.Dto.GoalDto;
import com.mkts.waac.models.Goal;

import java.util.List;

public interface GoalService {

    List<GoalDto> getAll();

	GoalDto getOne(Integer id);

	void save(GoalDto goalDto);

	void delete(Integer id);

	Integer getFirstId ();

	public GoalDto getFirst();
}
