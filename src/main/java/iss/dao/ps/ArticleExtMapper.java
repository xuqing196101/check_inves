
package iss.dao.ps;

import iss.model.ps.ArticleExt;

import java.util.List;
import java.util.Map;

/**
 * 
* @ClassName: ArticleExtMapper 
* @Description: 文章扩展接口
* @author SongDong 
* @date 2017年3月1日 下午5:38:46 
*
 */
public interface ArticleExtMapper {
	
	/**
	 * 
	* @Title: selectAllArticleAddSolr 
	* @Description: 查询文章
	* @author SongDong
	* @param @return    设定文件 
	* @return List<ArticleExt>    返回类型 
	* @throws
	 */
	public List<ArticleExt> selectAllArticleAddSolr();
	
	/**
	 * 
	* @Title: updateIndex 
	* @Description: 修改索引值
	* @author SongDong
	* @param @param id
	* @param @return    设定文件 
	* @return int    返回类型 
	* @throws
	 */
	public void updateIndex(Map<String, Object> map);
}