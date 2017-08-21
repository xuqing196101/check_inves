package synchro.inner.read.expert.impl;

import common.dao.FileUploadMapper;
import common.model.UploadFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ses.dao.bms.UserMapper;
import ses.dao.ems.ExpertAuditFileModifyMapper;
import ses.dao.ems.ExpertAuditMapper;
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
import ses.model.ems.ExpertCategory;
import ses.model.ems.ExpertEngHistory;
import ses.model.ems.ExpertHistory;
import ses.model.ems.ExpertTitle;
import ses.service.bms.UserServiceI;
import ses.service.ems.ExpertCategoryService;
import ses.service.ems.ExpertService;
import ses.util.DictionaryDataUtil;
import synchro.inner.read.expert.InnerExpertService;
import synchro.service.SynchRecordService;
import synchro.util.FileUtils;

import java.io.File;
import java.util.List;

/**
 * 
 * 版权：(C) 版权所有 
 * <简述>读取专家信息service
 * <详细描述>
 * @author   WangHuijie
 * @version  
 * @since
 * @see
 */
@Service
public class InnerExpertServiceImpl implements InnerExpertService {
    
    /** 记录service **/
    @Autowired
    private SynchRecordService synchRecordService;
    
    /** 用户service **/
    @Autowired
    private UserServiceI userService;
    
    /** 专家 service **/
    @Autowired
    private ExpertService expertService;
    
    /** 专家-品目service **/
    @Autowired
    private ExpertCategoryService expertCategoryService;
    
    @Autowired
    private ExpertTitleMapper expertTitleMapper;
    
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private ExpertAuditMapper expertAuditMapper;
    @Autowired
    private ExpertEngHistoryMapper expertEngHistoryMapper;
    @Autowired
    private ExpertEngModifyMapper expertEngModifyMapper;
    @Autowired
    private ExpertAuditFileModifyMapper expertAuditFileModifyMapper;
    
    @Autowired
    private FileUploadMapper fileUploadMapper;
    
    @Autowired
    private ExpertMapper expertMapper;
    /**
     * 
     * @see synchro.inner.read.expert.InnerExpertService#readNewExpertInfo(java.io.File)
     */
    @Override
    public void readNewExpertInfo(final File file) {
      
       List<Expert> list = getExpert(file);
//       try{
           if(null != list && !list.isEmpty()){
               for (Expert expert : list) {
                   List<ExpertTitle> titles = expert.getTitles();
                   if(null != titles && !titles.isEmpty()){
                       for(ExpertTitle et:titles){
                           ExpertTitle ets = expertTitleMapper.selectByPrimaryKey(et.getId());
                           if(ets==null){
                               expertTitleMapper.insertSelective(et);
                           }
                           if(ets!=null){
                        	   expertTitleMapper.updateByPrimaryKeySelective(et);  
                           }
                       }
                   }
                   List<RoleUser> roles = expert.getUserRoles();
                   if(null != roles && !roles.isEmpty()){
                       for(RoleUser ur:roles){
                           RoleUser us=new RoleUser();
                           us.setRoleId(ur.getRoleId());
                           us.setUserId(ur.getUserId());
                           List<RoleUser> queryByUserId = userMapper.queryByUserId(ur.getUserId(), ur.getRoleId());
                           if(queryByUserId.size()<1){
                               userMapper.saveUserRole(us);
                           }
                       }
                   }
                   
                   if(expert.getAttchList().size()>0){
                   	for(UploadFile uf:expert.getAttchList()){
        				   UploadFile ufile = fileUploadMapper.queryById(uf.getId(), "T_SES_EMS_EXPERT_ATTACHMENT");
        				   if(ufile==null){
        					   uf.setTableName("T_SES_EMS_EXPERT_ATTACHMENT");
        	    			   fileUploadMapper.saveFile(uf);
        				   }else{
        					   uf.setTableName("T_SES_EMS_EXPERT_ATTACHMENT");
        	    			   fileUploadMapper.updateFileById(uf);
        				   }
            			   
            		   }
                   }
                   
                   
                   saveUser(expert.getUser());
                   saveExpert(expert);
                   saveCategory(expert, expert.getExpertCategory());
               }
               synchRecordService.importNewExpertRecord(new Integer(list.size()).toString());
           }
//       }catch (Exception e){
//           e.printStackTrace();
//       }
    }
    
