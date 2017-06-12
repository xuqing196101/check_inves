package app.dao.app;

import iss.model.ps.Article;

import java.util.List;
import java.util.Map;

import app.model.AppHotLine;

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
    
    /**
     * 
     * Description: App查询公告列表  采购公告 中标公示
     * 
     * @author zhang shubin
     * @data 2017年6月6日
     * @param 
     * @return
     */
    List<Article> selectAppNoticeList(Map<String, Object> map);
    
    /**
     * 
     * Description: 服务热线查询
     * 
     * @author zhang shubin
     * @data 2017年6月7日
     * @param 
     * @return
     */
    List<AppHotLine> selectHotLineList(Map<String, Object> map);
}
