package com.mkts.waac.api;

import com.mkts.waac.Dto.PersonDto;
import com.mkts.waac.Dto.UserDto;
import com.mkts.waac.services.UserService;
import com.mkts.waac.services.utils.ErrorDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;

@RestController
@RequestMapping(value = "api/user",
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE)
public class UserApi {


    private UserService userService;

    private ErrorDataService errorDataService;

    @Autowired
    public UserApi(UserService userService, ErrorDataService errorDataService) {
        this.userService = userService;
        this.errorDataService = errorDataService;
    }

    @PostMapping
    public ResponseEntity<Map<String, String>> addPerson(@Valid @RequestBody UserDto userDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            Map<String, String> errorMap = errorDataService.fillErrorMap(bindingResult);
            return new ResponseEntity<>(errorMap, HttpStatus.NON_AUTHORITATIVE_INFORMATION);
        } else {
            userService.save(userDto);
            return new ResponseEntity<>(null, HttpStatus.OK);
        }
    }

    @DeleteMapping(value = "/{userId}",
            consumes = MediaType.ALL_VALUE)
    public ResponseEntity<String> removePerson(@PathVariable Integer userId) {
        userService.delete(userId);
        return new ResponseEntity<>("Success", HttpStatus.OK);
    }

    @GetMapping(value = "/{userId}",
            consumes = MediaType.ALL_VALUE)
    public ResponseEntity<UserDto> getPerson(@PathVariable Integer userId) {
        UserDto userDto = userService.getOne(userId);
        return new ResponseEntity<>(userDto, HttpStatus.OK);
    }



}
