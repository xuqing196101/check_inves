package ses.dao.ems;

import org.apache.ibatis.annotations.Param;
import ses.model.ems.ExpertAuditOpinion;

/**
 * <p>ExpertAuditOpinionMapper </p>
 * <p>Description:专家审核意见 </p>
 * @author XuQing
 * @date 2017-4-1下午5:48:39
 */
public interface ExpertAuditOpinionMapper {
	
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
	 * Description: 根据主键查询
	 *
	 * @author Easong
	 * @version 2017/7/12
	 * @param id
	 * @since JDK1.7
	 */
	ExpertAuditOpinion findByPrimaryKey(@Param("id") String id);

    /**
     *
     * Description: 更新数据
     *
     * @author Easong
     * @version 2017/7/12
     * @param [supplierAuditOpinion]
     * @since JDK1.7
     */
    void updateByPrimaryKeySelective(ExpertAuditOpinion expertAuditOpinion);
    
    /**
     * 
     * Description: 修改下载次数
     * 
     * @author zhang shubin
     * @data 2017年8月22日
     * @param 
     * @return
     */
    void updateIsDownload(String expertId);
}
