package ses.service.sms;

import java.util.List;

import java.math.BigDecimal;
import java.util.List;
import ses.model.sms.AfterSaleSer;



public interface AfterSaleSerService {
	
	/**
	 * 1.获取所有模板对象
	 */
	List<AfterSaleSer> getAll(Integer pageNum);
	/**
	 * 2.添加模板
	 */
	public void add(AfterSaleSer AfterSaleSer);
	
	/**
	 * 3.更新模板
	 */
	public void update(AfterSaleSer AfterSaleSer);
	
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
     *〈简述〉
     * 添加或修改售后服务信息
     *〈详细描述〉
     * @author 
     * @param AfterSaleSer
     */
    public void saveOrUpdateAfterSaleSer(AfterSaleSer AfterSaleSer);
    
    public List<AfterSaleSer> findAfterSaleSer();
    
    
    public void updateAfterSaleSer(AfterSaleSer AfterSaleSer);

}
