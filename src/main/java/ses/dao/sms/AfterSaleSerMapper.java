package ses.dao.sms;

import bss.model.cs.ContractRequired;
import org.apache.ibatis.annotations.Param;
import ses.model.sms.AfterSaleSer;

import java.util.HashMap;
import java.util.List;


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
     * 
     * @Title: queryByCount
     * @author LiChenHao 
     * @Description:查询记录数
     * @param:     
     * @return:
     */
    Integer queryByCount(String id);
    
    /**
     *〈简述〉
     * 根据明细ID查询
     *〈详细描述〉
     * @author LiChenHao
     * @return
     */
    
    List<AfterSaleSer> queryByList();
    
    public List< AfterSaleSer > findAfterSaleSerByrequiredId(String requiredId);

    /**
     * 根据供应商id查询数据
     * @param supplierId
     * @return
     */
    List<AfterSaleSer> queryBySupplierIdList(@Param("supplierId") String supplierId,@Param("goodsName") String goodsName,@Param("code") String code,@Param("name") String name);
    int updateAfterSaleSer(AfterSaleSer AfterSaleSer);
    
    /**
     * 
     *〈根据合同ID查询售后〉
     *〈详细描述〉
     * @author FengTian
     * @param map
     * @return
     */
    List<AfterSaleSer> selectByAll(HashMap<String, Object> map);

}
