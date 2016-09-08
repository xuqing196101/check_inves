package iss.service.impl;

import iss.dao.ArticleTypeMapper;
import iss.model.ArticleType;
import iss.service.ArticleTypeService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


	
@Service("ArticleService")
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
