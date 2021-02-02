package com.mkts.waac.services;

import com.mkts.waac.Dto.RoleDto;
import com.mkts.waac.Dto.UserDto;
import org.springframework.validation.BindingResult;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public interface UserService {

	/*boolean isEmailAlreadyExists(String email, Integer id);

	boolean isLoginAlreadyExists(String email, Integer id);*/

    UserDto getUserByLogin(String login);

    List<RoleDto> getRolesForUser(UserDto user);

    List<UserDto> getAll();

    UserDto getOne(Integer id);

    void save(UserDto userBean);

    void delete(Integer id);

    //Map<String, String> createErrorMap(BindingResult bindingResult);

    String encodePassword (String password);

    void userLogIn (String userName, LocalDateTime dateLogin);

    void userLogOut (String userName, LocalDateTime dateDestroy);
}
