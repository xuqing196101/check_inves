package ses.service.bms.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ses.dao.bms.CategoryAptitudeMapper;
import ses.model.bms.CategoryAptitude;
import ses.service.bms.CategoryAptitudeService;

@Service("CategoryAptitudeService")
public class CategoryAptitudeServiceImpl implements CategoryAptitudeService{
	@Autowired
	private  CategoryAptitudeMapper categoryAptitudeMapper;

	@Override
	public void deleteByPrimaryKey(String id) {
		// TODO Auto-generated method stu
	 categoryAptitudeMapper.deleteByPrimaryKey(id);
	}

	@Override
	public void insertSelective(CategoryAptitude aptitude) {
		// TODO Auto-generated method stub
		 categoryAptitudeMapper.insertSelective(aptitude);
	}

	

	@Override
	public void updateByPrimaryKeySelective(CategoryAptitude categoryAptitude) {
		// TODO Auto-generated method stub
		categoryAptitudeMapper.updateByPrimaryKeySelective(categoryAptitude);
	}

	@Override
	public CategoryAptitude queryByCategoryId(String id) {
		// TODO Auto-generated method stub
		return categoryAptitudeMapper.queryByCategoryId(id);
	}

	@Override
	public List<CategoryAptitude> findProductByCategoryId(String id) {
		// TODO Auto-generated method stub
		return categoryAptitudeMapper.findProductByCategoryId(id);
	}

	@Override
	public List<CategoryAptitude> findSaleByCategoryId(String id) {
		// TODO Auto-generated method stub
		return categoryAptitudeMapper.findSaleByCategoryId(id);
	}

	@Override
	public List<CategoryAptitude> findListByCategoryId(String categoryId) {
		// TODO Auto-generated method stub
		return categoryAptitudeMapper.findListByCategoryId(categoryId);
	}

}
