package com.mkts.waac.services;

import com.mkts.waac.Dto.DangerousPowDto;

import java.util.List;

public interface DangerousPowService {

    List<DangerousPowDto> getAll();

	DangerousPowDto getOne(Integer id);

	void save(DangerousPowDto dangerousPowDto);

	void delete(Integer id);

	String toExcel();
}
