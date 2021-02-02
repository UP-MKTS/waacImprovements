package com.mkts.waac.services;

import com.mkts.waac.Dto.RoleDto;

import java.util.List;

public interface RoleService {

    List<RoleDto> getAll();

    RoleDto getOne(Integer id);
}
