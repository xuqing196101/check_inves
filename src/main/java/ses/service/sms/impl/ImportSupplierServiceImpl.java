package ses.service.sms.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;

import ses.dao.sms.ImportSupplierMapper;
import ses.model.sms.ImportSupplierWithBLOBs;
import ses.service.sms.ImportSupplierService;
import ses.util.PropertiesUtil;


/**
* <p>Title:ImportSupplierServiceImpl </p>
* <p>Description:进口供应商服务层实现 </p> 
* @author Song Biaowei
* @date 2016-9-7下午5:55:01
 */

@Service
public class ImportSupplierServiceImpl implements ImportSupplierService {
	@Autowired
	private ImportSupplierMapper isMapper;
	
	@Override
	public void register(ImportSupplierWithBLOBs is) {
		isMapper.insertSelective(is);
	}

	@Override
	public void updateRegisterInfo(ImportSupplierWithBLOBs is) {
		isMapper.updateByPrimaryKeySelective(is);
	}

	@Override
	public ImportSupplierWithBLOBs findById(String id) {
		ImportSupplierWithBLOBs is=isMapper.selectByPrimaryKey(id);
		return is;
	}

	@Override
	public int getCount(ImportSupplierWithBLOBs is) {
		int count=isMapper.getCount(is);
		return count;
	}

	@Override
	public List<ImportSupplierWithBLOBs> selectByFsInfo(
			ImportSupplierWithBLOBs is,Integer page) {
		PropertiesUtil config = new PropertiesUtil("config.properties");
		PageHelper.startPage(page,Integer.parseInt(config.getString("pageSize")));
		List<ImportSupplierWithBLOBs> sfiList=isMapper.selectByFsInfo(is);
		return sfiList;
	}

	@Override
	public ImportSupplierWithBLOBs selectByPrimaryKey(
			ImportSupplierWithBLOBs is) {
		ImportSupplierWithBLOBs importSupplierWithBLOBs=isMapper.selectByPrimaryKey(is.getId());
		return importSupplierWithBLOBs;
	}

	@Override
	public String selectIdByLoginName(ImportSupplierWithBLOBs is) {
		String id=isMapper.selectIdByLoginName(is);
		return id;
	}

	@Override
	public void delete(String id) {
		isMapper.deleteByPrimaryKey(id);
	}
}
