package com.mkts.waac.services.impl;

import com.mkts.waac.Dao.*;
import com.mkts.waac.Dto.*;
import com.mkts.waac.mappers.*;
import com.mkts.waac.models.*;
import com.mkts.waac.services.*;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Predicate;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.*;
import java.util.stream.Collectors;

import static org.springframework.cglib.core.CollectionUtils.filter;

@Service
@Transactional
public class AccompPasspServiceImpl implements AccompPasspService {

	private AccompPasspDao accompPasspDao;

	private AccompPasspWasteService accompPasspWasteService;

	private WasteTypeService wasteTypeService;

	private AccompPasspMapper accompPasspMapper;

	@Autowired
	private AccompPasspWasteDao accompPasspWasteDao;

	@Autowired
	private OrganizationDao organizationDao;

	@Autowired
	private Pod9OwnWasteService pod9OwnWasteService;

	@Autowired
	private ContractDao contractDao;

	@Autowired
	private GoalMapper goalMapper;

	@Autowired
	private WasteTypeMapper wasteTypeMapper;

	@Autowired
	private DepartmentMapper departmentMapper;

	@Autowired
	private AccompPasspDepartmentMapper accompPasspDepartmentMapp;

	@Autowired
	private AccompPasspDepartmentService accompPasspDepartmentService;

	@Autowired
	private WasteTypeDao wasteTypeDao;

	@Autowired
	private DepartmentDao departmentDao;

	@Autowired
	private GoalDao goalDao;

	@Autowired
	private AccompPasspWasteMapper accompPasspWasteMapper;

	@Autowired
	public AccompPasspServiceImpl(AccompPasspDao accompPasspDao, AccompPasspWasteService accompPasspWasteService, WasteTypeService wasteTypeService, AccompPasspMapper accompPasspMapper) {
		this.accompPasspDao = accompPasspDao;
		this.accompPasspWasteService = accompPasspWasteService;
		this.wasteTypeService = wasteTypeService;
		this.accompPasspMapper = accompPasspMapper;

	}

	@Override
	public List<AccompPasspDto> getAll() {
		List<AccompPassp> all = accompPasspDao.findAll();
		List<AccompPasspDto> accompPasspDtos = accompPasspMapper.convertToDtoList(all);
		return  accompPasspDtos;
	}

	@Override
	public AccompPasspDto getOne(Integer accompPasspId) {
		AccompPassp accompPassp = accompPasspDao.findById(accompPasspId).get();
		return  accompPasspMapper.convertToDto(accompPassp);
	}

	@Override
	public void delete(Integer id) {
		AccompPassp accompPassp = accompPasspDao.findById(id).get();
		for (AccompPasspWaste accompPasspWaste:accompPassp.getAccompPasspWastes())
		{
			pod9OwnWasteService.deleteByAccopmPasspWasteId(accompPasspWaste.getId());
			accompPasspWasteService.delete(accompPasspWaste.getId());
		}
		accompPasspDepartmentService.delete(id);
		accompPasspDao.deleteById(id);
	}


	@Override
	public List<AccompPasspJournalDto> getAllByYearAndMonth(String year, String month) {
		List<AccompPassp> allAccompPassps = accompPasspDao.getAllByYearAndMonth(year, month);
		return accompPasspMapper.convertToJournalsDtoList(allAccompPassps);
	}

	@Override
	public void save(AccompPasspDto accompPasspDto) {
		AccompPassp saveEntity = accompPasspMapper.convertToEntity(accompPasspDto);
		accompPasspDao.save(saveEntity);
		AccompPassp lastAccompPassp = accompPasspDao.findFirstByNumber(accompPasspDto.getNumber());
		accompPasspWasteService.saveAll(accompPasspDto, lastAccompPassp);
	}

