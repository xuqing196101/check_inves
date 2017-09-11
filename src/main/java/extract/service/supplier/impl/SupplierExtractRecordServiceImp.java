/**
 * 
 */
package extract.service.supplier.impl;

import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Service;

import ses.dao.sms.SupplierMapper;
import ses.model.bms.PreMenu;
import ses.model.bms.Role;
import ses.model.bms.User;
import ses.model.bms.UserPreMenu;
import ses.model.bms.Userrole;
import ses.model.sms.Supplier;
import ses.service.bms.PreMenuServiceI;
import ses.service.bms.RoleServiceI;
import ses.service.bms.UserServiceI;
import ses.util.PropUtil;
import ses.util.WfUtil;
import bss.dao.ppms.SaleTenderMapper;
import bss.model.ppms.SaleTender;

import com.github.pagehelper.PageHelper;

import extract.dao.supplier.SupplierExtractRecordMapper;
import extract.model.supplier.SupplierExtractProjectInfo;
import extract.service.supplier.SupplierExtractRecordService;

/**
 * @Description:供应商抽取
 *	 
 * @author Wang Wenshuai
 * @date 2016年9月18日下午2:03:38
 * @since  JDK 1.7
 */
@Service
public class SupplierExtractRecordServiceImp implements SupplierExtractRecordService {
    @Autowired
    SupplierExtractRecordMapper supplierExtractsMapper;
    
    @Autowired
    SupplierMapper supplierMapper;
    @Autowired
    UserServiceI userServiceI;//用户管理
    
    @Autowired
    RoleServiceI roleService;
    @Autowired
    private PreMenuServiceI menuService;// 地区查询
    @Autowired
    SaleTenderMapper saleTenderMapper;
    

    /**
     * @Description:插入记录
     *
     * @author Wang Wenshuai
     * @version 2016年9月27日 下午4:32:28  
     * @param @param record      
     * @return void
     */
    @Override
    public void insert(SupplierExtractProjectInfo record) {
    	record.setCreatedAt(new Date());
        supplierExtractsMapper.insertSelective(record);
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
    public List<SupplierExtractProjectInfo> listExtractRecord(
        SupplierExtractProjectInfo expExtractRecord,Integer pageNum) {
        if(pageNum!=0){
            PageHelper.startPage(pageNum, 10);
        }
        return supplierExtractsMapper.list(expExtractRecord);
    }

    /**
     * 
     *〈简述〉修改
     *〈详细描述〉
     * @author Wang Wenshuai
     */
    @Override
    public void update(SupplierExtractProjectInfo extracts) {
    	extracts.setExtractionTime(new Date());
        supplierExtractsMapper.saveOrUpdateProjectInfo(extracts);
    }
    
    /**
     * 
     *〈简述〉添加临时供应商
     *〈详细描述〉
     * @author Wang Wenshuai
     * @param expExtractRecordService
     * @return
     */
    @Override
    public Map<String, String> addTemporaryExpert(Supplier suuplier, String projectId,
                                                  String packageId, String loginName,
                                                  String loginPwd, HttpServletRequest request) {

        Map<String, String> map=new HashMap<String, String>();
        //插入供应商表一条数据
        String uuId=WfUtil.createUUID();
        suuplier.setId(uuId);
        suuplier.setIsProvisional(new Short("1"));
        suuplier.setCreatedAt(new Timestamp(new Date().getTime()));
        suuplier.setStatus(5);
        supplierMapper.insertSelective(suuplier);
        //插入供应商关联表
        SaleTender saleTender = new SaleTender();
        saleTender.setSupplierId(uuId);
        saleTender.setStatusBid((short)2);
        saleTender.setPackages(packageId);
        saleTender.setProjectId(projectId);
        User users = (User) request.getSession().getAttribute("loginUser");
        if(users != null){
            saleTender.setUserId(users.getId());
        }
      
        saleTenderMapper.insertSelective(saleTender);
      
        //插入登录表
        User user = new User();
        user.setLoginName(loginName);
        user.setTypeId(uuId);
        user.setPassword(loginPwd);
        user.setOrgName(suuplier.getSupplierName());
        user.setRelName(suuplier.getArmyBusinessName());
        user.setMobile(suuplier.getArmyBuinessTelephone());
        userServiceI.save(user, null);
        //新增权限
        Role role = new Role();
        role.setCode("SUPPLIER_R");
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
            /** 给用户初始化供应商菜单权限 */
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

    @Override
    public void updateTemporaryExpert(Supplier supplier, String loginName, String loginPwd,
        HttpServletRequest sq) {
        supplier.setUpdatedAt(new Date());
        supplier.setLoginName(loginName);
        User user = userServiceI.findByTypeId(supplier.getId());
        user.setLoginName(loginName);
        if (loginPwd != null && !"".equals(loginPwd)) {
            Md5PasswordEncoder md5 = new Md5PasswordEncoder();     
            // false 表示：生成32位的Hex版, 这也是encodeHashAsBase64的, Acegi 默认配置; true  表示：生成24位的Base64版     
            md5.setEncodeHashAsBase64(false);     
            String pwd = md5.encodePassword(loginPwd, user.getRandomCode());
            user.setPassword(pwd);
            supplier.setPassword(pwd);
        }
        supplierMapper.updateByPrimaryKeySelective(supplier);
        user.setOrgName(supplier.getSupplierName());
        user.setRelName(supplier.getArmyBusinessName());
        user.setMobile(supplier.getArmyBuinessTelephone());
        userServiceI.update(user);
    }

	@Override
	public SupplierExtractProjectInfo selectByPrimaryKey(String id) {
		return supplierExtractsMapper.selectByPrimaryKey(id);
	}

	@Override
	public List<SupplierExtractProjectInfo> getList(int i) {
		 PageHelper.startPage(i, PropUtil.getIntegerProperty("pageSize"));
		return supplierExtractsMapper.getList();
	}

	@Override
	public void saveOrUpdateProjectInfo(SupplierExtractProjectInfo projectInfo) {
		supplierExtractsMapper.saveOrUpdateProjectInfo(projectInfo);
	}

	@Override
	public void insertProjectInfo(SupplierExtractProjectInfo record) {
		supplierExtractsMapper.insertProjectInfo(record);
	}

}
