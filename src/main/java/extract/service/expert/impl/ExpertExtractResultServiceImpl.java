package extract.service.expert.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ses.dao.bms.AreaMapper;
import ses.dao.ems.ExpertTitleMapper;
import ses.model.bms.Area;
import ses.model.ems.Expert;
import ses.model.ems.ExpertTitle;
import ses.model.ems.ProjectExtract;
import ses.util.DictionaryDataUtil;
import system.model.sms.SmsRecord;
import common.utils.SMSUtil;
import extract.dao.expert.ExpertExtractProjectMapper;
import extract.dao.expert.ExpertExtractResultMapper;
import extract.model.expert.ExpertExtractProject;
import extract.model.expert.ExpertExtractResult;
import extract.service.expert.ExpertExtractResultService;

/**
 * 
 * Description: 专家抽取结果
 * 
 * @author zhang shubin
 * @version 2016-9-7
 * @since JDK1.7
 */
@Service("expertExtractResultService")
public class ExpertExtractResultServiceImpl implements ExpertExtractResultService {

    /** 抽取结果 **/
    @Autowired
    private ExpertExtractResultMapper expertExtractResultMapper;
    
    /** 专家抽取项目信息 **/
    @Autowired
    private ExpertExtractProjectMapper expertExtractProjectMapper;
    
    //地区
    @Autowired
    private AreaMapper areaMapper;
    
    private ExpertTitleMapper expertTitleMapper;
    
