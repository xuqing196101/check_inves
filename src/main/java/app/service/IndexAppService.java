package app.service;

import iss.model.ps.Article;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import app.model.AppBlackList;
import app.model.AppHotLine;
import app.model.AppSupplier;

/**
 * 
 * Description: app接口Service层
 * 
 * @author zhang shubin
 * @version 2016-9-7
 * @since JDK1.7
 */
public interface IndexAppService {

    /**
     * 
     * Description: 分页查询公告列表
     * 
     * @author zhang shubin
     * @data 2017年6月6日
     * @param 
     * @return
     */
    List<Article> selectAppArticleListByTypeId(String articleTypeId,Integer page);
    
    /**
     * 
     * Description: 分页查询供应商列表
     * 
     * @author zhang shubin
     * @data 2017年6月6日
     * @param 
     * @return
     */
    List<AppSupplier> selectAppSupplierList(Map<String, Object> map);
    
    /**
     * 
     * Description: 供应商黑名单
     * 
     * @author zhang shubin
     * @data 2017年6月6日
     * @param 
     * @return
     */
    List<AppBlackList> findAppSupplierBlacklist(Integer page);
    
    /**
     * 
     * Description: 专家名录
     * 
     * @author zhang shubin
     * @data 2017年6月6日
     * @param 
     * @return
     */
    List<AppSupplier> selectAppExpertList(Map<String, Object> map);
    
    /**
     * 
     * Description: 专家黑名单
     * 
     * @author zhang shubin
     * @data 2017年6月6日
     * @param 
     * @return
     */
    List<AppBlackList> findAppExpertBlacklist(Integer page);
    
    /**
     * 
     * Description: 分页查询法规
     * 
     * @author zhang shubin
     * @data 2017年6月6日
     * @param 
     * @return
     */
    List<Article> selectAppRegulations(Map<String, Object> map);
    
    /**
     * 
     * Description: 分页查询处罚公告
     * 
     * @author zhang shubin
     * @data 2017年6月6日
     * @param 
     * @return
     */
    List<Article> selectAppPunishment(Map<String, Object> map);
    
    /**
     * 
     * Description: 搜索
     * 
     * @author zhang shubin
     * @data 2017年6月6日
     * @param 
     * @return
     */
    List<Article> search(Map<String, Object> map);
    
    /**
     * 
     * Description: App查询公告列表  采购公告 中标公示
     * 
     * @author zhang shubin
     * @data 2017年6月6日
     * @param 
     * @return
     */
    List<Article> selectAppNoticeList(String ids[],Integer page);
    
    /**
     * 
     * Description: 查询公告内容
     * 
     * @author zhang shubin
     * @data 2017年6月6日
     * @param 
     * @return
     */
    Article selectContentById(String id);
    
    /**
     * 
     * Description: 服务热线查询
     * 
     * @author zhang shubin
     * @data 2017年6月7日
     * @param 
     * @return
     */
    List<AppHotLine> selectHotLineList(Integer page);
    
    /**
     * 
     * Description: 生成公告图片
     * 
     * @author zhang shubin
     * @data 2017年7月5日
     * @param 
     * @return
     */
    void getContentImg(Article articleDetail,HttpServletRequest request);
}
