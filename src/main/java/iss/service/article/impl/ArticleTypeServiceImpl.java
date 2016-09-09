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
	

	@Override
	public ArticleType selectByPrimaryKey(String id) {
		// TODO Auto-generated method stub
		return articleTypeMapper.selectByPrimaryKey(id);
	}


	@Override
	public List<ArticleType> selectAllArticleType() {
		// TODO Auto-generated method stub
		return articleTypeMapper.selectAllArticleType();
	}


	@Override
	public void updateByPrimaryKey(ArticleType record) {
		// TODO Auto-generated method stub
		articleTypeMapper.updateByPrimaryKey(record);
	}
	
	
}
