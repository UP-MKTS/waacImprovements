package com.mkts.waac.services.impl;

import com.mkts.waac.Dao.ContractDao;
import com.mkts.waac.Dto.ContractDto;
import com.mkts.waac.mappers.ContractMapper;
import com.mkts.waac.models.Contract;
//import com.mkts.waac.mappers.ContractMapper;
import com.mkts.waac.services.ContractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ContractServiceImpl implements ContractService {

    @Autowired
    private ContractDao contractDao;

    @Autowired
    private ContractMapper contractMapper;

    @Override
    public List<ContractDto> getAll() {
        List<Contract> allContracts = contractDao.findAll();
        return contractMapper.convertToDtoList(allContracts);
    }

    @Override
    public ContractDto getOne(Integer id) {
        Contract contract = contractDao.getOne(id);
        return contractMapper.convertToDto(contract);
    }

    @Override
    public void save(ContractDto contractDto) {
        Contract saveEntity = contractMapper.convertToEntity(contractDto);
        contractDao.save(saveEntity);
    }

    @Override
    public void delete(Integer id) {
        contractDao.deleteById(id);
    }

//	private ContractDao contractDao;
//
//	private ContractMapper contractMapper;
//
//	@Autowired
//	public ContractServiceImpl(ContractDao contractDao, ContractMapper contractMapper) {
//		this.contractDao = contractDao;
//		this.contractMapper = contractMapper;
//	}
//
//	@Override
//	public List<ContractDto> getAll() {
//		List<Contract> allContracts = contractDao.findAll();
//		return contractMapper.convertToDtoList(allContracts);
//	}
//
//	@Override
//	public ContractDto getOne(Integer id) {
//		Contract contract = contractDao.getOne(id);
//		return contractMapper.convertToDto(contract);
//	}
//
//	@Override
//	public void save(ContractDto contractDto) {
//		Contract saveEntity = contractMapper.convertToEntity(contractDto);
//		contractDao.save(saveEntity);
//	}
//
//	@Override
//	public void delete(Integer id) {
//		contractDao.deleteById(id);
//	}
}
