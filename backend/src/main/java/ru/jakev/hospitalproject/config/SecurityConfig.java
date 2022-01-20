package ru.jakev.hospitalproject.config;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Value("${security.enable-csrf}")
    private boolean csrfEnabled;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and()
                .authorizeRequests()
                .antMatchers("/hospitals/*/doctors/*/appointments").authenticated()
                .antMatchers("/doctors/*/appointments/*/*").authenticated()
                .antMatchers("/doctors*").authenticated()
                .antMatchers("/patients/*/appointments").authenticated()
                .and()
                .oauth2ResourceServer()
                .jwt().and();
        if (!csrfEnabled) {
            http.csrf().disable();
        }
    }
}

