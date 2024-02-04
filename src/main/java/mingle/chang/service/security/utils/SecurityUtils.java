package mingle.chang.service.security.utils;

import jakarta.servlet.http.HttpServletRequest;
import mingle.chang.service.security.extend.AuthenticationUser;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;

import java.util.Collections;
import java.util.Objects;

public class SecurityUtils {
    public static final String LANG = "lang";
    public static final String AUTHORIZATION = "authorization";
    public static final String AUTHORIZATION_BEARER = "Bearer";

    public static String obtainHeader(HttpServletRequest request, String header) {
        String content = request.getHeader(header);
        return content;
    }

    public static String obtainAuthorization(HttpServletRequest request) {
        String authorization = obtainHeader(request, AUTHORIZATION);
        if (!StringUtils.hasText(authorization)) {
            return null;
        }
        int index = authorization.indexOf(AUTHORIZATION_BEARER + " ");
        if (index == -1) { // 未找到
            return null;
        }
        return authorization.substring(index + 7).trim();
    }
    public static Authentication getAuthentication() {
        SecurityContext context = SecurityContextHolder.getContext();
        if (context == null) {
            return null;
        }
        return context.getAuthentication();
    }

    public static AuthenticationUser getAuthorizationUser() {
        Authentication authentication = getAuthentication();
        if (authentication == null) {
            return null;
        }
        if (authentication.getPrincipal() instanceof AuthenticationUser) {
            return (AuthenticationUser) authentication.getPrincipal();
        }else {
            return null;
        }
    }

    public static void setAuthorizationUser(HttpServletRequest request, AuthenticationUser user) {
        Authentication authentication = buildAuthentication(request, user);
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    private static Authentication buildAuthentication(HttpServletRequest request, AuthenticationUser user) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                user, null, Collections.emptyList());
        authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        return authenticationToken;
    }

    public static Long getUserId() {
        AuthenticationUser user = getAuthorizationUser();
        if (Objects.isNull(user)) {
            return null;
        } else {
            return user.getId();
        }
    }
}
