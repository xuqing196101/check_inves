package yggc.dao.iss;

import java.util.List;
import java.util.Map;

import yggc.model.bms.Article;

/**
 * 
 *<p>Title:IndexNewsMapper</p>
 *<p>Description:首页信息操作Mapper</p>
 *<p>Company:yggc</p>
 * @author Mrlovablee
 *@date 2016-8-26下午4:28:37
 */
public interface IndexNewsMapper {
	/**
	 * 
	* @Title: selectIndexNews
	* @author Mrlovablee 
	* @date 2016-8-26 下午4:29:43  
	* @Description: 查询所有首页信息 
	* @param @return      
	* @return Map<String,Object>
	 */
	List<Article> selectIndexNews();
	
	/**
	 * 
	* @Title: selectNewsByArticleTypeId
	* @author Mrlovablee 
	* @date 2016-8-29 上午8:52:43  
	* @Description: 根据栏目类型id查询对应信息 
	* @param @param id
	* @param @return      
	* @return List<Article>
	 */
	List<Article> selectNewsByArticleTypeId(Integer id);
}
