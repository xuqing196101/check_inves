package ses.service.ems.impl;

import com.github.pagehelper.PageHelper;

import common.constant.StaticVariables;
import common.utils.DateUtils;
import common.utils.JdcgResult;

import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import ses.dao.ems.ExpertAuditFileModifyMapper;
import ses.dao.ems.ExpertAuditMapper;
import ses.dao.ems.ExpertAuditOpinionMapper;
import ses.dao.ems.ExpertBatchDetailsMapper;
import ses.dao.ems.ExpertCategoryMapper;
import ses.dao.ems.ExpertMapper;
import ses.dao.ems.ExpertReviewTeamMapper;
import ses.dao.ems.ExpertTitleMapper;
import ses.model.bms.DictionaryData;
import ses.model.bms.User;
import ses.model.ems.Expert;
import ses.model.ems.ExpertAudit;
import ses.model.ems.ExpertAuditFileModify;
import ses.model.ems.ExpertAuditOpinion;
import ses.model.ems.ExpertCategory;
import ses.model.ems.ExpertPublicity;
import ses.model.ems.ExpertReviewTeam;
import ses.model.sms.SupplierAuditOpinion;
import ses.service.ems.ExpertAuditService;
import ses.service.ems.ExpertService;
import ses.util.Constant;
import ses.util.DictionaryDataUtil;
import ses.util.PropertiesUtil;
import ses.util.WfUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
 * <p>Title:ExpertAuditServiceImpl </p>
 * <p>Description: 专家审核</p>
 * @author XuQing
 * @date 2016-12-27上午11:03:02
 */
@Service("expertAuditService")
public class ExpertAuditServiceImpl implements ExpertAuditService {
	@Autowired
	private ExpertAuditMapper mapper;
	
	@Autowired
	private ExpertAuditFileModifyMapper fileModifyMapper;
	
	@Autowired
	private ExpertService expertService;
	
	@Autowired
	private ExpertTitleMapper expertTitleMapper;
	
	@Autowired
	private ExpertMapper expertMapper;
	
	// 注入专家品目关联数据Mapper
	@Autowired
	private ExpertCategoryMapper expertCategoryMapper;
	
	// 注入专家审核Mapper
	@Autowired
	private ExpertAuditMapper expertAuditMapper;

	// 注入专家审核意见Mapper
	@Autowired
	private ExpertAuditOpinionMapper expertAuditOpinionMapper;
	
	@Autowired
	private ExpertReviewTeamMapper expertReviewTeamMapper;
	
	@Autowired
	private ExpertBatchDetailsMapper expertBatchDetailsMapper;

