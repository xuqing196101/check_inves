package ses.service.bms.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ses.dao.bms.CategoryMapper;
import ses.model.bms.Category;
import ses.service.bms.CategoryService;

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

	

	public void insertSelective(Category category) {
		 categoryMapper.insertSelective(category);
	}

	public List<Category> findTreeByPid(String id) {
		return categoryMapper.findTreeByPid(id);
	}

	public void updateByPrimaryKey(Category category) {
		categoryMapper.updateByPrimaryKey(category);
	}

	public Category selectByPrimaryKey(String id) {
		return categoryMapper.selectByPrimaryKey(id);
	}

	public void updateNameById(String id) {
		categoryMapper.updateNameById(id);
	}

	public int deleteByPrimaryKey(String id) {
		return categoryMapper.deleteByPrimaryKey(id);
	}

	public void updateByPrimaryKeySelective(Category category) {
		categoryMapper.updateByPrimaryKeySelective(category);
	}
	
	public List<Category> readExcel(Category category) {
		return categoryMapper.readExcel(category);
	}

	public List<Category> selectAll() {
		return categoryMapper.selectAll();
	}

	@Override
	public List<Category> listByKeyword(Map map) {
		return categoryMapper.listByKeyword(map);
	}

	@Override
	public List<Category> listByParent(String pid) {
		// TODO Auto-generated method stub
		return categoryMapper.findTreeByPid(pid);
	}

	

}
