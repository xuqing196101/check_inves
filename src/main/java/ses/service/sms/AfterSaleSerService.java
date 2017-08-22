package ses.service.sms;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.math.BigDecimal;
import java.util.List;

import ses.model.sms.AfterSaleSer;



public interface AfterSaleSerService {
	
	/**
	 * 1.获取所有模板对象
	 */
	List<AfterSaleSer> getAll(Map<String, Object> map);

	/**
	 * 根据供应商id获取相关数据
	 * @param supplierId
	 * @param map
	 * @return
	 */
	List<AfterSaleSer> queryBySupplierIdList(String supplierId,String goodsName,String code,String name,Map<String,Object> map);
	/**
	 * 2.添加模板
	 */
	public void add(AfterSaleSer afterSaleSer);
	
	/**
	 * 3.更新模板
	 */
	public void update(AfterSaleSer afterSaleSer);
	
	/**
	 * 4.根据主键查询模板
	 */
	AfterSaleSer get(String id);
	
	/**
	 * 5.查询；图片路径
	 */
	String queryPath(String id);
	
	/**
	 * 6.根据主键删除模板
	 */
	public void delete(String id);
	/**
	 * 7.查询模板条数
	 */
	Integer queryByConut(String id);
    
    /**
     *〈简述〉
     * 添加或修改售后服务信息
     *〈详细描述〉
     * @author 
     * @param AfterSaleSer
     */
    public void saveOrUpdateAfterSaleSer(AfterSaleSer afterSaleSer);
    
    public List<AfterSaleSer> findAfterSaleSer();
    
    
    public void updateAfterSaleSer(AfterSaleSer afterSaleSer);
    
    /**
     * 
     *〈简述〉
     *〈详细描述〉
     * @author FengTian
     * @param map
     * @return
     */
    List<AfterSaleSer> selectByAll(HashMap<String, Object> map);

}