	/**
	 * 
	  * @Title: deleteByPrimaryKey
	  * @author XuQing
	  * @date 2016年12月15日 下午2:26:23  
	  * @Description: TODO 根据主键删除
	  * @param @param id
	 */
	@Override
	public boolean deleteByIds(String[] ids) {
		ExpertCategory expertCategory = new ExpertCategory();
		for(int i=0; i<ids.length; i++){
			//更新品目的状态(0通过);
			ExpertAudit expertAudit = mapper.selectByPrimaryKey(ids[i]);
			if(expertAudit.getAuditFieldId() !=null && expertAudit.getAuditFieldId() !=""){
				expertCategory.setExpertId(expertAudit.getExpertId());
				expertCategory.setCategoryId(expertAudit.getAuditFieldId());
				expertCategory.setAuditStatus(0);
				expertCategoryMapper.updateAuditStatus(expertCategory);
			}
			
			//删除审核记录
			mapper.deleteByPrimaryKey(ids[i]);
		}
		return true;
	}
	/**
     * 
      * @Title: insert
      * @author ShaoYangYang
      * @date 2016年9月26日 下午2:26:34  
      * @Description: TODO 增加  可为空
      * @param @param record
      * @param @return      
      * @return int
     */
	@Override
	public int addAll(ExpertAudit record) {
		return mapper.insert(record);
	}
	  /**
     * 
      * @Title: insertSelective
      * @author ShaoYangYang
      * @date 2016年9月26日 下午2:27:05  
      * @Description: TODO 新增不为空的
      * @param @param record
      * @param @return      
      * @return int
     */
	@Override
	public void add(ExpertAudit record) {

		mapper.insertSelective(record);
	}
	 /**
     * 
      * @Title: selectByPrimaryKey
      * @author ShaoYangYang
      * @date 2016年9月26日 下午2:27:18  
      * @Description: TODO 根据主键查询
      * @param @param id
      * @param @return      
      * @return ExpertAudit
     */
	@Override
	public ExpertAudit findById(String id) {
		
		return mapper.selectByPrimaryKey(id);
	}
	 /**
     * 
      * @Title: updateByPrimaryKeySelective
      * @author ShaoYangYang
      * @date 2016年9月26日 下午2:27:33  
      * @Description: TODO 修改不为空的数据
      * @param @param record
      * @param @return      
      * @return int
     */
	@Override
	public int update(ExpertAudit record) {
		// TODO Auto-generated method stub
		return mapper.updateByPrimaryKeySelective(record);
	}
	 /**
     * 
      * @Title: updateByPrimaryKey
      * @author ShaoYangYang
      * @date 2016年9月26日 下午2:27:47  
      * @Description: TODO 修改全部
      * @param @param record
      * @param @return      
      * @return int
     */
	@Override
	public int updateAll(ExpertAudit record) {
		// TODO Auto-generated method stub
		return mapper.updateByPrimaryKey(record);
	}
	 /**
     * 
      * @Title: auditExpert
      * @author ShaoYangYang
      * @date 2016年9月26日 下午3:04:32  
      * @Description: TODO 审核信息
      * @param @param isPass
      * @param @param remark
      * @param @param user      
      * @return void
     */
	@Override
   public void auditExpert(Expert expert,String remark,User user){
		//查询出以前的审核信息
		List<ExpertAudit> expertAuditList = mapper.selectByExpertId(expert.getId());
		if(expertAuditList!=null && expertAuditList.size()>0){
			for (ExpertAudit expertAudit : expertAuditList) {
				//修改状态为历史状态
//				expertAudit.setIsHistory("1");
				mapper.updateByPrimaryKeySelective(expertAudit);
			}
		}
		ExpertAudit audit = new ExpertAudit();
		audit.setId(WfUtil.createUUID());
		audit.setAuditAt(new Date());
		//审核理由
		audit.setAuditReason(remark);
		//审核结果
		audit.setAuditResult(expert.getStatus());
		audit.setExpertId(expert.getId());
		if(user!=null){
			//审核人id
			audit.setAuditUserId(user.getId());
			//审核人姓名
			audit.setAuditUserName(user.getRelName());
			}
		mapper.insertSelective(audit);
    }
	/**
     * 
      * @Title: getListByExpertId
      * @author ShaoYangYang
      * @date 2016年9月30日 下午4:16:36  
      * @Description: TODO 根据专家id 查询出该专家的审核信息
      * @param @param expertId
      * @param @return      
      * @return List<ExpertAudit>
     */
	@Override
    public List<ExpertAudit> getListByExpertId(String expertId){
		List<ExpertAudit> list = mapper.selectByExpertId(expertId);
		return list;
	}
	
	@Override
	public List<ExpertAudit> findResultByExpertId(String expertId) {
		return mapper.findResultByExpertId(expertId);
	}
	
	@Override
	public List<ExpertAudit> findAllPassExpert() {
		return mapper.findAllPassExpert();
	}
	
	
	@Override
	public List<ExpertAudit> selectFailByExpertId(ExpertAudit expertAudit) {
		return mapper.selectFailByExpertId(expertAudit);
	}
	
	/**
     * @Title: updateByExpertId
     * @author XuQing 
     * @date 2016-12-27 上午11:00:46  
     * @Description:更新isdelete
     * @param @param expertId      
     * @return void
     */
    public void updateIsDeleteByExpertId (String expertId) {
    	mapper.updateIsDeleteByExpertId(expertId);
	}
    
