package iss.service.ps.impl;

import iss.dao.ps.ArticleMapper;
import iss.model.ps.Article;
import iss.service.ps.ArticleService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;

import ses.util.PropertiesUtil;


/**
* @Title:ArticleServiceImpl
* @Description: 信息发布接口实现类
* @author Shen Zhenfei
* @date 2016-9-7下午6:03:56
 */
@Service("articleService")
public class ArticleServiceImpl implements ArticleService {
	
	@Autowired
	private ArticleMapper articleMapper;
	
	/**
	 * 新增信息
	 */
	@Override
	public void addArticle(Article article) {
		articleMapper.insertSelective(article);
	}
	
	/**
	 * 查询所有信息列表
	 */
	@Override
	public List<Article> selectAllArticle(Article article,Integer pageNum) {
		PropertiesUtil config = new PropertiesUtil("config.properties");
		PageHelper.startPage(pageNum,Integer.parseInt(config.getString("pageSize")));
		return articleMapper.selectAllArticle(article);
	}
	
	/**
	 * 修改信息
	 */
	@Override
	public void update(Article article) {
		articleMapper.update(article);
	}

	@Override
	public void delArticleById(String id) {
		articleMapper.deleteByPrimaryKey(id);
	}
	
	/**
	 * 根据id查询信息
	 */
	@Override
	public Article selectArticleById(String id) {
		return articleMapper.selectById(id);
	}

	@Override
	public void delete(String id) {
		articleMapper.Isdelete(id);
	}

	/**
	 * 查询标题信息
	 */
	@Override
	public List<Article> checkName(Article article) {
		List<Article> list = articleMapper.checkName(article);
		return list;
	}

	/**
	 * 根据类型查询
	 */
	@Override
	public List<Article> selectArticleByStatus(Article article,Integer pageNum) {
		PropertiesUtil config = new PropertiesUtil("config.properties");
		PageHelper.startPage(pageNum,Integer.parseInt(config.getString("pageSize")));
		List<Article> list = articleMapper.selectArticleByStatus(article);
		return list;
	}
	
	/**
	 * 为solr查询所有信息
	 */
	@Override
	public List<Article> selectAllArticleToSolr() {
		return articleMapper.selectAllArticleToSolr();
	}
}
