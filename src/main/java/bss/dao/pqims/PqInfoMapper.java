package bss.dao.pqims;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;

import ses.model.sms.Supplier;

import bss.model.pqims.PqInfo;
import bss.model.sstps.Select;

/**
 * 
 * @Title:PqInfoMapper 
 * @Description: 质检信息表持久化操作
 * @author Liyi
 * @date 2016-9-18上午9:37:06
 *
 */
public interface PqInfoMapper {
	
	/**
	 * 
	 * @Title: deleteByPrimaryKey
	 * @author Liyi 
	 * @date 2016-9-18 上午9:37:51  
	 * @Description:根据id删除
	 * @param:     
	 * @return:
	 */
    int deleteByPrimaryKey(String id);

    /**
     * 
     * @Title: insert
     * @author Liyi 
     * @date 2016-9-18 上午9:38:26  
     * @Description:插入数据
     * @param:     
     * @return:
     */
    int insert(PqInfo record);

    /**
     * 
     * @Title: insertSelective
     * @author Liyi 
     * @date 2016-9-18 上午9:38:36  
     * @Description:插入不为空数据
     * @param:     
     * @return:
     */
    int insertSelective(PqInfo record);

    /**
     * 
     * @Title: selectByPrimaryKey
     * @author Liyi 
     * @date 2016-9-18 上午9:39:05  
     * @Description:根据id查询
     * @param:     
     * @return:
     */
    PqInfo selectByPrimaryKey(String id);

    /**
     * 
     * @Title: updateByPrimaryKeySelective
     * @author Liyi 
     * @date 2016-9-18 上午9:40:15  
     * @Description:更新不为空数据
     * @param:     
     * @return:
     */
    int updateByPrimaryKeySelective(PqInfo record);

    /**
     * 
     * @Title: updateByPrimaryKey
     * @author Liyi 
     * @date 2016-9-18 上午9:41:00  
     * @Description:更新数据
     * @param:     
     * @return:
     */
    int updateByPrimaryKey(PqInfo record);
    
    /**
     * 
     * @Title: queryByList
     * @author Liyi 
     * @date 2016-9-18 下午4:19:35  
     * @Description:查询所有信息
     * @param:     
     * @return:
     */
    List<PqInfo> queryByList(HashMap<String, Object> map);
    
    /**
     * 
     * @Title: queryByCount
     * @author Liyi 
     * @date 2016-9-18 下午4:19:53  
     * @Description:查询记录数
     * @param:     
     * @return:
     */
    Integer queryByCount(String id);
    
    /**
     * 
     * @Title: selectByCondition
     * @author Liyi 
     * @date 2016-9-18 下午4:25:43  
     * @Description:根据不为空的条件查询用户信息
     * @param:     
     * @return:
     */
    List<PqInfo> selectByCondition (PqInfo pqInfo);
    
    /**
     * 
     * @Title: queryByCountSuccess
     * @author Liyi 
     * @date 2016-10-10 下午4:34:01  
     * @Description:查询供应商质检成功的次数
     * @param:     
     * @return:
     */
    BigDecimal queryByCountSuccess(String supplierName);
    
    /**
     * 
     * @Title: queryByCountFail
     * @author Liyi 
     * @date 2016-10-10 下午4:34:52  
     * @Description:查询供应商质检失败的次数
     * @param:     
     * @return:
     */
    BigDecimal queryByCountFail(String supplierName);
    
    /**
     * 
     * @Title: queryDepName
     * @author Liyi 
     * @date 2016-10-10 下午4:35:04  
     * @Description:查询供应商
     * @param:     
     * @return:
     */
    List<String> queryDepName();
    
    /**
     * 
     * @Title: selectByDepName
     * @author Liyi 
     * @date 2016-10-17 上午10:15:35  
     * @Description:根据供应商名称查询
     * @param:     
     * @return:
     */
    List<Supplier> selectByDepName(PqInfo pqInfo);
    
    /**
     * 
     * @Title: selectChose
     * @author Liyi 
     * @date 2016-11-15 上午11:10:54  
     * @Description: select2合同查询
     * @param:     
     * @return:
     */
    List<Select> selectChose(String purchaseType);
    
    /**
     * 
     * @Title: queryPath
     * @author Liyi 
     * @date 2016-11-21 上午9:39:35  
     * @Description: 查询图片路劲
     * @param:     
     * @return:
     */
    String queryPath(String id);
    
    List<PqInfo> selectByContract(HashMap<String, Object> map);
}