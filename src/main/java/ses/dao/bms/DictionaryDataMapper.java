package ses.dao.bms;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import ses.model.bms.DictionaryData;
import ses.util.DictionaryDataUtil;

public interface DictionaryDataMapper {
    
    int delete(String id);

    int insert(DictionaryData dd);

    List<DictionaryData> findList(DictionaryData dd);

    /**
     * 不等于等级查询
     * @param dd
     * @return
     */
    List<DictionaryData> findListByNotDefinedLevel(DictionaryData dd);

    int update(DictionaryData dd);
    
    DictionaryData selectByPrimaryKey(String id);
    
    /**
     * 查询除了当前id对象外重复的编码
     * @author Ye MaoLin
     * @param dd
     * @return 数据集合
     */
    List<DictionaryData> findRepeat(DictionaryData dd);

    /**
     * 
    * @Title: queryAudit
    * @Description: 查询审核轮次
    * author: Li Xiaoxiao 
    * @param @param dd
    * @param @return     
    * @return List<DictionaryData>     
    * @throws
     */
    List<DictionaryData> queryAudit(DictionaryData dd);
    
    /**
     * @Title: findByMap
     * @author: Wang Zhaohua
     * @date: 2016-11-9 上午11:33:31
     * @Description: 根据 map 查询集合
     * @param: @param param
     * @param: @return
     * @return: List<DictionaryData>
     */
    List<DictionaryData> findByMap(Map<String, Object> param);
    
    /**
     * 
     *〈简述〉
     *  按照类型查询
     *〈详细描述〉
     * @author myc
     * @param kind 类型
     * @return
     */
    List<DictionaryData> findByKind(String kind);
    
    
    DictionaryData queryByName(@Param("name")String name);
    
    /**
     * 
    * @Title: viewFlows
    * @author FengTian 
    * @date 2017-7-13 下午2:21:54  
    * @Description: 监督根据code获取对应的流程 
    * @param @param map
    * @param @return      
    * @return List<DictionaryData>
     */
    List<DictionaryData> viewFlows(HashMap<String, Object> map);

	DictionaryDataUtil selectByPrimaryCode(String supplierTypeCode);
	
	List<DictionaryData> selectByCode(String supplierTypeCode);
}