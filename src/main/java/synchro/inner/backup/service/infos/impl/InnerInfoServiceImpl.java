package synchro.inner.backup.service.infos.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;

import common.constant.Constant;
import common.model.UploadFile;
import common.service.UploadService;
import iss.model.ps.Article;
import iss.service.ps.ArticleService;
import synchro.inner.backup.service.infos.InnerInfoService;
import synchro.service.SynchRecordService;
import synchro.util.FileUtils;
import synchro.util.OperAttachment;

/**
 * 
 * 版权：(C) 版权所有 
 * <简述>内外信息同步-导出
 * <详细描述>
 * @author   myc
 * @version  
 * @since
 * @see
 */
@Service
public class InnerInfoServiceImpl implements InnerInfoService {
    
    /** 信息发布service **/
    @Autowired
    private ArticleService articleService;
    
    /** 记录service  **/
    @Autowired
    private SynchRecordService  recordService;
    
    /** 附件service **/
    @Autowired
    private UploadService uploadService;
    
    
    /**
     * 
     * @see synchro.inner.backup.service.infos.InnerInfoService#backUpInfos(java.lang.String, java.lang.String)
     */
    @Override
    public void backUpInfos(String startTime, String endTime, Date synchDate) {
        List<Article>  list = articleService.getListBypublishedTime(startTime, endTime);
        if (list != null && list.size() > 0){
            List<UploadFile> attachList = new ArrayList<>();
            for (Article article : list){
                List<UploadFile> fileList = uploadService.findBybusinessId(article.getId(), Constant.TENDER_SYS_KEY);
                attachList.addAll(fileList);
            }
            FileUtils.writeFile(FileUtils.getInfoBackUpFile(),JSON.toJSONString(list));
            if (attachList.size() > 0){
                FileUtils.writeFile(FileUtils.getInfoAttachmentFile(),JSON.toJSONString(attachList));
                String basePath = FileUtils.attachExportPath(Constant.TENDER_SYS_KEY);
                if (StringUtils.isNotBlank(basePath)){
                    OperAttachment.writeFile(basePath, attachList);
                    recordService.backupAttach(new Integer(attachList.size()).toString());
                }
            }
        }
        recordService.backupInfos(synchDate, new Integer(list.size()).toString());
    }

}
