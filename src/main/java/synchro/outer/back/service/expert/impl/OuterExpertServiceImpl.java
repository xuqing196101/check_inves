package synchro.outer.back.service.expert.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;

import common.constant.Constant;
import common.dao.FileUploadMapper;
import common.model.UploadFile;
import ses.dao.bms.UserMapper;
import ses.dao.ems.ExpertAuditFileModifyMapper;
import ses.dao.ems.ExpertAuditMapper;
import ses.dao.ems.ExpertBatchDetailsMapper;
import ses.dao.ems.ExpertBatchMapper;
import ses.dao.ems.ExpertCategoryMapper;
import ses.dao.ems.ExpertEngHistoryMapper;
import ses.dao.ems.ExpertEngModifyMapper;
import ses.dao.ems.ExpertMapper;
import ses.dao.ems.ExpertTitleMapper;
import ses.model.bms.DictionaryData;
import ses.model.bms.RoleUser;
import ses.model.bms.User;
import ses.model.ems.Expert;
import ses.model.ems.ExpertAudit;
import ses.model.ems.ExpertAuditFileModify;
import ses.model.ems.ExpertAuditOpinion;
import ses.model.ems.ExpertBatch;
import ses.model.ems.ExpertBatchDetails;
import ses.model.ems.ExpertCategory;
import ses.model.ems.ExpertEngHistory;
import ses.model.ems.ExpertFinalInspect;
import ses.model.ems.ExpertHistory;
import ses.model.ems.ExpertTitle;
import ses.model.sms.SupplierSynch;
import ses.service.bms.UserServiceI;
import ses.service.ems.ExpertAuditOpinionService;
import ses.service.ems.ExpertCategoryService;
import ses.service.ems.ExpertFinalInspectService;
import ses.service.ems.ExpertService;
import ses.util.DictionaryDataUtil;
import synchro.outer.back.service.expert.OuterExpertService;
import synchro.service.SynchRecordService;
import synchro.util.DateUtils;
import synchro.util.FileUtils;
import synchro.util.OperAttachment;

/**
 * 版权：(C) 版权所有
 * <简述> 专家业务service
 * <详细描述>
 *
 * @author WangHuijie
 * @see
 */
@Service
public class OuterExpertServiceImpl implements OuterExpertService {

    /**
     * 同步service
     */
    @Autowired
    private SynchRecordService recordService;

    /**
     * 用户service
     */
    @Autowired
    private UserServiceI userService;

    /**
     * 专家service
     */
    @Autowired
    private ExpertService expertService;
    
    @Autowired
	private ExpertFinalInspectService finalInspectService;
    /**
     * 专家-品目service
     */
    @Autowired
    private ExpertCategoryService expertCategoryService;


    @Autowired
    private ExpertTitleMapper expertTitleMapper;

    @Autowired
    private FileUploadMapper fileUploadMapper;

    @Autowired
    private ExpertAuditMapper expertAuditMapper;
    @Autowired
    private ExpertEngHistoryMapper expertEngHistoryMapper;
    @Autowired
    private ExpertEngModifyMapper expertEngModifyMapper;
    @Autowired
    private ExpertAuditFileModifyMapper expertAuditFileModifyMapper;
    @Autowired
    private ExpertMapper expertMapper;

    @Autowired
    private ExpertCategoryMapper expertCategoryMapper;

    // 专家审核意见Service
    @Autowired
    private ExpertAuditOpinionService expertAuditOpinionService;
    // 专家审核批次
    @Autowired
    private ExpertBatchDetailsMapper expertBatchDetailsMapper;
    @Autowired
    private ExpertBatchMapper expertBatchMapper;

    /**
     * @see synchro.outer.back.service.supplier.OuterReadExpertService#backupCreated()
     */

    @Autowired
    private UserMapper userMapper;

    @Override
    public void backupCreated(String startTime, String endTime) {
        getCreatedData(startTime, endTime);
    }

    /**
     * @see synchro.outer.back.service.supplier.OuterReadExpertService#backupModified()
     */
    @Override
    public void backupModified() {
        getModifiedData();
    }

