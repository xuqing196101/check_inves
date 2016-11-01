package ses.dao.bms;

import java.util.List;

import ses.model.bms.DictionaryData;

public interface DictionaryDataMapper {
    
    int delete(String id);

    int insert(DictionaryData dd);

    List<DictionaryData> findList(DictionaryData dd);

    int update(DictionaryData dd);
    
    DictionaryData selectByPrimaryKey(String id);

}