package yggc.service.iss;

import java.util.List;

import yggc.model.iss.ArticleType;

/*
 *@Title:ArticleTypeService
 *@Description:栏目类型Service接口
 *@author QuJie
 *@date 2016-8-26下午4:44:02
 */
public interface ArticleTypeService {
	/**
	 * 
	* @Title: selectArticleType
	* @author QuJie 
	* @date 2016-8-26 下午4:45:15  
	* @Description: 查询所有栏目类别 
	* @param @return      
	* @return List<ArticleType>
	 */
	List<ArticleType> selectArticleType();
}
