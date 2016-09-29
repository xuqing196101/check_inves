package ses.service.bms.impl;



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
	 * 根据id查询信息附件
	 */
	public CategoryAttchment selectCategoryAttaById(String id) {
		// TODO Auto-generated method stub
		return categoryAttchmentMapper.selectByPrimaryKey(id);
	}

	@Override
	public int insertSelective(CategoryAttchment attachment) {
		// TODO Auto-generated method stub
		return categoryAttchmentMapper.insertSelective(attachment);
	}

	@SuppressWarnings("unused")
	@Override
	public CategoryAttchment selectByCategoryId(String id) {
		CategoryAttchment categoryAttchment =categoryAttchmentMapper.selectByCategoryId(id);
		return categoryAttchmentMapper.selectByCategoryId(id);
	}

}