    /**
     * @Title: downloadFile
     * @author XuQing 
     * @date 2016-12-27 下午2:21:18  
     * @Description:生成的word文件下载
     * @param @param fileName
     * @param @param filePath
     * @param @param downFileName
     * @param @return      
     * @return ResponseEntity<byte[]>
     */
	@Override
	public ResponseEntity<byte[]> downloadFile(String fileName, String filePath, String downFileName) {
		try {
			File file=new File(filePath+"/"+fileName);  
			    HttpHeaders headers = new HttpHeaders(); 
			    headers.setContentDispositionFormData("attachment", downFileName);   
			    headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);   
			    ResponseEntity<byte[]> entity = new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(file),headers, HttpStatus.CREATED); 
			    file.delete();
			    return entity;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
    /**
     * @Title: deleteByExpertId
     * @author XuQing 
     * @date 2017-2-14 下午5:05:58  
     * @Description:删除记录
     * @param @param expertId      
     * @return void
     */
	@Override
	public void deleteByExpertId(String expertId) {
		mapper.deleteByExpertId(expertId);
	}
	
	/**
     * @author Ma Mingwei
     * @param expertAudit
     * @return 返回ExpertAudit列表
     * @Description:根据expertAudit封装的条件查询列表
     */
	@Override
	public List<ExpertAudit> getListByExpert(ExpertAudit expertAudit) {
		// TODO Auto-generated method stub
		return mapper.findConditionPassExpert(expertAudit);
	}
	
	@Override
	public List<ExpertAudit> selectbyAuditType(ExpertAudit expertAudit){
		return mapper.selectbyAuditType(expertAudit);
	}
	
	
	/**
	 * @Title: selectByExpertId
	 * @author XuQing 
	 * @date 2017-4-21 下午6:27:57  
	 * @Description:查询附件修改记录
	 * @param @param expertId
	 * @param @return      
	 * @return List<ExpertAuditFileModify>
	 */
	@Override
	public List<ExpertAuditFileModify> selectFileModifyByExpertId(ExpertAuditFileModify expertAuditFileModify) {
		return fileModifyMapper.selectByExpertId(expertAuditFileModify);
	}
	
	/**
	 * @Title: deleteByExpertId
	 * @author XuQing 
	 * @date 2017-4-21 下午6:28:24  
	 * @Description:删除附件修改记录
	 * @param @param expertId      
	 * @return void
	 */
	@Override
	public void delFileModifyByExpertId(String expertId) {
		fileModifyMapper.delByExpertId(expertId);
		
	}

	/**
	 * @Title: addFileInfo
	 * @author XuQing 
	 * @date 2017-4-26 下午5:30:54  
	 * @Description:插入附件退回后修改记录
	 * @param @param expertAuditFileModify      
	 * @return void
	 */
	public void addFileInfo(String businessId, String fileTypeId){
		ExpertAuditFileModify expertAuditFileModify = new ExpertAuditFileModify();
		Expert expert;
		expert = expertService.selectByPrimaryKey(businessId);
		if(expert !=null && expert.getStatus().equals("3")){
			expertAuditFileModify.setExpertId(businessId);
			expertAuditFileModify.setTypeId(fileTypeId);
			fileModifyMapper.insert(expertAuditFileModify);
		}
		
		String expertId = expertTitleMapper.findExpertIdById(businessId);
		if(expertId !=null){
			expert = expertService.selectByPrimaryKey(expertId);
			if(expert !=null && expert.getStatus().equals("3")){
				expertAuditFileModify.setExpertId(expertId);
				expertAuditFileModify.setTypeId(fileTypeId);
				expertAuditFileModify.setListId(businessId);
				fileModifyMapper.insert(expertAuditFileModify);
			}
		}
	}
	
	
	/**
	 * @Title: updateIsDeletedByExpertId
	 * @author XuQing 
	 * @date 2017-5-2 下午5:03:13  
	 * @Description:软删除附件历史信息
	 * @param @param expertId      
	 * @return void
	 */
	@Override
	public void updateIsDeleted(String expertId) {
		fileModifyMapper.updateIsDeletedByExpertId(expertId);
		
	}
	
