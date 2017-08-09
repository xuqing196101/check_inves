package synchro.inner.back.service.infos.impl;

import iss.dao.ps.ArticleMapper;
import iss.model.ps.Article;
import iss.model.ps.ArticleCategory;
import iss.service.ps.ArticleService;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import synchro.inner.back.service.infos.InnerInfoExportService;
import synchro.service.SynchRecordService;
import synchro.util.FileUtils;
import synchro.util.OperAttachment;

import com.alibaba.fastjson.JSON;

import common.constant.Constant;
import common.model.UploadFile;
import common.service.UploadService;

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
public class InnerInfoExportServiceImpl implements InnerInfoExportService {
    
    /** 信息发布service **/
    @Autowired
    private ArticleService articleService;
    
    /** 记录service  **/
    @Autowired
    private SynchRecordService  recordService;
    
    /** 附件service **/
    @Autowired
    private UploadService uploadService;
    
    @Autowired
    private ArticleMapper articleMapper;
    
    
    /**
     * 
     * @see synchro.inner.backup.service.infos.InnerInfoExportService#backUpInfos(java.lang.String, java.lang.String)
     */
    @Override
    public void backUpInfos(String startTime, String endTime, Date synchDate) {
        List<Article>  list = articleService.getListBypublishedTime(startTime, endTime);
        List<Article> cancelList = getCancelNews(startTime, endTime);
        if (list != null && list.size() > 0){
            list.addAll(cancelList);
            List<UploadFile> attachList = new ArrayList<>();
            for (Article article : list){
                List<UploadFile> fileList = uploadService.findBybusinessId(article.getId(), Constant.TENDER_SYS_KEY);
                attachList.addAll(fileList);
            }
            FileUtils.writeFile(FileUtils.getInfoBackUpFile(),JSON.toJSONString(list));
            //导出公告品目关联信息
            List<ArticleCategory> articleCategoryList = new ArrayList<>();
            ArticleCategory ac = new ArticleCategory();
            for (Article article : list){
                ac.setArticleId(article.getId());
                List<ArticleCategory> acList = articleMapper.findArtCategory(ac);
                articleCategoryList.addAll(acList);
            }
            FileUtils.writeFile(FileUtils.getArticleCategoryFile(),JSON.toJSONString(articleCategoryList));
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
    
    /**
     * 
     *〈简述〉获取取消的信息
     *〈详细描述〉
     * @author myc
     * @param startTime 开始时间
     * @param endTime 结束时间
     */
    public List<Article> getCancelNews(String startTime, String endTime){
        List<Article> cancelList = articleService.getCancelNews(startTime, endTime);
        if (cancelList != null && cancelList.size() > 0){
            return cancelList;
        }
        return new ArrayList<>();
    }

}
