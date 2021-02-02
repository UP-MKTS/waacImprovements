//package by.mkts.waac.services.impl;
//
//import by.mkts.waac.dao.AccompPasspDao;
//import by.mkts.waac.dto.*;
//import by.mkts.waac.mappers.Pod9Mapper;
//import by.mkts.waac.mappers.Pod9OwnWasteMapper;
//import by.mkts.waac.models.AccompPassp;
//import by.mkts.waac.services.Pod9OwnWasteService;
//import by.mkts.waac.services.Pod9Service;
//import by.mkts.waac.services.WasteTypeService;
//import by.mkts.waac.services.helpers.Pod9Helper;
//import by.mkts.waac.services.helpers.WasteTypeHelper;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.util.*;
//
//@Service
//@Transactional
//public class Pod9ServiceImpl implements Pod9Service {
//
//	private AccompPasspDao accompPasspDao;
//
//	private Pod9OwnWasteService pod9OwnWasteService;
//
//	private Pod9OwnWasteMapper pod9OwnWasteMapper;
//
//	private WasteTypeService wasteTypeService;
//
//	private Pod9Mapper pod9Mapper;
//
//	@Autowired
//	public Pod9ServiceImpl(AccompPasspDao accompPasspDao, Pod9OwnWasteService pod9OwnWasteService, Pod9OwnWasteMapper pod9OwnWasteMapper, WasteTypeService wasteTypeService, Pod9Mapper pod9Mapper) {
//		this.accompPasspDao = accompPasspDao;
//		this.pod9OwnWasteService = pod9OwnWasteService;
//		this.pod9OwnWasteMapper = pod9OwnWasteMapper;
//		this.wasteTypeService = wasteTypeService;
//		this.pod9Mapper = pod9Mapper;
//	}
//
//	@Override
//	public Pod9Dto getPasspsByDepartmentIdAndWasteTypeId(Integer departmentId, Integer wasteTypeId) {
//		return fillPod9(departmentId, wasteTypeId);
//	}
//
//	private Pod9Dto fillPod9 (Integer departmentId, Integer wasteTypeId) {
//		List<WasteTypeDto> wasteTypeDtos = wasteTypeService.getFromAccompPassp(departmentId);
//		List<Pod9OwnWasteDto> pod9OwnWasteDtos = pod9OwnWasteService.getPod9OwnByDepartmentId(departmentId);
//
//		Set<WasteTypeDto> pod9OwnWasteTypeDtos = pod9OwnWasteMapper.convertToWasteTypeDtoList(pod9OwnWasteDtos);
//		wasteTypeDtos.addAll(pod9OwnWasteTypeDtos);
//
//		TreeSet<WasteTypeDto> wasteTypeDtoTreeSet = new TreeSet<>(wasteTypeDtos);
//		String dangerousPowAndClass = getDangerousPowAndClass(departmentId, wasteTypeId, wasteTypeDtoTreeSet);
//
//		if (wasteTypeDtoTreeSet.size() == 0) {
//			wasteTypeDtoTreeSet.add(new WasteTypeDto());
//		}
//
//		if(wasteTypeId == null) {
//			wasteTypeId = wasteTypeDtoTreeSet.first().getId();
//		}
//
//		List<AccompPassp> accompPassps = accompPasspDao.findByDepartmentIdAndWasteTypeId(departmentId, wasteTypeId);
//		List<Pod9WasteDto> pod9AccompPasspList = pod9Mapper.convertToWasteDtoList(accompPassps);
//		List<Pod9WasteDto> pod9OwnWasteList = pod9OwnWasteService.getPod9OwnByDepartmentIdAndWasteTypeId(departmentId, wasteTypeId);
//
//		List<Pod9WasteDto> pod9WasteDtos = Pod9Helper.mergePod9Records(pod9AccompPasspList, pod9OwnWasteList);
//
//		return new Pod9Dto(departmentId, dangerousPowAndClass, pod9WasteDtos, wasteTypeDtoTreeSet);
//	}
//
//	private String getDangerousPowAndClass (Integer departmentId, Integer wasteTypeId, TreeSet<WasteTypeDto> wasteTypeDtoTreeSet) {
//		String dangerousPowAndClass = " - ";
//		if (wasteTypeDtoTreeSet.size() == 0) {
//			return dangerousPowAndClass;
//		}
//
//		if (wasteTypeId == null) {
//			wasteTypeId = wasteTypeDtoTreeSet.first().getId();
//		}
//
//		WasteTypeDto wasteTypeDto = wasteTypeService.getOne(wasteTypeId);
//		dangerousPowAndClass = WasteTypeHelper.mergeDangerousPowAndClass(wasteTypeDto);
//
//		return dangerousPowAndClass;
//	}
//}