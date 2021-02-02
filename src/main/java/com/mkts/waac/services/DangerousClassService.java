package com.mkts.waac.services;

import com.mkts.waac.Dto.DangerousClassDto;

import java.util.List;

public interface DangerousClassService {

    List<DangerousClassDto> getAll();

	DangerousClassDto getOne(Integer id);

	void save(DangerousClassDto dangerousClassDto);

	void delete(Integer id);

	String toExcel();
}
