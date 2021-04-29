package com.eg.chatserver.file;

import com.eg.chatserver.bean.File;
import com.eg.chatserver.bean.FileExample;
import com.eg.chatserver.bean.mapper.FileMapper;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;

@Service
public class FileService {
    @Resource
    private FileMapper fileMapper;

    public File getByFileId(String fileId) {
        FileExample fileExample = new FileExample();
        FileExample.Criteria criteria = fileExample.createCriteria();
        criteria.andFileIdEqualTo(fileId);
        List<File> files = fileMapper.selectByExample(fileExample);
        if (CollectionUtils.isEmpty(files))
            return null;
        else
            return files.get(0);
    }
}
