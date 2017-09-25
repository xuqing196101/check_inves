package extract.dao.supplier;

import java.util.HashMap;
import java.util.List;

import extract.model.supplier.ExtractConditionRelation;

public interface ExtractConditionRelationMapper {

	void insertConditionRelation(List<ExtractConditionRelation> list);

	void deleteConditionRelationByMap(String id);

	List<String> getByMap(HashMap<Object, Object> hashMap);

}
