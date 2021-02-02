package com.mkts.waac.services.impl;

import com.mkts.waac.Dao.DateDao;
import com.mkts.waac.Dto.DateDto;
import com.mkts.waac.models.Date;
//import com.mkts.waac.mappers.DateMapper;
import com.mkts.waac.services.DateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class DateServiceImpl implements DateService {
    @Override
    public List<DateDto> getAll() {
        return null;
    }

    @Override
    public DateDto getOne(Integer id) {
        return null;
    }

    @Override
    public void save(DateDto dateDto) {

    }

    @Override
    public void delete(Integer id) {

    }

//	private DateDao dateDao;
//
//	private DateMapper dateMapper;
//
//	@Autowired
//	public DateServiceImpl(DateDao dateDao, DateMapper dateMapper) {
//		this.dateDao = dateDao;
//		this.dateMapper = dateMapper;
//	}
//
//	@Override
//	public List<DateDto> getAll() {
//		List<Date> allDates = dateDao.findAll();
//		return dateMapper.convertToDtoList(allDates);
//	}
//
//	@Override
//	public DateDto getOne(Integer id) {
//		Date date = dateDao.getOne(id);
//		return dateMapper.convertToDto(date);
//	}
//
//	@Override
//	public void save(DateDto dateDto) {
//		Date saveEntity = dateMapper.convertToEntity(dateDto);
//		dateDao.save(saveEntity);
//	}
//
//	@Override
//	public void delete(Integer id) {
//		dateDao.deleteById(id);
//	}
}
