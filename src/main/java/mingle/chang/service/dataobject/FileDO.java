package mingle.chang.service.dataobject;

import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import mingle.chang.service.mybatis.dataobject.BaseDO;

@TableName("file")
@KeySequence("file_id_seq")
@Data
@EqualsAndHashCode(callSuper = false)
public class FileDO extends BaseDO {
    private String name;
    private String contentType;
    private Long size;
    private String path;
}