	@Override
	public void saveUpdete(AccompPasspSaveDto accompPasspSaveDto) {
		AccompPasspDto temp = accompPasspMapper.convertToDto(accompPasspDao.findById(accompPasspSaveDto.getId()).get());
		temp.setAccompPasspDate(accompPasspSaveDto.getAccompPasspDate());
		temp.setCarModel(accompPasspSaveDto.getCarModel());
		temp.setCarNumber(accompPasspSaveDto.getCarNumber());
		Organization carrierOrg = organizationDao.findById(accompPasspSaveDto.getCarrierOrganizationId()).get();
		temp.setCarrierOrganizationId(carrierOrg.getId());
		temp.setCarrierOrganizationAddress(carrierOrg.getAddress());
		temp.setCarrierOrganizationName(carrierOrg.getName());
		Contract contract = contractDao.findById(accompPasspSaveDto.getContractId()).get();
		temp.setContractDate(contract.getContractDate().toString());
		temp.setContractId(contract.getId());
		temp.setContractNumber(contract.getNumber());
		temp.setDriverFio(accompPasspSaveDto.getDriverFio());
		temp.setNumber(accompPasspSaveDto.getNumber());
		temp.setTransportationDate(accompPasspSaveDto.getTransportationDate());
		temp.setRecipientOrganizationAddress(contract.getOrganization().getAddress());
		temp.setRecipientOrganizationName(contract.getOrganization().getName());
		temp.setWasteDestination(contract.getOrganization().getWasteDestination());
		List<Integer> allNew = new ArrayList<>();
		List<Integer> wasteCurrentNow = new ArrayList<>();
		AccompPassp ap = accompPasspMapper.convertToEntity(temp);
		ap = accompPasspDao.save(ap);
		for (AccompPasspWasteSaveDto accompPasspWasteSaveDto:accompPasspSaveDto.getAccompPasspWasteSaveDtos()){
			allNew.add(accompPasspWasteSaveDto.getWasteId());
		}

		for (AccompPasspWasteDto accompPasspWaste: temp.getWasteTypeIdList()){
			wasteCurrentNow.add(accompPasspWaste.getWasteTypeId().getId());
		}
		allNew = allNew.stream().distinct().collect(Collectors.toList());
		wasteCurrentNow = wasteCurrentNow.stream().distinct().collect(Collectors.toList());

		for(Integer wasteId: allNew){
			if(wasteCurrentNow.contains(wasteId)){
				List<Integer> currentDeps = new ArrayList<>();
				List<Integer> newDeps = new ArrayList<>();

				for (AccompPasspWasteSaveDto accompPasspWasteSaveDto:accompPasspSaveDto.getAccompPasspWasteSaveDtos()){
					if(accompPasspWasteSaveDto.getWasteId()==wasteId){
						newDeps.add(accompPasspWasteSaveDto.getDepartmentId());
					}
				}
				for (AccompPasspWasteDto accompPasspWasteDto:temp.getWasteTypeIdList()){
					if(accompPasspWasteDto.getWasteTypeId().getId()==wasteId){
						currentDeps.add(accompPasspWasteDto.getDepartment().getId());
					}
				}

				for(Integer newDep: newDeps){
					if(currentDeps.contains(newDep)){
						AccompPasspWaste accompPasspWaste = accompPasspWasteDao.findByAccompPassps_IdAndWasteTypes_IdAndDepartment_Id(temp.getId(), wasteId, newDep);
						AccompPasspWasteSaveDto saveDep = getByWasteDep(accompPasspSaveDto.getAccompPasspWasteSaveDtos(),wasteId,newDep);
						accompPasspWaste.setBoxing(saveDep.getBoxing());
						accompPasspWaste.setAddress(saveDep.getAddress());
						accompPasspWaste.setDepartment(departmentDao.findById(saveDep.getDepartmentId()).get());
						accompPasspWaste.setWasteTypes(wasteTypeDao.findById(saveDep.getWasteId()).get());
						accompPasspWasteDao.save(accompPasspWaste);
					}
					else
					{
						AccompPasspWaste accompPasspWaste = new AccompPasspWaste();
						AccompPasspWasteSaveDto saveDep = getByWasteDep(accompPasspSaveDto.getAccompPasspWasteSaveDtos(),wasteId,newDep);
						accompPasspWaste.setBoxing(saveDep.getBoxing());
						accompPasspWaste.setAddress(saveDep.getAddress());
						accompPasspWaste.setDepartment(departmentDao.findById(saveDep.getDepartmentId()).get());
						accompPasspWaste.setWasteTypes(wasteTypeDao.findById(saveDep.getWasteId()).get());
						accompPasspWaste.setAccompPassps(accompPasspMapper.convertToEntity(temp));
						AccompPasspWaste newAp = accompPasspWasteDao.save(accompPasspWaste);
						Pod9OwnWaste pod9OwnWaste = new Pod9OwnWaste();
						pod9OwnWaste.setAccompPasspWaste(newAp);
						pod9OwnWaste.setTransparentDate(newAp.getAccompPassps().getTransportationDate());
						pod9OwnWasteService.save(pod9OwnWaste);
					}
				}


			}
			else{
				List<AccompPasspWasteSaveDto> wasteSaveDtos = getByWasteId(accompPasspSaveDto.getAccompPasspWasteSaveDtos(),wasteId);
				for (AccompPasspWasteSaveDto wasteSaveDto: wasteSaveDtos){
					AccompPasspWaste accompPasspWaste = new AccompPasspWaste();
					accompPasspWaste.setBoxing(wasteSaveDto.getBoxing());
					accompPasspWaste.setAddress(wasteSaveDto.getAddress());
					accompPasspWaste.setDepartment(departmentDao.findById(wasteSaveDto.getDepartmentId()).get());
					accompPasspWaste.setWasteTypes(wasteTypeDao.findById(wasteSaveDto.getWasteId()).get());
					accompPasspWaste.setAccompPassps(accompPasspMapper.convertToEntity(temp));
					AccompPasspWaste newAp = accompPasspWasteDao.save(accompPasspWaste);
					Pod9OwnWaste pod9OwnWaste = new Pod9OwnWaste();
					pod9OwnWaste.setAccompPasspWaste(newAp);
					pod9OwnWaste.setTransparentDate(newAp.getAccompPassps().getTransportationDate());
					pod9OwnWasteService.save(pod9OwnWaste);
				}
			}
		}
		temp = accompPasspMapper.convertToDto(accompPasspDao.findById(accompPasspSaveDto.getId()).get());
		for (AccompPasspWasteDto accompPasspWaste: temp.getWasteTypeIdList()){
			wasteCurrentNow.add(accompPasspWaste.getWasteTypeId().getId());
		}

		wasteCurrentNow = wasteCurrentNow.stream().distinct().collect(Collectors.toList());
		for (Integer wasteId: wasteCurrentNow){
			if(allNew.contains(wasteId)){
				List<Integer> currentDeps = new ArrayList<>();
				List<Integer> newDeps = new ArrayList<>();
				for (AccompPasspWasteSaveDto accompPasspWasteSaveDto:accompPasspSaveDto.getAccompPasspWasteSaveDtos()){
					if(accompPasspWasteSaveDto.getWasteId()==wasteId){
						newDeps.add(accompPasspWasteSaveDto.getDepartmentId());
					}
				}
				for (AccompPasspWasteDto accompPasspWasteDto:temp.getWasteTypeIdList()){
					if(accompPasspWasteDto.getWasteTypeId().getId()==wasteId){
						currentDeps.add(accompPasspWasteDto.getDepartment().getId());
					}
				}

				for (Integer currentDep:currentDeps){
					if(!newDeps.contains(currentDep)){
						AccompPasspWaste currendAPWaste = accompPasspWasteDao.findByAccompPassps_IdAndWasteTypes_IdAndDepartment_Id(temp.getId(), wasteId, currentDep);
						pod9OwnWasteService.deleteByAccopmPasspWasteId(currendAPWaste.getId());
						accompPasspWasteDao.delete(currendAPWaste);
					}
				}
			}
			else
			{
				List<Integer> currentDeps = new ArrayList<>();
				for (AccompPasspWasteDto accompPasspWasteDto:temp.getWasteTypeIdList()){
					if(accompPasspWasteDto.getWasteTypeId().getId()==wasteId){
						currentDeps.add(accompPasspWasteDto.getDepartment().getId());
					}
				}
				for (Integer currentDep:currentDeps){
					AccompPasspWaste currendAPWaste = accompPasspWasteDao.findByAccompPassps_IdAndWasteTypes_IdAndDepartment_Id(temp.getId(), wasteId, currentDep);
					pod9OwnWasteService.deleteByAccopmPasspWasteId(currendAPWaste.getId());
					accompPasspWasteDao.delete(currendAPWaste);
				}
			}
		}
	}

