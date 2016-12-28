package synchro.outer.back.service.expert.impl;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ses.model.bms.User;
import ses.model.ems.Expert;
import ses.model.ems.ExpertCategory;
import ses.model.ems.ExpertHistory;
import ses.service.bms.UserServiceI;
import ses.service.ems.ExpertCategoryService;
import ses.service.ems.ExpertService;
import synchro.outer.back.service.expert.OuterExpertService;
import synchro.service.SynchRecordService;
import synchro.util.DateUtils;
import synchro.util.FileUtils;

import com.alibaba.fastjson.JSON;

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
    
    /**
     * @see synchro.outer.back.service.supplier.OuterReadExpertService#backupCreated()
     */
    @Override
    public void backupCreated() {
        getCreatedData();
    }
    
    /**
     * @see synchro.outer.back.service.supplier.OuterReadExpertService#backupModified()
     */
    @Override
    public void backupModified() {
        getModifiedData();
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
        List<ExpertCategory> categoryList = expertCategoryService.getListByExpertId(expertId);
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
    public void getCreatedData() {
        List<Expert> expertList = expertService.getCommitExpertByDate(DateUtils.getYesterDay());
        List<Expert> list = getNewExpertList(expertList);
        if (list != null && list.size() > 0){
            FileUtils.writeFile(FileUtils.getNewExpertBackUpFile(),JSON.toJSONString(list));
        }
        recordService.backNewExpertRecord(new Integer(list.size()).toString());
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
            expert.setExpertCategory(getCategory(expert.getId()));
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
            expert.setUser(getUser(expert.getId()));
            expert.setExpertCategory(getCategory(expert.getId()));
            expert.setHistory(getHistory(expert.getId()));
            list.add(expert);
        }
        return list;
    }
}