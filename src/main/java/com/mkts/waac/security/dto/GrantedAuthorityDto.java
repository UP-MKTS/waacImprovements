package com.mkts.waac.security.dto;

import org.springframework.security.core.GrantedAuthority;

public class GrantedAuthorityDto implements GrantedAuthority {

	private static final long serialVersionUID = 1L;

	private String role;

	public GrantedAuthorityDto(String role) {
		this.role = role;
	}

	@Override
	public String getAuthority() {
		return role;
	}
}
