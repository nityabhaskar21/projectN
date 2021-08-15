package com.projectN.app.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.projectN.app.filter.JwtRequestFilter;
import com.projectN.app.service.CustomUserDetailsService;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter{
	
	@Autowired
	private CustomUserDetailsService customUserDetailsService;
	
	@Autowired
	private JwtRequestFilter jwtRequestFilter;

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(customUserDetailsService);
		
	}
//	
//	@Override
//	public void configure(WebSecurity web) throws Exception {
//		// TODO Auto-generated method stub
//		
//	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			.cors().and()
			.csrf().disable()
			.authorizeRequests()
			.antMatchers(HttpMethod.POST, "/posts").hasAnyRole("USER", "SUPER_USER", "DEVELOPER", "ADMIN")
			.antMatchers(HttpMethod.GET, "/users/{username}").hasAnyRole("USER", "SUPER_USER", "DEVELOPER", "ADMIN")
			.antMatchers(HttpMethod.GET, "/users/{id}").hasAnyRole("USER", "SUPER_USER", "DEVELOPER", "ADMIN")
			.antMatchers(HttpMethod.PUT, "/posts/{id}").hasAnyRole("USER", "SUPER_USER", "DEVELOPER", "ADMIN")
			.antMatchers(HttpMethod.DELETE, "/posts/{id}").hasAnyRole("USER", "SUPER_USER", "DEVELOPER", "ADMIN")
			.antMatchers(HttpMethod.PUT, "/users/{id}").hasAnyRole("USER", "SUPER_USER", "DEVELOPER", "ADMIN")
			.antMatchers(HttpMethod.DELETE, "/users/{id}").hasAnyRole("DEVELOPER", "ADMIN")
			.antMatchers(HttpMethod.POST, "/authenticate").anonymous()
			.antMatchers(HttpMethod.POST, "/users").hasAnyRole("DEVELOPER", "ADMIN")
			.antMatchers(HttpMethod.POST, "/users").anonymous()
			.antMatchers(HttpMethod.GET, "/").hasAnyRole("DEVELOPER", "ADMIN")
			.antMatchers(HttpMethod.GET, "/posts/**", "/users").permitAll()
			.anyRequest().authenticated()
			.and().sessionManagement()
			.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
			;
		
		http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
			
	}

	@SuppressWarnings("deprecation")
	@Bean
	public PasswordEncoder passwordEncoder() {
		return NoOpPasswordEncoder.getInstance();
	}
	
	@Bean(name = BeanIds.AUTHENTICATION_MANAGER)
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}
