package com.example.apidemo.config;

import com.example.apidemo.security.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.filter.CorsFilter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

@EnableWebSecurity
@Slf4j
@RequiredArgsConstructor
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    @Override
    protected void configure(HttpSecurity http) throws Exception  {

        // 인증 API
        http
                .cors() // WebMvcConfig에서 이미 설정되었으므로 기본 cors 설정

       .and()
                .csrf() // csrf 현재 사용 안함
                    .disable()
                .httpBasic() // 토큰을 사용하므로 basic 사용 안함
                    .disable()
                .sessionManagement()
                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS) // JWT는 항상 STATELESS여야함 ( 세션기반이 아님을 선언)

      // 인가 API
      .and()

                // /와 /auth/** 경로 외에 모든 경로 인증해야함
                .authorizeRequests()
                    .antMatchers("/", "/auth/**").permitAll()
                .anyRequest()
                    .authenticated()
        ;


        http

                // filter 순서 정하기
                // CorsFilter를 실행한 후 jwtAuthenticationFilter 실행
                .addFilterAfter(
                    jwtAuthenticationFilter,
                    CorsFilter.class
                );
    }
}
