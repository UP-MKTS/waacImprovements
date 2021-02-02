package com.mkts.waac.security.config;

import com.mkts.waac.security.dto.GrantedAuthorityDto;
import com.mkts.waac.security.dto.SignedInAccountDto;
import com.mkts.waac.security.services.SecurityService;
import com.mkts.waac.Dto.RoleDto;
import com.mkts.waac.Dto.UserDto;
import com.mkts.waac.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
@Transactional
public class WaacAuthenticationProvider implements AuthenticationProvider {

	@Autowired
	private UserService userService;

	@Autowired
	private SecurityService securityService;

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		SignedInAccountDto signedInAccount = null;
		try {
			signedInAccount = createSignedInAccount(authentication);
		} catch (Exception e) {
			throw new BadCredentialsException("Email или пароль введены неверно!");
		}

		if (signedInAccount == null) {
			throw new BadCredentialsException("Email или пароль введены неверно!");
		}

		String rawPassword = (String) authentication.getCredentials();
		if (!securityService.verifyPassword(signedInAccount, rawPassword)) {
			throw new BadCredentialsException("Email или пароль введены неверно!");
		}

		if (!signedInAccount.isAccountNonLocked()) {
			throw new LockedException("Аккаунт заблокирован!<br>Пожалуйста, обратитесь в службу поддержки", new Exception());
		}

		return new UsernamePasswordAuthenticationToken(signedInAccount, signedInAccount.getPassword(), signedInAccount.getAuthorities());
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return true;
	}

	private SignedInAccountDto createSignedInAccount(Authentication authentication) {
		String login = authentication.getName();

		UserDto user = userService.getUserByLogin(login);
		List<RoleDto> roles = userService.getRolesForUser(user);
		Collection<GrantedAuthorityDto> authorities = getUserAuthorities(roles);

		SignedInAccountDto signedInAccount = new SignedInAccountDto(user, authorities);

		return signedInAccount;
	}

	private Collection<GrantedAuthorityDto> getUserAuthorities(List<RoleDto> roles) {
		Collection<GrantedAuthorityDto> authorities = new ArrayList<>();

		for (RoleDto role : roles) {
			GrantedAuthorityDto bean = new GrantedAuthorityDto(role.getName());
			authorities.add(bean);
		}

		return authorities;
	}
}
