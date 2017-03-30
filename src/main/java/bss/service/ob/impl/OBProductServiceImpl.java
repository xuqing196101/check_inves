package bss.service.ob.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ses.util.PropertiesUtil;

import com.github.pagehelper.PageHelper;

import common.dao.FileUploadMapper;
import bss.dao.ob.OBProductMapper;
import bss.model.ob.OBProduct;
import bss.service.ob.OBProductService;

@Service("oBProductService")
public class OBProductServiceImpl implements OBProductService {

	@Autowired
	private OBProductMapper oBProductMapper;
	
	@Autowired
	private FileUploadMapper fileUploadMapper;

	@Override
	public List<OBProduct> selectByExample(OBProduct example,Integer page) {
		PropertiesUtil config = new PropertiesUtil("config.properties");
		PageHelper.startPage(page,Integer.parseInt(config.getString("pageSize")));
		List<OBProduct> list = oBProductMapper.selectByExample(example);
		return list;
	}

	@Override
	public void deleteByPrimaryKey(String id) {
		oBProductMapper.deleteByPrimaryKey(id);
		fileUploadMapper.deleteByBusinessId(id);
		
	}

	@Override
	public OBProduct selectByPrimaryKey(String id) {
		return oBProductMapper.selectByPrimaryKey(id);
	}

	@Override
	public int insertSelective(OBProduct record) {
		return oBProductMapper.insertSelective(record);
	}

	@Override
	public int updateByPrimaryKeySelective(OBProduct record) {
		return oBProductMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public int yzProductCode(String code,String id) {
		return oBProductMapper.yzProductCode(code,id);
	}

	@Override
	public int yzProductName(String name,String id) {
		return oBProductMapper.yzProductName(name,id);
	}

	@Override
	public int yzorg(String shortName) {
		return oBProductMapper.yzorg(shortName);
	}

	@Override
	public void fab(String id) {
		oBProductMapper.fab(id);
	}

	@Override
	public List<OBProduct> selectAllAmallPointsId(String name) {
		return oBProductMapper.selectAllAmallPointsId(name);
	}

}
