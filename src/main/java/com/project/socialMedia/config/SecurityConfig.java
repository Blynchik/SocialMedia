package com.project.socialMedia.config;

import com.project.socialMedia.exception.AppUserNotFoundException;
import com.project.socialMedia.model.user.AppUser;
import com.project.socialMedia.model.user.AuthUser;
import com.project.socialMedia.service.AppUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.Optional;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final AppUserService appUserService;
    public static final PasswordEncoder PASSWORD_ENCODER = PasswordEncoderFactories.createDelegatingPasswordEncoder();
    private final JWTFilter jwtFilter;

    @Autowired
    public SecurityConfig(AppUserService userService,
                          JWTFilter jwtFilter) {
        this.appUserService = userService;
        this.jwtFilter = jwtFilter;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PASSWORD_ENCODER;
    }

    @Bean
    public UserDetailsService userDetailService() {
        return email -> {
            Optional<AppUser> optionalUser = appUserService.getByEmail(email);

            return new AuthUser(optionalUser.orElseThrow(
                    () -> new AppUserNotFoundException(email)
            ));
        };
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return web -> web.ignoring().requestMatchers("/", "/v3/api-docs/**", "/swagger-ui.html", "/swagger-ui/**");
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http.authorizeHttpRequests((authorizeHttpRequests) ->
                        authorizeHttpRequests
                                .requestMatchers("/api/registration", "/api/login", "/error").anonymous()
                                .requestMatchers("/api/**").authenticated())
                .formLogin(httpSecurityFormLoginConfigurer ->
                        httpSecurityFormLoginConfigurer
                                .loginPage("/api/login")
                                .loginProcessingUrl("/process_login")
                                .defaultSuccessUrl("/api/user/getOwn", true)
                                .failureUrl("/api/login?error"))
                .logout(httpSecurityLogoutConfigurer ->
                        httpSecurityLogoutConfigurer
                                .logoutUrl("/api/logout")
                                .logoutSuccessUrl("/api/login")
                                .invalidateHttpSession(true)
                                .deleteCookies("JSESSIONID")
                                .permitAll())
                .httpBasic(withDefaults())
                .sessionManagement((sessionManagement) ->
                        sessionManagement
                                .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .csrf(AbstractHttpConfigurer::disable);

        http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
}
