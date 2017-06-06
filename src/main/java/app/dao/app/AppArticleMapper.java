package app.dao.app;

import iss.model.ps.Article;

import java.util.Map;

/**
 * 
 * Description: app公告查询Mapper
 * 
 * @author zhang shubin
 * @version 2016-9-7
 * @since JDK1.7
 */
public interface AppArticleMapper {
    
    /**
     * 
     * Description: app首页查询采购法规
     * 
     * @author zhang shubin
     * @data 2017年6月3日
     * @param 
     * @return
     */
    Article selectsumApp(Map<String, Object> map);
    
    /**
     * 
     * Description: App业务公告单条查询
     * 
     * @author zhang shubin
     * @data 2017年6月2日
     * @param 
     * @return 
     * @exception
     */
    Article selectOneAppNoticeByParId(Map<String, Object> map);
}
