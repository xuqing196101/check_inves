package synchro.outer.read.infos.impl;

import java.io.File;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import iss.model.ps.Article;
import iss.service.ps.ArticleService;
import synchro.outer.read.infos.OuterInfoService;
import synchro.service.SynchRecordService;
import synchro.util.FileUtils;

/**
 * 
 * 版权：(C) 版权所有 
 * <简述> 信息同步
 * <详细描述>
 * @author   myc
 * @version  
 * @since
 * @see
 */
@Service
public class OuterInfoServiceImpl implements OuterInfoService {

    
    /** 信息发布service **/
    @Autowired
    private ArticleService articleService;
    
    /** 记录service  **/
    @Autowired
    private SynchRecordService  recordService; 
    
    /**
     * 
     * @see synchro.outer.read.infos.OuterInfoService#importInfos(java.io.File)
     */
    @Override
    public void importInfos(final File file) {
        List<Article> list = FileUtils.getBeans(file, Article.class); 
        if (list != null && list.size() > 0){
            for (Article article : list){
                Integer count = articleService.getArticleCount(article.getId());
                if (count > 0){
                    if (article != null && StringUtils.isNotBlank(article.getId())){
                        articleService.updateArticle(article);
                    }
                } else {
                    articleService.insertArticle(article);
                }
            }
            recordService.importInfos(new Integer(list.size()).toString());
        }
    }

    
}
