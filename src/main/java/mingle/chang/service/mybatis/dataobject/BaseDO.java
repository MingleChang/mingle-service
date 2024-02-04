package mingle.chang.service.mybatis.dataobject;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(callSuper = false)
public abstract class BaseDO<T extends Model<?>> extends Model<T> {
    public static final String SF_ID = "id";
    public static final String SF_CREATOR = "creator";
    public static final String SF_UPDATER = "updater";
    public static final String SF_CREATE_TIME = "createTime";
    public static final String SF_UPDATE_TIME = "updateTime";
    public static final String SF_IS_DELETED = "isDeleted";

    @TableId
    private Long id;

    @TableField(fill = FieldFill.INSERT)
    private Long creator;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Long updater;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @TableField(fill = FieldFill.INSERT_UPDATE, update = "NOW()")
    private LocalDateTime updateTime;

    @TableField(fill = FieldFill.INSERT)
    private Integer isDeleted;

    @Override
    public Serializable pkVal() {
        return this.id;
    }
}
