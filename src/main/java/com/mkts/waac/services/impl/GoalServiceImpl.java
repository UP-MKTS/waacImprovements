package com.mkts.waac.services.impl;

import com.mkts.waac.Dao.GoalDao;
import com.mkts.waac.Dto.GoalDto;
import com.mkts.waac.mappers.GoalMapper;
import com.mkts.waac.models.Goal;
//import com.mkts.waac.mappers.GoalMapper;
import com.mkts.waac.services.GoalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class GoalServiceImpl implements GoalService {

	private GoalDao goalDao;

	private GoalMapper goalMapper;

	@Autowired
	public GoalServiceImpl(GoalDao goalDao, GoalMapper goalMapper) {
		this.goalDao = goalDao;
		this.goalMapper = goalMapper;
	}

	@Override
	public List<GoalDto> getAll() {
		List<Goal> allGoals = goalDao.findAll();
		return goalMapper.convertToDtoList(allGoals);
	}

	@Override
	public GoalDto getOne(Integer id) {
		Goal goal = goalDao.getOne(id);
		return goalMapper.convertToDto(goal);
	}

	@Override
	public void save(GoalDto goalDto) {
		Goal saveEntity = goalMapper.convertToEntity(goalDto);
		goalDao.save(saveEntity);
	}

	@Override
	public void delete(Integer id) {
		goalDao.deleteById(id);
	}

	@Override
	public Integer getFirstId() {
		return goalDao.findFirst1ByOrderById().getId();
	}

	@Override
	public GoalDto getFirst() {
		return goalMapper.convertToDto(goalDao.findFirst1ByOrderById());
	}
}
