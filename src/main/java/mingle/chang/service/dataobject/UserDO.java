package mingle.chang.service.dataobject;

import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import mingle.chang.service.mybatis.dataobject.BaseDO;

@TableName("users")
@KeySequence("users_id_seq")
@Data
@EqualsAndHashCode(callSuper = false)
public class UserDO extends BaseDO {
    public static final String SF_USERNAME = "username";
    public static final String SF_PASSWORD = "password";

    private String username;
    private String password;
}
