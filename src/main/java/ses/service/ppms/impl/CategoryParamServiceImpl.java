package ses.service.ppms.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ses.dao.ppms.CategoryParamMapper;
import ses.model.bms.Category;
import ses.model.ppms.CategoryParam;
import ses.service.ppms.CategoryParamService;

@Service("CategoryParamService")
public class CategoryParamServiceImpl implements CategoryParamService{
	@Autowired
	private CategoryParamMapper categoryParamMapper;

	
	public List<CategoryParam> selectAll() {
		// TODO Auto-generated method stub
		return categoryParamMapper.selectAll();
	}


	public void deleteByPrimaryKey(String id) {
		categoryParamMapper.deleteByPrimaryKey(id);
	}

	
	public CategoryParam selectByPrimaryKey(String id) {
		// TODO Auto-generated method stub
		return categoryParamMapper.selectByPrimaryKey(id);
	}


	public int updateByPrimaryKeySelective(String id) {
		// TODO Auto-generated method stub
		return categoryParamMapper.updateByPrimaryKeySelective(id);
	}


	@Override
	public List<Category> listByParent(String pid) {
		// TODO Auto-generated method stub
		return categoryParamMapper.listByParent(pid);
	}


	@Override
	public CategoryParam insertSelective(CategoryParam categoryParam) {
		// TODO Auto-generated method stub
		return categoryParamMapper.insertSelective(categoryParam);
	}

}
