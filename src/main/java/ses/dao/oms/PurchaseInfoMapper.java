package ses.dao.oms;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import ses.model.bms.AnalyzeBigDecimal;
import ses.model.oms.PurchaseInfo;

public interface PurchaseInfoMapper {
	
	List<PurchaseInfo> findPurchaseList(HashMap<String, Object> map);
	
	/**
	 * 
	 *〈简述〉根据采购机构id查询采购人信息
	 *〈详细描述〉
	 * @author Administrator
	 * @param id 采购机构Id
	 * @return
	 */
	List<PurchaseInfo> findPurchaseUserList(String id);
	
	int  savePurchase(PurchaseInfo purchaseInfo);
	
	int updatePurchase(PurchaseInfo purchaseInfo);
	
	int delPurchaseByMap(HashMap<String, Object> map);
	
	/**
	 * 
	 *〈简述〉根据采购机构Id查询采购人员数量
	 *〈详细描述〉
	 * @author myc
	 * @param ordId 组织机构Id
	 * @return 如果存在返回大于0的数
	 */
	Integer findPurchaserByOrgId(@Param("orgId")String ordId);
	
	/**
	 * 
	 *〈简述〉业务删除
	 *〈详细描述〉
	 * @author myc
	 * @param id 主键
	 */
	void busDelPurchase(@Param("id")String id);
	
	/**
	 * 
	 * Description: 查询各采购机构人员数量
	 * 
	 * @author Easong
	 * @version 2017年5月31日
	 * @return
	 */
	List<AnalyzeBigDecimal> selectMemNumByOrg();
	
	/**
	 * 
	 * Description: 各类型人员数量
	 * 
	 * @author Easong
	 * @version 2017年6月1日
	 * @param purcahserType
	 * @return
	 */
	BigDecimal selectMenberByType(@Param("purcahserType") Integer purcahserType);
	
	/**
	 * 
	 * Description: 查询男女比例数量
	 * 
	 * @author Easong
	 * @version 2017年6月1日
	 * @param typeId
	 * @return
	 */
	BigDecimal selectMenberByGender(@Param("typeId") String typeId);
	
	/**
	 * 
	 * Description: 查询采购人员总数量
	 * 
	 * @author Easong
	 * @version 2017年6月2日
	 * @return
	 */
	Long selectMemberNum();
}