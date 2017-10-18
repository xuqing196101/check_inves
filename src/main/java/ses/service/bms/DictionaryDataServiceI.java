package ses.service.bms;

import java.util.List;

import ses.model.bms.DictionaryData;
import ses.model.sms.SupplierDictionaryData;

public interface DictionaryDataServiceI {
    
    List<DictionaryData> find(DictionaryData dd);

    List<DictionaryData> findByNotDefinedLevel(DictionaryData dd);
    
    void delete(String id);
    
    void save(DictionaryData dd);
    
    void update(DictionaryData dd);

    List<DictionaryData> listByPage(DictionaryData dd, int i);
    
    List<DictionaryData> findRepeat(DictionaryData dd);
    
//    List<DictionaryData> queryAudit(DictionaryData dd);
    
    /**
     * @Title: getSupplierDictionary
     * @author: Wang Zhaohua
     * @date: 2016-11-9 上午11:34:27
     * @Description: 查询供应商相关字典集合
     * @param: @param param
     * @param: @return
     * @return: SupplierDictionaryData
     */
    SupplierDictionaryData getSupplierDictionary();
    
    /**
     * 
     *〈简述〉根据ID获取对象
     *〈详细描述〉
     * @author Wang Wenshuai
     * @param id
     * @return
     */
    DictionaryData getDictionaryData(String id);
    
    /**
     * 
     *〈简述〉
     *  根据字典类型查询数据
     *〈详细描述〉
     * @author myc
     * @param kind 类型Id
     * @return
     */
    public List<DictionaryData> findByKind(String kind);
    
    public List<DictionaryData> list(DictionaryData dd);
    
    List<DictionaryData> findListByScore(DictionaryData dd);
}
