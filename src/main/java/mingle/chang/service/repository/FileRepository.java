package mingle.chang.service.repository;

import com.baomidou.mybatisplus.extension.service.IService;
import mingle.chang.service.dataobject.FileDO;

public interface FileRepository extends IService<FileDO> {
    FileDO save(String name, String contentType, Long size, String path);
}
