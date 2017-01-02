package synchro.inner.backup.service.att.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;

import common.constant.Constant;
import common.model.UploadFile;
import common.service.UploadService;
import synchro.inner.backup.service.att.InnerAttachmentService;
import synchro.service.SynchRecordService;
import synchro.util.DateUtils;
import synchro.util.FileUtils;

/**
 * 
 * 版权：(C) 版权所有 
 * <简述> 附件备份
 * <详细描述>
 * @author   myc
 * @version  
 * @since
 * @see
 */
@Service
public class InnerAttachmentServiceImpl implements InnerAttachmentService {
    
    /** 文件上传 **/
    @Autowired
    private UploadService uploadService;
    
    /** 记录service  **/
    @Autowired
    private SynchRecordService  recordService;
    
    /**
     * 
     * @see synchro.inner.backup.service.att.InnerAttachmentService#backAttachment(java.lang.String, java.lang.String)
     */
    @Override
    public void backAttachment() {
        List<UploadFile> list = uploadService.getListByBusinessId(DateUtils.getCurrentDate(),Constant.TENDER_SYS_KEY);
        if (list != null && list.size() > 0){
            FileUtils.writeFile(FileUtils.getInfoAttachmentFile(),JSON.toJSONString(list));
            recordService.backupAttach(new Integer(list.size()).toString());
        }
    }
    
    
    
    
}