	/**
     * @Title: findByObj
     * @author XuQing 
     * @date 2017-5-8 上午10:53:24  
     * @Description:唯一校验
     * @param @param expertAudit
     * @param @return      
     * @return Integer
     */
	@Override
	public Integer findByObj(ExpertAudit expertAudit) {
		return mapper.findByObj(expertAudit);
	}
	
	/**
     * @Title: temporaryAudit
     * @date 2017-6-15 下午4:00:26  
     * @Description:暂存审核
     * @param @param expert      
     * @return void
     */
	@Override
	public boolean temporaryAudit(String expertId) {
		Expert expert = new Expert();
		expert.setId(expertId);
		Expert expertInfo = expertMapper.selectByPrimaryKey(expertId);
		String status = expertInfo.getStatus();
		if("0".equals(status) || "15".equals(status) || "16".equals(status) || "9".equals(status)){
			//初审中
			expert.setAuditTemporary(1);
		}else if("4".equals(status)){
			//复审中
			expert.setAuditTemporary(2);
		}else if("6".equals(status)){
			//复查中
			expert.setAuditTemporary(3);
		}else{
			return false;
		}
		expertMapper.updateByPrimaryKeySelective(expert);
		return true;
	}
	
	/**
	 * 
	 * Description:修改公示状态
	 * 
	 * @author Easong
	 * @version 2017年6月27日
	 * @param ids
	 * @return
	 */
	@Override
	public JdcgResult updatePublicityStatus(String[] ids) {
		if(ids != null){
			Expert expert = null;
			for (int i = 0; i < ids.length; i++) {
				expert = new Expert();
				expert.setId(ids[i]);
				// 设置公示状态
				expert.setStatus("-3");
				expert.setUpdatedAt(new Date());
				expertMapper.updateByPrimaryKey(expert);
			}
		}
		return JdcgResult.ok();
	}
	


	/**
	 * 
	 * Description:查询公示专家，公示7天后自动入库
	 * 
	 * @author Easong
	 * @version 2017年6月27日
	 */
	@Override
	public void handlerPublictyExp() {
		// 获取当前时间
	    Date nowDate = new Date();
	    // 查询所有公示供应商
	    String nowDateString = DateUtils.getDateOfFormat(nowDate);
	    List<Expert> list = expertMapper.selectExpByPublicty();
	    if(list != null && !list.isEmpty()){
	        for (Expert expert : list) {
	            // 将公示7天的拟入库供应商入库 
	            // 获取七天后的今天
	            String afterDateString = DateUtils.getDateOfFormat(DateUtils.addDayDate(expert.getAuditAt(), 7));
	            if(nowDateString.equals(afterDateString)){
	                // 审核通过，自动入库
	            	expert.setStatus("4");
	                // 修改
	            	expertMapper.updateByPrimaryKeySelective(expert);
	            }
	        }
         }
	}
	
