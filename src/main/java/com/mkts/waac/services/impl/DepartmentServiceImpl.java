package com.mkts.waac.services.impl;

import com.mkts.waac.Dao.DepartmentDao;
import com.mkts.waac.Dto.DepartmentDto;
import com.mkts.waac.models.Department;
import com.mkts.waac.mappers.DepartmentMapper;
import com.mkts.waac.services.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class DepartmentServiceImpl implements DepartmentService {

    @Autowired
    private DepartmentDao departmentDao;

    @Autowired
    private DepartmentMapper departmentMapper;

    @Override
    public List<DepartmentDto> getAll(String sortFieldName) {
        List<Department> allDepartment = departmentDao.findAll(Sort.by(sortFieldName));
        return departmentMapper.convertToDtoList(allDepartment);
    }

    @Override
    public void save(DepartmentDto departmentDto) {
        Department department = departmentMapper.convertToEntity(departmentDto);
        departmentDao.save(department);
    }

    @Override
    public void delete(Integer id) {
        departmentDao.deleteById(id);
    }

    @Override
    public DepartmentDto getOne(Integer id) {
        Department department = departmentDao.getOne(id);
        return departmentMapper.convertToDto(department);
    }


	/*@Override
	public List<DepartmentBean> getAllDepartmentByRts() {
		List<Department> allDepartmentsForSource = departmentDao.findAllByRts(true);
		List<DepartmentBean> departmentBeans = converter.convertToBeanList(allDepartmentsForSource, DepartmentBean.class);

		return departmentBeans;
	}

	@Override
	public void saveAllDepartments(List<DepartmentBean> addList) {
		List<Department> addListEntity = converter.convertToEntityList(addList, Department.class);
		departmentDao.saveAll(addListEntity);
	}

	@Override
	public void deleteDepartment(DepartmentBean delDepartment) {
		Department delDepartmentEntity = converter.convertToEntity(delDepartment, Department.class);
		departmentDao.delete(delDepartmentEntity);
	}*/
}
