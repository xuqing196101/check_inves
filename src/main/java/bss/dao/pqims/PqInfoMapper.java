package bss.dao.pqims;

import java.util.List;

import bss.model.pqims.PqInfo;

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
    
    List<PqInfo> queryByList(PqInfo pqInfo);
    
    List<PqInfo> selectPqInfo (PqInfo pqInfo);
}