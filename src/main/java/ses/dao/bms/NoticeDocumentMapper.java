package ses.dao.bms;

import java.util.List;
import java.util.Map;

import ses.model.bms.NoticeDocument;

public interface NoticeDocumentMapper {
	/**
	 * 
	 * @Title: deleteByPrimaryKey
	 * @author Liyi 
	 * @date 2016-10-18 下午3:42:49  
	 * @Description:根据id进行删除
	 * @param:     
	 * @return:
	 */
    int deleteByPrimaryKey(String id);

    /**
     * 
     * @Title: insertSelective
     * @author Liyi 
     * @date 2016-10-18 下午3:42:03  
     * @Description:新增须知文档
     * @param:     
     * @return:
     */
    int insertSelective(NoticeDocument record);

    /**
     * 
     * @Title: selectByPrimaryKey
     * @author Liyi 
     * @date 2016-10-18 下午3:44:10  
     * @Description:根据ID查询
     * @param:     
     * @return:
     */
    NoticeDocument selectByPrimaryKey(String id);

    /**
     * 
     * @Title: updateByPrimaryKeySelective
     * @author Liyi 
     * @date 2016-10-18 下午3:43:22  
     * @Description:修改须知文档
     * @param:     
     * @return:
     */
    int updateByPrimaryKeySelective(NoticeDocument record);

    /**
     * 
     * @Title: queryByList
     * @author Liyi 
     * @date 2016-10-18 下午3:45:04  
     * @Description:查询列表
     * @param:     
     * @return:
     */
    List<NoticeDocument> queryByList();
    
    /**
     * 
     * @Title: selectByType
     * @author Liyi 
     * @date 2016-10-18 下午3:46:22  
     * @Description:根据模板类型查询 
     * @param:     
     * @return:
     */
    List<NoticeDocument> selectByType(NoticeDocument noticeDocument);
    
    /**
     * @Title: findByMap
     * @author: Wang Zhaohua
     * @date: 2016-11-10 下午3:42:31
     * @Description: findByMap
     * @param: @param param
     * @param: @return
     * @return: List<String>
     */
    List<String> findByMap(Map<String, Object> param);
    
    /**
     * 
     * Description: 查询须知文档名称
     * 
     * @author  zhang shubin
     * @version  2017年4月10日 
     * @param  @param param
     * @param  @return 
     * @return List<String> 
     * @exception
     */
    List<String> findDocNameByMap(Map<String, Object> param);
}