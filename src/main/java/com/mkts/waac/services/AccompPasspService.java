package com.mkts.waac.services;

import com.mkts.waac.Dto.*;
import com.mkts.waac.models.AccompPassp;

import java.util.List;

public interface AccompPasspService {

    List<AccompPasspDto> getAll();

    AccompPasspDto getOne(Integer accompPasspId);

    AccompPasspDetailsDto getDetailsById(Integer id);

    Integer getNextNumber();

    void save(AccompPasspDto accompPasspDto);

    void saveUpdete(AccompPasspSaveDto accompPasspSaveDto);

    void newAccopmPassp(AccompPasspSaveDto accompPasspSaveDto);

    List<String> getYears ();

    List<MonthDto> getMonth ();

    void saveWeightAndGoal(Integer accompPasspWaste, Integer goalId, double weight);

    List<AccompPasspJournalDto> getAllByYearAndMonth(String year, String month);

    void delete(Integer id);

}
