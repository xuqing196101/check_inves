package synchro.outer.back.service.expert.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ses.dao.bms.UserMapper;
import ses.dao.ems.*;
import ses.model.bms.RoleUser;
import ses.model.bms.User;
import ses.model.bms.Userrole;
import ses.model.ems.*;
import ses.service.bms.UserServiceI;
import ses.service.ems.ExpertCategoryService;
import ses.service.ems.ExpertService;
import synchro.outer.back.service.expert.OuterExpertService;
import synchro.service.SynchRecordService;
import synchro.util.DateUtils;
import synchro.util.FileUtils;
import synchro.util.OperAttachment;

import com.alibaba.fastjson.JSON;

import common.constant.Constant;
import common.dao.FileUploadMapper;
import common.model.UploadFile;

/**
 * 版权：(C) 版权所有 
 * <简述> 专家业务service
 * <详细描述>
 * @author   WangHuijie
 * @version  
 * @since
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

    /**
     * @see synchro.outer.back.service.supplier.OuterReadExpertService#backupCreated()
     */
    
    @Autowired
    private UserMapper userMapper;
    @Override
    public void backupCreated(String startTime, String endTime) {
        getCreatedData(startTime,  endTime);
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
        List<Expert> expertList = expertService.getAuditExpertByDate(startTime,endTime);
        List<Expert> experts = new ArrayList<Expert>();
        if(null != expertList && !expertList.isEmpty()){
            ExpertEngHistory expertEngHistory = null;
            ExpertAuditFileModify expertAuditFileModify = null;
            for(Expert expert : expertList){
                //专家审核记录表
                List<ExpertAudit> expertAuditList = expertAuditMapper.selectByExpertId(expert.getId());
                if(null != expertAuditList){
                    expert.setExpertAuditList(expertAuditList);
                }
                //工程执业资格历史表
                expertEngHistory = new ExpertEngHistory();
                expertEngHistory.setExpertId(expert.getId());
                List<ExpertEngHistory> expertEngHistoryList = expertEngHistoryMapper.selectByExpertId(expertEngHistory);
                if(null != expertEngHistoryList){
                    expert.setExpertEngHistoryList(expertEngHistoryList);
                }
                //工程执业资格修改表
                List<ExpertEngHistory> expertEngModifyList = expertEngModifyMapper.selectByExpertId(expertEngHistory);
                if(null != expertEngModifyList){
                    expert.setExpertEngModifyList(expertEngModifyList);
                }
                //专家历史表
                ExpertHistory expertHistory = expertService.selectOldExpertById(expert.getId());
                if(null != expertHistory){
                    expert.setHistory(expertHistory);
                }
                //工程执业资格文件修改表
                expertAuditFileModify = new ExpertAuditFileModify();
                expertAuditFileModify.setExpertId(expert.getId());
                List<ExpertAuditFileModify> expertAuditFileModifyList = expertAuditFileModifyMapper.selectByExpertId(expertAuditFileModify);
                if(null != expertAuditFileModifyList){
                    expert.setExpertAuditFileModifyList(expertAuditFileModifyList);
                }
                expert.setAttchList(getAttch(expert.getId()));
                experts.add(expert);
            }
        }
        //将数据写入文件
        if(!experts.isEmpty()){
            FileUtils.writeFile(FileUtils.getExpertAuidtNot(),JSON.toJSONString(experts));
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
     * 
     *〈简述〉获取专家与用户信息
     *〈详细描述〉
     * @author WangHuijie
     * @param expertId 专家Id
     * @return
     */
    private User getUser(String expertId) {
        User user = userService.findByTypeId(expertId);
        return user;
    }
    
    /**
     * 
     *〈简述〉获取专家与品目的关系
     *〈详细描述〉
     * @author WangHuijie
     * @param expertId 专家Id
     * @return
     */
    private List<ExpertCategory> getCategory(String expertId) {
        List<ExpertCategory> categoryList = expertCategoryService.getListByExpertId(expertId, null);
        return categoryList;
    }
    
    /**
     * 
     *〈简述〉获取专家历史记录信息
     *〈详细描述〉
     * @author WangHuijie
     * @param expertId 专家Id
     * @return
     */
    private ExpertHistory getHistory(String expertId) {
        ExpertHistory history = expertService.selectOldExpertById(expertId);
        return history;
    }
    
    /**
     *〈简述〉获取新注册的专家信息
     *〈详细描述〉
     * @author WangHuijie
     */
    public void getCreatedData(String startTime, String endTime) {
        List<Expert> expertList = expertService.getCommitExpertByDate(startTime,endTime);
        List<Expert> list = getNewExpertList(expertList);
        List<UploadFile> attachList = new ArrayList<>();
        for(Expert e:list){
        	attachList.addAll(e.getAttchList());
        }
        if (list != null && list.size() > 0){
            FileUtils.writeFile(FileUtils.getNewExpertBackUpFile(),JSON.toJSONString(list));
            String basePath = FileUtils.attachExportPath(Constant.EXPERT_SYS_KEY);
            if (StringUtils.isNotBlank(basePath)){
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
     *〈简述〉获取修改的专家信息
     *〈详细描述〉
     * @author WangHuijie
     */
    public void getModifiedData() {
        List<Expert> expList = expertService.getModifyExpertByDate(DateUtils.getYesterDay());
        List<Expert> expertList  = new ArrayList<Expert>();
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
        if (list != null && list.size() > 0){
            FileUtils.writeFile(FileUtils.getModifyExpertBackUpFile(),JSON.toJSONString(list));
        }
        recordService.backModifyExpertRecord(new Integer(list.size()).toString());
    }
    
    /**
     * 
     *〈简述〉根据主数据查询关联的数据(新注册)
     *〈详细描述〉
     * @author WangHuijie
     * @param expertList 主数据
     * @return 组装完成的数据
     */
    private List<Expert> getNewExpertList(List<Expert> expertList) {
        List <Expert> list = new ArrayList<Expert>();
        for (Expert expert : expertList){
            expert.setUser(getUser(expert.getId()));
            List<RoleUser> userRoles = userMapper.queryByUserId(expert.getUser().getId(), null);
        	expert.setUserRoles(userRoles);
            expert.setExpertCategory(getCategory(expert.getId()));
            List<UploadFile> attchs = getAttch(expert.getId());
//            expert.setAttchList(getAttch(expert.getId()));
            expert.setTitles(getTitle(expert.getId()));
            for(ExpertTitle e:expert.getTitles()){
            	  List<UploadFile> attch = fileUploadMapper.quyerExpertAttchment(e.getId());
            	  attchs.addAll(attch);
            }
            expert.setAttchList(attchs);
            list.add(expert);
        }
        return list;
    }
    
    /**
     * 
     *〈简述〉根据主数据查询关联的数据(修改)
     *〈详细描述〉
     * @author WangHuijie
     * @param expertList 主数据
     * @return 组装完成的数据
     */
    private List<Expert> getModifyExpertList(List<Expert> expertList) {
        List <Expert> list = new ArrayList<Expert>();
        for (Expert expert : expertList){
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
     * 
    * @Title: getTitle
    * @Description: 专家职业类型
    * author: Li Xiaoxiao 
    * @param @param expertId
    * @param @return     
    * @return List<ExpertTitle>     
    * @throwsRS
     */
    public List<ExpertTitle> getTitle(String expertId){
    	List<ExpertTitle> list = expertTitleMapper.selectByExpertId(expertId);
    	return list;
    }
    
    /**
     * 
    * @Title: getAttch
    * @Description:专家附件
    * author: Li Xiaoxiao 
    * @param @param expertId
    * @param @return     
    * @return List<UploadFile>     
    * @throws
     */
    public  List<UploadFile> getAttch(String expertId){
        List<UploadFile> attchs = fileUploadMapper.quyerExpertAttchment(expertId);
    	List<ExpertTitle> list = expertTitleMapper.selectByExpertId(expertId);
	    	for(ExpertTitle ep:list){
	    	     List<UploadFile> titleFile = fileUploadMapper.substrBusinessId(ep.getId());
	    	     attchs.addAll(titleFile);
	    	}
    	 return attchs;
    }
    
    
}