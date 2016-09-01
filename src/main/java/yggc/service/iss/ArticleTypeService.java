package yggc.service.iss;

import java.util.List;

import yggc.model.iss.ArticleType;

/**
 * 
 *<p>Title:ArticleTypeService</p>
 *<p>Description:栏目类型Service接口</p>
 *<p>Company:yggc</p>
 * @author Mrlovablee
 *@date 2016-8-26下午4:44:02
 */
public interface ArticleTypeService {
	/**
	 * 
	* @Title: selectArticleType
	* @author Mrlovablee 
	* @date 2016-8-26 下午4:45:15  
	* @Description: 查询所有栏目类别 
	* @param @return      
	* @return List<ArticleType>
	 */
	List<ArticleType> selectArticleType();
}
