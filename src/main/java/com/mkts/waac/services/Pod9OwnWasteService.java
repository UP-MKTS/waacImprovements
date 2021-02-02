package com.mkts.waac.services;

//import com.mkts.waac.Dto.Pod10Dto;
//import com.mkts.waac.Dto.Pod9OwnWasteDto;
//import com.mkts.waac.Dto.Pod9WasteDto;

import com.mkts.waac.Dto.Pod9OwnWasteDto;
import com.mkts.waac.Dto.Pod9SaveDto;
import com.mkts.waac.models.AccompPasspWaste;
import com.mkts.waac.models.Pod9OwnWaste;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface Pod9OwnWasteService {

    void save(AccompPasspWaste accompPasspWaste);

    void deleteByAccopmPasspWasteId(Integer accompPasspWasteId);

    List<Pod9OwnWasteDto> getAllByWasteTypeId(Integer wasteType);

    List<Pod9OwnWaste> getAll();

    List<Pod9OwnWaste> getAllByDate(String month, String year);

    String download(Integer departmentId) throws IOException;

    void test(Integer wasteId, String year);

    void save(Pod9OwnWaste pod9OwnWaste);

    void save(Pod9SaveDto pod9SaveDto);

    void update(Pod9SaveDto pod9SaveDto);

    Pod9OwnWaste getBuAccompPasspWasteId(Integer accompPasspWasteId);

    Pod9SaveDto getOneSave(Integer pod9Id);

    Pod9OwnWaste getOne(Integer id);

    void delete(Integer id);

    Pod9OwnWasteDto toDto(Pod9OwnWaste pod9OwnWaste);

    String downloadSved() throws IOException;

    List<Pod9OwnWaste> getPod9ByByMonthAndYear(String month, String year);
}
