package ses.service.oms;

import java.util.HashMap;

/**
 * 
 * @Title: PurChaseDepOrg
 * @Description: 机构 多对多服务
 * @author: Tian Kunfeng
 * @date: 2016-9-26上午10:30:11
 */
public interface PurChaseDepOrgService {
	int  saveByMap(HashMap<String, Object> map);
	int delByMap(HashMap<String, Object> map);
	int delByOrgId(HashMap<String, Object> map);
}
