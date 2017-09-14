/**
 * 
 */
package extract.service.supplier.impl;

import java.net.URLEncoder;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Service;

import ses.dao.bms.AreaMapper;
import ses.dao.bms.DictionaryDataMapper;
import ses.dao.oms.OrgnizationMapper;
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
import ses.service.ems.ExpertService;
import ses.util.PropUtil;
import ses.util.WfUtil;
import ses.util.WordUtil;
import bss.dao.ppms.SaleTenderMapper;
import bss.model.ppms.SaleTender;

import com.github.pagehelper.PageHelper;

import extract.dao.common.ExtractUserMapper;
import extract.dao.common.PersonRelMapper;
import extract.dao.common.SuperviseMapper;
import extract.dao.supplier.ExtractConditionRelationMapper;
import extract.dao.supplier.SupplierExtractConditionMapper;
import extract.dao.supplier.SupplierExtractRecordMapper;
import extract.dao.supplier.SupplierExtractRelateResultMapper;
import extract.model.supplier.SupplierExtractCondition;
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
    
    @Autowired
    private PersonRelMapper personRelMapper;
    
    @Autowired
    private ExpertService service;
    
    @Autowired
    private DictionaryDataMapper dictionaryDataMapper; 
    
    @Autowired
    private AreaMapper areaMapper;
    
    @Autowired
    private SupplierExtractConditionMapper conditionMapper;
    
    @Autowired
    private ExtractConditionRelationMapper conditionRelationMapper;
    
    @Autowired
    private SupplierExtractRelateResultMapper conMapper;
    
    @Autowired
    private ExtractUserMapper userMapper;
    @Autowired
    private SuperviseMapper superviseMapper;
    
    @Autowired
    private OrgnizationMapper orgnizationMapper;
    

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
		 List<SupplierExtractProjectInfo> list = supplierExtractsMapper.getList();
		 for (int j = 0; j < list.size(); j++) {
			list.get(j).setExtractUser(personRelMapper.getlistByRid(list.get(j).getId()));
		}
		return list;
	}

	@Override
	public void saveOrUpdateProjectInfo(SupplierExtractProjectInfo projectInfo,User user) {
		projectInfo.setProcurementDepId(user.getOrg().getId());//存储采购机构
		supplierExtractsMapper.saveOrUpdateProjectInfo(projectInfo);
	}

	/**
	 * 插入项目记录
	 */
	@Override
	public void insertProjectInfo(SupplierExtractProjectInfo record) {
		supplierExtractsMapper.insertProjectInfo(record);
	}

	/**
	 * 打印记录表
	 * @return 
	 * @throws Exception 
	 */
	@Override
	public ResponseEntity<byte[]> printRecord(String id, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		//根据记录id 查询项目信息不同供应商类别打印两个记录表
		Map<String, Object> info = selectExtractInfo(id);
		
		if(null==info){
			return null;
		}
	        
        // 文件存储地址
        String filePath = request.getSession().getServletContext()
            .getRealPath("/WEB-INF/upload_file/");
        // 文件名称
        String name ;
        if("PROJECT".equals(info.get("typeCode"))){
        	 name = new String(("军队供应商抽取记录表(工程类).doc").getBytes("UTF-8"),
        	            "UTF-8");
        }else{
        	 name = new String(("军队供应商抽取记录表(物资服务类).doc").getBytes("UTF-8"),
        	            "UTF-8");
        }
       
//	        Supplier supplier = JSON.parseObject(supplierJson, Supplier.class);
        /** 创建word文件 **/
        String fileName;
        if("PROJECT".equals(info.get("typeCode"))){
        	 fileName = WordUtil.createWord(info, "extractSupplierEng.ftl",
        	            name, request);
        }else if(null !=info.get("typeCode") && (info.get("typeCode").toString().split(";").length==1) ){
        	 fileName = WordUtil.createWord(info, "extractSupplierSV.ftl",
        	            name, request);
        }else{
        	 fileName = WordUtil.createWord(info, "extractSupplier2.ftl",
        	            name, request);
        }
       
//	        String fileName = WordUtil.createWord(supplier, "test2.ftl",
//	        		name, request);
        // 下载后的文件名
        String downFileName;
        if("PROJECT".equals(info.get("typeCode"))){
        	downFileName = "军队供应商抽取记录表(工程类).doc";
        }else{
        	downFileName = "军队供应商抽取记录表(物资服务类).doc";
        }
      
        if (request.getHeader("User-Agent").toUpperCase().indexOf("MSIE") > 0) {
            //解决IE下文件名乱码
            downFileName = URLEncoder.encode(downFileName, "UTF-8");
        } else {
            //解决非IE下文件名乱码
            downFileName = new String(downFileName.getBytes("UTF-8"), "ISO8859-1");
        }
        return service.downloadFile(fileName, filePath, downFileName);
	}

	private Map<String, Object> selectExtractInfo(String recordId) {
		SupplierExtractProjectInfo projectInfo = supplierExtractsMapper.getProjectInfoById(recordId);
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yy/MM/dd");
		
		String projectCode = projectInfo.getProjectType();
		Map<String, Object> map = new HashMap<>();
		SupplierExtractCondition condition = conditionMapper.getByRid(projectInfo.getId());
		if(null ==condition){
			return null;
		}
		HashMap<Object, Object> hashMap = new HashMap<>();
		hashMap.put("conditionId", condition.getId());
		
		String c = projectCode.toLowerCase();
		/*//首字母大写
		char[] cs=c.toCharArray();
        cs[0]-=32;
        String code = String.valueOf(cs);*/
		
		//采购机构
		map.put("ProcurementDep",orgnizationMapper.findOrgByPrimaryKey(projectInfo.getProcurementDepId()).getName());
		
		//项目实施地点
		map.put("construction", areaMapper.selectById(projectInfo.getConstructionPro()).getName() + areaMapper.selectById(projectInfo.getConstructionAddr()).getName());
		
		//抽取时间
		map.put("extractTime", simpleDateFormat.format(projectInfo.getCreatedAt()));
		
		//项目编号
		map.put("projectCode", projectInfo.getProjectCode());
		
		//抽取方式
		map.put("extractTheWay", projectInfo.getExtractTheWay()==0?"自动抽取":"人工抽取");
		
		//项目名称
		map.put("projectName", projectInfo.getProjectName());
		
		//供应商地域
		map.put("areaName", condition.getAreaName());
		
		
		//人员信息
			map.put("extractUsers",  userMapper.getlistByRid(recordId));
			map.put("supervises",  superviseMapper.getlistByRid(recordId));
		
		
		
		if("PROJECT".equals(projectCode)){
		
			//供应商类型
			map.put("typeCode",projectCode);
			
			//建设单位
			map.put("conCom", "");
			
			//类别
			hashMap.put("propertyName", c+"CategoryId");
			List<String> byMap2 = conditionRelationMapper.getByMap(hashMap);
			List<String> list = conditionMapper.getCategoryByList(byMap2);
			String temp = "";
			for (String string : list) {
				temp +=(string + ",");
			}
			map.put("category", temp.substring(0,temp.lastIndexOf(",")));
			
			//供应商数量
			hashMap.put("propertyName", c+"ExtractNum");
			map.put("extractNum", conditionRelationMapper.getByMap(hashMap).get(0));
			
			//供应商资质等级
			hashMap.put("propertyName", c+"Level");
			List<String> byMap = conditionRelationMapper.getByMap(hashMap);
			List<String> list3 = conditionMapper.getLevelByList(byMap);
			temp = "";
			for (String string : list3) {
				temp +=(string + ",");
			}
			map.put("quaLevel", temp.substring(0,temp.lastIndexOf(",")));
			
			//抽取结果
			HashMap<String,String> hashMap2 = new HashMap<>();
			hashMap2.put("recordId", recordId);
			hashMap2.put("supplierType",projectCode);
			map.put("result", conMapper.getSupplierListByRid(hashMap2));
			
			//保密要求
			hashMap.put("propertyName", c+"IsHavingConCert");
			map.put("projectIsHavingConCert", conditionRelationMapper.getByMap(hashMap).get(0));
			
			//企业性质
			hashMap.put("propertyName", c+"BusinessNature");
			map.put("projectBusinessNature", conditionRelationMapper.getByMap(hashMap).get(0));
			
		}else if("GOODS".equals(projectCode)){
			if(condition.getSupplierTypeCodes().length>1){
				for (String typeCode : condition.getSupplierTypeCodes()) {
					//供应商类型
					c=typeCode.toLowerCase();
					map.put(c+"TypeCode",typeCode);
					
					//类别
					hashMap.put("propertyName", c+"CategoryId");
					List<String> byMap2 = conditionRelationMapper.getByMap(hashMap);
					List<String> list = conditionMapper.getCategoryByList(byMap2);
					String temp = "";
					for (String string : list) {
						temp +=(string + ",");
					}
					map.put(c+"Category", temp.substring(0,temp.lastIndexOf(",")));
					/*map.put(c+"Category",list.toString());*/
					
					//供应商数量
					hashMap.put("propertyName", c+"ExtractNum");
					map.put(c+"ExtractNum", conditionRelationMapper.getByMap(hashMap).get(0));
					
					//供应商等级
					hashMap.put("propertyName", c+"Level");
					List<String> byMap = conditionRelationMapper.getByMap(hashMap);
					temp = "";
					for (String string : byMap) {
						temp +=(string + ",");
					}
					map.put(c+"Level", temp.substring(0,temp.lastIndexOf(",")));
					/*map.put(c+"Level",byMap.toString());*/
					
					//抽取结果
					HashMap<String,String> hashMap2 = new HashMap<>();
					hashMap2.put("recordId", recordId);
					hashMap2.put("supplierType",typeCode);
					map.put(c+"Result", conMapper.getSupplierListByRid(hashMap2));
				}	
				
			}else{
				//供应商类型
				c=condition.getSupplierTypeCode().toLowerCase();
				map.put("typeCode",condition.getSupplierTypeCode());
				
				//类别
				hashMap.put("propertyName", c+"CategoryId");
				List<String> byMap2 = conditionRelationMapper.getByMap(hashMap);
				List<String> list = conditionMapper.getCategoryByList(byMap2);
				String temp = "";
				for (String string : list) {
					temp +=(string + ",");
				}
				map.put("category", temp.substring(0,temp.lastIndexOf(",")));
				
				//供应商数量
				hashMap.put("propertyName", c+"ExtractNum");
				map.put("extractNum", conditionRelationMapper.getByMap(hashMap).get(0));
				
				//供应商等级
				hashMap.put("propertyName", c+"Level");
				List<String> byMap = conditionRelationMapper.getByMap(hashMap);
				temp = "";
				for (String string : byMap) {
					temp +=(string + ",");
				}
				map.put("level", temp.substring(0,temp.lastIndexOf(",")));
				
				//抽取结果
				HashMap<String,String> hashMap2 = new HashMap<>();
				hashMap2.put("recordId", recordId);
				hashMap2.put("supplierType",projectCode);
				map.put("result", conMapper.getSupplierListByRid(hashMap2));
			}
			
			
		}else{
			//服务类
			//供应商类型
			map.put("typeCode",projectCode);
			//类别
			hashMap.put("propertyName", c+"CategoryId");
			List<String> byMap2 = conditionRelationMapper.getByMap(hashMap);
			List<String> list = conditionMapper.getCategoryByList(byMap2);
			String temp = "";
			for (String string : list) {
				temp +=(string + ",");
			}
			map.put("category", temp.substring(0,temp.lastIndexOf(",")));
			
			//供应商数量
			hashMap.put("propertyName", c+"ExtractNum");
			map.put("extractNum", conditionRelationMapper.getByMap(hashMap).get(0));
			
			//供应商等级
			hashMap.put("propertyName", c+"Level");
			List<String> byMap = conditionRelationMapper.getByMap(hashMap);
			temp = "";
			for (String string : byMap) {
				temp +=(string + ",");
			}
			map.put("level", temp.substring(0,temp.lastIndexOf(",")));
			
			//抽取结果
			HashMap<String,String> hashMap2 = new HashMap<>();
			hashMap2.put("recordId", recordId);
			hashMap2.put("supplierType",projectCode);
			map.put("result", conMapper.getSupplierListByRid(hashMap2));
		}
		return map;
	}

}
