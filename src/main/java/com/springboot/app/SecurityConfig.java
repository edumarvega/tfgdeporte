package com.springboot.app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private UserDetailsService userDetailsService;
	
	@Bean
	public PasswordEncoder encoder() {
	    return new BCryptPasswordEncoder();
	}
	
	private static final String[] AUTH_WHITELIST = {
	        // -- Swagger UI v2
	        "/v2/api-docs",
	        "/swagger-resources",
	        "/swagger-resources/**",
	        "/configuration/ui",
	        "/configuration/security",
	        "/swagger-ui.html",
	        "/webjars/**",
	        // -- Swagger UI v3 (OpenAPI)
	        "/v3/api-docs/**",
	        "/swagger-ui/**",
	        // other public endpoints
	        "/h2-console/**",
	 };

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		
		// {noop} => No operation for password encoder	(no password encoding needed)
		//auth.inMemoryAuthentication().withUser("admin").password("{noop}admin").authorities("ADMIN");
		//auth.inMemoryAuthentication().withUser("tincho").password("{noop}tincho").authorities("USER");
		auth.userDetailsService(this.userDetailsService).passwordEncoder(encoder());
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		http.sessionManagement()
        .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
        .invalidSessionUrl("/login");
		
		http.authorizeRequests().antMatchers("/h2-console/**").permitAll()
        .and().csrf().ignoringAntMatchers("/h2-console/**")
        .and().headers().frameOptions().sameOrigin();
		
		http.authorizeRequests().antMatchers("/login/**").permitAll()
        .and().csrf().ignoringAntMatchers("/login/**")
        .and().headers().frameOptions().sameOrigin();
		
		//declares which Page(URL) will have What access type
		http
		.csrf().disable()
		.authorizeRequests()
		.antMatchers("/listar").authenticated()
		.antMatchers("/listar").hasAuthority("ADMIN")
		.antMatchers("/listarp").authenticated()
		.antMatchers("/listarp").hasAuthority("ADMIN")
		.antMatchers("/listaru").authenticated()
		.antMatchers("/listaru").hasAnyAuthority("ADMIN","USER")
		.antMatchers("/listarpla").authenticated()
		.antMatchers("/listarpla").hasAnyAuthority("ADMIN","USER")
		.antMatchers("/listar").authenticated()
		.antMatchers("/calendario").hasAuthority("ADMIN")
		
	
		
		// Any other URLs which are not configured in above antMatchers
		// generally declared aunthenticated() in real time
		//.anyRequest().authenticated()
		
		//Login Form Details
		.and()
		.formLogin()
		.loginPage("/login")
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
