package bss.dao.pqims;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import ses.model.sms.Supplier;

import bss.model.pqims.PqInfo;
import bss.model.pqims.SupplierPqrecord;
import bss.model.sstps.Select;

/**
 * 
 * @Title:SupplierPqrecordMapper
 * @Description: 供应商质检信息表持久化操作
 * @author Liyi
 * @date 2016-9-18上午9:37:06
 *
 */
public interface SupplierPqrecordMapper {
	
	SupplierPqrecord selectByPrimaryKey(String id);
	
	List<SupplierPqrecord> queryByList(HashMap<String, Object> map);
	
	List<SupplierPqrecord> queryByName(String supplierName);
	
	int insert(SupplierPqrecord supplierPqrecord);
	
	int insertSupplierId(@Param(value="supplierId")String supplierId);
	
	int update(SupplierPqrecord supplierPqrecord);
	
	SupplierPqrecord selectByName(String name);
	
	void delete(String id);
	
	List<SupplierPqrecord> getAll(HashMap<String, Object> map);
}