/**
 * 
 */
package extract.service.supplier.impl;

import java.io.File;
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
import org.springframework.stereotype.Service;

import ses.dao.bms.AreaMapper;
import ses.dao.bms.DictionaryDataMapper;
import ses.dao.oms.OrgnizationMapper;
import ses.dao.sms.SupplierMapper;
import ses.model.bms.Area;
import ses.model.bms.User;
import ses.service.bms.RoleServiceI;
import ses.service.bms.UserServiceI;
import ses.service.sms.SupplierService;
import ses.util.AuthorityUtil;
import ses.util.DictionaryDataUtil;
import ses.util.PropUtil;
import ses.util.UUIDUtils;
import ses.util.WordUtil;
import system.model.sms.SmsRecordTemp;
import system.service.sms.SmsRecordTempService;
import bss.dao.ppms.SaleTenderMapper;

import com.github.pagehelper.PageHelper;
import common.service.DownloadService;

import extract.dao.common.ExtractUserMapper;
import extract.dao.common.PersonRelMapper;
import extract.dao.common.SuperviseMapper;
import extract.dao.expert.ExpertExtractResultMapper;
import extract.dao.supplier.ExtractConditionRelationMapper;
import extract.dao.supplier.SupplierExtractConditionMapper;
import extract.dao.supplier.SupplierExtractRecordMapper;
import extract.dao.supplier.SupplierExtractRelateResultMapper;
import extract.model.common.ExtractUser;
import extract.model.supplier.ExtractConditionRelation;
import extract.model.supplier.SupplierExtractCondition;
import extract.model.supplier.SupplierExtractProjectInfo;
import extract.model.supplier.SupplierExtractResult;
import extract.service.supplier.SupplierExtractRecordService;
import extract.util.DateUtils;

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
    SupplierExtractRecordMapper recordMapper;
    
    @Autowired
    SupplierMapper supplierMapper;
    @Autowired
    UserServiceI userServiceI;//用户管理
    
    @Autowired
    RoleServiceI roleService;
    @Autowired
    SaleTenderMapper saleTenderMapper;
    
    @Autowired
    AreaMapper areaMapper;
    
    @Autowired
    private DownloadService service;
    
    @Autowired
    private DictionaryDataMapper dictionaryDataMapper; 
    
    @Autowired
    private SupplierExtractConditionMapper conditionMapper;
    
    @Autowired
    private ExtractConditionRelationMapper conditionRelationMapper;
    
    @Autowired
    private SupplierExtractRelateResultMapper resultMapper;
    
    @Autowired
    private ExtractUserMapper extractUserMapper;
    @Autowired
    private SuperviseMapper superviseMapper;
    
    @Autowired
    private OrgnizationMapper orgnizationMapper;
    
    @Autowired
    private PersonRelMapper personRelMapper;

    @Autowired
    private SupplierService supplierService;
    
    @Autowired
    private ExpertExtractResultMapper expertExtractResultMapper;
    
    @Autowired
    private SmsRecordTempService smsRecordTempService;

	@Override
	public SupplierExtractProjectInfo selectByPrimaryKey(String id) {
		return recordMapper.selectByPrimaryKey(id);
	}

	
	@Override
	public List<SupplierExtractProjectInfo> getList(int i,User user,SupplierExtractProjectInfo project) throws Exception {
		
		
		if(user.getDataAccess() == 3){
			ArrayList<String> arrayList = new ArrayList<>();
			arrayList.add(user.getId());
			project.setProcurementDepIds(arrayList);
		}else{
			HashMap<String, Object> dataMap = AuthorityUtil.dataAuthority(user.getId());
			@SuppressWarnings("unchecked")
			List<String> orgIds = (List<String>) dataMap.get("superviseOrgs");
			project.setProcurementDepIds(orgIds);
		}
		
		
		 PageHelper.startPage(i, PropUtil.getIntegerProperty("pageSize"));
		 List<SupplierExtractProjectInfo> list = new ArrayList<>();
		 //获取是否内网标识 1外网 0内网
	     String ipAddressType= PropUtil.getProperty("ipAddressType");
	     if(ipAddressType.equals("1")){
	    	 project.setExtractTheWay((short)0);
	     }
	     
	     list = recordMapper.getList(project);
	     
		for (SupplierExtractProjectInfo projectInfo : list) {
			if(StringUtils.isBlank(projectInfo.getExtractUser())){
				String temp = "";
				List<ExtractUser> getlistByRid = extractUserMapper.getlistByRid(projectInfo.getId());
				for (ExtractUser e : getlistByRid) {
					temp += e.getName()+",";
				}
				if(StringUtils.isNotBlank(temp)){
					projectInfo.setExtractUser(temp.substring(0,temp.lastIndexOf(",")));
				}
			}
			if(projectInfo != null && projectInfo.getPurchaseType() != null && projectInfo.getPurchaseType().equals("询价采购")){
				projectInfo.setPurchaseType("询价");
			}
		}
		return list;
	}

	/**
	 * 修改项目信息
	 */
	@Override
	public int saveOrUpdateProjectInfo(SupplierExtractProjectInfo projectInfo) {
		
		if(1 == projectInfo.getStatus()){
			//项目状态为1,将首次抽取到的供应商做标记，去复核
			SupplierExtractProjectInfo record = recordMapper.selectByPrimaryKey(projectInfo.getId());
			/*List<SupplierExtractResult> sids = new ArrayList<>();
			if( StringUtils.isBlank(record.getProjectInto()) && StringUtils.isBlank(record.getProjectId())){
				sids = resultMapper.selectFirstSupplierToBeExtract(record.getId());
				
			}else if(StringUtils.isNotBlank(record.getProjectId())){
				sids =  resultMapper.selectFirstSupplierToBeExtractOfRel(record.getProjectId());
			}
			supplierService.updateExtractOrgid(record.getProcurementDepId(), sids);*/
			sendMessageToSupplier(record);
		}
		
		projectInfo.setExtractionTime(new Date());
		return recordMapper.saveOrUpdateProjectInfo(projectInfo);
	}

	/**
	 * 插入项目记录
	 */
	@Override
	public void insertProjectInfo(SupplierExtractProjectInfo record) {
		recordMapper.insertProjectInfo(record);
	}

	/**
	 * 打印记录表
	 * @return 
	 * @throws Exception 
	 */
	@Override
	public void printRecord(String id, HttpServletRequest request,
			HttpServletResponse response,String projectInto) throws Exception {
		
		//将项目状态变为抽取结束
		//SupplierExtractProjectInfo p = new SupplierExtractProjectInfo(id);
		//p.setStatus((short) 1);
		//recordMapper.saveOrUpdateProjectInfo(p);
		
		//根据记录id 查询项目信息不同供应商类别打印两个记录表
		Map<String, Object> info = selectExtractInfo(id,projectInto);
		
		if(null==info){
			return ;
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
        String fileName = "";
        if("PROJECT".equals(info.get("typeCode"))){
        	 fileName = WordUtil.createWord(info, "extractSupplierEng.ftl",
        	            name, request);
        }else if(null !=info.get("typeCode") && (info.get("typeCode").toString().split(";").length==1) ){
        	 fileName = WordUtil.createWord(info, "extractSupplierSV.ftl",
        	            name, request);
        }
        // 下载后的文件名
        String downFileName;
        if("PROJECT".equals(info.get("typeCode"))){
        	downFileName = "军队供应商抽取记录表(工程类).doc";
        }else{
        	downFileName = "军队供应商抽取记录表(物资服务类).doc";
        }
      
       /* if (request.getHeader("User-Agent").toUpperCase().indexOf("MSIE") > 0) {
            //解决IE下文件名乱码
            downFileName = URLEncoder.encode(downFileName, "UTF-8");
        } else {
            //解决非IE下文件名乱码
            downFileName = new String(downFileName.getBytes("UTF-8"), "ISO8859-1");
        }*/
        service.downLoadFile(request,response, filePath+File.separator+fileName, downFileName);
	}
	
	/**
	 * 
	 * <简述>需求变更前代码未优化时下载记录表查询信息方法 
	 *
	 * @author Jia Chengxiang
	 * @dateTime 2017-11-6下午6:17:32
	 * @param recordId
	 * @param projectInto
	 * @return
	 */
	@SuppressWarnings("unused")
	private Map<String, Object> selectExtractInfo1(String recordId, String projectInto) {
		
		SupplierExtractProjectInfo projectInfo = recordMapper.getProjectInfoById(recordId);
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
		/*if(StringUtils.isNotBlank(projectInfo.getExtractionSites())){
			map.put("construction", areaMapper.selectById(projectInfo.getConstructionPro()).getName() + areaMapper.selectById(projectInfo.getConstructionAddr()).getName());
		}*/
		//抽取地点
		map.put("construction", projectInfo.getExtractionSites());
		
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
		map.put("extractUsers",  extractUserMapper.getlistByRid(recordId));
		map.put("supervises",  superviseMapper.getlistByRid(recordId));
		
		String temp = "";
		if("PROJECT".equals(projectCode)){
			//供应商类型
			map.put("typeCode",projectCode);
			
			//建设单位
			map.put("buildCompany",projectInfo.getBuildCompany());
			
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
			HashMap<String,Object> hashMap2 = new HashMap<>();
			hashMap2.put("recordId", recordId);
			hashMap2.put("supplierType",projectCode);
			List<Integer> joins = new ArrayList<>();
			joins.add(0);
			joins.add(1);
			hashMap2.put("join",joins);
			if("relPro".equals(projectInto)){
				map.put("result", resultMapper.getSupplierListByRidForRel(hashMap2));
			}else if("advPro".equals(projectInto)){
				map.put("result", resultMapper.getSupplierListByRidForAdv(hashMap2));
			}else{
				map.put("result", resultMapper.getSupplierListByRid(hashMap2));
			}
			
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
					HashMap<String,Object> hashMap2 = new HashMap<>();
					hashMap2.put("recordId", recordId);
					hashMap2.put("supplierType",typeCode);
					List<Integer> joins = new ArrayList<>();
					joins.add(0);
					joins.add(1);
					hashMap2.put("join",joins);
					map.put(c+"Result", resultMapper.getSupplierListByRid(hashMap2));
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
				HashMap<String,Object> hashMap2 = new HashMap<>();
				hashMap2.put("recordId", recordId);
				hashMap2.put("supplierType",supplierTypeCode);
				ArrayList<Integer> joins = new ArrayList<>();
				joins.add(0);
				joins.add(1);
				hashMap2.put("joins", joins);
				if("relPro".equals(projectInto)){
					map.put("result", resultMapper.getSupplierListByRidForRel(hashMap2));
				}else if("advPro".equals(projectInto)){
					map.put("result", resultMapper.getSupplierListByRidForAdv(hashMap2));
				}else{
					map.put("result", resultMapper.getSupplierListByRid(hashMap2));
				}

				
				/*HashMap<String,String> hashMap2 = new HashMap<>();
				hashMap2.put("recordId", recordId);
				hashMap2.put("supplierType",supplierTypeCode);
				//List<SupplierExtractResult> supplierListByRid = resultMapper.getSupplierListByRid(hashMap2);
				map.put("result", resultMapper.getSupplierListByRid(hashMap2));*/
			}
		}
		
		return map;
	}
	
	/**
	 * 
	 * <简述>11-3日需求变更后，代码优化查询抽取信息 
	 *
	 * @author Jia Chengxiang
	 * @dateTime 2017-11-6下午6:18:27
	 * @param recordId
	 * @param projectInto
	 * @return
	 */
	private Map<String, Object> selectExtractInfo(String recordId, String projectInto) {
		
		SupplierExtractProjectInfo projectInfo = recordMapper.getProjectInfoById(recordId);
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日");
		
		String projectCode = projectInfo.getProjectType();
		Map<String, Object> map = new HashMap<>();
		SupplierExtractCondition condition = conditionMapper.getByRid(projectInfo.getId());
		if(null ==condition){
			return null;
		}
		HashMap<Object, Object> hashMap = new HashMap<>();
		hashMap.put("conditionId", condition.getId());
		
		//采购机构
		String ProcurementDep = "";
		if(projectInfo.getProcurementDepId().equals("4")){
			ProcurementDep = userServiceI.getUserById(projectInfo.getExtractUser()).getOrgName();
		}else{
			ProcurementDep = orgnizationMapper.findOrgByPrimaryKey(projectInfo.getProcurementDepId()).getName();
		}
		map.put("ProcurementDep",ProcurementDep);
		
		//项目实施地点
		/*if(StringUtils.isNotBlank(projectInfo.getExtractionSites())){
			map.put("construction", areaMapper.selectById(projectInfo.getConstructionPro()).getName() + areaMapper.selectById(projectInfo.getConstructionAddr()).getName());
		}*/
		//抽取地点
		map.put("construction", projectInfo.getExtractionSites());
		
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
		map.put("extractUsers",  extractUserMapper.getlistByRid(recordId));
		map.put("supervises",  superviseMapper.getlistByRid(recordId));
		
		//类别
		String temp = "";
		hashMap.put("propertyName", "categoryId");
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
		map.put("extractNum",condition.getExtractNum());
		
		if("PROJECT".equals(projectCode)){
			//供应商类型
			map.put("typeCode",projectCode);
			
			//建设单位
			map.put("buildCompany",projectInfo.getBuildCompany());
			
			//供应商资质等级
			hashMap.put("propertyName","levelTypeId");
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
			HashMap<String,Object> hashMap2 = new HashMap<>();
			hashMap2.put("recordId", recordId);
			hashMap2.put("supplierType",projectCode);
			List<Integer> joins = new ArrayList<>();
			joins.add(0);
			joins.add(1);
			hashMap2.put("join",joins);
			if("relPro".equals(projectInto)){
				map.put("result", resultMapper.getSupplierListByRidForRel(hashMap2));
			}else if("advPro".equals(projectInto)){
				map.put("result", resultMapper.getSupplierListByRidForAdv(hashMap2));
			}else{
				map.put("result", resultMapper.getSupplierListByRid(hashMap2));
			}
			
			//保密要求
			String isHavingConCert = condition.getIsHavingConCert();
			if(StringUtils.isNotBlank(isHavingConCert)){
				map.put("projectIsHavingConCert",isHavingConCert.equals("0")?"无":"有" );
			}else{
				map.put("projectIsHavingConCert","不限");
			}
			
			//企业性质
			String businessNature = condition.getBusinessNature();
			map.put("projectBusinessNature",StringUtils.isBlank(businessNature)?"不限":(dictionaryDataMapper.selectByPrimaryKey(businessNature).getName()));
			
		}else{
			//供应商类型
			String supplierTypeCode = condition.getSupplierTypeCode();
			/*
			 * 原打印记录表等级
			 * 
			 * if("GOODS".equals(supplierTypeCode)){
				map.put("typeCode","物资生产，物资销售");
				
				//生产供应商等级
				hashMap.put("propertyName", "levelTypeId");
				List<String> byMap = conditionRelationMapper.getByMap(hashMap);
				String productLevel = "物资生产：";
				if(null!=byMap && byMap.size()>0){
					for (String string : byMap) {
						productLevel +=(string + ",");
					}
					productLevel = productLevel.substring(0,productLevel.lastIndexOf(","));
				}else{
					productLevel +="不限等级";
				}
				//销售供应商等级
				hashMap.put("propertyName", "salesLevelTypeId");
				List<String> sales = conditionRelationMapper.getByMap(hashMap);
				String salesLevel = "物资销售：";
				if(null!=sales && sales.size()>0){
					for (String string : sales) {
						salesLevel +=(string + ",");
					}
					salesLevel = salesLevel.substring(0,salesLevel.lastIndexOf(","));
				}else{
					salesLevel +="不限等级";
				}
				
				
				map.put("level",productLevel+"  "+salesLevel);
				
			}else{
				map.put("typeCode",dictionaryDataMapper.selectByCode(supplierTypeCode).get(0).getName());
				
				//供应商等级
				hashMap.put("propertyName", "levelTypeId");
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
			}*/
			
			
			if("GOODS".equals(supplierTypeCode)){
				map.put("typeCode","物资生产，物资销售");
				
				//生产供应商等级
				hashMap.put("propertyName", "cateAndLevel");
				List<ExtractConditionRelation> byMap = conditionRelationMapper.getCateAndLevelByMap(hashMap);
				String productLevel = "物资生产：";
				if(null!=byMap && byMap.size()>0){
					for (ExtractConditionRelation cs : byMap) {
						productLevel +=(cs.getPropertyValue()+":"+cs.getCateLevel() + ",");
					}
					productLevel = productLevel.substring(0,productLevel.lastIndexOf(","));
				}else{
					productLevel +="不限等级";
				}
				//销售供应商等级
				hashMap.put("propertyName", "salesCateAndLevel");
				List<ExtractConditionRelation> sales = conditionRelationMapper.getCateAndLevelByMap(hashMap);
				String salesLevel = "物资销售：";
				if(null!=sales && sales.size()>0){
					for (ExtractConditionRelation cs : sales) {
						salesLevel +=(cs.getPropertyValue()+":"+cs.getCateLevel() + ",");
					}
					salesLevel = salesLevel.substring(0,salesLevel.lastIndexOf(","));
				}else{
					salesLevel +="不限等级";
				}
				map.put("level",productLevel+"  "+salesLevel);
				
			}else{
				map.put("typeCode",dictionaryDataMapper.selectByCode(supplierTypeCode).get(0).getName());
				
				//供应商等级
				hashMap.put("propertyName", "cateAndLevel");
				List<ExtractConditionRelation> byMap = conditionRelationMapper.getCateAndLevelByMap(hashMap);
				temp = "";
				if(null!=byMap && byMap.size()>0){
					for (ExtractConditionRelation cs : byMap) {
						temp +=(cs.getPropertyValue()+":"+cs.getCateLevel() + ",");
					}
					temp = temp.substring(0,temp.lastIndexOf(","));
				}else{
					temp ="不限等级";
				}
				map.put("level",temp);
			}
			
			//抽取结果
			HashMap<String,Object> hashMap2 = new HashMap<>();
			hashMap2.put("recordId", recordId);
			hashMap2.put("supplierType",supplierTypeCode);
			List<Integer> joins = new ArrayList<>();
			joins.add(0);
			joins.add(1);
			hashMap2.put("join",joins);
			List<SupplierExtractResult> supplierList = null ;
			if("relPro".equals(projectInto)){
				supplierList = resultMapper.getSupplierListByRidForRel(hashMap2);
			}else if("advPro".equals(projectInto)){
				supplierList = resultMapper.getSupplierListByRidForAdv(hashMap2);
			}else{
				supplierList = resultMapper.getSupplierListByRid(hashMap2);
			}
			map.put("result",supplierList );
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
		return recordMapper.getListByMap(p);
	}

	
	/**
	 * 查询待通知项目
	 */
	@Override
	public List<SupplierExtractProjectInfo> selectAutoExtractProject() {
		
		List<SupplierExtractProjectInfo> projectInfos = recordMapper.selectAutoExtractProject();
		return projectInfos;
	}


	@Override
	public List<SupplierExtractProjectInfo> selectRecordForExport(SupplierExtractProjectInfo projectInfo) {
		return  recordMapper.getListByMap(projectInfo);
	}


	@Override
	public Map<String, String> extractAgain(String recordId, String conditionId) {
		String rid_new = UUIDUtils.getUUID32();
    	String cid_new = UUIDUtils.getUUID32();
    	//复制项目信息修改id再保存一条抽取记录
    	recordMapper.copyRecordToAgainById(rid_new,cid_new,recordId);
    	
    	//复制人员信息在保存一份
    	personRelMapper.copyPersonRelToAgainByRid(rid_new,recordId);
    	
    	//返回新的recordId conditioinId
    	Map<String, String> hashMap = new HashMap<>();
    	hashMap.put("conditionId",cid_new);
    	hashMap.put("recordId", rid_new);
		return hashMap;
	}
	
	/**
	 * 
	 * <简述>给抽取到的供应商发送短信 
	 *
	 * @author Jia Chengxiang
	 * @dateTime 2018-1-2下午7:19:07
	 */
	@Override
	public void  sendMessageToSupplier(SupplierExtractProjectInfo record) {
		if(record.getProcurementDepId().equals("4")){
			return;
		}
		SmsRecordTemp smsRecord = new SmsRecordTemp();
		smsRecord.setOrgId(record.getProcurementDepId());
		smsRecord.setSendLink(DictionaryDataUtil.getId("GYSCQDX"));
		smsRecord.setOperator(record.getExtractUser());
        //受领采购文件地址
		StringBuffer sb = new StringBuffer();
        String province = record.getSellProvince();
        String city = record.getSellAddress();
        if(StringUtils.isNotBlank(province)){
            Area area1 = areaMapper.selectById(province);
            if(area1 != null){
            	sb.append(area1.getName());
            }
            if(StringUtils.isNotBlank(city)){
            	Area area2 = areaMapper.selectById(city);
            	if(area1 != null && area2 != null){
            		sb.append(area2.getName());
            	}
            }
            sb.append(record.getSellSite());
        }
        String address = sb.toString();
        
        //待发送短信供应商集合
        List<SupplierExtractResult> supplierList = null ;
        HashMap<String,Object> hashMap2 = new HashMap<>();
		hashMap2.put("recordId", record.getId());
		//hashMap2.put("supplierType",supplierTypeCode);
		List<Integer> joins = new ArrayList<>();
		joins.add(1);
		hashMap2.put("join",joins);
		if(StringUtils.isNotBlank(record.getProjectInto())){
			supplierList = resultMapper.getSupplierListByRidForAdv(hashMap2);
		}else{
			supplierList = resultMapper.getSupplierListByRid(hashMap2);
		}
		
		//编辑内容，发送短信
		for (SupplierExtractResult supplier : supplierList) {
			//短信发送内容
			String userId = expertExtractResultMapper.findUserByTypeId(supplier.getSupplierId());
			smsRecord.setRecipient(userId);
			smsRecord.setReceiveNumber(supplier.getArmyBuinessTelephone());
			String content = "【军队采购网通知】你单位已确定参加"+record.getProjectName()+"，请携带有效身份证明，于"+DateUtils.dateToZHString(record.getSellBegin())+"前往"+address+"购买或领取采购文件。采购机构联系人："+record.getContactPerson()+"，联系座机："+record.getContactNum()+"，联系手机："+record.getContactPhone()+"。";
			smsRecord.setSendContent(content);
			smsRecordTempService.insertSelective(smsRecord);
		}
	}

}
