package com.mkts.waac.security.services.impl;

import com.mkts.waac.security.dto.SignedInAccountDto;
import com.mkts.waac.security.services.SecurityService;
import com.mkts.waac.Dto.UserDto;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Random;

@Service
@Transactional
public class SecurityServiceImpl implements SecurityService {

    public static final int DEFAULT_PASSWORD_LENGTH = 11;

    private static String SOURCE_PASSWORD_CHARS = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabsdefghijklmnopqrstuvwxyz!@#$%^&*()_+{}:;|?/>.<,";

    @Override
    public boolean isUserSignedIn() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            return false;
        }

        Object principal = authentication.getPrincipal();
        if (principal.getClass() != UserDto.class) {
            return false;
        }

        return true;
    }

    @Override
    public UserDto registerNewUser(UserDto registration) {
        throw new UnsupportedOperationException();
    }

    @Override
    public String generateRandomPassword() {
        return generateRandomPassword(DEFAULT_PASSWORD_LENGTH);
    }

    @Override
    public String generateRandomPassword(int passwordLength) {
        Random random = null;
        try {
            random = SecureRandom.getInstance("SHA1PRNG");
        } catch (NoSuchAlgorithmException e) {
            random = new Random();
        }

        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < passwordLength; i++) {
            builder.append(SOURCE_PASSWORD_CHARS.charAt(random.nextInt(SOURCE_PASSWORD_CHARS.length())));
        }

        return builder.toString();
    }

    @Override
    public String encodePassword(String rawPassword) {
        String encodedPassword = getPasswordEncoder().encode(rawPassword);

        return encodedPassword;
    }

    @Override
    public boolean verifyPassword(SignedInAccountDto signedInAccount, String passwordCandidate) {
        String storadgeUserPassword = signedInAccount.getPassword();

        return getPasswordEncoder().matches(passwordCandidate, storadgeUserPassword);
    }

    @Override
	public SignedInAccountDto getSignedInAccount() {
    	Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    	SignedInAccountDto signedInAccount = (SignedInAccountDto) authentication.getPrincipal();

    	return signedInAccount;
	}

	private PasswordEncoder getPasswordEncoder() {
        PasswordEncoder encoder = new BCryptPasswordEncoder();
        return encoder;
    }
}
