package com.boot.tsamo.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import static org.springframework.boot.autoconfigure.security.servlet.PathRequest.toH2Console;

@Configuration
@EnableWebSecurity
public class SecurityConfig   {

/*    @Autowired
    UserService userService;*/

    @Bean
    public WebSecurityCustomizer configure() {
        return (web) -> web.ignoring()
                .requestMatchers(toH2Console())
                .requestMatchers("/static/**");
    }


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http.formLogin((formLogin) ->
                        formLogin
                                .loginPage("/users/login")
                                .defaultSuccessUrl("/main")
                                .usernameParameter("id")
                                .passwordParameter("password")
                                .passwordParameter("password")
                                .failureUrl("/users/login/error").permitAll())

                .logout((logoutCoinfig) ->

                        logoutCoinfig
                                .logoutRequestMatcher(new AntPathRequestMatcher("/users/logout"))
                                .logoutSuccessUrl("/main"))


                .authorizeRequests((authorizeRequests) ->
                        authorizeRequests
                                .requestMatchers("/css/**", "/js/**", "/Img/**").permitAll()
                                .requestMatchers("/main").permitAll()
                                .requestMatchers("/admin/**").hasRole("ADMIN")
                                .anyRequest().authenticated()

                );

        return http.build();
    }




    @Bean
    public static BCryptPasswordEncoder passwordEncoder() {

        return new BCryptPasswordEncoder();
    }



}
