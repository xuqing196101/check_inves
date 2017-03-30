package bss.service.ob.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ses.util.PropertiesUtil;

import com.github.pagehelper.PageHelper;

import common.constant.Constant;
import common.dao.FileUploadMapper;
import common.model.UploadFile;
import bss.dao.ob.OBSupplierMapper;
import bss.model.ob.OBSupplier;
import bss.service.ob.OBSupplierService;

@Service("oBSupplierService")
public class OBSupplierServiceImpl implements OBSupplierService {

	@Autowired
	private OBSupplierMapper oBSupplierMapper;
	
	@Autowired
    private FileUploadMapper fileDao;

	@Override
	public List<OBSupplier> selectByProductId(String id, Integer page,
			Integer status,String supplierName,String smallPointsName,String smallPointsId) {
		PropertiesUtil config = new PropertiesUtil("config.properties");
		PageHelper.startPage(page,
				Integer.parseInt(config.getString("pageSize")));
		List<OBSupplier> list = null;
		if (status == 1) {
			list = oBSupplierMapper.selectByProductId1(id,supplierName,smallPointsName,smallPointsId);
		} else if (status == 2) {
			list = oBSupplierMapper.selectByProductId2(id,supplierName,smallPointsName,smallPointsId);
		}else{
			list = oBSupplierMapper.selectByProductId(id,supplierName,smallPointsName,smallPointsId);
		}
		return list;
	}

	@Override
	public List<OBSupplier> selectSupplierNum() {
		return oBSupplierMapper.selectSupplierNum();
	}

	@Override
	public void deleteByPrimaryKey(String id) {
		oBSupplierMapper.deleteByPrimaryKey(id);
	}

	@Override
	public int insertSelective(OBSupplier record) {
		return oBSupplierMapper.insertSelective(record);
	}

	@Override
	public OBSupplier selectByPrimaryKey(String id) {
		return oBSupplierMapper.selectByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKeySelective(OBSupplier record) {
		return oBSupplierMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public int yzSupplierName(String supplierId, String smallPointsId,String id) {
		return oBSupplierMapper.yzSupplierName(supplierId, smallPointsId,id);
	}

	@Override
	public int yzShangchuan(String id) {
		return oBSupplierMapper.yzShangchuan(id);
	}

	@Override
	public List<UploadFile> findBybusinessId(String businessId, Integer key) {
		String tableName = Constant.fileSystem.get(key);
		return fileDao.findBybusinessId(businessId, tableName);
	}

	@Override
	public List<OBSupplier> selOfferSupplier(Map<String, Object> map) {
		PropertiesUtil config = new PropertiesUtil("config.properties");
		PageHelper.startPage((Integer)map.get("page"),
				Integer.parseInt(config.getString("pageSize")));
		List<OBSupplier> list = oBSupplierMapper.selOfferSupplier(map);
		return list;
	}
}
