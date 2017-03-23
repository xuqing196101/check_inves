package iss.service.ps.impl;

import iss.dao.ps.ArticleTypeMapper;
import iss.model.ps.ArticleType;
import iss.service.ps.ArticleTypeService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ses.dao.bms.CategoryMapper;
import ses.dao.bms.DictionaryDataMapper;
import ses.model.bms.Category;
import ses.model.bms.CategoryTree;
import ses.model.bms.DictionaryData;
import ses.util.DictionaryDataUtil;
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
	
	@Autowired
	private DictionaryDataMapper dictionaryDataMapper;
	
	@Autowired
	private CategoryMapper categoryMapper;
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
		PageHelper.startPage(page,Integer.parseInt(config.getString("pageSizeArticle")));
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

	@Override
	public List<ArticleType> selectShowNumByParId(String parentId) {
		return articleTypeMapper.selectShowNumByParId(parentId);
	}

  @Override
  public void backTree(String parentId, String backCategoryIds, List<CategoryTree> allCategories, String isRoot) {
      String[] ids = backCategoryIds.split(",");
      if (ids != null && ids.length > 0) {
          if ("root".equals(isRoot)) {
              //回显根节点
              DictionaryData data=new DictionaryData();
              data.setKind(6);
              List<DictionaryData> listByPage = dictionaryDataMapper.findList(data);
              for (DictionaryData dictionaryData : listByPage) {
                  //根节点
                  CategoryTree ct = new CategoryTree();
                  ct.setId(dictionaryData.getId());
                  ct.setName(dictionaryData.getName());
                  ct.setIsParent("true");
                  ct.setClassify(dictionaryData.getCode());
                  if (backCategoryIds.indexOf(dictionaryData.getId()) != -1) {
                    // 设置是否被选中
                    ct.setChecked(true);
                  }else {
                    // 设置是否被选中
                    ct.setChecked(false);
                  }
                  allCategories.add(ct);
              }
          }else {
              List < Category > tempNodes = categoryMapper.findPublishTree(parentId, null);
              for (Category category : tempNodes) {
                  CategoryTree ct = new CategoryTree();
                  ct.setName(category.getName());
                  ct.setId(category.getId());
                  ct.setParentId(category.getParentId());
                  // 判断是否为父级节点
                  List < Category > nodesList = categoryMapper.findPublishTree(category.getId(), null);
                  if(nodesList != null && nodesList.size() > 0) {
                    ct.setIsParent("true");
                  }
                  // 判断是否被选中
                  if (backCategoryIds.indexOf(category.getId()) != -1) {
                    // 设置是否被选中
                    ct.setChecked(true);
                  }else {
                    // 设置是否被选中
                    ct.setChecked(false);
                  }
                  allCategories.add(ct);
              }
          }
      }
  }
}
