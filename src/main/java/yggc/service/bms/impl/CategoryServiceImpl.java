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
    * <p>Company: yggc </p> 
    * @author javazxf
    * @date 
    */
@Service("categoryService")
public class CategoryServiceImpl implements CategoryService {
	
	@Autowired
	private CategoryMapper categoryMapper;

	

	@Override
	public List<Category> listByParent(String pid) {
		return categoryMapper.listByParent(pid);
		
	}



	@Override
	public void insertSelective(Category category) {
		
		 categoryMapper.insertSelective(category);
	}



	


	@Override
	public List<Category> findTreeByPid(String pid) {
		// TODO Auto-generated method stub
		return categoryMapper.findTreeByPid(pid);
	}



	@Override
	public void updateByPrimaryKey(Category category) {
		// TODO Auto-generated method stub
		categoryMapper.updateByPrimaryKey(category);
	}



	@Override
	public Category selectByPrimaryKey(String id) {
		// TODO Auto-generated method stub
		return categoryMapper.selectByPrimaryKey(id);
	}



	
	

	

}
