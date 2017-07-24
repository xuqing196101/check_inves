/**
 * 
 */
package bss.service.pqims.impl;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;

import ses.model.sms.Supplier;
import ses.util.PropertiesUtil;

import bss.dao.pqims.PqInfoMapper;
import bss.dao.pqims.SupplierPqrecordMapper;
import bss.model.pqims.PqInfo;
import bss.model.pqims.SupplierPqrecord;
import bss.model.sstps.Select;
import bss.service.pqims.PqInfoService;
import bss.service.pqims.SupplierPqrecordService;

/**
 * @Title:PqInfoServiceImpl 
 * @Description: 质检信息管理 实现类
 * @author Liyi
 * @date 2016-9-18下午5:40:03
 *
 */
@Service("SupplierPqrecordService")
public class SupplierPqrecordServiceImpl implements SupplierPqrecordService {

	
	@Resource
	private SupplierPqrecordMapper supplierPqrecordMapper;
	
	@Override
	public List<SupplierPqrecord> getAll(HashMap<String, Object> map) {
		return supplierPqrecordMapper.queryByList(map);
	}

	
	@Override
	public List<SupplierPqrecord> queryByName(String supplierName,Integer pageNum) {
		PropertiesUtil config = new PropertiesUtil("config.properties");
		PageHelper.startPage(pageNum,Integer.parseInt(config.getString("pageSize")));
		return supplierPqrecordMapper.queryByName(supplierName);
	}


	@Override
	public void add(SupplierPqrecord supplierPqrecord) {
		supplierPqrecordMapper.insert(supplierPqrecord);
	}


	@Override
	public void update(SupplierPqrecord supplierPqrecord) {
		supplierPqrecordMapper.update(supplierPqrecord);
	}

	@Override
	public SupplierPqrecord selectByName(String name) {
		return supplierPqrecordMapper.selectByName(name);
	}

	@Override
	public void delete(String id) {
		supplierPqrecordMapper.delete(id);
	}

	@Override
	public void insert(String id) {
		supplierPqrecordMapper.insertSupplierId(id);
		
	}


    @Override
    public List<SupplierPqrecord> selectAll() {
        // TODO Auto-generated method stub
        return null;
    }


    @Override
    public List<SupplierPqrecord> getByAll(Integer pageNum, HashMap<String, Object> map) {
        PropertiesUtil config = new PropertiesUtil("config.properties");
        PageHelper.startPage(pageNum,Integer.parseInt(config.getString("pageSize")));
        return supplierPqrecordMapper.getAll(map);
    }

	
}
