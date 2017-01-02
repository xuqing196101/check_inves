package synchro.inner.backup.service.infos.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;

import iss.model.ps.Article;
import iss.service.ps.ArticleService;
import synchro.inner.backup.service.infos.InnerInfoService;
import synchro.service.SynchRecordService;
import synchro.util.Constant;
import synchro.util.DateUtils;
import synchro.util.FileUtils;

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
    
    
    /**
     * 
     * @see synchro.inner.backup.service.infos.InnerInfoService#backUpInfos()
     */
    @Override
    public void backUpInfos() {
        String startTime = recordService.getSynchTime(Constant.OPER_TYPE_EXPORT, Constant.DATA_TYPE_INFOS_CREATED);
        if (!StringUtils.isNotBlank(startTime)){
            startTime = DateUtils.getCurrentDate() + " 00:00:00";
        }
        startTime = DateUtils.getCalcelDate(startTime);
        List<Article>  list = articleService.getListBypublishedTime(startTime, DateUtils.getCurrentTime());
        if (list != null && list.size() > 0){
            FileUtils.writeFile(FileUtils.getInfoBackUpFile(),JSON.toJSONString(list));
            recordService.backupInfos(new Integer(list.size()).toString());
        }
    }

}
