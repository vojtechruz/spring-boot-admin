package com.vojtechruzicka.springbootadminserver;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        SavedRequestAwareAuthenticationSuccessHandler successHandler = new SavedRequestAwareAuthenticationSuccessHandler();
        successHandler.setTargetUrlParameter("redirectTo");
        successHandler.setDefaultTargetUrl("/");

        http.formLogin().loginPage("/login.html").loginProcessingUrl("/login").permitAll();
        http.logout().logoutUrl("/logout");
        // Login page and static assets should not be protected
        http.authorizeRequests()
            .antMatchers("/login.html", "/**/*.css", "/img/**", "/third-party/**")
            .permitAll();
        // Everything else requires authentication
        http.authorizeRequests()
            .antMatchers("/**")
            .authenticated();
        http.httpBasic();
        // 1.x does not support csrf
        http.csrf().disable();
    }
}