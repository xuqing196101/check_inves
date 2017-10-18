package extract.dao.supplier;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import extract.model.supplier.ExtractConditionRelation;

public interface ExtractConditionRelationMapper {

	int insertConditionRelation(List<ExtractConditionRelation> list);

	void deleteConditionRelationByMap(String id);

	List<String> getByMap(HashMap<Object, Object> hashMap);

	List<Map<String, String>> getConTypeList(String conditionId);

}
