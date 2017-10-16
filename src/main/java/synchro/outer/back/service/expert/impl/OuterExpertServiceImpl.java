package synchro.outer.back.service.expert.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import common.constant.Constant;
import common.dao.FileUploadMapper;
import common.model.UploadFile;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ses.dao.bms.UserMapper;
import ses.dao.ems.ExpertAuditFileModifyMapper;
import ses.dao.ems.ExpertAuditMapper;
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
import ses.model.ems.ExpertCategory;
import ses.model.ems.ExpertEngHistory;
import ses.model.ems.ExpertHistory;
import ses.model.ems.ExpertTitle;
import ses.service.bms.UserServiceI;
import ses.service.ems.ExpertCategoryService;
import ses.service.ems.ExpertService;
import ses.util.DictionaryDataUtil;
import synchro.outer.back.service.expert.OuterExpertService;
import synchro.service.SynchRecordService;
import synchro.util.DateUtils;
import synchro.util.FileUtils;
import synchro.util.OperAttachment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
                if (null != expertAuditList) {
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
        List<Expert> experts = new ArrayList<Expert>();
        if (null != expertList && !expertList.isEmpty()) {
            ExpertEngHistory expertEngHistory = null;
            ExpertAuditFileModify expertAuditFileModify = null;
            for (Expert expert : expertList) {
                //专家审核记录表
                List<ExpertAudit> expertAuditList = expertAuditMapper.selectByExpertId(expert.getId());
                if (null != expertAuditList) {
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
                // 查询专家选择的小类
                // 查询军队专家类型
                DictionaryData dict = DictionaryDataUtil.get("ARMY");
                if (dict != null && dict.getId().equals(expert.getExpertsFrom()))
                    expert.setExpertCategory(expertCategoryMapper.findByExpertId(expert.getId()));
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

    /**
     * Description:查询注销供应商导出
     *
     * @param startTime
     * @param endTime
     * @author Easong
     * @version 2017/10/16
     * @since JDK1.7
     */
    @Override
    public void selectLogoutSupplierOfExport(String startTime, String endTime) {
        // 查询注销供应商
        Map<String, Object> map = new HashedMap();
        map.put("startTime", startTime);
        map.put("endTime", endTime);
        map.put("isDeleted", 1);
        List<User> users = userMapper.selectLogoutSupplier(map);
        // 将查询的数据封装
        //将数据写入文件
        if (!users.isEmpty()) {
            FileUtils.writeFile(FileUtils.getExporttFile(FileUtils.C_SYNCH_LOGOUT_SUPPLIER_FILENAME, 31), JSON.toJSONString(users, SerializerFeature.WriteMapNullValue));
        }
        recordService.synchBidding(null, new Integer(users.size()).toString(), synchro.util.Constant.SYNCH_LOGOUT_SUPPLIER, synchro.util.Constant.OPER_TYPE_EXPORT, synchro.util.Constant.EXPORT_SYNCH_LOGOUT_SUPPLIER);
    }

}