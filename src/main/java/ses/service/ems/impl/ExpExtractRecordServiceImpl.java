/**
 * 
 */
package ses.service.ems.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import bss.model.prms.PackageExpert;
import bss.service.prms.PackageExpertService;
import bss.util.PropUtil;

import com.github.pagehelper.PageHelper;

import ses.dao.bms.UserMapper;
import ses.dao.ems.ExpExtractRecordMapper;
import ses.dao.ems.ProjectExtractMapper;
import ses.model.bms.DictionaryData;
import ses.model.bms.PreMenu;
import ses.model.bms.Role;
import ses.model.bms.User;
import ses.model.bms.UserPreMenu;
import ses.model.bms.Userrole;
import ses.model.ems.ExpExtCondition;
import ses.model.ems.ExpExtPackage;
import ses.model.ems.ExpExtractRecord;
import ses.model.ems.Expert;
import ses.model.ems.ExtConType;
import ses.model.ems.ProjectExtract;
import ses.model.sms.SupplierExtracts;
import ses.service.bms.PreMenuServiceI;
import ses.service.bms.RoleServiceI;
import ses.service.bms.UserServiceI;
import ses.service.ems.ExpExtConditionService;
import ses.service.ems.ExpExtPackageService;
import ses.service.ems.ExpExtractRecordService;
import ses.service.ems.ExpertService;
import ses.service.ems.ExtConTypeService;
import ses.service.ems.ProjectExtractService;
import ses.util.DictionaryDataUtil;
import ses.util.WfUtil;

/**
 * @Description:
 *	 
 * @author Wang Wenshuai
 * @version 2016年9月29日下午1:45:37
 * @since  JDK 1.7
 */
@Service
public class ExpExtractRecordServiceImpl implements ExpExtractRecordService {
    @Autowired
    ExpExtractRecordMapper expExtractRecordMapper;
    @Autowired
    ExpertService ExpertService;//专家管理
    @Autowired
    ProjectExtractService extractService; //关联表
    @Autowired
    UserServiceI userServiceI;//用户管理
    @Autowired
    private PackageExpertService service;//包关联专家
    @Autowired
    ExpExtPackageService expExtPackageService; //项目和包关联
    @Autowired
    RoleServiceI roleService; //权限
    @Autowired
    private PreMenuServiceI menuService;// 菜单
    @Autowired
    ExtConTypeService conTypeService;//条件

    @Autowired
    ExpExtConditionService conditionService;//条件
    
    @Autowired
	private UserMapper userMapper;//用户
    
    @Autowired
    private ProjectExtractMapper projectExtractMapper;

    public static final String ALLCHAR = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
    /**
     * @Description:插入记录
     *
     * @author Wang Wenshuai
     * @version 2016年9月27日 下午4:32:28  
     * @param @param record      
     * @return void
     */
    @Override
    public void insert(ExpExtractRecord record) {
        expExtractRecordMapper.insertSelective(record);
    }

    /**
     * @Description:集合
     *
     * @author Wang Wenshuai
     * @version 2016年9月27日 下午4:32:28  
     * @param @param record      
     * @return void
     */
    @Override
    public List<ExpExtractRecord> listExtractRecord(
        ExpExtractRecord expExtractRecord,Integer pageNum) {
        if(pageNum!=0){
            PageHelper.startPage(pageNum, PropUtil.getIntegerProperty("pageSize"));
        }
        return expExtractRecordMapper.list(expExtractRecord);
    }

    /**
     * @Description:单个记录
     *
     * @author Wang Wenshuai
     * @version 2016年9月29日 下午2:19:50  
     * @param @param expExtractRecordService
     * @param @return      
     * @return ExpExtractRecord
     */
    @Override
    public List<ExpExtractRecord> showExpExtractRecord(ExpExtractRecord expExtractRecord) {
        return expExtractRecordMapper.list(expExtractRecord);
    }

