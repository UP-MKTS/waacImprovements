package com.mkts.waac.services.impl;

import com.mkts.waac.Dao.AccompPasspDepartmentDao;
import com.mkts.waac.Dao.AccompPasspWasteDao;
import com.mkts.waac.Dto.AccompPasspDto;
import com.mkts.waac.models.AccompPassp;
import com.mkts.waac.models.AccompPasspDepartment;
import com.mkts.waac.models.AccompPasspWaste;
import com.mkts.waac.services.AccompPasspDepartmentService;
import com.mkts.waac.services.AccompPasspWasteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class AccompPasspDepartmentServiceImpl implements AccompPasspDepartmentService {

	@Autowired
	AccompPasspDepartmentDao accompPasspDepartmentDao;



	@Override
	public void saveAll(AccompPasspDto accompPasspDto, AccompPassp lastAccompPassp) {

	}

	@Override
	public void save(AccompPasspDepartment accompPasspDepartment) {
		accompPasspDepartmentDao.save(accompPasspDepartment);
	}

	@Override
	public void save() {

	}

	@Override
	public void delete(Integer accompPasspId) {
		accompPasspDepartmentDao.deleteByAccompPassps_Id(accompPasspId);
	}

	@Override
	public void delete(Integer accompPasspId, Integer departmentId) {
		accompPasspDepartmentDao.deleteByAccompPassps_IdAndDepartment_Id(accompPasspId,departmentId);
	}

	@Override
	public void deleteAll(Integer accompPasspId) {
		accompPasspDepartmentDao.deleteAllByAccompPassps_Id(accompPasspId);
	}


//	private AccompPasspWasteDao accompPasspWasteDao;
//
//	@Autowired
//	AccompPasspDao accompPasspDao;
//
//	@Autowired
//	GoalDao goalDao;
//
//	@Autowired
//	public AccompPasspWasteServiceImpl(AccompPasspWasteDao accompPasspWasteDao) {
//		this.accompPasspWasteDao = accompPasspWasteDao;
//	}
//
//	@Override
//	public void saveAll(AccompPasspDto accompPasspDto, AccompPassp lastAccompPassp) {
//		List<AccompPasspWaste> accompPasspWasteList = new ArrayList<>();
//		AccompPassp accompPassp;
//		List<Integer> wasteTypeIdList = new ArrayList<>();
//		if(lastAccompPassp.getId()!=null)
//		{
//			accompPassp = lastAccompPassp;
//		}
//		else{
//			accompPassp = accompPasspDao.findById(accompPasspDto.getId()).get();
//		}
//		if(accompPasspDto.getWasteTypeIdList()==null)
//		{
//			List<AccompPasspWaste> temp= accompPasspWasteDao.findAllByAccompPassp_Id(accompPasspDto.getId());
//			for (AccompPasspWaste ap:temp){
//				wasteTypeIdList.add(ap.getWasteTypeId());
//			}
//		}
//		else
//		{
//			wasteTypeIdList = accompPasspDto.getWasteTypeIdList();
//		}
//		for (Integer wasteTypeId  : wasteTypeIdList) {
//			AccompPasspWaste accompPasspWaste = new AccompPasspWaste();
//			if(accompPasspDto.getId()!=null) {
//				accompPasspWaste = accompPasspWasteDao.findByAccompPassp_IdAndWasteTypeId(accompPasspDto.getId(),wasteTypeId);
//			}
//			accompPasspWaste.setGoal(goalDao.findAll().get(0));
//			accompPasspWaste.setWasteTypeId(wasteTypeId);
//			accompPasspWaste.setAccompPassp(accompPassp);
//			accompPasspWasteList.add(accompPasspWaste);
//		}
//		accompPasspWasteDao.saveAll(accompPasspWasteList);
//	}
//
//	@Override
//	public void save(AccompPasspWaste accompPasspWaste) {
//		accompPasspWasteDao.save(accompPasspWaste);
//	}
//
//	@Override
//	public void delete(Integer id) {
//		accompPasspWasteDao.deleteAllByAccompPasspId(id);
//	}
}
