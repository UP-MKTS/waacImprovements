package com.mkts.waac.security.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class WaacWebSecurityConfiguration extends WebSecurityConfigurerAdapter {

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable();

		http
				.authorizeRequests()
				.antMatchers("/",
						"/logout",
						"/scripts/**",
						"/styles/**",
						"/images/**")
				.permitAll()
				.antMatchers("/catalog/**", "/accomp-passp", "/accomp-passp-journal").hasAnyAuthority("admin", "editor","user", "supervisor")
				.anyRequest().authenticated()
				.and()
				.formLogin()
				.loginPage("/login")
				.successForwardUrl("/authorization")
				.failureUrl("/404")
				.permitAll()
				.and()
				.logout()
				.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
				.logoutSuccessUrl("/login")
				.invalidateHttpSession(true);
	}
}
