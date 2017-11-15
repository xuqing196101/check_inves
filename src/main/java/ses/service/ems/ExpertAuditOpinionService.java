package ses.service.ems;

import common.utils.JdcgResult;
import ses.model.ems.ExpertAuditOpinion;

/**
 * <p>ExpertAuditOpinionService </p>
 * <p>Description:专家审核意见 </p>
 * @author XuQing
 * @date 2017-4-1下午5:48:39
 */
public interface ExpertAuditOpinionService {
	/**
	 * @Title: insertSelective
	 * @author XuQing 
	 * @date 2017-4-1 下午5:39:10  
	 * @Description:插入数据
	 * @param @param expertAuditOpinionMapper      
	 * @return void
	 */
	void insertSelective (ExpertAuditOpinion expertAuditOpinion );
	
	/**
	 * @Title: selectByPrimaryKey
	 * @author XuQing 
	 * @date 2017-4-1 下午5:39:20  
	 * @Description:查询数据
	 * @param @param expertAuditOpinionMapper      
	 * @return void
	 */
	ExpertAuditOpinion selectByPrimaryKey (ExpertAuditOpinion expertAuditOpinion );

	/**
	 * 
	 * Description:根据专家ID查询信息
	 * 
	 * @author Easong
	 * @version 2017年7月3日
	 * @param expertId
	 * @return
	 */
	ExpertAuditOpinion selectByExpertId(ExpertAuditOpinion expertAuditOpinion);

	/**
	 *
	 * Description:根据专家ID查询信息-公示专用
	 *
	 * @author Easong
	 * @version 2017年10月12日
	 * @param expertId
	 * @return
	 */
	ExpertAuditOpinion selectByExpertId(ExpertAuditOpinion expertAuditOpinion, String flag);

	/**
	 *
	 * Description: 保存审核意见
	 *
	 * @author Easong
	 * @version 2017/7/13
	 * @param [vertifyFlag]
	 * @param [supplierAuditOpinion]
	 * @since JDK1.7
	 */
	JdcgResult insertSelective (ExpertAuditOpinion expertAuditOpinion, String vertifyFlag);
	
	/**
	 *
	 * Description: 修改
	 *
	 * @author zhangshubin
	 * @version 2017/8/21
	 * @param 
	 * @param 
	 * @since JDK1.7
	 */
	void updateIsDownload(String expertId);
	
	/**
	 * 记录复审已下载过附件
	 * @param expertAuditOpinion
	 */
	void updateIsDownloadAttch(ExpertAuditOpinion expertAuditOpinion);
	
	void deleteByExpertId(ExpertAuditOpinion expertAuditOpinion);
	
	ExpertAuditOpinion findByExpertId(ExpertAuditOpinion expertAuditOpinion);
	
	
	void updata(ExpertAuditOpinion expertAuditOpinion);
}
