package ses.service.sms.impl;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;

import ses.dao.sms.SupplierMapper;
import ses.dao.sms.SupplierStarsMapper;
import ses.model.sms.Supplier;
import ses.model.sms.SupplierStars;
import ses.service.sms.SupplierLevelService;
import ses.util.PropertiesUtil;

@Service(value = "supplierLevelService")
public class SupplierLevelServiceImpl implements SupplierLevelService {

	@Autowired
	private SupplierMapper supplierMapper;
	
	@Autowired
	private SupplierStarsMapper supplierStarsMapper;

	@Override
	public List<Supplier> findSupplier(Supplier supplier, int page) {
		PropertiesUtil config = new PropertiesUtil("config.properties");
		PageHelper.startPage(page, Integer.parseInt(config.getString("pageSize")));
		
		// 查询星级规则
		SupplierStars supplierStars = new SupplierStars();
		supplierStars.setStatus(1);
		List<SupplierStars> listSupplierStars = supplierStarsMapper.findSupplierStars(supplierStars);
		
		String level = supplier.getLevel();
		if(level != null && !"".equals(level)) {
			if (level.equals("1")) {
				supplier.setStartScore(listSupplierStars.get(0).getOneStars());
				supplier.setEndScore(listSupplierStars.get(0).getTwoStars());
			}
			if (level.equals("2")) {
				supplier.setStartScore(listSupplierStars.get(0).getTwoStars());
				supplier.setEndScore(listSupplierStars.get(0).getThreeStars());
			}
			if (level.equals("3")) {
				supplier.setStartScore(listSupplierStars.get(0).getThreeStars());
				supplier.setEndScore(listSupplierStars.get(0).getFourStars());
			}
			if (level.equals("4")) {
				supplier.setStartScore(listSupplierStars.get(0).getFourStars());
				supplier.setEndScore(listSupplierStars.get(0).getFiveStars());
			}
			if (level.equals("5")) {
				supplier.setStartScore(listSupplierStars.get(0).getFiveStars());
			}
		}
		supplier.setStatus(3);
		List<Supplier> listSuppliers = supplierMapper.findSupplier(supplier);
		
		for (SupplierStars ss : listSupplierStars) {
			for (Supplier s : listSuppliers) {
				Integer score = s.getScore();
				Integer oneStars = ss.getOneStars();
				Integer twoStars = ss.getTwoStars();
				Integer threeStars = ss.getThreeStars();
				Integer fourStars = ss.getFourStars();
				Integer fiveStars = ss.getFiveStars();
				if(score != null){
					if (score < oneStars) {
						s.setLevel("无级别");
					} else if (score < twoStars) {
						s.setLevel("一级");
					} else if (score < threeStars) {
						s.setLevel("二级");
					} else if (score < fourStars) {
						s.setLevel("三级");
					} else if (score < fiveStars) {
						s.setLevel("四级");
					} else {
						s.setLevel("五级");
					}
				}
			}
		}
		return listSuppliers;
	}

	@Override
	public void updateScore(Supplier supplier, String scores) {
		supplier = supplierMapper.getSupplier(supplier.getId());
		Integer score = supplier.getScore();
		for (String str : scores.split(",")) {
			score += Integer.valueOf(str);
		}
		supplier.setScore(score);
		supplierMapper.updateScore(supplier);
	}

}
