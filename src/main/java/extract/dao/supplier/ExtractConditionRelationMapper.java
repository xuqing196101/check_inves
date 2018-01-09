package extract.dao.supplier;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import extract.model.supplier.ExtractConditionRelation;

public interface ExtractConditionRelationMapper {

	int insertConditionRelation(List<ExtractConditionRelation> list);

	void deleteConditionRelationByCid(String id);

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

	/**
	 * 
	 * <简述>导出抽取条件附表 
	 *
	 * @author Jia Chengxiang
	 * @dateTime 2018-1-5下午4:00:19
	 * @param hashMap
	 * @return
	 */
	List<ExtractConditionRelation> selectConTypeListByMap(
			Map<String, Object> hashMap);

	/**
	 * 
	 * <简述>根据条件id集合批量删除 
	 *
	 * @author Jia Chengxiang
	 * @dateTime 2018-1-5下午4:31:54
	 * @param conditionIds
	 */
	void deleteConditionRelationByCids(Set<String> conditionIds);

}
