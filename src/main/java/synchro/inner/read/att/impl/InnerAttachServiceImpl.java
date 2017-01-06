package synchro.inner.read.att.impl;

import java.io.File;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import common.constant.Constant;
import common.model.UploadFile;
import common.service.UploadService;
import synchro.inner.read.att.InnerAttachService;
import synchro.service.SynchRecordService;
import synchro.util.FileUtils;

/**
 * 
 * 版权：(C) 版权所有 
 * <简述> 内网附件
 * <详细描述>
 * @author   myc
 * @version  
 * @since
 * @see
 */
@Service
public class InnerAttachServiceImpl implements InnerAttachService {
    
    /** 注入service */
    @Autowired
    private UploadService uploadService;
    
    /** 记录service  **/
    @Autowired
    private SynchRecordService  recordService;
    
    /**
     * 
     * @see synchro.inner.read.att.InnerAttachService#importAttach(java.io.File)
     */
    @Override
    public void importAttach(File file) {
        List<UploadFile> list = FileUtils.getBeans(file, UploadFile.class); 
        if (list != null && list.size() > 0){
            for (UploadFile uploadFile : list){
                Integer count = uploadService.findCountById(uploadFile.getId(),Constant.TENDER_SYS_KEY);
                if (count > 0){
                    uploadService.updateFile(uploadFile, Constant.TENDER_SYS_KEY);
                } else {
                    uploadService.insertFile(uploadFile,Constant.TENDER_SYS_KEY);
                }
            }
            recordService.importAttach(new Integer(list.size()).toString());
        }
    }

}
