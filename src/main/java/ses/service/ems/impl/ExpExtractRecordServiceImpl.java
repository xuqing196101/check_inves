/**
 * 
 */
package ses.service.ems.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Service;

import bss.model.prms.PackageExpert;
import bss.service.prms.PackageExpertService;

import com.github.pagehelper.PageHelper;

import ses.dao.ems.ExpExtractRecordMapper;
import ses.model.bms.PreMenu;
import ses.model.bms.Role;
import ses.model.bms.User;
import ses.model.bms.UserPreMenu;
import ses.model.bms.Userrole;
import ses.model.ems.ExpExtPackage;
import ses.model.ems.ExpExtractRecord;
import ses.model.ems.Expert;
import ses.model.ems.ProjectExtract;
import ses.model.sms.SupplierExtracts;
import ses.service.bms.PreMenuServiceI;
import ses.service.bms.RoleServiceI;
import ses.service.bms.UserServiceI;
import ses.service.ems.ExpExtPackageService;
import ses.service.ems.ExpExtractRecordService;
import ses.service.ems.ExpertService;
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
    RoleServiceI roleService;
    @Autowired
    private PreMenuServiceI menuService;// 地区查询
    
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
            PageHelper.startPage(pageNum, 10);
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
    public ExpExtractRecord showExpExtractRecord(ExpExtractRecordService expExtractRecordService) {
        return expExtractRecordMapper.selectByPrimaryKey("21321");
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
        ExpertService.insertSelective(expert);
        //生成15位随机码
        String randomCode = generateString(15);
        //根据随机码+密码加密
        Md5PasswordEncoder md5 = new Md5PasswordEncoder();     
        // false 表示：生成32位的Hex版, 这也是encodeHashAsBase64的, Acegi 默认配置; true  表示：生成24位的Base64版     
        md5.setEncodeHashAsBase64(false);     
        String pwd = md5.encodePassword(loginPwd, randomCode);

        //插入专家抽取关联表
        ProjectExtract extract = new ProjectExtract();
        extract.setExpertId(uuId);
        extract.setOperatingType((short)1);
        extract.setIsProvisional((short)1);
        ExpExtPackage expExtPackage=new ExpExtPackage();
        expExtPackage.setPackageId(packageId);
        List<ExpExtPackage> list = expExtPackageService.list(expExtPackage, "0");
        if (list != null && list.size() !=0 ){}
        extract.setProjectId(list.get(0).getId());
        extractService.insertProjectExtract(extract);
        //插入登录表
        User user = new User();
        user.setLoginName(loginName);
        user.setPassword(pwd);
//        user.setTypeName(DictionaryDataUtil.get("EXPERT_U").getId());
        user.setTypeId(uuId);
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
        
      

        //插入到包关联专家
        PackageExpert record = new PackageExpert();
        record.setProjectId(projectId);
        record.setPackageId(packageId);
        record.setExpertId(uuId);
        // 评审状态 未评审
        record.setIsAudit((short) 0);
        // 初审是否汇总 未汇总
        record.setIsGather((short) 0);
        // 是否评分
        record.setIsGrade((short) 0);
        // 评分是否汇总
        record.setIsGatherGather((short) 0);
        record.setIsGroupLeader((short) 0);
        service.save(record);
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

}
