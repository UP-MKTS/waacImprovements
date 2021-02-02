package com.mkts.waac.Dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
public class UserDto {

    private Integer id;

    private String fio;

    private String userName;

    private String password;

    private String email;

    private Integer departmentId;

    private String departmentFullName;

    private String departmentShortName;

    private Boolean activated;

    private Integer roleId;

    private String roleName;

    private Boolean loginStatus;

    private String dateLogin;

    private String dateDestroy;
}
