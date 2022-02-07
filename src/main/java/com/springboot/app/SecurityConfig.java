package com.springboot.app;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		
		// {noop} => No operation for password encoder	(no password encoding needed)
		auth.inMemoryAuthentication().withUser("admin").password("{noop}admin").authorities("ADMIN");
		auth.inMemoryAuthentication().withUser("tincho").password("{noop}tincho").authorities("EMPLOYEE");
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		http.sessionManagement()
        .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
        .invalidSessionUrl("/login");
		
		//declares which Page(URL) will have What access type
		http
		.csrf().disable()
		.authorizeRequests()
		.antMatchers("/listar").authenticated()
		.antMatchers("/listar").hasAuthority("ADMIN")
		.antMatchers("/listarp").authenticated()
		.antMatchers("/listarp").hasAuthority("ADMIN")
		.antMatchers("/listaru").authenticated()
		.antMatchers("/listaru").hasAnyAuthority("ADMIN","EMPLOYEE")
		.antMatchers("/listarpla").authenticated()
		.antMatchers("/listarpla").hasAnyAuthority("ADMIN","EMPLOYEE")
		
	
		
		// Any other URLs which are not configured in above antMatchers
		// generally declared aunthenticated() in real time
		//.anyRequest().authenticated()
		
		//Login Form Details
		.and()
		.formLogin()
		.defaultSuccessUrl("/listarpla", true)
		
		//Logout Form Details
		.and()
		.logout()
		.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
				
		//Exception Details		
		.and()	
		.exceptionHandling()
		.accessDeniedPage("/accessDenied")
		;
	}
}
