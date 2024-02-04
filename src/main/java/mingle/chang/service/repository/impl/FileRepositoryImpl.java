package mingle.chang.service.repository.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import mingle.chang.service.dataobject.FileDO;
import mingle.chang.service.mapper.FileMapper;
import mingle.chang.service.repository.FileRepository;
import org.springframework.stereotype.Repository;

@Repository
public class FileRepositoryImpl extends ServiceImpl<FileMapper, FileDO> implements FileRepository {
    @Override
    public FileDO save(String name, String contentType, Long size, String path) {
        FileDO fileDO = new FileDO();
        fileDO.setName(name);
        fileDO.setContentType(contentType);
        fileDO.setSize(size);
        fileDO.setPath(path);
        this.save(fileDO);
        return fileDO;
    }
}