	/**
	 * 
	 * Description:专家公示列表
	 * 
	 * @author Easong
	 * @version 2017年6月27日
	 * @return
	 */
	@Override
	public List<ExpertPublicity> selectExpByPublictyList(Map<String, Object> map) {
		PropertiesUtil config = new PropertiesUtil("config.properties");
		if(map.get("flag") != null && ("app").equals(map.get("flag"))){
			PageHelper.startPage((Integer) (map.get("page")),10);
		}else{
			PageHelper.startPage((Integer) (map.get("page")),Integer.parseInt(config.getString("pageSize")));
		}
		// 查询公示专家列表
		ExpertPublicity expertPublicityQuery = (ExpertPublicity) map.get("expertPublicity");
		List<ExpertPublicity> list = expertMapper.selectExpByPublictyList(expertPublicityQuery);
		StringBuffer sb = new StringBuffer();
		if(list != null && !list.isEmpty()){
			for (ExpertPublicity expertPublicity : list) {
				// 查询专家类别
				if(expertPublicity.getExpertsTypeId() != null){
					for(String typeId: expertPublicity.getExpertsTypeId().split(",")) {
	                    DictionaryData data = DictionaryDataUtil.findById(typeId);
	                    if(data != null){
	                    	if(6 == data.getKind()) {
	                    		sb.append(data.getName() + "技术" + StaticVariables.COMMA_SPLLIT);
	                        } else {
	                        	sb.append(data.getName() + StaticVariables.COMMA_SPLLIT);
	                        }
	                    }
	                    
	                }
					if(sb.length() > 0){
						String expertsType = sb.toString().substring(0, sb.length() - 1);
						expertPublicity.setExpertsTypeId(expertsType);
						// 清空sb
						sb.delete(0, sb.length());
	                }
				}else{
					expertPublicity.setExpertsTypeId("");
				}
				// 查询选择和未通过的产品小类
				selectChooseOrNoPassCateOfDB(expertPublicity);
				// 查询审核意见
                ExpertAuditOpinion expertAuditOpinion = new ExpertAuditOpinion();
                expertAuditOpinion.setExpertId(expertPublicity.getId());
                expertAuditOpinion.setFlagTime(1);
                ExpertAuditOpinion expertAuditOpinionByCond = expertAuditOpinionMapper.selectByExpertId(expertAuditOpinion);
                if(expertAuditOpinionByCond != null && StringUtils.isNotEmpty(expertAuditOpinionByCond.getOpinion())){
                    expertPublicity.setAuditOpinion(expertAuditOpinionByCond.getOpinion());
				}else {
					// 没意见设置为""
                    expertPublicity.setAuditOpinion("");
				}
			}
		}
		return list;
	}

	private ExpertPublicity selectChooseOrNoPassCateOfDB(ExpertPublicity expertPublicity) {
		/**
		 *
		 * Description:查询选择和未通过的小类
		 *
		 * @author Easong
		 * @version 2017/7/13
		 * @param [expertPublicity]
		 * @since JDK1.7
		 */
		Map<String, Object> selectMap = new HashMap<>();
		// 封装改专家选择了多少小类目录，通过了多少小类目录
		// 通过审核数量
		Integer passCount = expertCategoryMapper.selectRegExpCateCount(expertPublicity.getId());
		expertPublicity.setPassCateCount(passCount);
		// 未通过审核数量
		selectMap.put("expertId", expertPublicity.getId());
		selectMap.put("regType", "six");
		Integer noPassCount = expertAuditMapper.selectRegExpCateCount(selectMap);
		expertPublicity.setNoPassCateCount(noPassCount);
		return expertPublicity;
	}

	@Override
	public ExpertPublicity selectChooseOrNoPassCate(ExpertPublicity expertPublicity) {
		/**
		 *
		 * Description:查询选择和未通过的小类
		 *
		 * @author Easong
		 * @version 2017/7/13
		 * @param [expertPublicity]
		 * @since JDK1.7
		 */
		return this.selectChooseOrNoPassCateOfDB(expertPublicity);
	}

	@Override
	public JdcgResult selectAndVertifyAuditItem(String expertId,int auditFalg) {
		/**
		 *
		 * Description:审核前判断是否有通过项和未通过项--是否符合通过要求
		 *
		 * @author Easong
		 * @version 2017/7/14
		 * @param [expertId]
		 * @since JDK1.7
		 */
        // 判断基本信息是否存在审核未通过项
        Map<String, Object> map = new HashedMap();
        map.put("expertId",expertId);
        map.put("auditFalg",auditFalg);
        
        map.put("regType", Constant.EXPERT_BASIC_INFO_ITEM_FLAG);
        Integer count;
        // 定义选择类型数量
        Integer selectCount;
        count = expertAuditMapper.selectRegExpCateCount(map);
        if(count != null && count > 0){
            return JdcgResult.build(500, "基本信息中有不通过项");
        }

        // 判断专家类型和产品类别分别不能有全不通过项
        // 获取专家选择品目的类型
        map.put("regType", Constant.EXPERT_CATE_INFO_ITEM_FLAG);
        count = expertAuditMapper.selectRegExpCateCount(map);
        Expert expert = expertMapper.selectByPrimaryKey(expertId);
        if(expert != null && expert.getExpertsTypeId() != null){
            String[] split = expert.getExpertsTypeId().split(",");
            selectCount = split.length;
            if(count != null && (selectCount - count) <= 0){
                return JdcgResult.build(500, "类别不能全部为不通过项");
            }
        }
        map.put("regType",Constant.EXPERT_BASIC_BOOK_FLAG);
        count = expertAuditMapper.selectRegExpCateCount(map);
        if(count>0){
        	return JdcgResult.build(500, "专家承诺书和申请表有不通过项");
        }
        // 判断产品类别
        ExpertPublicity expertPublicity = new ExpertPublicity();
        expertPublicity.setId(expertId);
        ExpertPublicity expertPublicityAfter = this.selectChooseOrNoPassCate(expertPublicity);
        // 获取选择的产品类别数量
        selectCount = expertPublicityAfter.getPassCateCount();
        count = expertPublicityAfter.getNoPassCateCount();
        if(count != null && selectCount != null && (selectCount - count) <= 0){
            return JdcgResult.build(500, "产品类别不能全部为不通过项");
        }
        return JdcgResult.ok();
	}

