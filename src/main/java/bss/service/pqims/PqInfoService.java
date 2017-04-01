/**
 * 
 */
package bss.service.pqims;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;

import ses.model.sms.Supplier;

import bss.model.pqims.PqInfo;
import bss.model.sstps.Select;

/**
 * @Title:PqInfoService 
 * @Description: 质检信息管理 业务接口
 * @author Liyi
 * @date 2016-9-18下午5:08:47
 *
 */
public interface PqInfoService {
	
	/**
	 * 1.获取所有模板对象
	 */
	List<PqInfo> getAll(Integer pageNum);
	
	/**
	 * 2.添加模板
	 */
	public void add(PqInfo pqInfo);
	
	/**
	 * 3.更新模板
	 */
	public void update(PqInfo pqInfo);
	
	/**
	 * 4.根据主键查询模板
	 */
	PqInfo get(String id);
	
	/**
	 * 5.根据主键删除模板
	 */
	public void delete(String id);
	
	/**
	 * 6.查询模板条数
	 */
	Integer queryByConut(String id);
	
	/**
	 * 7.根据条件查询
	 */
	List<PqInfo> selectByCondition(PqInfo pqInfo,Integer pageNum);
	
	/**
	 * 8.查询供应商质检合格的次数
	 */
	BigDecimal queryByCountSuccess(String supplierName);
	
	/**
	 * 9.查询供应商质检不合格的次数
	 */
	BigDecimal queryByCountFail(String supplierName);
	
	/**
	 * 10.查询所有供应商
	 */
	List<String> queryDepName(Integer pageNum);
	
	/**
	 * 11.根据供应商名称查询
	 */
	List<Supplier> selectByDepName(Integer pageNum,PqInfo pqInfo);
	
	/*
	 * 12.select2查询合同
	 */
	List<Select> selectChose(String purchaseType);
	
	/*
	 * 13.查询；图片路径
	 */
	String queryPath(String id);
	
	List<PqInfo> selectByContract(HashMap<String, Object> map);
}
