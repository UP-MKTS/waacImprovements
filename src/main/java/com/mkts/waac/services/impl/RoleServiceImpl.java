package com.mkts.waac.services.impl;

import com.mkts.waac.Dao.RoleDao;
import com.mkts.waac.Dto.RoleDto;
import com.mkts.waac.models.Role;
import com.mkts.waac.mappers.RoleMapper;
import com.mkts.waac.services.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class RoleServiceImpl implements RoleService {

    private RoleDao roleDao;

    private RoleMapper roleMapper;

    @Autowired
    public RoleServiceImpl(RoleDao roleDao, RoleMapper roleMapper) {
        this.roleDao = roleDao;
        this.roleMapper = roleMapper;
    }

    @Override
    public List<RoleDto> getAll() {
        List<Role> allRole = roleDao.findAll();
        return roleMapper.convertToDtoList(allRole);
    }

    @Override
    public RoleDto getOne(Integer id) {
        Role role = roleDao.getOne(id);
        return roleMapper.convertToDto(role);
    }

}