    @Override
    public void backModifyExpert(String startTime, String endTime) {
        List<Expert> expertList = expertService.getAuditExpertByDate(startTime, endTime);
        List<Expert> experts = new ArrayList<Expert>();
        if (null != expertList && !expertList.isEmpty()) {
            ExpertEngHistory expertEngHistory = null;
            ExpertAuditFileModify expertAuditFileModify = null;
            for (Expert expert : expertList) {
                //专家审核记录表
                List<ExpertAudit> expertAuditList = expertAuditMapper.selectByExpertId(expert.getId());
                if(null != expertAuditList){
                	for (ExpertAudit expertAudit : expertAuditList) {
                		if(expertAudit!=null){
                			expertAudit.setDataType(expertAudit.getType());
                		}
					}
                    expert.setExpertAuditList(expertAuditList);
                }
                //工程执业资格历史表
                expertEngHistory = new ExpertEngHistory();
                expertEngHistory.setExpertId(expert.getId());
                List<ExpertEngHistory> expertEngHistoryList = expertEngHistoryMapper.selectByExpertId(expertEngHistory);
                if (null != expertEngHistoryList) {
                    expert.setExpertEngHistoryList(expertEngHistoryList);
                }
                //工程执业资格修改表
                List<ExpertEngHistory> expertEngModifyList = expertEngModifyMapper.selectByExpertId(expertEngHistory);
                if (null != expertEngModifyList) {
                    expert.setExpertEngModifyList(expertEngModifyList);
                }
                //专家历史表
                ExpertHistory expertHistory = expertService.selectOldExpertById(expert.getId());
                if (null != expertHistory) {
                    expert.setHistory(expertHistory);
                }
                //工程执业资格文件修改表
                expertAuditFileModify = new ExpertAuditFileModify();
                expertAuditFileModify.setExpertId(expert.getId());
                List<ExpertAuditFileModify> expertAuditFileModifyList = expertAuditFileModifyMapper.selectByExpertId(expertAuditFileModify);
                if (null != expertAuditFileModifyList) {
                    expert.setExpertAuditFileModifyList(expertAuditFileModifyList);
                }
                expert.setAttchList(getAttch(expert.getId()));
                experts.add(expert);
            }
        }
        //将数据写入文件
        if (!experts.isEmpty()) {
            FileUtils.writeFile(FileUtils.getExpertAuidtNot(), JSON.toJSONString(experts));
            //附件处理
//            List<UploadFile> attachList = new ArrayList<>();
//            for(Expert expert:experts){
//                if(null!=expert.getAttchList() && !expert.getAttchList().isEmpty()){
//                    attachList.addAll(expert.getAttchList());
//                }
//            }
//            String basePath = FileUtils.attachExportPath(Constant.EXPERT_SYS_KEY);
//            if (StringUtils.isNotBlank(basePath)){
//                OperAttachment.writeFile(basePath, attachList);
//                recordService.backupAttach(new Integer(attachList.size()).toString());
//            }
        }
        recordService.commitExpertRecord(new Integer(experts.size()).toString());
    }

    /**
     * Description: 查询公示专家导出外网
     *
     * @param startTime
     * @param endTime
     * @author Easong
     * @version 2017/7/9
     * @since JDK1.7
     */
    @Override
    public void selectExpByPublictyOfExport(String startTime, String endTime) {
        Map<String, Object> map = new HashMap<>();
        map.put("startTime", startTime);
        map.put("endTime", endTime);
        map.put("status", -3);
        List<Expert> expertList = expertMapper.selectExpByPublictyOfExport(map);
        List<Expert> experts = new ArrayList<>();
        ExpertBatch batch = null;
        List<ExpertBatchDetails> expertBatchDetailsList = null;
        ExpertBatchDetails expertBatchDetails = null;
        List<ExpertBatch> expertBatchs = null;
        if(null != expertList && !expertList.isEmpty()){
            for (Expert expert : expertList) {
                //专家审核记录表
                List<ExpertAudit> expertAuditList = expertAuditMapper.selectByExpertId(expert.getId());
                if (null != expertAuditList) {
                    expert.setExpertAuditList(expertAuditList);
                }
                // 查询专家选择的小类
                // 查询军队专家类型
                DictionaryData dict = DictionaryDataUtil.get("ARMY");
                if (dict != null && dict.getId().equals(expert.getExpertsFrom())){
                	expert.setExpertCategory(expertCategoryMapper.findByExpertId(expert.getId()));
                	//执业资格  T_SES_EMS_EXPERT_TITLE
    				List<ExpertTitle> tList = expertTitleMapper.selectByExpertId(expert.getId());
    				expert.setTitles(tList);
                }

                // 查询专家复审意见
                ExpertAuditOpinion expertAuditOpinion = new ExpertAuditOpinion();
                expertAuditOpinion.setExpertId(expert.getId());
                // 设置审核标识  1：复审标识
                expertAuditOpinion.setFlagTime(1);
                ExpertAuditOpinion expertAuditOpinionOut = expertAuditOpinionService.selectByExpertId(expertAuditOpinion, null);
                expert.setExpertAuditOpinion(expertAuditOpinionOut);

                // 专家审核批次详情表
                // 查询专家编号
                expertBatchDetails = new ExpertBatchDetails();
                expertBatchDetails.setExpertId(expert.getId());
                expertBatchDetailsList = expertBatchDetailsMapper.findExpertBatchDetailsList(expertBatchDetails);
                expert.setExpertBatchDetails(expertBatchDetailsList);
                // 查询专家审核批次表
                expertBatchs = new ArrayList<>();
                if(expertBatchDetailsList != null && !expertBatchDetailsList.isEmpty()){
                    for (ExpertBatchDetails expertBatchDetails1: expertBatchDetailsList){
                        batch = expertBatchMapper.getExpertBatchByKey(expertBatchDetails1.getBatchId());
                        if(batch != null){
                            expertBatchs.add(batch);
                        }
                    }
                }
                // 将专家信息添加到集合
                experts.add(expert);
            }
        }
        //将数据写入文件
        if (!experts.isEmpty()) {
            FileUtils.writeFile(FileUtils.getExporttFile(FileUtils.C_SYNCH_PUBLICITY_EXPERT_FILENAME, 24), JSON.toJSONString(experts, SerializerFeature.WriteMapNullValue));
        }
        recordService.synchBidding(null, new Integer(experts.size()).toString(), synchro.util.Constant.SYNCH_PUBLICITY_EXPERT, synchro.util.Constant.OPER_TYPE_EXPORT, synchro.util.Constant.COMMIT_SYNCH_PUBLICITY_EXPERT);
    }
    /**
     *查询注销专家导出外网 
     */
    public void getDeleteExpertByDate(String startDate,String endDate) {
		List<Expert> list = expertService.getDeleteExpertByDate(startDate, endDate);
		if(list.size()>0){
			 FileUtils.writeFile(FileUtils.getExpertLogout(), JSON.toJSONString(list));
		}
		 recordService.synchBidding(null, new Integer(list.size()).toString(), synchro.util.Constant.SYNCH_LOGOUT_EXPERT, synchro.util.Constant.OPER_TYPE_EXPORT, synchro.util.Constant.EXPORT_SYNCH_LOGOUT_EXPERT);
	}
    /**
     * 〈简述〉获取专家与用户信息
     * 〈详细描述〉
     *
     * @param expertId 专家Id
     * @return
     * @author WangHuijie
     */
    private User getUser(String expertId) {
        User user = userService.findByTypeId(expertId);
        return user;
    }

