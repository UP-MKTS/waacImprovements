package com.mkts.waac.services;

import com.mkts.waac.Dto.PersonDto;

import java.util.List;

public interface PersonService {

    List<PersonDto> getAll();

	PersonDto getOne(Integer id);

	void save(PersonDto personDto);

	void delete(Integer id);
}
