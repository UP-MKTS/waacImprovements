package com.mkts.waac.services;


import com.mkts.waac.Dto.DepartmentDto;

import java.util.List;

public interface DepartmentService {

    List<DepartmentDto> getAll(String sortFieldName);

    DepartmentDto getOne(Integer id);

    void save(DepartmentDto departmentDto);

    void delete(Integer id);
}
