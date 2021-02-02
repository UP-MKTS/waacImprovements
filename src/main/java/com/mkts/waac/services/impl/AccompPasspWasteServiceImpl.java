package com.mkts.waac.services.impl;

import com.mkts.waac.Dao.AccompPasspDao;
import com.mkts.waac.Dao.AccompPasspWasteDao;
import com.mkts.waac.Dao.GoalDao;
import com.mkts.waac.Dto.AccompPasspDto;
import com.mkts.waac.Dto.AccompPasspWasteDto;
import com.mkts.waac.mappers.AccompPasspWasteMapper;
import com.mkts.waac.models.AccompPassp;
import com.mkts.waac.models.AccompPasspWaste;
import com.mkts.waac.services.AccompPasspWasteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class AccompPasspWasteServiceImpl implements AccompPasspWasteService {

	@Autowired
	AccompPasspWasteDao accompPasspWasteDao;

	@Autowired
	AccompPasspWasteMapper accompPasspWasteMapper;

	@Override
	public void saveAll(AccompPasspDto accompPasspDto, AccompPassp lastAccompPassp) {

	}

	@Override
	public AccompPasspWaste save(AccompPasspWaste accompPasspWaste) {
		return accompPasspWasteDao.save(accompPasspWaste);
	}

	@Override
	public void delete(Integer id) {
		accompPasspWasteDao.deleteById(id);
	}

	@Override
	public void deleteAll(Integer id) {
		accompPasspWasteDao.deleteAllByAccompPassps_Id(id);
	}

	@Override
	public AccompPasspWaste findDyAccompPasspIdAndWasteId(Integer accompPasspId, Integer wasteId) {
		return accompPasspWasteDao.findByAccompPassps_IdAndWasteTypes_Id(accompPasspId,wasteId);
	}

	@Override
	public void deleteByAccompPasspIdAndWasteId(Integer accompPasspId, Integer wasteId) {
		accompPasspWasteDao.deleteByAccompPassps_IdAndWasteTypes_Id(accompPasspId,wasteId);
	}

	@Override
	public AccompPasspWaste getOne(Integer id) {

		AccompPasspWaste accompPasspWaste = accompPasspWasteDao.getOne(id);
		return accompPasspWaste;
	}

	@Override
	public AccompPasspWasteDto getOneDto(Integer id) {

		AccompPasspWaste accompPasspWaste = accompPasspWasteDao.getOne(id);
		return accompPasspWasteMapper.convertToDto(accompPasspWaste);
	}

	@Override
	public AccompPasspWasteDto toDto(AccompPasspWaste accompPasspWaste) {
		return accompPasspWasteMapper.convertToDto(accompPasspWaste);
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
