package bss.dao.sstps;

import java.util.List;

import bss.model.sstps.AppraisalContract;

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
    
    List<AppraisalContract> select(AppraisalContract record);
    
    List<AppraisalContract> selectByObject(AppraisalContract record);
    
    List<AppraisalContract> selectByObjectLike(AppraisalContract record);
    
    AppraisalContract selectContractId(AppraisalContract record);

}