    /**
     * 临时专家
     * @see ses.service.ems.ExpExtractRecordService#addTemporaryExpert(ses.model.ems.Expert, java.lang.String, java.lang.String, java.lang.String)
     */
    @Override
    public  Map<String, String> addTemporaryExpert(Expert expert, String projectId,String packageId,
        String loginName, String loginPwd,HttpServletRequest request) {
        Map<String, String> map=new HashMap<String, String>();
        //插入专家表一条数据
        String uuId=WfUtil.createUUID();
        expert.setId(uuId);
        expert.setIsProvisional(new Short("1"));
        expert.setStatus("4");
        expert.setIsSubmit("1");
        ExpertService.insertSelective(expert);
        //插入专家抽取关联表
        String[] arrayPackId = packageId.split(",");
        String[] array = expert.getExpertsTypeId().split(",");
        int i = 0;
        if(array.length > 1 ){
          int max=array.length-1;
          int min=0;
          Random random = new Random();
          i = random.nextInt(max)%(max-min+1) + min;
        }
        for (String pId : arrayPackId) {
          ProjectExtract extract = new ProjectExtract();
          extract.setExpertId(uuId);
          extract.setOperatingType((short)1);
          extract.setIsProvisional((short)1);
          extract.setProjectId(pId);
          if(array.length!=0){
            extract.setReviewType(array[i]);
          }
          extractService.insertProjectExtract(extract);
        }
      
        //插入登录表
        User user = new User();
        user.setLoginName(loginName);
        user.setTypeId(uuId);
        user.setPassword(loginPwd);
        user.setRelName(expert.getRelName());
        user.setMobile(expert.getMobile());
        user.setIdNumber(expert.getIdCardNumber());
        user.setDuites(expert.getAtDuty());
        userServiceI.save(user, null);
        //新增权限
        Role role = new Role();
        role.setCode("EXPERT_R");
        List<Role> listRole = roleService.find(role);
        if (listRole != null && listRole.size() > 0) {
            Userrole userrole = new Userrole();
            userrole.setRoleId(listRole.get(0));
            userrole.setUserId(user);
            /** 删除用户之前的菜单权限*/
            UserPreMenu userPreMenu = new UserPreMenu();
            userPreMenu.setUser(user);
            userServiceI.deleteUserMenu(userPreMenu);
            /** 删除用户之前的角色信息*/
            /** 给该用户初始化专家角色 */
            userServiceI.saveRelativity(userrole);
            String[] roleIds = listRole.get(0).getId().split(",");
            List<String> listMenu = menuService.findByRids(roleIds);
            /** 给用户初始化专家菜单权限 */
            for (String menuId : listMenu) {
                UserPreMenu upm = new UserPreMenu();
                PreMenu preMenu = menuService.get(menuId);
                upm.setPreMenu(preMenu);
                upm.setUser(user);
                userServiceI.saveUserMenu(upm);
            }
        }


        map.put("sccuess", "sccuess");
        return map;
    }

    /**
     * Description: 返回一个定长的随机字符串(只包含大小写字母、数字)
     * 
     * @author Ye MaoLin
     * @version 2016-9-14
     * @param length
     * @return String
     * @exception IOException
     */
    public String generateString(int length) {  
        StringBuffer sb = new StringBuffer();  
        Random random = new Random();  
        for (int i = 0; i < length; i++) {  
            sb.append(ALLCHAR.charAt(random.nextInt(ALLCHAR.length())));  
        }  
        return sb.toString();  
    }


    /**
     * 
     *〈简述〉修改
     *〈详细描述〉
     * @author Wang Wenshuai
     */
    @Override
    public void update(ExpExtractRecord extracts) {
        expExtractRecordMapper.updateByPrimaryKeySelective(extracts);
    }

    /**
     * 供应商类型
     * @see ses.service.ems.ExpExtractRecordService#ddList()
     */
    @Override
    public List<DictionaryData> ddList() {
        List<DictionaryData> productType = DictionaryDataUtil.find(6);
        for (DictionaryData dictionaryData : productType) {
            dictionaryData.setName(dictionaryData.getName()+"技术");
        }
        List<DictionaryData> experType = DictionaryDataUtil.find(19);
        productType.addAll(experType);
        return productType;
    }

