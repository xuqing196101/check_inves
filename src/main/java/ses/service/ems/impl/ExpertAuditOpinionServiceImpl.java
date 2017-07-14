package ses.service.ems.impl;

import common.utils.JdcgResult;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ses.dao.ems.ExpertAuditOpinionMapper;
import ses.model.ems.ExpertAuditOpinion;
import ses.service.ems.ExpertAuditOpinionService;

import java.util.Date;

/**
 * <p>Title:ExpertAuditOpinionServiceImpl </p>
 * <p>Description:专家审核意见 </p>
 * @author XuQing
 * @date 2017-4-1下午5:48:39
 */
@Service("expertAuditOpinionService")
public class ExpertAuditOpinionServiceImpl implements ExpertAuditOpinionService{

	@Autowired 
	private ExpertAuditOpinionMapper mapper;
	
	@Override
	public void insertSelective(ExpertAuditOpinion expertAuditOpinion) {
		mapper.insertSelective(expertAuditOpinion);
		
	}

	@Override
	public ExpertAuditOpinion selectByPrimaryKey(ExpertAuditOpinion expertAuditOpinion) {
		return mapper.selectByPrimaryKey(expertAuditOpinion);
	}

	/**
	 * 
	 * Description:根据专家ID查询信息
	 * 
	 * @author Easong
	 * @version 2017年7月3日
	 * @param expertId
	 * @return
	 */
	@Override
	public ExpertAuditOpinion selectByExpertId(ExpertAuditOpinion expertAuditOpinion) {
		return mapper.selectByExpertId(expertAuditOpinion);
	}

	@Override
	public JdcgResult insertSelective(ExpertAuditOpinion expertAuditOpinion, String vertifyFlag) {
		/**
		 *
		 * Description:保存审核意见
		 *
		 * @author Easong
		 * @version 2017/7/13
		 * @param [expertAuditOpinion, vertifyFlag]
		 * @since JDK1.7
		 */

		// 非暂存判断
		if(StringUtils.isNotEmpty(vertifyFlag) && "vartify".equals(vertifyFlag)){
			// 需要判断用户输入的意见是否为空
			if(StringUtils.isEmpty(expertAuditOpinion.getOpinion())){
				return JdcgResult.build(500,"审核意见不能为空");
			}
		}

		// 判断是不是原有的数据
		if(StringUtils.isNotEmpty(expertAuditOpinion.getId())){
			// 查询此条数据
            ExpertAuditOpinion byPrimaryKey = mapper.findByPrimaryKey(expertAuditOpinion.getId());
			if(byPrimaryKey != null){
			    // 修改时间
				expertAuditOpinion.setUpdatedAt(new Date());
				// 更新数据
				mapper.updateByPrimaryKeySelective(expertAuditOpinion);
			}
		}else {
			// 设置创建时间
			expertAuditOpinion.setCreatedAt(new Date());
            // 修改时间
            expertAuditOpinion.setUpdatedAt(new Date());
			// 插入数据
			mapper.insertSelective(expertAuditOpinion);
		}
		return JdcgResult.ok();
	}

}
