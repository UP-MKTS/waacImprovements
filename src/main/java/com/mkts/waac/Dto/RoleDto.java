package com.mkts.waac.Dto;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RoleDto {

    private Integer id;

    private String name;

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (obj == null) {
            return false;
        }

        if (getClass() != obj.getClass()) {
            return false;
        }

        RoleDto other = (RoleDto) obj;
        if (name == null) {
            if (other.name != null) {
                return false;
            }
        } else if (!name.equals(other.name)) {
            return false;
        }

        return true;
    }
}
