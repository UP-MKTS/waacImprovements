package com.mkts.waac.services.impl;

import com.mkts.waac.Dao.PersonDao;
import com.mkts.waac.Dto.PersonDto;
import com.mkts.waac.models.Person;
import com.mkts.waac.mappers.PersonMapper;
import com.mkts.waac.services.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class PersonServiceImpl implements PersonService {

	private PersonDao personDao;

	private PersonMapper personMapper;

	@Autowired
	public PersonServiceImpl(PersonDao personDao, PersonMapper personMapper) {
		this.personDao = personDao;
		this.personMapper = personMapper;
	}

	@Override
	public List<PersonDto> getAll() {
		List<Person> allPersons = personDao.findAll();
		return personMapper.convertToDtoList(allPersons);
	}

	@Override
	public PersonDto getOne(Integer id) {
		Person person = personDao.getOne(id);
		return personMapper.convertToDto(person);
	}

	@Override
	public void save(PersonDto personDto) {
		Person saveEntity = personMapper.convertToEntity(personDto);
		personDao.save(saveEntity);
	}

	@Override
	public void delete(Integer id) {
		personDao.deleteById(id);
	}
}