	private List<AccompPasspWasteSaveDto> getByWasteId(List<AccompPasspWasteSaveDto> dtos, Integer wasteId){
		List<AccompPasspWasteSaveDto> result = new ArrayList<>();
		for (AccompPasspWasteSaveDto temp:dtos){
			if(temp.getWasteId()==wasteId){
				result.add(temp);
			}
		}
		return result;
	}

	private AccompPasspWasteSaveDto getByWasteDep(List<AccompPasspWasteSaveDto> saveDtos,Integer wasteId,Integer depId){

		for (AccompPasspWasteSaveDto temp: saveDtos){
			if(temp.getWasteId() == wasteId && temp.getDepartmentId()==depId){
				return temp;
			}
		}
		return null;
	}

	@Override
	public void newAccopmPassp(AccompPasspSaveDto accompPasspSaveDto) {
		AccompPasspDto temp = new AccompPasspDto();
		temp.setAccompPasspDate(accompPasspSaveDto.getAccompPasspDate());
		temp.setCarModel(accompPasspSaveDto.getCarModel());
		temp.setCarNumber(accompPasspSaveDto.getCarNumber());
		Organization carrierOrg = organizationDao.findById(accompPasspSaveDto.getCarrierOrganizationId()).get();
		temp.setCarrierOrganizationId(carrierOrg.getId());
		temp.setCarrierOrganizationAddress(carrierOrg.getAddress());
		temp.setCarrierOrganizationName(carrierOrg.getName());
		Contract contract = contractDao.findById(accompPasspSaveDto.getContractId()).get();
		temp.setContractDate(contract.getContractDate().toString());
		temp.setContractId(contract.getId());
		temp.setContractNumber(contract.getNumber());
		temp.setDriverFio(accompPasspSaveDto.getDriverFio());
		temp.setNumber(accompPasspSaveDto.getNumber());
		temp.setTransportationDate(accompPasspSaveDto.getTransportationDate());
		temp.setRecipientOrganizationAddress(contract.getOrganization().getAddress());
		temp.setRecipientOrganizationName(contract.getOrganization().getName());
		temp.setWasteDestination(contract.getOrganization().getWasteDestination());
		temp.setWasteTypeIdList(new ArrayList<>());
		temp.setDepartmentDtos(new ArrayList<>());
		AccompPassp ap = accompPasspMapper.convertToEntity(temp);
		ap = accompPasspDao.save(ap);
		for (AccompPasspWasteSaveDto accompPasspWasteSaveDto : accompPasspSaveDto.getAccompPasspWasteSaveDtos()){
			AccompPasspWasteDto accompPasspWasteDto = new AccompPasspWasteDto();
			Pod9OwnWaste pod9OwnWaste = new Pod9OwnWaste();
			accompPasspWasteDto.setDepartment(departmentMapper.convertToDto(departmentDao.findById(accompPasspWasteSaveDto.getDepartmentId()).get()));
			accompPasspWasteDto.setWasteTypeId(wasteTypeMapper.convertToDto(wasteTypeDao.findById(accompPasspWasteSaveDto.getWasteId()).get()));
			accompPasspWasteDto.setAddress(accompPasspWasteSaveDto.getAddress());
			accompPasspWasteDto.setBoxing(accompPasspWasteSaveDto.getBoxing());
			AccompPasspWaste newWaste = accompPasspWasteMapper.convertToEntity(accompPasspWasteDto);
			newWaste.setAccompPassps(ap);
			pod9OwnWaste.setAccompPasspWaste(accompPasspWasteService.save(newWaste));
			pod9OwnWaste.setTransparentDate(newWaste.getAccompPassps().getTransportationDate());
			pod9OwnWasteService.save(pod9OwnWaste);
		}

	}