    /**
     * 保存抽取结果信息
     */
    @Override
    public void save(ExpertExtractResult expertExtractResult) {
    	//专家抽取结果保存
        Map<String, Object> map = new HashMap<>();
        map.put("conditionId", expertExtractResult.getConditionId());
        map.put("expertId", expertExtractResult.getExpertId());
        List<ExpertExtractResult> list = expertExtractResultMapper.findByConditionIdExpertId(map);
        if(list != null && list.size() > 0){
            expertExtractResult.setId(list.get(0).getId());
            expertExtractResult.setUpdatedAt(new Date());
            expertExtractResultMapper.updateByPrimaryKeySelective(expertExtractResult);
        }else{
            String uuid = UUID.randomUUID().toString().toUpperCase().replace("-", "");
            expertExtractResult.setId(uuid);
            expertExtractResult.setIsDeleted((short) 0);
            expertExtractResult.setCreatedAt(new Date());
            expertExtractResult.setUpdatedAt(new Date());
            expertExtractResultMapper.insertSelective(expertExtractResult);
        }
        //项目实施部分结果保存
        ExpertExtractProject expertExtractProject = expertExtractProjectMapper.selectByPrimaryKey(expertExtractResult.getProjectId());
        if(expertExtractProject != null && expertExtractProject.getPackageId() != null){
        	String[] packageIds = expertExtractProject.getPackageId().split(",");
        	for (String packageId : packageIds) {
        		Map<String, Object> proMap = new HashMap<>();
                proMap.put("packageId", packageId);
                proMap.put("expertId", expertExtractResult.getExpertId());
                List<ProjectExtract> proList = expertExtractResultMapper.findByPackageId(proMap);
                ProjectExtract projectExtract = new ProjectExtract();
                if(proList != null && proList.size() > 0){
                	//修改
                	projectExtract = proList.get(0);
                	projectExtract.setUpdatedAt(new Date());
                	projectExtract.setProjectId(packageId);
                	projectExtract.setExpertId(expertExtractResult.getExpertId());
                	projectExtract.setReason(expertExtractResult.getReason());
                	projectExtract.setReviewType(DictionaryDataUtil.getId(expertExtractResult.getExpertCode() == null ? "" : expertExtractResult.getExpertCode()));
                	projectExtract.setOperatingType(expertExtractResult.getIsJoin());
                	projectExtract.setIsProvisional(expertExtractResult.getIsAlternate());
                	projectExtract.setExpertConditionId(expertExtractResult.getConditionId());
                	expertExtractResultMapper.updateProject(projectExtract);
                }else{
                	//新增
                	String uuid = UUID.randomUUID().toString().toUpperCase().replace("-", "");
                	projectExtract.setId(uuid);
                	projectExtract.setProjectId(packageId);
                	projectExtract.setIsDeleted((short) 0);
                	projectExtract.setCreatedAt(new Date());
                	projectExtract.setUpdatedAt(new Date());
                	projectExtract.setExpertId(expertExtractResult.getExpertId());
                	projectExtract.setReason(expertExtractResult.getReason());
                	projectExtract.setReviewType(DictionaryDataUtil.getId(expertExtractResult.getExpertCode() == null ? "" : expertExtractResult.getExpertCode()));
                	projectExtract.setOperatingType(expertExtractResult.getIsJoin());
                	projectExtract.setIsProvisional(expertExtractResult.getIsAlternate());
                	projectExtract.setExpertConditionId(expertExtractResult.getConditionId());
                	expertExtractResultMapper.insertProject(projectExtract);
                }
            }
        }
    }

    
    /**
     * 专家抽取结束短信通知
     */
	@Override
	public void smsNotice(String projectId, Integer sign) {
		ExpertExtractProject expertExtractProject = expertExtractProjectMapper.selectByPrimaryKey(projectId);
		SmsRecord smsRecord = new SmsRecord();
		smsRecord.setSendLink(DictionaryDataUtil.getId("ZJCQDC"));
		smsRecord.setOperator(expertExtractProject.getCreaterId());
		smsRecord.setOrgId(expertExtractProject.getProcurementDepId());
        //评审地点
		StringBuffer sb = new StringBuffer();
        String reviewProvince = expertExtractProject.getReviewProvince();
        String reviewAddress = expertExtractProject.getReviewAddress();
        if(reviewProvince != null && reviewAddress != null){
            Area area1 = areaMapper.selectById(reviewProvince);
            Area area2 = areaMapper.selectById(reviewAddress);
            if(area1 != null){
            	sb.append(area1.getName());
            }
            if(area1 != null && area2 != null){
            	sb.append(area2.getName());
            }
        }
        String address = sb.toString();
		List<ExpertExtractResult> resultList = expertExtractResultMapper.findByProjectId(projectId);
		for (ExpertExtractResult expertExtractResult : resultList) {
			Expert expert = expertExtractResultMapper.findByExpertId(expertExtractResult.getExpertId());
			//执业资格  T_SES_EMS_EXPERT_TITLE
			List<ExpertTitle> tList = expertTitleMapper.selectByExpertId(expert.getId());
			StringBuffer titleSb = new StringBuffer();
			for (ExpertTitle expertTitle : tList) {
				titleSb.append(expertTitle.getQualifcationTitle() == null ? "" : expertTitle.getQualifcationTitle());
				titleSb.append("、");
			}
			String titles = "";
			if(titleSb.length() > 0){
				titles = titleSb.toString().substring(0, titleSb.length() - 1);
            }
			smsRecord.setRecipient(expert.getRelName());
			smsRecord.setReceiveNumber(expert.getMobile());
			//短信发送内容
			String content = "";
			switch (sign) {
				case 1:
					//专家抽取成功（自动/人工）
					content = "【军队采购网通知】"+expert.getRelName()+"专家您好！您已确定参加"+expertExtractProject.getProjectName()+"项目评审。请携带有效身份证件，于xxxx年xx月xx日xx时xx分（评审时间）前往"+address+"参加评审，共需评审"+expertExtractProject.getReviewDays()+"天。采购机构联系人："+expertExtractProject.getContactPerson()+"；手机："+expertExtractProject.getContactNum()+"。";
					break;
				case 2:
					//专家请假（回拨）
					content = "【军队采购网通知】"+expert.getRelName()+"专家您好！"+expertExtractProject.getProjectName()+"项目距离评审时间不足24小时，已不允许取消。如您有特殊情况，请立刻与采购机构联系，联系人："+expertExtractProject.getContactPerson()+"，手机："+expertExtractProject.getContactNum()+"。X小时内未联系，视为可以按时参加采购评审。无故不参加评审活动，将可能失去评审专家资格。";
					break;
			}
			smsRecord.setSendContent(content);
			SMSUtil.sendMsg(smsRecord);
			if(sign == 1 && null == expertExtractResult.getIsAlternate()){
				//正式专家第一次被抽取到
				Integer cNum = expertExtractResultMapper.vaIsOnceJoin(expertExtractResult.getExpertId());
				if(cNum < 2){
					if(!"".equals(titles)){
						titles = "," + titles + "执业资格证书";
					}
					//专家第一次抽取后（自动/人工）
					content = "【军队采购网通知】"+expert.getRelName()+"专家您好！您已确定参加"+expertExtractProject.getProjectName()+"项目评审。请携带有效身份证件,军队人员身份证件(军队专家),专业技术职称证书,学位证书,相关机关事业部门推荐信(可无),国家科技进步三等或军队科技进步二等以上获奖证书(可无)"+ titles +"等注册基本信息彩色扫描件原件，至评审现场进行复查。";
					smsRecord.setSendContent(content);
					SMSUtil.sendMsg(smsRecord);
				}
			}
		}
	}

	
	/**
	 * 根据项目id查询参加抽取的专家
	 */
	@Override
	public List<ExpertExtractResult> findByProjectId(String projectId) {
		return expertExtractResultMapper.findByProjectId(projectId);
	}

}