	@Override
	public JdcgResult selectAuditNoPassItemCount(String expertId) {
		/**
		 * @deprecated:查询审核不通过数量
		 *
		 * @Author:Easong
		 * @Date:Created in 2017/7/22
		 * @param: [supplierId]
		 * @return: common.utils.JdcgResult
		 *
		 */
		Map<String, Object> map = new HashedMap();
		map.put("expertId", expertId);
		Integer auditNoPassCount = expertAuditMapper.selectRegExpCateCount(map);
		if(auditNoPassCount != null && auditNoPassCount == 0){
			return JdcgResult.build(500, "没有审核不通过项");
		}
		return JdcgResult.ok();
	}
	
	
	/**
     * 参评类别撤销审核
     * @param expertId
     * @param categoryId
     * @return 
     * @return
     */
	@Override
	public boolean revokeCategoryAudit(String expertId, String[] categoryIds, Integer sign) {
		ExpertAudit expertAudit = new ExpertAudit();
    	expertAudit.setExpertId(expertId);
    	
    	ExpertCategory expertCategory = new ExpertCategory();
		expertCategory.setExpertId(expertId);
		for(int i = 0 ; i < categoryIds.length ; i ++){
			expertCategory.setCategoryId(categoryIds[i]);
			expertCategory.setAuditStatus(1);
			ExpertCategory category = expertCategoryMapper.selectCategoryByCategoryId(expertCategory);
			
			expertAudit.setAuditFieldId(categoryIds[i]);
			expertAudit.setIsDeleted(0);
			expertAudit.setAuditFalg(sign);
			expertAudit.setSuggestType("six");
			//查询是否有审核记录
			ExpertAudit findAuditByExpertId = mapper.findAuditByExpertId(expertAudit);
			if(findAuditByExpertId !=null && category !=null){
				//删除审核信息
				mapper.deleteByExpertIdAndAuditFieldId(expertAudit);
				
				//更新category中间表审核状态
				expertCategory.setAuditStatus(0);
				expertCategoryMapper.updateAuditStatus(expertCategory);
			}
		}
		return true;
	}
	
	
	@Override
	public ExpertAudit findAuditByExpertId(ExpertAudit expertAudit) {
		ExpertAudit findAuditByExpertId = mapper.findAuditByExpertId(expertAudit);
		return findAuditByExpertId;
	}
	@Override
	public List<ExpertReviewTeam> getExpertReviewTeamList(String expertId) {
		List<String> list = expertBatchDetailsMapper.selGroupIdByExpertId(expertId);
		String groupId = "";
		if(list != null && list.size() > 0){
			groupId = list.get(0);
		}
		ExpertReviewTeam expertReviewTeam = new ExpertReviewTeam();
		expertReviewTeam.setGroupId(groupId);
		List<ExpertReviewTeam> reviewTeamList = expertReviewTeamMapper.getExpertReviewTeamList(expertReviewTeam);
		return reviewTeamList;
	}


}
