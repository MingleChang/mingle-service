package mingle.chang.service.mapper;

import mingle.chang.service.dataobject.UserDO;
import mingle.chang.service.mybatis.mapper.BaseMapperX;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper extends BaseMapperX<UserDO> {
}