    /**
     *〈简述〉返回专家抽取方法
     *〈详细描述〉
     * @author Wang Wenshuai
     * @param sq HttpServletRequest
     * @param ids 集合  [1]关联表id [2]条件表id [3]参与不参与
     * @return List<ProjectExtract>
     */
    @Override
    public List<ProjectExtract> resultProjectExtract(HttpServletRequest sq, String[] ids) {
        //存放已抽取的数量
        Map<String, Integer> mapcount = new HashMap<String, Integer>();
        //存放已操作
        List<ProjectExtract> projectExtractListYes = new ArrayList<ProjectExtract>();
        //未操作
        List<ProjectExtract> projectExtractListNo = new ArrayList<ProjectExtract>();
        //循环出抽取未抽取的
        forExtract(mapcount, ids[1], projectExtractListYes, projectExtractListNo, 0);
        //拿出数量和session中存放的数字进行对比
        ProjectExtract pe = new ProjectExtract();
        pe.setId(ids[0]);
        List<ProjectExtract> list2 = extractService.list(pe);
        ExtConType extConType = conTypeService.getExtConType(list2.get(0).getConTypeId());
        Integer count = mapcount.get(extConType.getId());
        if (count != null && count != 0){
            if (count >= extConType.getExpertsCount()){
                extractService.updateStatusCount("1",extConType.getId());
                forExtract(mapcount, ids[1], projectExtractListYes, projectExtractListNo,1);
            } else {
                forExtract(mapcount, ids[1], projectExtractListYes, projectExtractListNo,1);
            }
        }
        //获取查询条件类型
        List<ExpExtCondition> listCondition = conditionService.list(new ExpExtCondition(ids[1], ""),null);
        List<ExtConType> conTypes = listCondition.get(0).getConTypes();
        for (ExtConType extConType1 : conTypes) {
            extConType1.setAlreadyCount(mapcount.get(extConType1.getId()) == null ? 0 : mapcount.get(extConType1.getId()));
        }
        projectExtractListYes.get(0).setConType(conTypes);
        if (projectExtractListNo.size() != 0){
            Collections.shuffle(projectExtractListNo);
            projectExtractListYes.add(projectExtractListNo.get(0));
        }else{
            //已抽取
            conditionService.update(new ExpExtCondition(ids[1],(short)2));
        }
        return projectExtractListYes;
    }


    /**
     * 
     *〈简述〉循环出抽取未抽取的
     *〈详细描述〉
     * @author yggc
     * @param mapcount
     * @param expertConditionId
     * @param projectExtractListYes
     * @param projectExtractListNo
     */
    private void forExtract(Map<String, Integer> mapcount, String expertConditionId,
                            List<ProjectExtract> projectExtractListYes,
                            List<ProjectExtract> projectExtractListNo,Integer type) {
        //每次进入方法清除数据
        projectExtractListYes.clear();
        projectExtractListNo.clear();
        //查询数据
        List<ProjectExtract> list = extractService.list(new ProjectExtract(expertConditionId));
        for (ProjectExtract projectExtract : list) {
            if (projectExtract.getOperatingType() != null && (projectExtract.getOperatingType()==1 || projectExtract.getOperatingType() == 2)){
                projectExtractListYes.add(projectExtract);
                if (type == 0){
                    Integer conTypeId = mapcount.get(projectExtract.getConTypeId());
                    if (conTypeId != null && conTypeId != 0){
                        mapcount.put(projectExtract.getConTypeId(), conTypeId += 1);
                    } else {
                        mapcount.put(projectExtract.getConTypeId(), 1);
                    }
                }
            } else if (projectExtract != null && projectExtract.getOperatingType() != null && projectExtract.getOperatingType() == 3){
                //不参与
                projectExtractListYes.add(projectExtract);
                if (type == 0){
                    ExtConType extConType = conTypeService.getExtConType(projectExtract.getConTypeId());
                    extractService.updateStatusCount("0",extConType.getId());
                }
            } else {
                projectExtractListNo.add(projectExtract);
            }
        }
    }

