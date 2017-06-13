package iss.service.ps.impl;

import iss.dao.ps.ArticleAttachmentsMapper;
import iss.dao.ps.IndexNewsMapper;
import iss.model.ps.Article;
import iss.service.ps.IndexNewsService;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ses.util.PropertiesUtil;

import com.github.pagehelper.PageHelper;



/**
 * 
 *<p>Title:IndexNewsServiceImpl</p>
 *<p>Description:首页信息service实现类</p>
 *<p>Company:ses</p>
 * @author Mrlovablee
 *@date 2016-8-29上午9:15:16
 */
@Service("indexNewsService")
public class IndexNewsServiceImpl implements IndexNewsService {
	
	@Autowired
	private IndexNewsMapper indeNewsMapper;
	
	@Autowired
	private ArticleAttachmentsMapper articleAttachmentsMapper;
	
	/**
	 * 首页查询所有信息方法
	 */
	@Override
	public List<Article> selectNewsByArticleTypeId(Map<String, Object> map) {
		List<Article> indexNewsList = indeNewsMapper.selectNewsByArticleTypeId(map);
		if(indexNewsList.isEmpty()){
			return null;
		}else{
			return indexNewsList;
		}	
	}
	
	/**
	 * 根据栏目类型id查询对应信息 
	 */
	@Override
	public List<Article> selectNews(String id) {
		return indeNewsMapper.selectNews(id);
	}
	
	/**
	 * 二级页信息数量
	 */
	@Override
	public Integer selectCount(Map<String,Object> countMap) {
		return indeNewsMapper.selectCount(countMap);
	}

	@Override
	public List<Article> selectNewsForJob(String id) {
		return indeNewsMapper.selectNewsForJob(id);
	}

	@Override
	public List<Article> selectAllByName(Map<String, Object> map) {
		PropertiesUtil config = new PropertiesUtil("config.properties");
		PageHelper.startPage((Integer)(map.get("page")),Integer.parseInt(config.getString("pageSize")));
		return indeNewsMapper.selectAllByName(map);
	}

  @Override
  public List<Article> selectIndexChuFaNewsByTypeId(Map<String, Object> map) {
    List<Article> indexNewsList = indeNewsMapper.selectIndexChuFaNewsByTypeId(map);
    if(indexNewsList.isEmpty()){
      return null;
    }else{
      return indexNewsList;
    }
  }

  @Override
  public Integer selectChufaCount(Map<String, Object> countMap) {
    return indeNewsMapper.selectChufaCount(countMap);
  }

@Override
public List<Article> selectSupplierAllNews(Map<String, Object> map) {
	List<Article> indexNewsList = indeNewsMapper.selectSupplierAllNews(map);
	if(indexNewsList.isEmpty()){
		return null;
	}else{
		return indexNewsList;
	}	
}

@Override
public Integer selectSupplierCount(Map<String, Object> countMap) {
	return indeNewsMapper.selectSupplierCount(countMap);
}

}
