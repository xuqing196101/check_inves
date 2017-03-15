package bss.service.ob.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ses.util.PropertiesUtil;

import com.github.pagehelper.PageHelper;

import bss.dao.ob.OBSupplierMapper;
import bss.model.ob.OBSupplier;
import bss.service.ob.OBSupplierService;

@Service("oBSupplierService")
public class OBSupplierServiceImpl implements OBSupplierService {

	@Autowired
	private OBSupplierMapper oBSupplierMapper;

	@Override
	public List<OBSupplier> selectByProductId(String id, Integer page,
			Integer status,String supplierName) {
		PropertiesUtil config = new PropertiesUtil("config.properties");
		PageHelper.startPage(page,
				Integer.parseInt(config.getString("pageSize")));
		List<OBSupplier> list = null;
		if (status == 1) {
			list = oBSupplierMapper.selectByProductId1(id,supplierName);
		} else if (status == 2) {
			list = oBSupplierMapper.selectByProductId2(id,supplierName);
		}else{
			list = oBSupplierMapper.selectByProductId(id,supplierName);
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
	public int yzSupplierName(String supplierId, String productId,String id) {
		return oBSupplierMapper.yzSupplierName(supplierId, productId,id);
	}

	@Override
	public int yzShangchuan(String id) {
		return oBSupplierMapper.yzShangchuan(id);
	}
}
