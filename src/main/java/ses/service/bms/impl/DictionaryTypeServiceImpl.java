package ses.service.bms.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;

import ses.dao.bms.DictionaryTypeMapper;
import ses.model.bms.DictionaryType;
import ses.service.bms.DictionaryTypeService;
import ses.util.PropUtil;
import ses.util.PropertiesUtil;

@Service("dictionaryDataService")
public class DictionaryTypeServiceImpl implements DictionaryTypeService {

	@Autowired
	private DictionaryTypeMapper dictionaryTypeMapper;

	@Override
	public List<DictionaryType> findList() {
		return dictionaryTypeMapper.findList();
	}

	@Override
	public DictionaryType selectByCode(String code) {
		return dictionaryTypeMapper.selectByCode(code);
	}

	@Override
	public void deleteByPrimaryKey(String id) {
		dictionaryTypeMapper.deleteByPrimaryKey(id);
	}

	@Override
	public void insertSelective(DictionaryType dt) {
		dictionaryTypeMapper.insertSelective(dt);
	}

	@Override
	public void updateByPrimaryKeySelective(DictionaryType dt) {
		dictionaryTypeMapper.updateByPrimaryKeySelective(dt);
	}

	@Override
	public List<DictionaryType> listByPage(int page) {
        PageHelper.startPage(page,Integer.parseInt(PropUtil.getProperty("pageSize")));
        List<DictionaryType> dts = dictionaryTypeMapper.findList();
		return dts;
	}


	@Override
	public List<DictionaryType> search(Integer pageNum,DictionaryType dt) {
		PropertiesUtil config = new PropertiesUtil("config.properties");
		PageHelper.startPage(pageNum,Integer.parseInt(config.getString("pageSize")));
		List<DictionaryType> dts = dictionaryTypeMapper.search(dt);
		return dts;
	}



}
