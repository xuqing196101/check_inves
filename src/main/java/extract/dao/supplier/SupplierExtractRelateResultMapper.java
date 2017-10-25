package extract.dao.supplier;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ses.model.sms.Supplier;
import ses.model.sms.SupplierExtRelate;
import extract.model.supplier.SupplierExtractCondition;
import extract.model.supplier.SupplierExtractResult;

public interface SupplierExtractRelateResultMapper {


	/**
	 *
	 * @param record
	 */
	int insertSelective(SupplierExtractResult record);
	
	/**
	 * 查询已经抽取到的供应商
	 * @param recordId
	 * @return
	 */
	List<String> selectSupplierIdListByRecordId(String recordId);
	
	
	Object listExtractionExpertCount(SupplierExtractCondition condition);
	
	
	List<Supplier> listExtractionExpert(SupplierExtractCondition condition);
	
	/**
	 * 记录id查询抽取到的详细供应商信息
	 * @param hashMap2
	 * @return
	 */
	List<SupplierExtractResult> getSupplierListByRid(HashMap<String, String> hashMap2);
	
	/**
	 * 
	 * <简述>查询真实项目抽取结果 
	 *
	 * @author Jia Chengxiang
	 * @dateTime 2017-9-26上午4:39:34
	 * @param hashMap2
	 * @return
	 */
	List<SupplierExtractResult> getSupplierListByRidForRel(HashMap<String, String> hashMap2);
	
	/**
	 * 查询预言项目抽取结果
	 * <简述> 
	 *
	 * @author Jia Chengxiang
	 * @dateTime 2017-9-26上午4:40:03
	 * @param hashMap2
	 * @return
	 */
	List<SupplierExtractResult> getSupplierListByRidForAdv(HashMap<String, String> hashMap2);
	
	/**
	 * 查询该项目已经联系过的供应商
	 * @param projectId
	 * @return
	 */
	List<String> selectSupplierIdListByProjectId(String projectId);
	
	/**
	 * 查询供应商类型 返回typeCode
	 * <简述> 
	 *
	 * @author Jia Chengxiang
	 * @dateTime 2017-9-20下午11:55:13
	 * @param sid
	 * @return
	 */
	List<String> selectTypeCodeBySid(String sid);
	
	/**
	 * 批量添加
	 * <简述> 
	 *
	 * @author Jia Chengxiang
	 * @dateTime 2017-10-24下午8:39:40
	 * @param arrayList
	 */
	void insertAdv(ArrayList<SupplierExtractResult> arrayList);
	
	/**
	 * 批量添加
	 * <简述> 
	 *
	 * @author Jia Chengxiang
	 * @dateTime 2017-10-24下午8:39:48
	 * @param arrayList
	 */
	void insertRel(ArrayList<SupplierExtractResult> arrayList);
	
	/**
	 * 自动抽取的获取供应商列表
	 * <简述> 
	 *
	 * @author Jia Chengxiang
	 * @dateTime 2017-10-12下午3:47:50
	 * @param condition
	 * @return
	 */
	List<Supplier> autoExtractSupplierList(SupplierExtractCondition condition);
	
	/**
	 * 批量添加（自动抽取返回结果）
	 * <简述> 
	 *
	 * @author Jia Chengxiang
	 * @dateTime 2017-10-17上午10:25:25
	 * @param setSupplierExtractResult
	 */
	void insertBatch(ArrayList<SupplierExtractResult> setSupplierExtractResult);
	
	
	/**
	 * 删除保存的记录
	 * <简述> 
	 *
	 * @author Jia Chengxiang
	 * @dateTime 2017-10-17上午11:16:04
	 * @param recordId
	 */
	void deleteByRecordId(String recordId);
	
	/**
	 * 修改预研项目供应商参加状态（自动抽取）
	 * <简述> 
	 *
	 * @author Jia Chengxiang
	 * @dateTime 2017-10-17上午11:23:06
	 * @param supplierExtractResult
	 */
	void updateAdvSupplierJoin(SupplierExtractResult supplierExtractResult);
	
	/**
	 * 修改真实项目供应商参加状态（自动抽取）
	 * <简述> 
	 *
	 * @author Jia Chengxiang
	 * @dateTime 2017-10-17上午11:23:06
	 * @param supplierExtractResult
	 */
	void updateRelSupplierJoin(SupplierExtractResult supplierExtractResult);
	
	/**
	 * 修改供应商参加状态（自动抽取）
	 * <简述> 
	 *
	 * @author Jia Chengxiang
	 * @dateTime 2017-10-17上午11:23:06
	 * @param supplierExtractResult
	 */
	void updateSupplierJoin(SupplierExtractResult supplierExtractResult);
	
	/**
	 * 根据条件id查询抽取结果
	 * <简述> 
	 *
	 * @author Jia Chengxiang
	 * @dateTime 2017-10-23下午5:44:05
	 * @param conditionId
	 * @return
	 */
	List<SupplierExtractResult> selectByConditionId(String conditionId);
	
	/**
	 * 根据主键修改表数据
	 * <简述> 
	 *
	 * @author Jia Chengxiang
	 * @dateTime 2017-10-24下午8:33:43
	 * @param result
	 */
	void updateByPrimaryKeySelective(SupplierExtractResult result);
	
	/**
	 * 
	 * <简述> 按时间查询
	 *
	 * @author Jia Chengxiang
	 * @dateTime 2017-10-24下午8:33:54
	 * @param map
	 * @return
	 */
	List<SupplierExtractResult> selectByUpdateDate(Map<String, String> map);
	
	/**
	 * 
	 * <简述> 按时间查询语言项目
	 *
	 * @author Jia Chengxiang
	 * @dateTime 2017-10-24下午8:34:11
	 * @param map
	 * @return
	 */
	List<SupplierExtRelate> selectAdvByUpdateDate(Map<String, String> map);
	
	/**
	 * 
	 * <简述> 按主键修改预言项目信息
	 *
	 * @author Jia Chengxiang
	 * @dateTime 2017-10-24下午8:34:33
	 * @param result
	 */
	void updateAdvByPrimaryKeySelective(SupplierExtractResult result);
	
	/**
	 * 
	 * <简述> 添加数据到预研项目
	 *
	 * @author Jia Chengxiang
	 * @dateTime 2017-10-24下午8:35:02
	 * @param result
	 */
	void insertAdvSelective(SupplierExtractResult result);
	
	/**
	 * 
	 * <简述> 按主键查询
	 *
	 * @author Jia Chengxiang
	 * @dateTime 2017-10-24下午8:35:28
	 * @param id
	 * @return
	 */
	SupplierExtractResult selectByPrimaryKey(String id);
	
	/**
	 * 
	 * <简述> 按主键查询
	 *
	 * @author Jia Chengxiang
	 * @dateTime 2017-10-24下午8:40:04
	 * @param id
	 * @return
	 */
	SupplierExtractResult selectAdvByPrimaryKey(String id);

	
}