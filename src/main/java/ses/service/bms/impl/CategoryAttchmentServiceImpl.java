package ses.service.bms.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ses.dao.bms.CategoryAttchmentMapper;
import ses.model.bms.CategoryAttchment;
import ses.service.bms.CategoryAttchmentService;

@Service("CategoryAttchmentService")
public class CategoryAttchmentServiceImpl implements CategoryAttchmentService{
	@Autowired
	private CategoryAttchmentMapper categoryAttchmentMapper;
	/**
	 * 新增一个附件
	 */
	public int insert(CategoryAttchment attachment) {
		// TODO Auto-generated method stub
		return categoryAttchmentMapper.insertSelective(attachment);
	}

	/**
	 * 根据categoryId查询附件
	 */
	public List<CategoryAttchment> selectAllArticleAttachments(String id) {
		// TODO Auto-generated method stub
		return categoryAttchmentMapper.selectAllCategoryAttchment(id);
	}

	/**
	 * 根据id查询信息附件
	 */
	public CategoryAttchment selectCategoryAttaById(String id) {
		// TODO Auto-generated method stub
		return categoryAttchmentMapper.selectByPrimaryKey(id);
	}

}
