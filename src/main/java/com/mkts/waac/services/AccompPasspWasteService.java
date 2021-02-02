package com.mkts.waac.services;

import com.mkts.waac.Dto.AccompPasspDto;
import com.mkts.waac.Dto.AccompPasspWasteDto;
import com.mkts.waac.models.AccompPassp;
import com.mkts.waac.models.AccompPasspWaste;

public interface AccompPasspWasteService {

	void saveAll(AccompPasspDto accompPasspDto, AccompPassp lastAccompPassp);

	AccompPasspWaste save(AccompPasspWaste accompPasspWaste);

	void delete(Integer id);

	void deleteAll(Integer id);

	AccompPasspWaste findDyAccompPasspIdAndWasteId(Integer accompPasspId, Integer wasteId);

	void deleteByAccompPasspIdAndWasteId(Integer accompPasspId, Integer wasteId);

	AccompPasspWaste getOne(Integer id);

	AccompPasspWasteDto getOneDto(Integer id);

	AccompPasspWasteDto toDto(AccompPasspWaste accompPasspWaste);

}