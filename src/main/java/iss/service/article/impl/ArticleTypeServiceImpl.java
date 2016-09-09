package iss.service.article.impl;

import iss.dao.article.ArticleTypeMapper;
import iss.model.article.ArticleType;
import iss.service.article.ArticleTypeService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ses.util.PropertiesUtil;

import com.github.pagehelper.PageHelper;


	
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
	public List<ArticleType> selectAllArticleType(Integer page) {
		// TODO Auto-generated method stub
		PropertiesUtil config = new PropertiesUtil("config.properties");
		PageHelper.startPage(page,Integer.parseInt(config.getString("pageSize")));
		return articleTypeMapper.selectAllArticleType(page);
	}


	@Override
	public void updateByPrimaryKey(ArticleType record) {
		// TODO Auto-generated method stub
		articleTypeMapper.updateByPrimaryKey(record);
	}
	
	
}
