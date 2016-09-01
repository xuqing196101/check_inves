package yggc.service.iss.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import yggc.dao.iss.ArticleTypeMapper;
import yggc.model.iss.ArticleType;
import yggc.service.iss.ArticleTypeService;
	
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
