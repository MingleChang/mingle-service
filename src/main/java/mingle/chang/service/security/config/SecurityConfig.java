package mingle.chang.service.security.config;

import jakarta.annotation.Resource;
import mingle.chang.service.config.CustomProperties;
import mingle.chang.service.security.extend.AccessDeniedHandlerImpl;
import mingle.chang.service.security.extend.AuthenticationEntryPointImpl;
import mingle.chang.service.security.extend.CustomFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Resource
    private CustomProperties customProperties;
    @Resource
    private CustomFilter customFilter;
    @Resource
    private AccessDeniedHandlerImpl accessDeniedHandler;
    @Resource
    private AuthenticationEntryPointImpl authenticationEntryPoint;
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.sessionManagement(session -> {
            session.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        });
        http.csrf(auth -> {
            auth.disable();
        });
        http.httpBasic(auth -> {
            auth.disable();
        });
        List<String> permitAll = customProperties.getPermitAll();
        if (!CollectionUtils.isEmpty(permitAll)) {
            String[] permitUrls = new String[permitAll.size()];
            permitAll.toArray(permitUrls);
            http.authorizeHttpRequests(auth -> {
                auth.requestMatchers(permitUrls).permitAll();
            });
        }
        http.authorizeHttpRequests(auth -> {
            auth.anyRequest().authenticated();
        });
        http.addFilterBefore(customFilter, UsernamePasswordAuthenticationFilter.class);
        http.exceptionHandling(handling -> {
            handling.accessDeniedHandler(accessDeniedHandler);
            handling.authenticationEntryPoint(authenticationEntryPoint);
        });
        return http.build();
    }
}
