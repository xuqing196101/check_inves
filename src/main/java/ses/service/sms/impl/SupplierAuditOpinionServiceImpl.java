package ses.service.sms.impl;

import common.utils.JdcgResult;
import javafx.scene.control.Separator;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ses.dao.sms.SupplierAuditOpinionMapper;
import ses.model.sms.SupplierAuditOpinion;
import ses.service.sms.SupplierAuditOpinionService;

import java.util.Date;
import java.util.Map;

@Service("supplierAuditOpinionService")
public class SupplierAuditOpinionServiceImpl implements SupplierAuditOpinionService{

	@Autowired
	private SupplierAuditOpinionMapper supplierAuditOpinionMapper;


	@Override
	public void insertSelective(SupplierAuditOpinion supplierAuditOpinion) {
		supplierAuditOpinionMapper.insertSelective(supplierAuditOpinion);

	}

    @Override
    public JdcgResult insertSelective(SupplierAuditOpinion supplierAuditOpinion, String vertifyFlag) {
        /**
         *
         * Description:记录最终审核意见
         *
         * @author Easong
         * @version 2017/7/12
         * @param [supplierAuditOpinion]
         * @since JDK1.7
         */

        // 非暂存判断
        if(StringUtils.isNotEmpty(vertifyFlag) && "vartify".equals(vertifyFlag) && supplierAuditOpinion.getFlagAduit() == 0){
            // 需要判断用户输入的意见是否为空
            if(StringUtils.isEmpty(supplierAuditOpinion.getOpinion())){
                return JdcgResult.build(500,"审核意见不能为空");
            }
        }
		// 拼接审核意见  例如:同意....+ HelloWorld
		if(StringUtils.isNotEmpty(supplierAuditOpinion.getCateResult())){
            if(StringUtils.isEmpty(supplierAuditOpinion.getOpinion())){
                supplierAuditOpinion.setOpinion(supplierAuditOpinion.getCateResult());
            }else {
                supplierAuditOpinion.setOpinion(supplierAuditOpinion.getCateResult() + supplierAuditOpinion.getOpinion());
            }
		}
        // 判断是不是原有的数据
        if(StringUtils.isNotEmpty(supplierAuditOpinion.getId())){
            // 查询此条数据
            SupplierAuditOpinion byPrimaryKey = supplierAuditOpinionMapper.findByPrimaryKey(supplierAuditOpinion.getId());
            if(byPrimaryKey != null){
                // 更新数据
                supplierAuditOpinionMapper.updateByPrimaryKeySelective(supplierAuditOpinion);
            }
        }else {
            // 设置创建时间
            supplierAuditOpinion.setCreatedAt(new Date());
            // 插入数据
            supplierAuditOpinionMapper.insertSelective(supplierAuditOpinion);
        }
        return JdcgResult.ok();
    }

	@Override
	public SupplierAuditOpinion selectByExpertIdAndflagTime(String supplierId, Integer flagTime) {
		/**
		 *
		 * Description:查询审核意见（通过供应商id和审核次序）
		 *
		 * @author Easong
		 * @version 2017/7/13
		 * @param [supplierId, flagTime]
		 * @since JDK1.7
		 */
		Map<String, Object> map = new HashedMap();
		map.put("supplierId",supplierId);
		map.put("flagTime",flagTime);
        SupplierAuditOpinion supplierAuditOpinion = supplierAuditOpinionMapper.selectByExpertIdAndflagTime(map);
        //  获取意见切割字符串
        if(supplierAuditOpinion != null && StringUtils.isNotEmpty(supplierAuditOpinion.getOpinion())){
            int indexOf = supplierAuditOpinion.getOpinion().indexOf("。");
            supplierAuditOpinion.setOpinion(supplierAuditOpinion.getOpinion().substring(indexOf + 1));
        }
        return supplierAuditOpinion;
	}

	@Override
	public SupplierAuditOpinion selectByPrimaryKey(SupplierAuditOpinion supplierAuditOpinion) {
		
		return supplierAuditOpinionMapper.selectByPrimaryKey(supplierAuditOpinion);
	}

	/**
	 * 
	 * Description:根据供应商ID查询信息
	 * 
	 * @author Easong
	 * @version 2017年7月3日
	 * @param supplierId
	 * @return
	 */
	@Override
	public SupplierAuditOpinion selectByExpertId(String supplierId) {
		return supplierAuditOpinionMapper.selectByExpertId(supplierId);
	}

}
