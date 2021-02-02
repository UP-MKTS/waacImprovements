package com.mkts.waac.services;

import com.mkts.waac.Dto.AccompPasspDto;
import com.mkts.waac.models.AccompPassp;
import com.mkts.waac.models.AccompPasspDepartment;
import com.mkts.waac.models.AccompPasspWaste;

import java.util.List;

public interface AccompPasspDepartmentService {

	void saveAll(AccompPasspDto accompPasspDto, AccompPassp lastAccompPassp);

	void save(AccompPasspDepartment accompPasspDepartment);

	void save();

	void delete(Integer accompPasspId, Integer departmentId);

	void delete(Integer accompPasspId);

	void deleteAll(Integer accompPasspId);

}