package extract.dao.supplier;

import java.util.HashMap;
import java.util.List;

import extract.model.supplier.ExtractConditionRelation;

public interface ExtractConditionRelationMapper {

	int insertConditionRelation(List<ExtractConditionRelation> list);

	void deleteConditionRelationByMap(String id);

	List<String> getByMap(HashMap<Object, Object> hashMap);

	/**
	 * 
	 * <简述> 查询详细抽取条件
	 *
	 * @author Jia Chengxiang
	 * @dateTime 2017-12-7上午11:31:31
	 * @param conditionId
	 * @return
	 */
	List<ExtractConditionRelation> getConTypeList(String conditionId);

	void updateByMap(HashMap<Object, Object> hashMap);

	/**
	 * 
	 * <简述>查询品目等级抽取条件 
	 *
	 * @author Jia Chengxiang
	 * @dateTime 2017-12-7下午7:42:12
	 * @param hashMap
	 * @return
	 */
	List<ExtractConditionRelation> getCateAndLevelByMap(HashMap<Object, Object> hashMap);

}
