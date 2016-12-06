package ses.service.bms;

import java.util.List;

import ses.model.bms.DictionaryType;

public interface DictionaryTypeService {
    
	List<DictionaryType> findList();
	
    DictionaryType selectByCode(String code);
    
    void deleteByPrimaryKey(String id);
    
    void insertSelective(DictionaryType dt);
    
    void updateByPrimaryKeySelective(DictionaryType dt);
    
    List<DictionaryType> listByPage(int i);
    
    List<DictionaryType> search(Integer pageNum,DictionaryType dt);

}
