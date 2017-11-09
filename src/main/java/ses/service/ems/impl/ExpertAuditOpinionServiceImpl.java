package ses.service.ems.impl;

import common.utils.JdcgResult;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ses.dao.ems.ExpertAuditOpinionMapper;
import ses.dao.ems.ExpertMapper;
import ses.model.ems.Expert;
import ses.model.ems.ExpertAuditOpinion;
import ses.service.ems.ExpertAuditOpinionService;

import java.util.Date;
import java.util.List;

/**
 * <p>Title:ExpertAuditOpinionServiceImpl </p>
 * <p>Description:专家审核意见 </p>
 * @date 2017-4-1下午5:48:39
 */
@Service("expertAuditOpinionService")
public class ExpertAuditOpinionServiceImpl implements ExpertAuditOpinionService{

	@Autowired 
	private ExpertAuditOpinionMapper mapper;
	
	@Autowired
	private ExpertMapper expertMapper;
	
	@Override
	public void insertSelective(ExpertAuditOpinion expertAuditOpinion) {
		// 拼接审核意见  例如:同意....+ HelloWorld
		if(StringUtils.isNotEmpty(expertAuditOpinion.getCateResult())){
			if(StringUtils.isEmpty(expertAuditOpinion.getOpinion())){
				expertAuditOpinion.setOpinion(expertAuditOpinion.getCateResult());
			}else {
				expertAuditOpinion.setOpinion(expertAuditOpinion.getCateResult() + expertAuditOpinion.getOpinion());
			}
		}
		mapper.insertSelective(expertAuditOpinion);
		Integer flagAudit = expertAuditOpinion.getFlagAudit();
		//修改专家状态
		if(flagAudit != null && flagAudit == 15 || flagAudit == 16){
			Expert expert = new Expert();
			expert.setId(expertAuditOpinion.getExpertId());
			expert.setUpdatedAt(new Date());
			expert.setStatus(flagAudit.toString());
			expertMapper.updateByPrimaryKeySelective(expert);
		}
		
	}

	@Override
	public ExpertAuditOpinion selectByPrimaryKey(ExpertAuditOpinion expertAuditOpinion) {
		List<ExpertAuditOpinion> list = mapper.selectByPrimaryKey(expertAuditOpinion);
//		if(list != null && list.size() > 0){
//			 
//		}else{
//			return null;
//		}
		ExpertAuditOpinion eao=new ExpertAuditOpinion();
		for(ExpertAuditOpinion ea:list){
			if(ea.getOpinion()!=null){
				 eao=ea;
			}
		}
		return eao;
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
        expertAuditOpinion = mapper.selectByExpertId(expertAuditOpinion);
        if(expertAuditOpinion != null && expertAuditOpinion.getFlagTime() != null && expertAuditOpinion.getFlagTime() == 1){
        	//  获取意见切割字符串
        	if(expertAuditOpinion != null && StringUtils.isNotEmpty(expertAuditOpinion.getOpinion())){
        		int indexOf = expertAuditOpinion.getOpinion().indexOf("。");
        		expertAuditOpinion.setOpinion(expertAuditOpinion.getOpinion().substring(indexOf + 1));
        	}
        }
		return expertAuditOpinion;
	}

	/**
	 *
	 * Description:根据专家ID查询信息-公示专用
	 *
	 * @author Easong
	 * @version 2017年7月3日
	 * @param expertId
	 * @return
	 */
	@Override
	public ExpertAuditOpinion selectByExpertId(ExpertAuditOpinion expertAuditOpinion, String flag) {
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

		// 拼接审核意见  例如:同意....+ HelloWorld
		if(StringUtils.isNotEmpty(expertAuditOpinion.getCateResult())){
			if(StringUtils.isEmpty(expertAuditOpinion.getOpinion())){
				expertAuditOpinion.setOpinion(expertAuditOpinion.getCateResult());
			}else {
				expertAuditOpinion.setOpinion(expertAuditOpinion.getCateResult() + expertAuditOpinion.getOpinion());
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
	
	/**
	 * 修改
	 */
	@Override
	public void updateIsDownload(String expertId){
		mapper.updateIsDownload(expertId);
	}

	/**
	 * 记录复审已下载过附件
	 */
	@Override
	public void updateIsDownloadAttch(ExpertAuditOpinion expertAuditOpinion) {
		mapper.updateIsDownloadAttch(expertAuditOpinion);
		
	}

	@Override
	public void deleteByExpertId(ExpertAuditOpinion expertAuditOpinion) {
		mapper.deleteByExpertId(expertAuditOpinion);
		
	}

	
	/**
	 * @version 2017年7月3日
	 * @param expertId
	 * @return
	 */
	@Override
	public ExpertAuditOpinion findByExpertId(ExpertAuditOpinion expertAuditOpinion) {
        expertAuditOpinion = mapper.selectByExpertId(expertAuditOpinion);
		return expertAuditOpinion;
	}

	@Override
	public void updata(ExpertAuditOpinion expertAuditOpinion) {
		 mapper.updateByPrimaryKeySelective(expertAuditOpinion);
		
	}
}
