
package com.edutech.progressive.config;

import com.edutech.progressive.jwt.JwtRequestFilter; 
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.*;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;
    private final JwtRequestFilter jwtRequestFilter;

    public SecurityConfig(UserDetailsService userDetailsService,
                          PasswordEncoder passwordEncoder,
                          JwtRequestFilter jwtRequestFilter) {
        this.userDetailsService = userDetailsService;
        this.passwordEncoder = passwordEncoder;
        this.jwtRequestFilter = jwtRequestFilter;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
            .userDetailsService(userDetailsService)
            .passwordEncoder(passwordEncoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .cors().and()
            .csrf().disable();

        http
            .sessionManagement()
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        http
            .authorizeRequests()

                .antMatchers("/user/login", "/user/register").permitAll()

                .antMatchers(HttpMethod.GET, "/student", "/student/**").permitAll()
                .antMatchers(HttpMethod.GET, "/course", "/course/**").hasAnyRole("STUDENT", "TEACHER")
                .antMatchers(HttpMethod.GET, "/course", "/course/**").hasAnyAuthority("ROLE_STUDENT","ROLE_TEACHER","STUDENT","TEACHER")

                .antMatchers(HttpMethod.POST, "/student", "/student/**").hasAnyRole("STUDENT")
                .antMatchers(HttpMethod.POST, "/student", "/student/**").hasAnyAuthority("ROLE_STUDENT","STUDENT")
                .antMatchers(HttpMethod.PUT, "/student", "/student/**").hasAnyRole("STUDENT")
                .antMatchers(HttpMethod.PUT, "/student", "/student/**").hasAnyAuthority("ROLE_STUDENT","STUDENT")
                .antMatchers(HttpMethod.DELETE, "/student", "/student/**").hasAnyRole("STUDENT")
                .antMatchers(HttpMethod.DELETE, "/student", "/student/**").hasAnyAuthority("ROLE_STUDENT","STUDENT")

                .antMatchers(HttpMethod.POST, "/course", "/course/**").hasAnyRole("TEACHER")
                .antMatchers(HttpMethod.POST, "/course", "/course/**").hasAnyAuthority("ROLE_TEACHER","TEACHER")
                .antMatchers(HttpMethod.PUT, "/course", "/course/**").hasAnyRole("TEACHER")
                .antMatchers(HttpMethod.PUT, "/course", "/course/**").hasAnyAuthority("ROLE_TEACHER","TEACHER")
                .antMatchers(HttpMethod.DELETE, "/course", "/course/**").hasAnyRole("TEACHER")
                .antMatchers(HttpMethod.DELETE, "/course", "/course/**").hasAnyAuthority("ROLE_TEACHER","TEACHER")

                .antMatchers("/teacher", "/teacher/**").hasAnyRole("TEACHER")
                .antMatchers("/teacher", "/teacher/**").hasAnyAuthority("ROLE_TEACHER","TEACHER")

                .antMatchers("/enrollment", "/enrollment/**").hasAnyRole("STUDENT")
                .antMatchers("/enrollment", "/enrollment/**").hasAnyAuthority("ROLE_STUDENT","STUDENT")
                .antMatchers("/attendance", "/attendance/**").hasAnyRole("TEACHER")
                .antMatchers("/attendance", "/attendance/**").hasAnyAuthority("ROLE_TEACHER","TEACHER")

                .anyRequest().authenticated();

        http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);

    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}