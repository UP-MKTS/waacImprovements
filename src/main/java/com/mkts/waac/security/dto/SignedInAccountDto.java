package com.mkts.waac.security.dto;

import com.mkts.waac.Dto.UserDto;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class SignedInAccountDto implements UserDetails {

    private static final long serialVersionUID = 1L;

    private UserDto user;

    private List<GrantedAuthorityDto> authorities;

    public SignedInAccountDto(UserDto user, Collection<GrantedAuthorityDto> authorities) {
        super();
        this.user = user;
        this.authorities = new ArrayList<>(authorities);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    public GrantedAuthority getPrimaryAuthority() {
    	return authorities.get(0);
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUserName();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return user.getActivated();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
    
    public Integer getDepartmentId() {
    	return user.getDepartmentId();
	}
    
    public boolean isEditingAllowed() {
    	String role = getPrimaryAuthority().getAuthority();
    	if ("root_admin".equals(role) || "admin".equals(role) || "editor".equals(role)) {
    		return true;
    	}
    	
    	return false;
    }
}