    /**
     * 〈简述〉获取专家与品目的关系
     * 〈详细描述〉
     *
     * @param expertId 专家Id
     * @return
     * @author WangHuijie
     */
    private List<ExpertCategory> getCategory(String expertId) {
        //List<ExpertCategory> categoryList = expertCategoryService.getListByExpertId(expertId, null);
        List<ExpertCategory> categoryList = expertCategoryService.selectListByExpertId1(expertId, null);
        return categoryList;
    }

    /**
     * 〈简述〉获取专家历史记录信息
     * 〈详细描述〉
     *
     * @param expertId 专家Id
     * @return
     * @author WangHuijie
     */
    private ExpertHistory getHistory(String expertId) {
        ExpertHistory history = expertService.selectOldExpertById(expertId);
        return history;
    }

    /**
     * 〈简述〉获取新注册的专家信息
     * 〈详细描述〉
     *
     * @author WangHuijie
     */
    public void getCreatedData(String startTime, String endTime) {
        List<Expert> expertList = expertService.getCommitExpertByDate(startTime, endTime);
        List<Expert> list = getNewExpertList(expertList);
        List<UploadFile> attachList = new ArrayList<>();
        for (Expert e : list) {
            attachList.addAll(e.getAttchList());
        }
        if (list != null && list.size() > 0) {
            FileUtils.writeFile(FileUtils.getNewExpertBackUpFile(), JSON.toJSONString(list));
            String basePath = FileUtils.attachExportPath(Constant.EXPERT_SYS_KEY);
            if (StringUtils.isNotBlank(basePath)) {
                OperAttachment.writeFile(basePath, attachList);
                recordService.backupAttach(new Integer(attachList.size()).toString());
            }
        }
        recordService.commitExpertRecord(new Integer(list.size()).toString());


//        if (list != null && list.size() > 0){
//            FileUtils.writeFile(FileUtils.getNewExpertBackUpFile(),JSON.toJSONString(list));
//        }
//        recordService.backNewExpertRecord(new Integer(list.size()).toString());
    }

    /**
     * 〈简述〉获取修改的专家信息
     * 〈详细描述〉
     *
     * @author WangHuijie
     */
    public void getModifiedData() {
        List<Expert> expList = expertService.getModifyExpertByDate(DateUtils.getYesterDay());
        List<Expert> expertList = new ArrayList<Expert>();
        // 去重
        if (expList != null && expList.size() > 1) {
            for (int i = 0; i < expList.size(); i++) {
                boolean flag = true;
                for (int j = expList.size() - 1; j > i; j--) {
                    if (expList.get(i).getId().equals(expList.get(j).getId())) {
                        flag = false;
                    }
                }
                if (flag) {
                    expertList.add(expList.get(0));
                }
            }
        }
        List<Expert> list = getModifyExpertList(expertList);
        if (list != null && list.size() > 0) {
            FileUtils.writeFile(FileUtils.getModifyExpertBackUpFile(), JSON.toJSONString(list));
        }
        recordService.backModifyExpertRecord(new Integer(list.size()).toString());
    }

