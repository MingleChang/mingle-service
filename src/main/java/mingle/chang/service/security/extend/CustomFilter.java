package mingle.chang.service.security.extend;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import jakarta.annotation.Resource;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import mingle.chang.service.config.CustomProperties;
import mingle.chang.service.dataobject.UserDO;
import mingle.chang.service.exception.ExceptionUtils;
import mingle.chang.service.exception.ServiceException;
import mingle.chang.service.mybatis.dataobject.BaseDO;
import mingle.chang.service.response.Response;
import mingle.chang.service.response.ResponseStatusEnum;
import mingle.chang.service.security.utils.SecurityUtils;
import mingle.chang.service.service.UserService;
import mingle.chang.service.utils.JsonUtils;
import mingle.chang.service.utils.JwtUtils;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Objects;

@Component
public class CustomFilter extends OncePerRequestFilter {

    @Resource
    private CustomProperties customProperties;
    @Resource
    private UserService userService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = SecurityUtils.obtainAuthorization(request);
        AuthenticationUser user = null;
        try {
            user = buildUserByToken(token);
        }catch (Throwable exception) {
            Response result = ExceptionUtils.allExceptionHandler(exception);
            String json = JsonUtils.toJson(result);
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            response.getWriter().write(json);
            return;
        }
        if (Objects.nonNull(user)) {
            SecurityUtils.setAuthorizationUser(request, user);
        }
        filterChain.doFilter(request, response);
    }

    private AuthenticationUser buildUserByToken(String token) {
        if (!StringUtils.hasText(token)) {
            return null;
        }
        try {
            DecodedJWT decodedJWT = JwtUtils.parseJwt(token);
            Long id = decodedJWT.getClaim(BaseDO.SF_ID).asLong();
            UserDO userDO = this.userService.selectById(id);
            decodedJWT = JwtUtils.verifierJwt(decodedJWT, userDO.getPassword());
            String username = decodedJWT.getClaim(UserDO.SF_USERNAME).asString();
            AuthenticationUser user = new AuthenticationUser();
            user.setId(id);
            user.setUsername(username);
            return user;
        }catch (JWTVerificationException exception) {
            throw new ServiceException(ResponseStatusEnum.TOKEN_ERROR);
        }
    }
}