    /**
     * 
     * @see synchro.inner.read.expert.InnerExpertService#readModifyExpertInfo(java.io.File)
     */
    @Override
    public void readModifyExpertInfo(final File file) {
        List<Expert> list = getExpert(file);
        try{
            if(null != list && !list.isEmpty()){
                for (Expert expert : list) {
                    List<ExpertTitle> titles = expert.getTitles();
                    if(null != titles && !titles.isEmpty()){
                        for(ExpertTitle et:titles){
                            ExpertTitle ets = expertTitleMapper.selectByPrimaryKey(et.getId());
                            if(ets!=null){
                                expertTitleMapper.updateByPrimaryKeySelective(ets);
                            }
                        }
                    }
                    List<RoleUser> roles = expert.getUserRoles();
                    if(null != roles && !roles.isEmpty()){
                        for(RoleUser ur:roles){
                            RoleUser us=new RoleUser();
                            us.setRoleId(ur.getRoleId());
                            us.setUserId(ur.getUserId());
                            List<RoleUser> queryByUserId = userMapper.queryByUserId(ur.getUserId(), ur.getRoleId());
                            if(null==queryByUserId || queryByUserId.size()==0){
                                userMapper.saveUserRole(us);
                            }
                        }
                    }
                    
                    updateUser(expert.getUser());
                    updateExpert(expert);
                    updateCategory(expert, expert.getExpertCategory());
                    saveHistory(expert.getHistory());
                }
                synchRecordService.importModifyExpertRecord(new Integer(list.size()).toString());
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void saveBackModifyExpertForOut(File file) {
        List<Expert> expertList = getExpert(file);
        if(null != expertList && !expertList.isEmpty()){
            try{
                for(Expert expert:expertList){
                    //入库是对每个表进行插入数据
                	expertMapper.updateExpert(expert.getId(), expert.getStatus(), expert.getIsSubmit(), expert.getAuditAt());
                    saveBackModifyOperation(expert);
                }
            }catch (RuntimeException e){
                e.printStackTrace();
            }

        }
    }

    /**
     *
     * Description: 导入公示的专家 到外网展示
     *
     * @author Easong
     * @version 2017/7/10
     * @param [file]
     * @since JDK1.7
     */
    @Override
    public void importExpOfPublicity(File file) {
        List<Expert> expertList = getExpert(file);
        if(null != expertList && !expertList.isEmpty()){
            try{
                for(Expert expert:expertList){
                    //入库是对每个表进行插入数据
                    Expert existsExpert = expertMapper.selectByPrimaryKey(expert.getId());
                    if(existsExpert != null){
                        // 修改基本数据
                        expertMapper.updateByPrimaryKeySelective(expert);
                    }else {
                       // 保存基本数据
                        expertMapper.insertSelective(expert);
                    }
                    // 保存相关联的数据
                    saveBackModifyOperation(expert);
                }
            }catch (RuntimeException e){
                e.printStackTrace();
            }
            synchRecordService.synchBidding(null, new Integer(expertList.size()).toString(), synchro.util.Constant.SYNCH_PUBLICITY_EXPERT, synchro.util.Constant.OPER_TYPE_IMPORT, synchro.util.Constant.IMPORT_SYNCH_PUBLICITY_EXPERT);
        }
    }

    @Transactional
    public void saveBackModifyOperation(Expert expert) throws RuntimeException{
        //专家审核记录表
        List<ExpertAudit> expertAudits = expert.getExpertAuditList();
        if(null != expertAudits && !expertAudits.isEmpty()){
        	expertAuditMapper.deleteByExpertId(expert.getId());
            ExpertAudit audit = null;
            for(ExpertAudit expertAudit:expertAudits){
                audit = expertAuditMapper.selectByPrimaryKey(expertAudit.getId());
                if(null == audit){
                    expertAuditMapper.insertActive(expertAudit);
                }else{
                	expertAuditMapper.updateByPrimaryKey(expertAudit);
                }
            }
        }
        //工程执业资格历史表
        List<ExpertEngHistory> expertEngHistoryList = expert.getExpertEngHistoryList();
        if(null != expertEngHistoryList && !expertEngHistoryList.isEmpty()){
            ExpertEngHistory engHistory = null;
            for(ExpertEngHistory expertEngHistory:expertEngHistoryList){
                engHistory = expertEngHistoryMapper.selectByPrimaryKey(expertEngHistory.getId());
                if(null == engHistory){
                    expertEngHistoryMapper.insertSelectiveById(expertEngHistory);
                }
            }
        }
        //工程执业资格修改表
        List<ExpertEngHistory> expertEngModifyList = expert.getExpertEngModifyList();
        if(null != expertEngModifyList && !expertEngModifyList.isEmpty()){
            ExpertEngHistory engHistory = null;
            for(ExpertEngHistory expertEngModify:expertEngModifyList){
                engHistory = expertEngModifyMapper.selectByPrimaryKey(expertEngModify.getId());
                if(null == engHistory){
                    expertEngModifyMapper.insertSelectiveById(expertEngModify);
                }
            }
        }
        //专家历史表
        ExpertHistory expertHistory = expert.getHistory();
        if(null != expertHistory){
            ExpertHistory history = expertService.selectOldExpertByPrimaryKey(expertHistory.getId());
            if(null == history){
                expertService.insertExpertHistoryById(expertHistory);
            }
        }
        //工程执业资格文件修改表
        List<ExpertAuditFileModify> expertAuditFileModifyList = expert.getExpertAuditFileModifyList();
        if(null != expertAuditFileModifyList && !expertAuditFileModifyList.isEmpty()){
            ExpertAuditFileModify auditFileModify = null;
            for(ExpertAuditFileModify expertAuditFileModify:expertAuditFileModifyList){
                auditFileModify = expertAuditFileModifyMapper.selectByPrimaryKey(expertAuditFileModify.getId());
                if(null == auditFileModify){
                    expertAuditFileModifyMapper.insertSelectiveById(expertAuditFileModify);
                }
            }
        }
        // 查询军队专家类型
        DictionaryData dict = DictionaryDataUtil.get("ARMY");
        if(dict != null && dict.getId().equals(expert.getExpertsFrom())){
            // 将军地专家选择小类插入到数据库中
            List<ExpertCategory> expertCategoryList = expert.getExpertCategory();
            if(expertCategoryList != null && !expertCategoryList.isEmpty()){
                for (ExpertCategory expertCategory: expertCategoryList){
                    expertCategoryService.insertSelective(expertCategory);
                }
            }
        }

    }

    /**
     *〈简述〉保存用户
     *〈详细描述〉
     * @author WangHuijie
     * @param user 用户
     */
    private void saveUser(User user) {
        if (user != null) {
        	User user2 = userMapper.queryByNameAndPw(user.getLoginName(), user.getPassword());
        	if(user2==null){
        		 userService.saveUser(user);
        	}
        	if(user2!=null){
        		userMapper.updateByPrimaryKeySelective(user);
        	}
        }
    }
    
    /**
     *〈简述〉保存专家基本信息
     *〈详细描述〉
     * @author WangHuijie
     * @param expert 专家
     */
    public void saveExpert(Expert expert) {
        if (expert != null) {
        	Expert ep = expertService.selectByPrimaryKey( expert.getId());
        	if(ep==null){
        		expertService.insertSelective(expert);
        	}
            if(ep!=null){
            	expertService.updateByPrimaryKeySelective(expert);
            }
        }
    }
    
    /**
     *〈简述〉保存专家品目信息
     *〈详细描述〉
     * @author WangHuijie
     * @param categories 品目List
     */
    public void saveCategory(Expert expert, List<ExpertCategory> categories) {
        if (categories != null && categories.size() > 0) {
            if(null == expert){
                expert = expertService.selectByPrimaryKey(categories.get(0).getExpertId());
            }
            for (ExpertCategory expertCategory : categories) {
            	ExpertCategory category = expertCategoryService.getExpertCategory(expert.getId(), expertCategory.getCategoryId());
            	if(category==null){
            		 expertCategoryService.save(expert, expertCategory.getCategoryId(), expertCategory.getTypeId(), expertCategory.getEngin_type());
            	}
               
            }
        }
    }
    
    /**
     *〈简述〉保存专家历史记录
     *〈详细描述〉
     * @author WangHuijie
     * @param history 专家历史信息
     */
    public void saveHistory(ExpertHistory history) {
        if (history != null) {
        	
            expertService.insertExpertHistory(history);
        }
    }
    
    /**
     *〈简述〉修改用户信息
     *〈详细描述〉
     * @author WangHuijie
     * @param user 用户
     */
    public void updateUser(User user) {
        if (user != null) {
            userService.update(user);
        }
    }
    
    /**
     *〈简述〉修改品目信息
     *〈详细描述〉
     * @author WangHuijie
     * @param categories 品目List
     */
    public void updateCategory(Expert expert, List<ExpertCategory> categories) {
        if (categories != null && categories.size() > 0) {
            expertCategoryService.deleteByExpertId(categories.get(0).getExpertId());
            saveCategory(expert, categories);
        }
    }
    
    /**
     *〈简述〉修改专家基本信息
     *〈详细描述〉
     * @author WangHuijie
     * @param expert 专家
     */
    public void updateExpert(Expert expert) {
        if(null != expert){
            expertService.updateByPrimaryKeySelective(expert);
        }
    }
    
    /**
     *〈简述〉获取专家list
     *〈详细描述〉
     * @author WangHuijie
     * @param file 文件
     * @return
     */
    private List<Expert> getExpert(final File file) {
        List<Expert> expertList = FileUtils.getExpert(file, Expert.class);
        return expertList;
    }
    

}
