package com.boot.tsamo.config;


import com.boot.tsamo.config.oauth.MyOAuth2UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {


/*    @Autowired
    UserService userService;*/

    private final MyOAuth2UserService myOAuth2UserService;

    @Bean
    public WebSecurityCustomizer configure() {
        return (web) -> web.ignoring()
                //.requestMatchers(toH2Console())
                .requestMatchers("/static/**");
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http.csrf(AbstractHttpConfigurer::disable);
        HttpSessionRequestCache requestCache = new HttpSessionRequestCache();
        requestCache.setMatchingRequestParameterName("null");

        http.requestCache((cache) -> cache
                .requestCache(requestCache));

        http.formLogin((formLogin) ->
                        formLogin
                                .loginPage("/user/login")
                                .defaultSuccessUrl("/")
                                .usernameParameter("id")
                                .passwordParameter("password")
                                .defaultSuccessUrl("/").permitAll()
                                .failureUrl("/user/login/error").permitAll())

                .logout((logoutConfig) ->
                        logoutConfig
                                .logoutRequestMatcher(new AntPathRequestMatcher("/user/logout"))
                                .logoutSuccessUrl("/user/logout/1"))

                .authorizeHttpRequests((authorizeRequests) ->
                        authorizeRequests
                                .requestMatchers("/admin/**").hasRole("ADMIN")
                                .requestMatchers("/downloadExcel").hasRole("ADMIN") // 엑셀 다운로드는 ADMIN 권한만 허용
                                .requestMatchers("/css/**", "/js/**", "/img/**").permitAll()
                                .requestMatchers("/main","/","/user/**","/createBoard","/BoardDetailView","/reply","/posts","/createBoard","createBoardRequest"
                                        ,"/api/addComment" ,"/attachFile", "/board/**", "/**","/error").permitAll()
                                .anyRequest().authenticated()

                )

                .oauth2Login(oauth2 -> oauth2
                        .redirectionEndpoint(endpoint -> endpoint.baseUri("/auth/kakao/*"))
                        .userInfoEndpoint(endpoint -> endpoint.userService(myOAuth2UserService))
                        .defaultSuccessUrl("/")
                        .failureHandler(new SimpleUrlAuthenticationFailureHandler("/user/login/error")));

        return http.build();
    }


    @Bean
    public static BCryptPasswordEncoder passwordEncoder() {

        return new BCryptPasswordEncoder();
    }


}
