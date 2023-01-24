package com.example.udemy.configs;

import com.example.udemy.filters.CustomAuthenticationFilter;
import com.example.udemy.filters.CustomAuthorizationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;

import java.util.Arrays;
import java.util.List;


@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserDetailsService userDetailsService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.cors().configurationSource(request -> {
            var cors = new CorsConfiguration();
            cors.setAllowedOrigins(List.of("http://localhost:3000", "http://127.0.0.1:80"));
            cors.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
            cors.setAllowedHeaders(List.of("*"));
            cors.setExposedHeaders(Arrays.asList("Access-Control-Allow-Headers", "Authorization, Access-Control-Allow-Headers, Origin, Accept, X-Requested-With, " +
                    "Content-Type, Access-Control-Request-Method, Access-Control-Request-Headers, access_token, refresh_token"));
            return cors;
        }).and();
        http.authorizeRequests().antMatchers("/signup/**").permitAll();
        http.authorizeRequests(authorize -> authorize
                .antMatchers("/,/login,/logout,/api/token/refresh/**").permitAll()
                .anyRequest().authenticated());
//        http.logout().logoutUrl("/logout").logoutSuccessUrl("/").and();
//        http.formLogin().loginPage("/login").loginProcessingUrl("/login").defaultSuccessUrl("/").failureUrl("/login?error");

        http.addFilter(new CustomAuthenticationFilter(authenticationManagerBean()));
        http.addFilterBefore(new CustomAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class);
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    //    @Bean
//    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//        http.csrf().disable();
//        http.cors().configurationSource(request -> {
//            var cors = new CorsConfiguration();
//            cors.setAllowedOrigins(List.of("http://localhost:3000", "http://127.0.0.1:80"));
//            cors.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
//            cors.setAllowedHeaders(List.of("*"));
//            cors.setExposedHeaders(Arrays.asList("Access-Control-Allow-Headers", "Authorization, Access-Control-Allow-Headers, Origin, Accept, X-Requested-With, " +
//                    "Content-Type, Access-Control-Request-Method, Access-Control-Request-Headers, access_token, refresh_token"));
//            return cors;
//        }).and();
//        http.authorizeHttpRequests(authorize -> authorize
//                .antMatchers("/,/login,/signup").permitAll()
//                .anyRequest().authenticated())
//                .logout().logoutUrl("/logout").logoutSuccessUrl("/").and()
//                .formLogin().loginPage("/login").loginProcessingUrl("/login").defaultSuccessUrl("/").failureUrl("/login?error");
//
//        return http.build();
//
//    }
}
