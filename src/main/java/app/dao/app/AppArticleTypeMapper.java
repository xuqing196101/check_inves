package app.dao.app;

import iss.model.ps.ArticleType;

/**
 * 
 * Description: app公告类型查询
 * 
 * @author zhang shubin
 * @version 2016-9-7
 * @since JDK1.7
 */
public interface AppArticleTypeMapper {
    
    /**
     * 
     * Description: app查询栏目类型
     * 
     * @author  zhang shubin
     * @version  2017年6月1日 
     * @param  @param id
     * @param  @return 
     * @return ArticleType 
     * @exception
     */
    ArticleType selectTypeApp(String id);
}
