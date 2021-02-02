package com.mkts.waac.services;

import com.mkts.waac.Dto.ActivityKindDto;

import java.util.List;

public interface ActivityKindService {

    List<ActivityKindDto> getAll();

	ActivityKindDto getOne(Integer id);

	void save(ActivityKindDto activityKindDto);

	void delete(Integer id);

	String toExcel();
}
