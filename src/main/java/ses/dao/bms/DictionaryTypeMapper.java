package ses.dao.bms;

import java.util.List;

import ses.model.bms.DictionaryType;

public interface DictionaryTypeMapper {
	
	List<DictionaryType> findList();
	
	DictionaryType selectByCode(String code);
	
    int deleteByPrimaryKey(String id);

    int insertSelective(DictionaryType record);

    int updateByPrimaryKeySelective(DictionaryType record);
    
    List<DictionaryType> search(DictionaryType record);
    
    DictionaryType get(String id);
}