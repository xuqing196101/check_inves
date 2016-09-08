package iss.service.article.impl;

import iss.dao.article.ArticleTypeMapper;
import iss.model.article.ArticleType;
import iss.service.article.ArticleTypeService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


	
@Service("articleTypeService")
public class ArticleTypeServiceImpl implements ArticleTypeService {
	
	@Autowired
	private ArticleTypeMapper articleTypeMapper;
	
	/**
	 * 查询所有栏目类别
	 */
	@Override
	public List<ArticleType> selectArticleType() {
		return articleTypeMapper.selectAllArticleType();
	}
	
	
}
