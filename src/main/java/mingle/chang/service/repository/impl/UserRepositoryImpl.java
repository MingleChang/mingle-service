package mingle.chang.service.repository.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import mingle.chang.service.dataobject.UserDO;
import mingle.chang.service.mapper.UserMapper;
import mingle.chang.service.mybatis.wrapper.LambdaQueryWrapperX;
import mingle.chang.service.repository.UserRepository;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Repository
public class UserRepositoryImpl extends ServiceImpl<UserMapper, UserDO> implements UserRepository {
    @Override
    public UserDO getUserById(Long id) {
        LambdaQueryWrapperX<UserDO> queryWrapper = new LambdaQueryWrapperX<>();
        queryWrapper.eq(UserDO::getId, id);
        List<UserDO> list = this.list(queryWrapper);
        if (CollectionUtils.isEmpty(list)) {
            return null;
        }else {
            return list.get(0);
        }
    }

    @Override
    public UserDO getUserByUsername(String username) {
        LambdaQueryWrapperX<UserDO> queryWrapper = new LambdaQueryWrapperX<>();
        queryWrapper.eq(UserDO::getUsername, username);
        List<UserDO> list = this.list(queryWrapper);
        if (CollectionUtils.isEmpty(list)) {
            return null;
        }else {
            return list.get(0);
        }
    }
}
