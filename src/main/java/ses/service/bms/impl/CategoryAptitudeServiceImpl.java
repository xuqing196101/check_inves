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
	public CategoryAptitude selectByPrimaryKey(String id) {
		// TODO Auto-generated method stub
		return categoryAptitudeMapper.selectByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKeySelective(String id) {
		// TODO Auto-generated method stub
		return categoryAptitudeMapper.updateByPrimaryKeySelective(id);
	}

	@Override
	public List<CategoryAptitude> queryListByCategoryId(String id) {
		// TODO Auto-generated method stub
		return categoryAptitudeMapper.queryListByCategoryId(id);
	}

}
