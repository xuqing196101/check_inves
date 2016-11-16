package ses.service.bms.impl;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ses.dao.bms.CategoryAttachmentMapper;
import ses.model.bms.CategoryAttachment;
import ses.service.bms.CategoryAttachmentService;

@Service("categoryAttachmentService")
public class CategoryAttachmentServiceImpl implements CategoryAttachmentService{
	
	@Autowired
	private CategoryAttachmentMapper categoryAttchmentMapper;
	/**
	 * 新增一个附件
	 */
	public int insert(CategoryAttachment attachment) {
		// TODO Auto-generated method stub
		return categoryAttchmentMapper.insertSelective(attachment);
	}


	/**
	 * 根据id查询信息附件
	 */
	public CategoryAttachment selectCategoryAttaById(String id) {
		// TODO Auto-generated method stub
		return categoryAttchmentMapper.selectByPrimaryKey(id);
	}

	@Override
	public int insertSelective(CategoryAttachment attachment) {
		// TODO Auto-generated method stub
		return categoryAttchmentMapper.insertSelective(attachment);
	}

	@SuppressWarnings("unused")
	@Override
	public CategoryAttachment selectByCategoryId(String id) {
		CategoryAttachment categoryAttchment =categoryAttchmentMapper.selectByCategoryId(id);
		return categoryAttchmentMapper.selectByCategoryId(id);
	}


	@Override
	public void deleteByPrimaryKey(String id) {
		categoryAttchmentMapper.deleteByPrimaryKey(id);
		
	}


	@Override
	public void updateByPrimaryKeySelective(
			CategoryAttachment categoryAttachment) {
	categoryAttchmentMapper.updateByPrimaryKeySelective(categoryAttachment);
		
	}

}
