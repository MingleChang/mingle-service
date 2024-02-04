package mingle.chang.service.service;

import mingle.chang.service.dataobject.UserDO;
import mingle.chang.service.dto.CreateUserDto;
import mingle.chang.service.dto.LoginUserDto;
import mingle.chang.service.vo.LoginVo;
import mingle.chang.service.vo.UserVo;

public interface UserService {
    LoginVo login(LoginUserDto dto);
    Long create(CreateUserDto dto);
    UserVo info();
    Boolean existByUsername(String username);
    UserDO selectById(Long id);
}
