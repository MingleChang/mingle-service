package mingle.chang.service.service.impl;

import jakarta.annotation.Resource;
import mingle.chang.service.dataobject.UserDO;
import mingle.chang.service.dto.CreateUserDto;
import mingle.chang.service.dto.LoginUserDto;
import mingle.chang.service.exception.ServiceException;
import mingle.chang.service.mybatis.dataobject.BaseDO;
import mingle.chang.service.repository.UserRepository;
import mingle.chang.service.response.ResponseStatusEnum;
import mingle.chang.service.security.utils.SecurityUtils;
import mingle.chang.service.service.UserService;
import mingle.chang.service.utils.JwtUtils;
import mingle.chang.service.vo.LoginVo;
import mingle.chang.service.vo.UserVo;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Service
public class UserServiceImpl implements UserService {
    @Resource
    private UserRepository userRepository;
    @Resource
    private PasswordEncoder passwordEncoder;

    @Override
    public LoginVo login(LoginUserDto dto) {
        String username = dto.getUsername();
        String password = dto.getPassword();
        UserDO userDO = this.userRepository.getUserByUsername(username);
        if (Objects.isNull(userDO) || !this.passwordEncoder.matches(password, userDO.getPassword())) {
            ServiceException.throwBizException(ResponseStatusEnum.USERNAME_OR_PASSWORD_ERROR);
        }
        Map<String, Object> map = new HashMap<>();
        map.put(BaseDO.SF_ID, userDO.getId());
        map.put(UserDO.SF_USERNAME, userDO.getUsername());
        String jwt = JwtUtils.createJwt(map, 3600 * 1000, userDO.getPassword());
        LoginVo loginVo = new LoginVo();
        loginVo.setUsername(username);
        loginVo.setAccessToken(jwt);
        return loginVo;
    }

    @Override
    public Long create(CreateUserDto dto) {
        String username = dto.getUsername();
        String password = dto.getPassword();
        if (this.existByUsername(username)) {
            ServiceException.throwBizException(ResponseStatusEnum.USERNAME_EXIST);
        }
        password = passwordEncoder.encode(password);
        UserDO userDO = new UserDO();
        userDO.setUsername(username);
        userDO.setPassword(password);
        this.userRepository.save(userDO);
        return userDO.getId();
    }

    @Override
    public UserVo info() {
        Long userId = SecurityUtils.getUserId();
        UserDO userDO = this.userRepository.getUserById(userId);
        UserVo userVo = new UserVo();
        userVo.setUsername(userDO.getUsername());
        return userVo;
    }

    @Override
    public Boolean existByUsername(String username) {
        UserDO userDO = this.userRepository.getUserByUsername(username);
        return Objects.nonNull(userDO);
    }

    @Override
    public UserDO selectById(Long id) {
        return this.userRepository.getUserById(id);
    }
}
