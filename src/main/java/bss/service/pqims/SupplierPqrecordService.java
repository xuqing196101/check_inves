/**
 * 
 */
package bss.service.pqims;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;

import net.sf.jsqlparser.statement.delete.Delete;

import ses.model.sms.Supplier;

import bss.model.pqims.PqInfo;
import bss.model.pqims.SupplierPqrecord;
import bss.model.sstps.Select;

/**
 * @Title:PqInfoService 
 * @Description: 质检信息管理 业务接口
 * @author Liyi
 * @date 2016-9-18下午5:08:47
 *
 */
public interface SupplierPqrecordService {
	
	/**
	 * 1.获取所有
	 */
	List<SupplierPqrecord> getAll(HashMap<String, Object> map);
	
	List<SupplierPqrecord> selectAll();
	
	List<SupplierPqrecord> queryByName(String supplierName,Integer pageNum);
	
	SupplierPqrecord selectByName(String name);

	public void add(SupplierPqrecord supplierPqrecord);
	
	public void insert(String id);
	
	public void update(SupplierPqrecord supplierPqrecord);
	
	public void delete(String id);
	
	List<SupplierPqrecord> getByAll(Integer pageNum, HashMap<String, Object> map);
	
}
