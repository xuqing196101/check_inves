package yggc.service.bms.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import yggc.dao.bms.CategoryMapper;
import yggc.model.bms.Category;
import yggc.service.bms.CategoryService;
   /**
    * 
    * <p>Title:CategoryServiceImpl</p>
    * <p>Description: 采购目录管理实现类</p>
    * @author javazxf
    * @date 
    */
@Service("categoryService")
public class CategoryServiceImpl implements CategoryService {
	
	@Autowired
	private CategoryMapper categoryMapper;

	


	public List<Category> listByParent(String pid) {
		return categoryMapper.listByParent(pid);
		
	}




	public void insertSelective(Category category) {
		
		 categoryMapper.insertSelective(category);
	}



	

	public List<Category> findTreeByPid(String pid) {
		// TODO Auto-generated method stub
		return categoryMapper.findTreeByPid(pid);
	}




	public void updateByPrimaryKey(Category category) {
		// TODO Auto-generated method stub
		categoryMapper.updateByPrimaryKey(category);
	}



	@Override
	public Category selectByPrimaryKey(String id) {
		// TODO Auto-generated method stub
		return categoryMapper.selectByPrimaryKey(id);
	}




	public void updateByPrimaryKey(String id) {
		categoryMapper.updateByPrimaryKey(id);		
	}




	public int deleteByPrimaryKey(String id) {
		// TODO Auto-generated method stub
		return categoryMapper.deleteByPrimaryKey(id);
	}



	
	

	

}