    /**
     * 〈简述〉根据主数据查询关联的数据(新注册)
     * 〈详细描述〉
     *
     * @param expertList 主数据
     * @return 组装完成的数据
     * @author WangHuijie
     */
    private List<Expert> getNewExpertList(List<Expert> expertList) {
        List<Expert> list = new ArrayList<Expert>();
        for (Expert expert : expertList) {
            expert.setUser(getUser(expert.getId()));
            List<RoleUser> userRoles = userMapper.queryByUserId(expert.getUser().getId(), null);
            expert.setUserRoles(userRoles);
            expert.setExpertCategory(getCategory(expert.getId()));
            List<UploadFile> attchs = getAttch(expert.getId());
//            expert.setAttchList(getAttch(expert.getId()));
            expert.setTitles(getTitle(expert.getId()));
            for (ExpertTitle e : expert.getTitles()) {
                List<UploadFile> attch = fileUploadMapper.quyerExpertAttchment(e.getId());
                attchs.addAll(attch);
            }
            expert.setAttchList(attchs);
            list.add(expert);
        }
        return list;
    }

    /**
     * 〈简述〉根据主数据查询关联的数据(修改)
     * 〈详细描述〉
     *
     * @param expertList 主数据
     * @return 组装完成的数据
     * @author WangHuijie
     */
    private List<Expert> getModifyExpertList(List<Expert> expertList) {
        List<Expert> list = new ArrayList<Expert>();
        for (Expert expert : expertList) {
            List<RoleUser> userRoles = userMapper.queryByUserId(expert.getUser().getId(), null);
            expert.setUserRoles(userRoles);
            expert.setUser(getUser(expert.getId()));
            expert.setExpertCategory(getCategory(expert.getId()));
            expert.setHistory(getHistory(expert.getId()));
            list.add(expert);
        }
        return list;
    }


    /**
     * @param @param  expertId
     * @param @return
     * @return List<ExpertTitle>
     * @Title: getTitle
     * @Description: 专家职业类型
     * author: Li Xiaoxiao
     * @throwsRS
     */
    public List<ExpertTitle> getTitle(String expertId) {
        List<ExpertTitle> list = expertTitleMapper.selectByExpertId(expertId);
        return list;
    }

    /**
     * @param @param  expertId
     * @param @return
     * @return List<UploadFile>
     * @throws
     * @Title: getAttch
     * @Description:专家附件 author: Li Xiaoxiao
     */
    public List<UploadFile> getAttch(String expertId) {
        List<UploadFile> attchs = fileUploadMapper.quyerExpertAttchment(expertId);
        List<ExpertTitle> list = expertTitleMapper.selectByExpertId(expertId);
        for (ExpertTitle ep : list) {
            List<UploadFile> titleFile = fileUploadMapper.substrBusinessId(ep.getId());
            attchs.addAll(titleFile);
        }
        return attchs;
    }

	@Override
	public void importCheckResult(File f) {
		// TODO Auto-generated method stub
		 for (File file2 : f.listFiles()) {
			 if(file2.getName().contains(FileUtils.EXPERT_CHECK_RESULT_FILENAME)){
				 List<Expert> list=FileUtils.getBeans(file2, Expert.class); 
					for (Expert expert : list) {
						//专家意见
						if(expert.getExpertAuditOpinion()!=null){
							expertAuditOpinionService.inserOpinion(expert.getExpertAuditOpinion());
						}
						//专家附件
						for (UploadFile uf : expert.getAttchList()) {
							  UploadFile ufile = fileUploadMapper.queryById(uf.getId(), "T_SES_EMS_EXPERT_ATTACHMENT");
	        				   if(ufile==null){
	        					   uf.setTableName("T_SES_EMS_EXPERT_ATTACHMENT");
	        	    			   fileUploadMapper.saveFile(uf);
	        				   }else{
	        					   uf.setTableName("T_SES_EMS_EXPERT_ATTACHMENT");
	        	    			   fileUploadMapper.updateFileById(uf);
	        				   }
						}
						//专家复查表
						for (ExpertFinalInspect expertFinalInspect : expert.getExpertFinalInspectList()) {
							finalInspectService.insertExpertFinalInspect(expertFinalInspect);
						}
						expertService.updateByPrimaryKeySelective(expert);
					}
					 recordService.backupExpertImportCheckResults(new Integer(list.size()).toString());
			 }else if (file2.getName().contains(synchro.util.Constant.ATTCH_FILE_EXPERT)){
				 OperAttachment.moveFolder(file2);
			 }
		 }
		
	}
}