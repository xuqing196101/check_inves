package extract.dao.common;

import java.util.List;

import extract.model.common.ExtractConditionRelation;

public interface ExtractConditionRelationMapper {

	void insertConditionRelation(List<ExtractConditionRelation> list);

	void deleteConditionRelationByMap(String id);

}
