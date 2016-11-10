package ses.service.bms;

import java.util.List;

import ses.model.bms.DictionaryData;
import ses.model.sms.SupplierDictionaryData;

public interface DictionaryDataServiceI {
    
    List<DictionaryData> find(DictionaryData dd);
    
    void delete(String id);
    
    void save(DictionaryData dd);
    
    void update(DictionaryData dd);

    List<DictionaryData> listByPage(DictionaryData dd, int i);
    
    List<DictionaryData> findRepeat(DictionaryData dd);
    
    List<DictionaryData> queryAudit(DictionaryData dd);
    
    /**
     * @Title: findSupplierDictionary
     * @author: Wang Zhaohua
     * @date: 2016-11-9 上午11:34:27
     * @Description: 查询供应商相关字典集合
     * @param: @param param
     * @param: @return
     * @return: SupplierDictionaryData
     */
    SupplierDictionaryData findSupplierDictionary();
}