	//
//	@Override
//	public void delete(Integer id) {
//		accompPasspWasteService.delete(id);
//		accompPasspDao.deleteById(id);
//	}
//
	@Override
	public AccompPasspDetailsDto getDetailsById(Integer id) {
		AccompPassp accompPassp = accompPasspDao.getOne(id);
		return accompPasspMapper.convertToDetailsDto(accompPassp);
	}
//
	@Override
	public Integer getNextNumber() {
		AccompPassp accompPassp = accompPasspDao.findFirst1ByOrderByNumberDesc();
		int lastNumber = accompPassp!=null?accompPassp.getNumber():0;
		return ++lastNumber;
	}
//
//	@Override
//	public AccompPasspDto setNextParameters(AccompPasspDto accompPasspDto) {
//		accompPasspDto.setId(null);
//		DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd.MM.yyyy");
//		LocalDate date = LocalDate.now();
//		accompPasspDto.setAccompPasspDate(date.format(dateFormat));
//		accompPasspDto.setWasteWeight(null);
//		accompPasspDto.setNumber(getNextNumber());
//		return accompPasspDto;
//	}
//
	@Override
	public List<String> getYears() {
		return accompPasspDao.findYears();
	}
//
	@Override
	public List<MonthDto> getMonth() {
		List<String> monthNumbers = accompPasspDao.findMonthNumber();
		List<MonthDto> months = new ArrayList<>();
		Locale loc = Locale.forLanguageTag("Ru");
		for (String monthNumber: monthNumbers) {
			String monthName = Month.of(Integer.parseInt(monthNumber)).getDisplayName(TextStyle.FULL_STANDALONE, loc);
			months.add(new MonthDto(monthNumber, monthName));
		}
		return months;
	}

