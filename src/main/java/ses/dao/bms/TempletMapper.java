package ses.dao.bms;

import java.util.List;

import ses.model.bms.Templet;



/**
 * 
 * @Title:TempletMapper
 * @Description:模板持久化接口 
 * @author Liyi
 * @date 2016-9-1下午4:13:28
 *
 */
public interface TempletMapper {
	
	/**
	 * 
	 * @Title: deleteByPrimaryKey
	 * @author Liyi 
	 * @date 2016-8-31 下午5:45:36  
	 * @Description:根据主键删除
	 * @param: id     
	 * @return: Integer
	 */
	Integer deleteByPrimaryKey(String id);

	/**
	 * 
	 * @Title: insertSelective
	 * @author Liyi 
	 * @date 2016-8-31 下午5:46:21  
	 * @Description: 添加
	 * @param:  record   
	 * @return: Integer
	 */
    Integer insertSelective(Templet record);

    /**
     * 
     * @Title: selectByPrimaryKey
     * @author Liyi 
     * @date 2016-8-31 下午5:47:00  
     * @Description:根据主键查询
     * @param: id    
     * @return:Templet
     */
    Templet selectByPrimaryKey(String id);
    
    /**
     * 
     * @Title: updateByPrimaryKeySelective
     * @author Liyi 
     * @date 2016-8-31 下午5:57:19  
     * @Description: 根据主键更新
     * @param:     
     * @return:
     */
    Integer updateByPrimaryKeySelective(Templet record);

    /**
     * 
     * @Title: queryByList
     * @author Liyi 
     * @date 2016-8-31 下午5:58:01  
     * @Description: 查询列表
     * @return: List<Templet>
     */
    List<Templet> queryByList();
    
    /**
     * 
     * @Title: queryByCount
     * @author Liyi 
     * @date 2016-8-31 下午5:58:22  
     * @Description: 查询列表数
     * @param:     
     * @return:
     */
    Integer queryByCount(); 
    
    /**
     * 
     * @Title: selectByType
     * @author Liyi 
     * @date 2016-10-9 上午11:18:21  
     * @Description:根据模板类型查询
     * @param:     
     * @return:
     */
    List<Templet>selectByType();
}