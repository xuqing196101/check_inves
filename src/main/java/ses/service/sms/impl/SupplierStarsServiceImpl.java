package ses.service.sms.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ses.dao.sms.SupplierStarsMapper;
import ses.model.sms.SupplierStars;
import ses.service.sms.SupplierStarsService;

@Service(value = "supplierStarsService")
public class SupplierStarsServiceImpl implements SupplierStarsService {
	
	
	@Autowired
	private SupplierStarsMapper supplierStarsMapper;
	
	@Autowired
	public List<SupplierStars> findSupplierStars() {
<<<<<<< HEAD
		return supplierStarsMapper.findSupplierStars();
=======
		return supplierStarsMapper.findSupplierStars(null);
>>>>>>> 86663f9259af7ae5cf0c28c23b496c80d05cadf9
	}


	@Override
	public void saveOrUpdateSupplierStars(SupplierStars supplierStars) {
		String id = supplierStars.getId();
		if (id != null && !"".equals(id)) {
			supplierStarsMapper.updateByPrimaryKeySelective(supplierStars);
		} else {
			supplierStarsMapper.insertSelective(supplierStars);
		}
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
