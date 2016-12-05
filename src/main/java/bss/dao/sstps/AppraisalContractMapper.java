package bss.dao.sstps;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import bss.model.sstps.AppraisalContract;
import bss.model.sstps.Select;

/**
* @Title:SingleBondMapper 
* @Description: 单一来源合同持久优化
* @author Shen Zhenfei
* @date 2016-9-20下午1:34:03
 */
public interface AppraisalContractMapper {
	
    int deleteById(String id);

    int insert(AppraisalContract record);

    AppraisalContract selectById(String id);

    int update(AppraisalContract record);
    
    int updateAppeal(String id);
    
    List<AppraisalContract> select(AppraisalContract record);
    
    List<AppraisalContract> selectByObject(AppraisalContract record);
    
    List<AppraisalContract> selectByObjectLike(AppraisalContract record);
    
    AppraisalContract selectContractId(AppraisalContract record);
    
    List<AppraisalContract> selectAppraisal(HashMap<String, Object> map);
    
    List<AppraisalContract> selectStatisical(AppraisalContract record);
    
    List<Select> selectChose(String purchaseType);
    
    List<AppraisalContract> selectAppraisalConByContractId(Map<String, Object> map);
    
    List<AppraisalContract> selectByObjectCheck(AppraisalContract record);
    
    List<AppraisalContract> selectByObjectUser(AppraisalContract record);
    
    List<AppraisalContract> selectByOffer(AppraisalContract record);
}