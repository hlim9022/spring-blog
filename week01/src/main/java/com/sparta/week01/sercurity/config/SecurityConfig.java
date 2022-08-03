package com.sparta.week01.sercurity.config;

import com.sparta.week01.repository.UserRepository;
import com.sparta.week01.sercurity.LoginSuccessHandler;
import com.sparta.week01.sercurity.filter.JwtAuthFilter;
import com.sparta.week01.sercurity.filter.LoginFilter;
import com.sparta.week01.sercurity.provider.LoginAuthProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.filter.CorsFilter;

@EnableWebSecurity(debug = true)
@Configuration
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final CorsFilter corsFilter;
    private final UserRepository userRepository;

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public LoginAuthProvider loginAuthProvider() {
        return new LoginAuthProvider(passwordEncoder());
    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) {
        auth
                .authenticationProvider(loginAuthProvider());
    }

    @Bean
    public LoginSuccessHandler formLoginSuccessHandler() {
        return new LoginSuccessHandler();
    }


    // /blog/members/login으로 접속시에 LoginFilter로 이동하도록하는 빈!
    @Bean
    public LoginFilter loginFilter() throws Exception {
        LoginFilter loginFilter = new LoginFilter(authenticationManager());
        loginFilter.setFilterProcessesUrl("/blog/members/login");
        loginFilter.setAuthenticationSuccessHandler(formLoginSuccessHandler());
        loginFilter.afterPropertiesSet();
        return loginFilter;
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    public void configure(WebSecurity web) {
        // h2-console 사용에 대한 허용 (CSRF, FrameOptions 무시)
        web
                .ignoring()
                .antMatchers("/h2-console/**");
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .addFilter(corsFilter)
                .addFilterBefore(loginFilter(), UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(new JwtAuthFilter(authenticationManager(),userRepository), UsernamePasswordAuthenticationFilter.class)
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
                .formLogin().disable()
                .httpBasic().disable()
                .authorizeRequests((authz)->authz
                        .antMatchers("/blog/auth/**").authenticated()
                        .anyRequest().permitAll());
    }

}
