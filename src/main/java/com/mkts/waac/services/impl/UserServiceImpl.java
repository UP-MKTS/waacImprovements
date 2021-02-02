package com.mkts.waac.services.impl;

import com.mkts.waac.Dao.UserDao;
import com.mkts.waac.Dto.RoleDto;
import com.mkts.waac.Dto.UserDto;
import com.mkts.waac.models.Role;
import com.mkts.waac.models.User;
import com.mkts.waac.mappers.RoleMapper;
import com.mkts.waac.mappers.UserMapper;
import com.mkts.waac.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    private static final DateTimeFormatter DATEFORMAT = DateTimeFormatter.ofPattern("dd.MM.yyyy (HH:mm)");

    @Autowired
    private UserDao userDao;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private RoleMapper roleMapper;

	/*@Override
	public boolean isEmailAlreadyExists(String email, Integer id) {
		if (StringUtils.isBlank(email)) {
			return false;
		}

		User existingUser = userDao.findByEmail(email);
		if (existingUser == null) {
			return false;
		} else {
			return !existingUser.getId().equals(id);
		}
	}

	@Override
	public boolean isLoginAlreadyExists(String login, Integer id) {
		if (StringUtils.isBlank(login)) {
			return false;
		}

		User existingUser = userDao.findByUserName(login);
		if (existingUser == null) {
			return false;
		} else {
			return !existingUser.getId().equals(id);
		}
	}*/

    @Override
    public UserDto getUserByLogin(String login) {
        User user = userDao.findByUserName(login);

        return userMapper.convertToDto(user);
    }

	/*@Override
	public Map<String, String> createErrorMap(BindingResult bindingResult) {
		Map<String, String> errorMsg = new HashMap<>();
		for (FieldError error : bindingResult.getFieldErrors()) {
			errorMsg.put(error.getField(), error.getDefaultMessage());
		}

		return errorMsg;
	}*/

    @Override
    public List<RoleDto> getRolesForUser(UserDto user) {
        User userEntity = userDao.getOne(user.getId());
        Role role = userEntity.getRole();
        RoleDto roleDto = roleMapper.convertToDto(role);

        List<RoleDto> roleDtos = new ArrayList<>();

        roleDtos.add(roleDto);

        return roleDtos;
    }

    @Override
    public List<UserDto> getAll() {
        List<User> allUsers = userDao.findAll();

		/*for (UserDto userDto : userBeans) {
			String dateLogin = userDto.getDateLogin();
			if (dateLogin != null) {
				LocalDateTime date = LocalDateTime.parse(dateLogin);
				userDto.setDateLogin(date.format(DATEFORMAT));
			}

			String dateDestroy = userDto.getDateDestroy();
			if (dateDestroy != null) {
				LocalDateTime date = LocalDateTime.parse(dateDestroy);
				userDto.setDateDestroy(date.format(DATEFORMAT));
			}
		}*/

        //return UserMapper.MAPPER.toUserDtoList(allUsers);
        return userMapper.convertToDtoList(allUsers);
    }

    @Override
    public UserDto getOne(Integer id) {
        User user = userDao.getOne(id);

        return userMapper.convertToDto(user);
    }

    private PasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    public String encodePassword(String rawPassword) {

        return getPasswordEncoder().encode(rawPassword);
    }

    @Override
    public void userLogIn(String userName, LocalDateTime dateLogin) {
        User user = userDao.findByUserName(userName);
        user.setLoginStatus(true);
        user.setDateLogin(dateLogin);
        userDao.save(user);
    }

    @Override
    public void userLogOut(String userName, LocalDateTime dateDestroy) {
        User user = userDao.findByUserName(userName);
        user.setLoginStatus(false);
        user.setDateDestroy(dateDestroy);
        userDao.save(user);
    }

    @Override
    public void save(UserDto userDto) {
        UserDto currentUser;
        if (userDto.getId() == null) {
            String encodePassword = encodePassword(userDto.getPassword());
            userDto.setPassword(encodePassword);
        } else {
            currentUser = getOne(userDto.getId());
            if (!currentUser.getPassword().equals(userDto.getPassword())) {
                String encodePassword = encodePassword(userDto.getPassword());
                userDto.setPassword(encodePassword);
            }
        }
        userDto.setLoginStatus(false);
        User saveEntity = userMapper.convertToEntity(userDto);
        userDao.save(saveEntity);
    }

    @Override
    public void delete(Integer id) {
        userDao.deleteById(id);
    }
}
