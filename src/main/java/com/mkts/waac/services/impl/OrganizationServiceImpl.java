package com.mkts.waac.services.impl;

import com.mkts.waac.Dao.OrganizationDao;
import com.mkts.waac.Dto.OrganizationDto;
import com.mkts.waac.mappers.OrganizationMapper;
import com.mkts.waac.models.Organization;
//import com.mkts.waac.mappers.OrganizationMapper;
import com.mkts.waac.services.OrganizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class OrganizationServiceImpl implements OrganizationService {
    private OrganizationDao organizationDao;

    private OrganizationMapper organizationMapper;

    @Autowired
    public OrganizationServiceImpl(OrganizationDao organizationDao, OrganizationMapper organizationMapper) {
        this.organizationDao = organizationDao;
        this.organizationMapper = organizationMapper;
    }

    @Override
    public List<OrganizationDto> getAll() {
        List<Organization> allOrganization = organizationDao.findAll();
        return organizationMapper.convertToDtoList(allOrganization);
    }

    @Override
    public OrganizationDto getOne(Integer id) {
        Organization organization = organizationDao.getOne(id);
        return organizationMapper.convertToDto(organization);
    }

    @Override
    public void save(OrganizationDto organizationDto) {
        Organization saveEntity = organizationMapper.convertToEntity(organizationDto);
        organizationDao.save(saveEntity);
    }

    @Override
    public void delete(Integer id) {
        organizationDao.deleteById(id);
    }

}
