package synchro.inner.read.infos.impl;

import java.io.File;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import iss.model.ps.Article;
import iss.service.ps.ArticleService;
import synchro.inner.read.infos.InnerInfoImportService;
import synchro.service.SynchRecordService;
import synchro.util.FileUtils;

/**
 * 
 * 版权：(C) 版权所有 
 * <简述> 内网信息导入实现类
 * <详细描述>
 * @author   myc
 * @version  
 * @since
 * @see
 */
@Service
public class InnerInfoImportServiceImpl implements InnerInfoImportService {
    
    
    /** 信息发布service **/
    @Autowired
    private ArticleService articleService;
    
    /** 记录service  **/
    @Autowired
    private SynchRecordService  recordService; 
    
    /**
     * 
     * @see synchro.inner.read.infos.InnerInfoImportService#importInfos(java.io.File)
     */
    @Override
    public void importInfos(File file) {
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