    /**
     * 抽取展示
     * @see ses.service.ems.ExpExtractRecordService#showResultProjectExt(org.springframework.ui.Model, java.util.List, java.util.Map, java.util.List)
     */
    /**
     * 
     *〈简述〉抽取
     *〈详细描述〉
     * @author Wang Wenshuai
     * @param model
     * @param listCon
     * @param mapcount
     * @param list
     */
    @Override
    public void showResultProjectExt(ExpExtCondition condition, Map<String, Object> map,
                                     Map<String, Integer> mapcount, List<ProjectExtract> list) {
        //已操作的
        List<ProjectExtract> projectExtractListYes = new ArrayList<ProjectExtract>();
        //未操作的
        List<ProjectExtract> projectExtractListNo = new ArrayList<ProjectExtract>();
        for (ProjectExtract projectExtract : list) {
            if (projectExtract.getOperatingType() != null && (projectExtract.getOperatingType() ==1 || projectExtract.getOperatingType() == 2)){
                projectExtractListYes.add(projectExtract);
                Integer conTypeId = mapcount.get(projectExtract.getConTypeId());
                if (conTypeId != null && conTypeId != 0){
                    mapcount.put(projectExtract.getConTypeId(), conTypeId+=1);
                } else {
                    mapcount.put(projectExtract.getConTypeId(), 1);
                }
            } else if (projectExtract.getOperatingType() != null && projectExtract.getOperatingType() == 3){
                projectExtractListYes.add(projectExtract);
            } else {
                projectExtractListNo.add(projectExtract);
            }
        }

        //获取查询条件类型
        List<ExpExtCondition> listCondition = conditionService.list(new ExpExtCondition(condition.getId(), ""), 0);
        List<ExtConType> conTypes = listCondition.get(0).getConTypes();
        for (ExtConType extConType1 : conTypes) {
            extConType1.setAlreadyCount(mapcount.get(extConType1.getId()) == null ? 0 : mapcount.get(extConType1.getId()));
        }
        map.put("extConType", conTypes);

        if (projectExtractListNo.size() != 0) {
            Collections.shuffle(projectExtractListNo);
            projectExtractListYes.add(projectExtractListNo.get(0));
            projectExtractListNo.remove(0);
        }
        map.put("extRelateListYes", projectExtractListYes);
        map.put("extRelateListNo", projectExtractListNo);
        //删除查询不出的查询结果
        if (projectExtractListNo.size() == 0 && projectExtractListYes.size() == 0){
            conditionService.delById(listCondition.get(0).getId());
            conTypeService.delete(listCondition.get(0).getConTypes().get(0).getId());
        }
    }

    /**
     * 修改临时专家
     */
	@Override
	public Map<String, String> editTemporaryExpert(Expert expert,
			String projectId, String packageId, String loginName,
			String loginPwd, HttpServletRequest request,String oldPackageId) {
		//修改专家表数据
		expert.setUpdatedAt(new Date());
		expert.setLoginName(loginName);
		ExpertService.updateByPrimaryKeySelective(expert);
		//修改用户表数据
		User user = userServiceI.findByTypeId(expert.getId());
		if(user != null){
			user.setLoginName(loginName);
			user.setRelName(expert.getRelName());
	        user.setMobile(expert.getMobile());
	        user.setIdNumber(expert.getIdCardNumber());
	        user.setDuites(expert.getAtDuty());
			if(loginPwd != null && !("").equals(loginPwd)){
				Md5PasswordEncoder md5 = new Md5PasswordEncoder();     
	            // false 表示：生成32位的Hex版, 这也是encodeHashAsBase64的, Acegi 默认配置; true  表示：生成24位的Base64版     
	            md5.setEncodeHashAsBase64(false);     
	            String pwd = md5.encodePassword(loginPwd, user.getRandomCode());
				user.setPassword(pwd);
			}
		}
		userMapper.updateByPrimaryKeySelective(user);
		//修改包Id
		Map<String, Object> exmap=new HashMap<>();
		exmap.put("newProjectId", packageId);
		exmap.put("projectId", oldPackageId);
		exmap.put("expertId", expert.getId());
		projectExtractMapper.updateProjectByExpertId(exmap);
		Map<String, String> map=new HashMap<String, String>();
		map.put("sccuess", "sccuess");
        return map;
	}


}
