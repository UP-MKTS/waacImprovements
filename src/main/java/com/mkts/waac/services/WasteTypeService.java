package com.mkts.waac.services;

import com.mkts.waac.Dto.WasteTypeDto;

import java.util.List;

public interface WasteTypeService {

    List<WasteTypeDto> getAll();

    List<WasteTypeDto> getAllByListId(List<Integer> indexes);

	WasteTypeDto getOne(Integer id);

	void save(WasteTypeDto wasteTypeDto);

	void delete(Integer id);

	List<WasteTypeDto> getFromAccompPassp(Integer departmentId);

	String toExcel();
}
