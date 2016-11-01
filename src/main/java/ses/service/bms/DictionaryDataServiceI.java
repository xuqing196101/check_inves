package ses.service.bms;

import java.util.List;

import ses.model.bms.DictionaryData;

public interface DictionaryDataServiceI {
    
    List<DictionaryData> find(DictionaryData dd);
    
    void delete(String id);
    
    void save(DictionaryData dd);
    
    void update(DictionaryData dd);

    List<DictionaryData> listByPage(DictionaryData dd, int i);

}
