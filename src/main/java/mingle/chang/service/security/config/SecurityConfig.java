package mingle.chang.service.security.config;

import jakarta.annotation.Resource;
import jakarta.annotation.security.PermitAll;
import mingle.chang.service.config.CustomProperties;
import mingle.chang.service.security.extend.AccessDeniedHandlerImpl;
import mingle.chang.service.security.extend.AuthenticationEntryPointImpl;
import mingle.chang.service.security.extend.CustomFilter;
import org.ini4j.BasicMultiMap;
import org.ini4j.MultiMap;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.util.CollectionUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;
import org.springframework.web.util.pattern.PathPattern;

import java.util.*;
import java.util.stream.Collectors;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Resource
    private ApplicationContext applicationContext;
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
        Map<HttpMethod, Set<String>> permitAllUrls = getPermitAllUrlsFromAnnotations();
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
        String[] permitUrls = new String[permitAll.size()];
        permitAll.toArray(permitUrls);
        http.authorizeHttpRequests(auth -> {
            auth.requestMatchers(HttpMethod.GET, "/swagger-ui/*", "/swagger-resources/*",  "/v3/api-docs/**", "/*.html", "/*/*.html", "/*/*.css", "/*/*.js").permitAll();
            auth.requestMatchers(HttpMethod.GET, permitAllUrls.getOrDefault(HttpMethod.GET, new HashSet<>()).toArray(new String[0])).permitAll();
            auth.requestMatchers(HttpMethod.POST, permitAllUrls.getOrDefault(HttpMethod.POST, new HashSet<>()).toArray(new String[0])).permitAll();
            auth.requestMatchers(HttpMethod.PUT, permitAllUrls.getOrDefault(HttpMethod.PUT, new HashSet<>()).toArray(new String[0])).permitAll();
            auth.requestMatchers(HttpMethod.DELETE, permitAllUrls.getOrDefault(HttpMethod.DELETE, new HashSet<>()).toArray(new String[0])).permitAll();
            auth.anyRequest().authenticated();
        });
        http.addFilterBefore(customFilter, UsernamePasswordAuthenticationFilter.class);
        http.exceptionHandling(handling -> {
            handling.accessDeniedHandler(accessDeniedHandler);
            handling.authenticationEntryPoint(authenticationEntryPoint);
        });
        return http.build();
    }
    private Map<HttpMethod, Set<String>> getPermitAllUrlsFromAnnotations() {
        Map<HttpMethod, Set<String>> result = new HashMap<>();
        // 获得接口对应的 HandlerMethod 集合
        RequestMappingHandlerMapping requestMappingHandlerMapping = applicationContext.getBean(RequestMappingHandlerMapping.class);
        Map<RequestMappingInfo, HandlerMethod> handlerMethodMap = requestMappingHandlerMapping.getHandlerMethods();
        // 获得有 @PermitAll 注解的接口
        for (Map.Entry<RequestMappingInfo, HandlerMethod> entry : handlerMethodMap.entrySet()) {
            HandlerMethod handlerMethod = entry.getValue();
            if (!handlerMethod.hasMethodAnnotation(PermitAll.class)) {
                continue;
            }
            if (entry.getKey().getPathPatternsCondition() == null) {
                continue;
            }
            Set<PathPattern> patterns = entry.getKey().getPathPatternsCondition().getPatterns();
            List<String> urlList = patterns.stream().map(PathPattern::getPatternString).collect(Collectors.toList());
            // 根据请求方法，添加到 result 结果
            entry.getKey().getMethodsCondition().getMethods().forEach(requestMethod -> {
                Set<String> urls;
                switch (requestMethod) {
                    case GET:
                        urls = result.getOrDefault(HttpMethod.GET, new HashSet<>());
                        urls.addAll(urlList);
                        result.put(HttpMethod.GET, urls);
                        break;
                    case POST:
                        urls = result.getOrDefault(HttpMethod.POST, new HashSet<>());
                        urls.addAll(urlList);
                        result.put(HttpMethod.POST, urls);
                        break;
                    case PUT:
                        urls = result.getOrDefault(HttpMethod.PUT, new HashSet<>());
                        urls.addAll(urlList);
                        result.put(HttpMethod.PUT, urls);
                        break;
                    case DELETE:
                        urls = result.getOrDefault(HttpMethod.DELETE, new HashSet<>());
                        urls.addAll(urlList);
                        result.put(HttpMethod.DELETE, urls);
                        break;
                }
            });
        }
        return result;
    }

}