	@Override
	public void saveWeightAndGoal(Integer accompPasspWaste, Integer goalId, double weight) {
		AccompPasspWaste temp = accompPasspWasteDao.findById(accompPasspWaste).get();
		temp.setGoal(goalDao.findById(goalId).get());
		temp.setWasteWeight(weight);
		accompPasspWasteDao.save(temp);
	}
//
//	@Override
//	public List<AccompPasspWeightDto> getAllAPWeight() {
//		List<AccompPassp> accompPassps = accompPasspDao.getAllWeightNull();
//		return accompPasspMapper.convertToWieghtDtoList(accompPassps);
//	}
//
//	@Override
//	public AccompPasspDto setWeightAndGoal(Integer id, Integer goalId, Double weight) {
//		AccompPasspDto accompPasspDto = getOne(id);
//		if (weight < 0 ) {
//			weight = null;
//		}
//		accompPasspDto.setGoalId(goalId);
//		accompPasspDto.setWasteWeight(weight);
//		return accompPasspDto;
//	}
//
//	@Override
//	public List<Pod10Dto> getAccompPasspByMonth(String month) {
//		List<AccompPassp> accompPassps = accompPasspDao.findAllByMonth(month);
//		List<Pod10Dto> pod10Dtos = accompPasspMapper.convertToPasspDtoList(accompPassps);
//		for (Pod10Dto pod10Dto: pod10Dtos) {
//			switch (pod10Dto.getGoalName()) {
//				case "Использование": pod10Dto.setTransferredUsed(pod10Dto.getWasteGenerate());
//					break;
//				case "Обезвреживание": pod10Dto.setTransferredNeutralized(pod10Dto.getWasteGenerate());
//					break;
//				case "Хранение": pod10Dto.setTransferredStored(pod10Dto.getWasteGenerate());
//					break;
//				case "Захоронение": pod10Dto.setTransferredBuried(pod10Dto.getWasteGenerate());
//					break;
//			}
//		}
//		return pod10Dtos;
//	}
}
