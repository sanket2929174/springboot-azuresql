package com.sanket.blog.blogappapis1.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
//import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.sanket.blog.blogappapis.security.CustomUserDetailService;
import com.sanket.blog.blogappapis.security.JwtAuthenticationEntryPoint;
import com.sanket.blog.blogappapis.security.JwtAuthenticationFilter;

@Configuration
@EnableWebMvc
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

  public static final String[] PUBLIC_URLS = { "/api/v1/auth/**",
      "/v3/api-docs", "/v2/api-docs", "/swagger-resources/**", "/swagger-ui/** ", "/webjars/**" };

  @Autowired
  private CustomUserDetailService customUserDetailService;

  @Autowired
  private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

  @Autowired
  private JwtAuthenticationFilter jwtAuthenticationFilter;

  protected void configure(HttpSecurity http) throws Exception {
    http.csrf().disable()
        .authorizeRequests()
        .antMatchers(PUBLIC_URLS).permitAll()
        .antMatchers(HttpMethod.GET).permitAll()
        .anyRequest()
        .authenticated()
        .and()
        .exceptionHandling()
        .authenticationEntryPoint(this.jwtAuthenticationEntryPoint)
        .and()
        .sessionManagement()
        .sessionCreationPolicy(SessionCreationPolicy.STATELESS);

    http.addFilterBefore(this.jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

  }

  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    auth.userDetailsService(this.customUserDetailService).passwordEncoder(passwordEncoder());
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  @Override
  public AuthenticationManager authenticationManagerBean() throws Exception {
    return super.authenticationManagerBean();
  }
  
  @Bean
  public FilterRegistrationBean coresFilter() {
    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    CorsConfiguration corsCfiguration = new CorsConfiguration();
    corsCfiguration.setAllowCredentials(true);
    corsCfiguration.addAllowedOriginPattern("*");
    corsCfiguration.addAllowedHeader("Authorization");
    corsCfiguration.addAllowedHeader("Content-Type");
    corsCfiguration.addAllowedHeader("Accept");
    corsCfiguration.addAllowedMethod("POST");
    corsCfiguration.addAllowedMethod("GET");
    corsCfiguration.addAllowedMethod("DELETE");
    corsCfiguration.addAllowedMethod("PUT");
    corsCfiguration.addAllowedMethod("OPTION");
    corsCfiguration.setMaxAge(3600L);

    source.registerCorsConfiguration("/**", corsCfiguration);
    FilterRegistrationBean bean = new FilterRegistrationBean(new CorsFilter(source));
    // Set the order to an appropriate value
    return bean;
  }
}

// package com.sanket.blog.blogappapis.config;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.boot.web.servlet.FilterRegistrationBean;
// import org.springframework.context.annotation.Bean;
// import org.springframework.context.annotation.Configuration;
// import org.springframework.http.HttpMethod;
// import org.springframework.security.authentication.AuthenticationManager;
// import
// org.springframework.security.authentication.dao.DaoAuthenticationProvider;
// import
// org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
// import
// org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
// import
// org.springframework.security.config.annotation.web.builders.HttpSecurity;
// import
// org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
// import org.springframework.security.config.http.SessionCreationPolicy;
// import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
// import org.springframework.security.crypto.password.PasswordEncoder;
// import org.springframework.security.web.DefaultSecurityFilterChain;
// import org.springframework.security.web.SecurityFilterChain;
// import
// org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
// import org.springframework.web.cors.CorsConfiguration;
// import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
// import org.springframework.web.filter.CorsFilter;
// import org.springframework.web.servlet.config.annotation.EnableWebMvc;

// import com.sanket.blog.blogappapis.security.CustomUserDetailService;
// import com.sanket.blog.blogappapis.security.JwtAuthenticationEntryPoint;
// import com.sanket.blog.blogappapis.security.JwtAuthenticationFilter;

// @Configuration
// @EnableWebSecurity
// @EnableWebMvc
// @EnableGlobalMethodSecurity(prePostEnabled = true)
// public class SecurityConfig {

// public static final String[] PUBLIC_URLS = { "/api/v1/auth/**",
// "/v3/api-docs" };

// @Autowired
// private CustomUserDetailService customUserDetailService;

// @Autowired
// private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

// @Autowired
// private JwtAuthenticationFilter jwtAuthenticationFilter;

// @Bean
// public SecurityFilterChain securityFilterChain(HttpSecurity http) throws
// Exception {
// http.csrf()
// .disable()
// .authorizeHttpRequests()
// .antMatchers(PUBLIC_URLS).permitAll()
// .antMatchers(HttpMethod.GET)
// .permitAll()
// .anyRequest()
// .authenticated()
// .and()
// .exceptionHandling().authenticationEntryPoint(this.jwtAuthenticationEntryPoint)
// .and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

// http.addFilterBefore(this.jwtAuthenticationFilter,
// UsernamePasswordAuthenticationFilter.class);
// http.authenticationProvider(daoAuthenticationProvider());
// DefaultSecurityFilterChain defaultSecurityFilterChain = http.build();
// return defaultSecurityFilterChain;
// }

// @Bean
// public DaoAuthenticationProvider daoAuthenticationProvider() {
// DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
// provider.setUserDetailsService(this.customUserDetailService);
// provider.setPasswordEncoder(passwordEncoder());
// return provider;
// }

// @Bean
// public PasswordEncoder passwordEncoder() {
// return new BCryptPasswordEncoder();
// }

// @Bean
// public AuthenticationManager
// authenticationManagerBean(AuthenticationConfiguration configuration) throws
// Exception {
// return configuration.getAuthenticationManager();
// }

// @Bean
// public FilterRegistrationBean coresFilter() {
// UrlBasedCorsConfigurationSource source = new
// UrlBasedCorsConfigurationSource();
// CorsConfiguration corsCfiguration = new CorsConfiguration();
// corsCfiguration.setAllowCredentials(true);
// corsCfiguration.addAllowedOriginPattern("*");
// corsCfiguration.addAllowedHeader("Authorization");
// corsCfiguration.addAllowedHeader("Content-Type");
// corsCfiguration.addAllowedHeader("Accept");
// corsCfiguration.addAllowedMethod("POST");
// corsCfiguration.addAllowedMethod("GET");
// corsCfiguration.addAllowedMethod("DELETE");
// corsCfiguration.addAllowedMethod("PUT");
// corsCfiguration.addAllowedMethod("OPTION");
// corsCfiguration.setMaxAge(3600L);

// source.registerCorsConfiguration("/**", corsCfiguration);
// FilterRegistrationBean bean = new FilterRegistrationBean(new
// CorsFilter(source));
// // Set the order to an appropriate value
// return bean;
// }
// }
