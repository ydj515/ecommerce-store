package org.example.userservice.config.security;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.userservice.config.security.provider.TokenProvider;
import org.example.userservice.filter.LoginAuthenticationFilter;
import org.example.userservice.filter.security.AuthFilter;
import org.example.userservice.service.member.MemberService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@Slf4j
public class SecurityConfig {

    private final TokenProvider tokenProvider;
    private final CustomAuthenticationManager customAuthenticationManager;
    private final MemberService memberService;

    // Resource 대상
    public static final String[] RESOURCE_LIST = {
            "/css/**", "/fonts/**", "/images/**", "/js/**", "/modules/**", "/h2-console/**", "/swagger-ui/**"
    };

    // 권한 제외 대상
    public static final String[] PERMITTED_LIST = {
            "/", "/login", "/logout", "/swagger-ui/**", "/swagger-ui", "/swagger/**", "/v3/api-docs/**"
    };

    // api white list
    private static final String[] API_WHITE_LIST = {
            "/actuator/**",
            "/users/**",
            "/auth/**"
    };

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .cors(AbstractHttpConfigurer::disable)
                .formLogin(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)
                .headers(config -> config.frameOptions(HeadersConfigurer.FrameOptionsConfig::disable).disable())
                .sessionManagement(config -> config.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> {
                    try {
                        auth
                                .requestMatchers(RESOURCE_LIST).permitAll()
                                .requestMatchers(PERMITTED_LIST).permitAll()
                                .requestMatchers(API_WHITE_LIST).permitAll()
                                .anyRequest().authenticated()
//                                .anyRequest().permitAll() // 로그인 하지 않고 모두 권한을 가짐
                        ;
                    } catch (Exception e) {
                        log.error(e.toString());
                    }
                })

                .addFilter(getLoginAuthenticationFilter())
//                .addFilterBefore(new AuthFilter(tokenProvider), BasicAuthenticationFilter.class)
                .getOrBuild();
    }

    private LoginAuthenticationFilter getLoginAuthenticationFilter() {
        return new LoginAuthenticationFilter(memberService, customAuthenticationManager, tokenProvider);
    }
//    @Bean
//    public BCryptPasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }

}
