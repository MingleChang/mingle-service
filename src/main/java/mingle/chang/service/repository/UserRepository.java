package mingle.chang.service.repository;

import com.baomidou.mybatisplus.extension.service.IService;
import mingle.chang.service.dataobject.UserDO;

public interface UserRepository extends IService<UserDO> {
    UserDO getUserById(Long id);
    UserDO getUserByUsername(String username);
}
