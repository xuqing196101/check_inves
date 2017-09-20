/**
 * 
 */
package extract.service.supplier.impl;

import java.net.URLEncoder;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
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
import bss.model.ppms.Project;
import bss.model.ppms.SaleTender;

import com.github.pagehelper.PageHelper;

import extract.dao.common.ExtractUserMapper;
import extract.dao.common.PersonRelMapper;
import extract.dao.common.SuperviseMapper;
import extract.dao.supplier.ExtractConditionRelationMapper;
import extract.dao.supplier.SupplierExtractConditionMapper;
import extract.dao.supplier.SupplierExtractRecordMapper;
import extract.dao.supplier.SupplierExtractRelateResultMapper;
import extract.model.common.ExtractUser;
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
    


    @Override
    public void update(SupplierExtractProjectInfo extracts) {
    	extracts.setExtractionTime(new Date());
        supplierExtractsMapper.saveOrUpdateProjectInfo(extracts);
    }
    

	@Override
	public SupplierExtractProjectInfo selectByPrimaryKey(String id) {
		return supplierExtractsMapper.selectByPrimaryKey(id);
	}

	
	@Override
	public List<SupplierExtractProjectInfo> getList(int i,User user,SupplierExtractProjectInfo project) {
		 PageHelper.startPage(i, PropUtil.getIntegerProperty("pageSize"));
		 
		 project.setProcurementDepId(user.getOrg().getId());
		 List<SupplierExtractProjectInfo> list = new ArrayList<>();
		 if("1".equals(user.getTypeName())){
			list = supplierExtractsMapper.getList(project);
		 }else if("4".equals(user.getTypeName())){
			 project.setProcurementDepId(null);
			 list = supplierExtractsMapper.getList(project);
		 }
		for (SupplierExtractProjectInfo projectInfo : list) {
			String temp = "";
			List<ExtractUser> getlistByRid = userMapper.getlistByRid(projectInfo.getId());
			for (ExtractUser e : getlistByRid) {
				temp += e.getName()+",";
			}
			if(StringUtils.isNotBlank(temp)){
				projectInfo.setExtractUser(temp.substring(0,temp.lastIndexOf(",")));
			}
		}
		return list;
	}

	/**
	 * 修改项目信息
	 */
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
		
		//将项目状态变为抽取结束
		SupplierExtractProjectInfo p = new SupplierExtractProjectInfo(id);
		p.setStatus((short) 1);
		supplierExtractsMapper.saveOrUpdateProjectInfo(p);
		
		
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
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日");
		
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
		if(StringUtils.isNotBlank(projectInfo.getConstructionPro())){
			map.put("construction", areaMapper.selectById(projectInfo.getConstructionPro()).getName() + areaMapper.selectById(projectInfo.getConstructionAddr()).getName());
		}
		
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
		
		
		
			String temp = "";
		if("PROJECT".equals(projectCode)){
			//供应商类型
			map.put("typeCode",projectCode);
			
			//建设单位
			map.put("conCom", "");
			
			//类别
			hashMap.put("propertyName", c+"CategoryId");
			List<String> byMap2 = conditionRelationMapper.getByMap(hashMap);
			if(null !=byMap2 && byMap2.size()>0){
				List<String> list = conditionMapper.getCategoryByList(byMap2);
				temp = "";
				for (String string : list) {
					temp +=(string + ",");
				}
				temp = temp.substring(0,temp.lastIndexOf(","));
			}else{
				temp = "全部类别";
			}
			map.put("category", temp);
			
			//供应商数量
			hashMap.put("propertyName", c+"ExtractNum");
			List<String> byMap4 = conditionRelationMapper.getByMap(hashMap);
			if(null !=byMap4 && byMap4.size()>0){
				
				map.put("extractNum",byMap4.get(0) );
			}else{
				
				map.put("extractNum", "0");
			}
			
			//供应商资质等级
			hashMap.put("propertyName", c+"Level");
			List<String> byMap = conditionRelationMapper.getByMap(hashMap);
			if(null!=byMap && byMap.size()>0){
				List<String> list3 = conditionMapper.getLevelByList(byMap);
				temp = "";
				for (String string : list3) {
					temp +=(string + ",");
				}
				temp = temp.substring(0,temp.lastIndexOf(","));
			}else{
				temp = "所有等级";
			}
			map.put("quaLevel", temp);
			
			//抽取结果
			HashMap<String,String> hashMap2 = new HashMap<>();
			hashMap2.put("recordId", recordId);
			hashMap2.put("supplierType",projectCode);
			map.put("result", conMapper.getSupplierListByRid(hashMap2));
			
			//保密要求
			hashMap.put("propertyName", c+"IsHavingConCert");
			List<String> byMap3 = conditionRelationMapper.getByMap(hashMap);
			if(null !=byMap3 && byMap3.size()>0){
				
				map.put("projectIsHavingConCert",byMap3.get(0).equals("0")?"无":"有" );
			}else{
				map.put("projectIsHavingConCert", "不限");
			}
			
			//企业性质
			hashMap.put("propertyName", c+"BusinessNature");
			List<String> byMap5 = conditionRelationMapper.getByMap(hashMap);
			if(null !=byMap5 && byMap5.size()>0){
				map.put("projectBusinessNature",byMap5.get(0).equals("0")?"不限":(dictionaryDataMapper.selectByPrimaryKey(byMap5.get(0)).getName()));
			}else{
				map.put("projectBusinessNature", "不限");
			}
			
		}else{
			if(condition.getSupplierTypeCodes().length>1){
				for (String typeCode : condition.getSupplierTypeCodes()) {
					//供应商类型
					c=typeCode.toLowerCase();
					map.put(c+"TypeCode",dictionaryDataMapper.selectByCode(typeCode).get(0).getName());
					
					//类别
					hashMap.put("propertyName", c+"CategoryId");
					List<String> byMap2 = conditionRelationMapper.getByMap(hashMap);
					List<String> list = null;
					if(null != byMap2 && byMap2.size()>0){
						
						list= conditionMapper.getCategoryByList(byMap2);
					}
					if(null != list && list.size()>0){
						temp = "";
						for (String string : list) {
							temp +=(string + ",");
						}
						temp = temp.substring(0,temp.lastIndexOf(","));
					}else{
						temp = "所有类别";
					}
					map.put(c+"Category", temp);
					/*map.put(c+"Category",list.toString());*/
					
					//供应商数量
					hashMap.put("propertyName", c+"ExtractNum");
					List<String> byMap3 = conditionRelationMapper.getByMap(hashMap);
					if(null !=byMap3 && byMap3.size()>0){
						
						map.put(c+"ExtractNum",byMap3.get(0) );
					}else{
						
						map.put(c+"ExtractNum", "0");
					}
					
					//供应商等级
					hashMap.put("propertyName", c+"Level");
					List<String> byMap = conditionRelationMapper.getByMap(hashMap);
					temp = "";
					if(null!=byMap && byMap.size()>0){
						for (String string : byMap) {
							temp +=(string + ",");
						}
						temp = temp.substring(0,temp.lastIndexOf(","));
					}else{
						temp ="不限等级";
					}
					map.put(c+"Level",temp);
					
					//抽取结果
					HashMap<String,String> hashMap2 = new HashMap<>();
					hashMap2.put("recordId", recordId);
					hashMap2.put("supplierType",typeCode);
					map.put(c+"Result", conMapper.getSupplierListByRid(hashMap2));
				}	
				
			}else{
				//供应商类型
				map.put("typeCode",dictionaryDataMapper.selectByCode(condition.getSupplierTypeCode()).get(0).getName());
				String supplierTypeCode = condition.getSupplierTypeCode();
				c=supplierTypeCode.toLowerCase();
				//类别
				hashMap.put("propertyName", c+"CategoryId");
				List<String> byMap2 = conditionRelationMapper.getByMap(hashMap);
				List<String> list = null;
				if(null != byMap2 && byMap2.size()>0){
					
					list= conditionMapper.getCategoryByList(byMap2);
				}
				if(null != list && list.size()>0){
					temp = "";
					for (String string : list) {
						temp +=(string + ",");
					}
					temp = temp.substring(0,temp.lastIndexOf(","));
				}else{
					temp = "所有类别";
				}
				map.put("category", temp);
				
				//供应商数量
				hashMap.put("propertyName", c+"ExtractNum");
				List<String> byMap3 = conditionRelationMapper.getByMap(hashMap);
				if(null !=byMap3 && byMap3.size()>0){
					
					map.put("extractNum",byMap3.get(0) );
				}else{
					
					map.put("extractNum", "0");
				}
				
				//供应商等级
				hashMap.put("propertyName", c+"Level");
				List<String> byMap = conditionRelationMapper.getByMap(hashMap);
				temp = "";
				if(null!=byMap && byMap.size()>0){
					for (String string : byMap) {
						temp +=(string + ",");
					}
					temp = temp.substring(0,temp.lastIndexOf(","));
				}else{
					temp ="不限等级";
				}
				map.put("level",temp);
				
				//抽取结果
				HashMap<String,String> hashMap2 = new HashMap<>();
				hashMap2.put("recordId", recordId);
				hashMap2.put("supplierType",supplierTypeCode);
				map.put("result", conMapper.getSupplierListByRid(hashMap2));
			}
		}
		return map;
	}

	/**
	 * 校验项目编号唯一
	 */
	@Override
	public List<SupplierExtractProjectInfo> checkSoleProjectCdoe(
			String projectCode) {
		SupplierExtractProjectInfo p = new SupplierExtractProjectInfo();
		p.setProjectCode(projectCode);
		return supplierExtractsMapper.getListByMap(p);
	}

}
