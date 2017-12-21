package ses.service.bms;

import java.util.List;

import ses.model.bms.ContinentNationRel;

/**
 * 洲-国家关系服务类
 * @author hxg
 * @date 2017-12-19 下午2:02:51
 */
public interface ContinentNationRelService {

	/**
	 * 根据国家id查询
	 * @param nationId
	 * @return
	 */
	ContinentNationRel findByNationId(String nationId);
	
	/**
	 * 根据洲id查询
	 * @param continentId
	 * @return
	 */
	List<ContinentNationRel> findByContinentId(String continentId);

}
