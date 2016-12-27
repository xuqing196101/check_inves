package iss.service.ps.impl;

import iss.dao.ps.ArticleTypeMapper;
import iss.model.ps.ArticleType;
import iss.service.ps.ArticleTypeService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ses.util.PropertiesUtil;

import com.github.pagehelper.PageHelper;

/*
 *@Title:ArticleTypeServiceImpl
 *@Description:文章类型service实现类
 *@author QuJie
 *@date 2016-9-12上午8:52:34
 */
@Service("articleTypeService")
public class ArticleTypeServiceImpl implements ArticleTypeService {
	
	@Autowired
	private ArticleTypeMapper articleTypeMapper;
	
	/**
	 * 根据id查找文章类型
	 */
	@Override
	public ArticleType selectTypeByPrimaryKey(String id) {
		return articleTypeMapper.selectTypeByPrimaryKey(id);
	}

	/**
	 * 查找所有文章类型(分页)
	 */
	@Override
	public List<ArticleType> selectAllArticleType(Integer page) {
		PropertiesUtil config = new PropertiesUtil("config.properties");
		PageHelper.startPage(page,Integer.parseInt(config.getString("pageSize")));
		return articleTypeMapper.selectAllArticleType();
	}
	
	/**
	 * 查找所有文章类型
	 */
	@Override
	public List<ArticleType> getAll() {
		return articleTypeMapper.getAll();
	}
	/**
	 * 根据主键修改文章类型
	 */
	@Override
	public void updateByPrimaryKey(ArticleType record) {
		articleTypeMapper.updateByPrimaryKey(record);
	}
	
	/**
	 * 为首页查询所有文章类型
	 */
	@Override
	public List<ArticleType> selectAllArticleTypeForSolr() {
		return articleTypeMapper.selectAllArticleTypeForSolr();
	}


	@Override
	public ArticleType selectArticleTypeByCode(String code) {
		
		return articleTypeMapper.selectArticleTypeByCode(code);
	}

	@Override
	public List<ArticleType> selectByParentId(String parentId) {
		return articleTypeMapper.selectByParentId(parentId);
	}

	@Override
	public void updateShowNum() {
		articleTypeMapper.updateShowNum();
	}
}
