package ses.service.sms.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import common.utils.JdcgResult;

import ses.dao.sms.SupplierStarsMapper;
import ses.model.sms.SupplierStars;
import ses.service.sms.SupplierStarsService;
import ses.util.PropertiesUtil;

@Service(value = "supplierStarsService")
public class SupplierStarsServiceImpl implements SupplierStarsService {
	
	
	@Autowired
	private SupplierStarsMapper supplierStarsMapper;
	
	@Override
	public List<SupplierStars> findSupplierStars(Map<String, Object> map) {
		PropertiesUtil config = new PropertiesUtil("config.properties");
		PageHelper.startPage((Integer) (map.get("page")),
				Integer.parseInt(config.getString("pageSize")));
		return supplierStarsMapper.findSupplierStars(null);
	}


	@Override
	public JdcgResult saveOrUpdateSupplierStars(SupplierStars supplierStars) {
		String id = supplierStars.getId();
		if (id != null && !"".equals(id)) {
			supplierStarsMapper.updateByPrimaryKeySelective(supplierStars);
		} else {
			supplierStarsMapper.insertSelective(supplierStars);
		}
		return JdcgResult.ok();
	}


	@Override
	public SupplierStars get(String id) {
		return supplierStarsMapper.selectByPrimaryKey(id);
	}


	@Override
	public void updateStatus(SupplierStars supplierStars) {
		supplierStarsMapper.updateStatus(supplierStars);
	}


	@Override
	public void delete(String ids) {
		for (String id : ids.split(",")) {
			supplierStarsMapper.deleteByPrimaryKey(id);
		}
	}
}
