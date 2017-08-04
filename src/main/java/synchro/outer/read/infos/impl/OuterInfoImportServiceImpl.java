package synchro.outer.read.infos.impl;

import iss.dao.ps.ArticleMapper;
import iss.model.ps.Article;
import iss.model.ps.ArticleCategory;
import iss.service.ps.ArticleService;

import java.io.File;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ses.util.PropUtil;
import synchro.outer.read.infos.OuterInfoImportService;
import synchro.service.SynchRecordService;
import synchro.util.FileUtils;
import app.service.IndexAppService;

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
public class OuterInfoImportServiceImpl implements OuterInfoImportService {

    
    /** 信息发布service **/
    @Autowired
    private ArticleService articleService;
    
    /** 记录service  **/
    @Autowired
    private SynchRecordService  recordService; 
    
    //App接口Service注入
    @Autowired
    private IndexAppService indexAppService;
    
    @Autowired
    private ArticleMapper articleMapper;
    
    /**
     * 
     * @see synchro.outer.read.infos.OuterInfoImportService#importInfos(java.io.File)
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
                        //删除原来图片
                        String filePath = PropUtil.getProperty("file.noticePic.base")+ File.separator + "zanpic";
                        String glisteningPath = PropUtil.getProperty("file.noticePic.base")+ File.separator + "glistening";
                        File glisteningFile = new File(glisteningPath+"/"+article.getId()+".jpg");
                        File zanPicFile = new File(filePath+"/"+article.getId()+".png");
                        //判读图片是否存在
                        if(glisteningFile.exists()){
                            glisteningFile.delete();
                        }
                        if(zanPicFile.exists()){
                            zanPicFile.delete();
                        }
                        
                        //如果App公告图片存在 如果存在 就执行删除
                        String appFilePath = PropUtil.getProperty("file.noticePic.base")+ File.separator + "Appzanpic";
                        String appGlisteningPath = PropUtil.getProperty("file.noticePic.base")+ File.separator + "Appglistening";
                        File appGlisteningFile = new File(appGlisteningPath+"/"+article.getId()+".jpg");
                        File appZanPicFile = new File(appFilePath+"/"+article.getId()+".png");
                        //判断图片是否存在
                        if(appGlisteningFile.exists()){
                            appGlisteningFile.delete();
                        }
                        if(appZanPicFile.exists()){
                            appZanPicFile.delete();
                        }
                    }
                } else {
                    articleService.insertArticle(article);
                    //生成App公告图片
                    indexAppService.getContentImg(article, null);
                }
                
            }
            recordService.importInfos(new Integer(list.size()).toString());
        }
    }

    /**
     * 导入公告关联品目
     */
    @Override
    public void importArticleCategory(final File file) { 
		 List<ArticleCategory> list = FileUtils.getBeans(file, ArticleCategory.class); 
	        if (list != null && list.size() > 0){
	        	for (ArticleCategory articleCategory : list) {
	        		if(articleMapper.findArtCategory(articleCategory) != null && articleMapper.findArtCategory(articleCategory).size() > 0){
	        			articleMapper.deleteArtCategory(articleCategory);
	        		}
	        		articleMapper.saveArtCategory(articleCategory);
	        	}
	        }
    }
    
}
