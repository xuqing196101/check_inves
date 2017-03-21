package ses.dao.sms;

import java.util.List;

import bss.model.cs.ContractRequired;
import ses.model.sms.AfterSaleSer;
import ses.model.sms.SupplierStars;


public interface AfterSaleSerMapper {
	/**
     *〈简述〉
     * 根据主键查询
     *〈详细描述〉
     * @author LiChenHao
     * @param id
     * @return AfterSaleSer对象
     */
    public AfterSaleSer selectByPrimaryKey(String id);
    
    public ContractRequired selectConRequByPrimaryKey(String id);
    /**
     * 
     * @Title: insert
     * @author LiChenHao 
     * @Description:插入数据
     * @param:     
     * @return:
     */
    int insert(AfterSaleSer record);
    /**
     *〈简述〉
     * 新增数据不得为空
     *〈详细描述〉
     * @author LiChenHao
     */
    public void insertSelective(AfterSaleSer record);
    
    /**
     *〈简述〉
     * 修改不为空
     *〈详细描述〉
     * @author LiChenHao
     */
    public void updateByPrimaryKeySelective(AfterSaleSer record);
    
    /**
     * 
     * @Title: updateByPrimaryKey
     * @author LiChenHao 
     * @Description:更新数据
     * @param:     
     * @return:
     */
    int updateByPrimaryKey(AfterSaleSer record);
    
    /**
	 * 
	 * @Title: deleteByPrimaryKey
	 * @author LiChenHao 
	 * @Description:根据id删除
	 * @param:     
	 * @return:
	 */
    int deleteByPrimaryKey(String id);
    
    /**
     *〈简述〉
     * 根据明细ID查询
     *〈详细描述〉
     * @author LiChenHao
     * @return
     */
    
    List<AfterSaleSer> queryByList();
    
    public List< AfterSaleSer > findAfterSaleSerByrequiredId(String requiredId);
    
    int updateAfterSaleSer(AfterSaleSer AfterSaleSer);

}
