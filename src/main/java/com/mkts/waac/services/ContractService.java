package com.mkts.waac.services;

import com.mkts.waac.Dto.ContractDto;

import java.util.List;

public interface ContractService {

    List<ContractDto> getAll();

	ContractDto getOne(Integer id);

	void save(ContractDto contractDto);

	void delete(Integer id);
}
