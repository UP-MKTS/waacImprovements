package com.mkts.waac.services;

import com.mkts.waac.Dto.OrganizationDto;
import com.mkts.waac.Dto.PersonDto;

import java.util.List;

public interface OrganizationService {

    List<OrganizationDto> getAll();

	OrganizationDto getOne(Integer id);

	void save(OrganizationDto organizationDto);

	void delete(Integer id);
}
