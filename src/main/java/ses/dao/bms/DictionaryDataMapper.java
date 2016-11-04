package ses.dao.bms;

import java.util.List;

import ses.model.bms.DictionaryData;

public interface DictionaryDataMapper {
    
    int delete(String id);

    int insert(DictionaryData dd);

    List<DictionaryData> findList(DictionaryData dd);

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
}