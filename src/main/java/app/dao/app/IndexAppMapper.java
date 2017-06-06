package app.dao.app;

import java.util.List;
import java.util.Map;

import iss.model.ps.Article;

import org.apache.ibatis.annotations.Param;

/**
 * 
 * Description: app通知查询
 * 
 * @author zhang shubin
 * @version 2016-9-7
 * @since JDK1.7
 */
public interface IndexAppMapper {

	/**
	 * 
	 * Description: APP首页信息查询
	 * 
	 * @author zhang shubin
	 * @version 2017年6月1日
	 * @param @param articleTypeId
	 * @param @return
	 * @return List<Article>
	 * @exception
	 */
	Article selectAppNewsByArticleTypeId(@Param("articleTypeId") String articleTypeId);

	/**
	 * 
	 * Description: APP首页处罚公告查询
	 * 
	 * @author zhang shubin
	 * @version 2017年6月1日
	 * @param @param articleTypeId
	 * @param @return
	 * @return List<Article>
	 * @exception
	 */
	Article selectAppChuFaNewsByTypeId(@Param("articleTypeId") String articleTypeId);

	/**
	 * 
	 * Description: 查询公告列表
	 * 
	 * @author zhang shubin
	 * @data 2017年6月5日
	 * @param
	 * @return
	 */
	List<Article> selectAppArticleListByTypeId(Map<String, Object> map);
	
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
}
