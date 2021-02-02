package com.mkts.waac.services;

import com.mkts.waac.Dto.DateDto;

import java.util.List;

public interface DateService {

    List<DateDto> getAll();

	DateDto getOne(Integer id);

	void save(DateDto dateDto);

	void delete(Integer id);
}
