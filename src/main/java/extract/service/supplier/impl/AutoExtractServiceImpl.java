package extract.service.supplier.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ses.model.sms.Supplier;
import ses.model.sms.SupplierExtRelate;
import synchro.service.SynchRecordService;
import synchro.util.Constant;
import synchro.util.FileUtils;
import bss.service.ppms.PackageService;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.databind.ObjectMapper;

import extract.autoVoiceExtract.Epoint005WebService;
import extract.autoVoiceExtract.PeopleYytz;
import extract.autoVoiceExtract.ProjectYytz;
import extract.dao.supplier.ExtractConditionRelationMapper;
import extract.dao.supplier.SupplierExtractConditionMapper;
import extract.dao.supplier.SupplierExtractRelateResultMapper;
import extract.model.supplier.ProjectVoiceResult;
import extract.model.supplier.SupplierConType;
import extract.model.supplier.SupplierExtractCondition;
import extract.model.supplier.SupplierExtractProjectInfo;
import extract.model.supplier.SupplierExtractResult;
import extract.model.supplier.SupplierVoiceResult;
import extract.service.supplier.AutoExtractSupplierService;
import extract.service.supplier.SupplierExtractConditionService;
import extract.service.supplier.SupplierExtractRecordService;
import extract.service.supplier.SupplierExtractRelateResultService;
import extract.util.DateUtils;
import extract.util.WebServiceUtil;

@Service
public class AutoExtractServiceImpl implements AutoExtractSupplierService {

	  ObjectMapper mapper = new ObjectMapper();
	  

	  @Autowired
	  SupplierExtractConditionMapper conditionMapper;
	  
	  @Autowired
	  private SupplierExtractConditionService conditionService;

	  @Autowired
	  SupplierExtractRelateResultMapper supplierExtRelateMapper;

	  @Autowired
	  PackageService packageService;
	  
	  
	  @Autowired
	  private ExtractConditionRelationMapper contypeMapper;//条件关联表
	  
	  
	  @Autowired
	  private SupplierExtractRecordService recordService; //记录
	  
	  @Autowired
	  private SupplierExtractRelateResultService resultService;
	  
	  /** 导入导出记录service  **/
      @Autowired
      private SynchRecordService  synchRecordService;
	
      @Autowired
      private SupplierExtractRelateResultMapper supplierExtractRelateResultMapper;//真实、预研结果项目存储
	/**
	 * 自动抽取
	 */
	@Override
	public Map<String, Object> autoExtract(SupplierExtractCondition condition,
			SupplierConType conType,String projectInfo) {
		
		HashMap<String, Object> map = new HashMap<>();
		
		
		//排除供应商
		conditionService.excludeSupplier(condition);
		
		String typeCode = condition.getSupplierTypeCode();
		try {
			//设置抽取条件
			String code = conditionService.setExtractCondition(typeCode, condition, conType);
			//查询供应商
			if(null == condition.getExtractNum()){
				map.put("error",code+"ExtractNumError");
				return map;
			}
			List<Supplier> suppliers = supplierExtRelateMapper.autoExtractSupplierList(condition);
			//存储自动抽取结果
			resultService.saveOrUpdateVoiceResult(condition,suppliers,null,projectInfo);
			
			String status = callVoiceService(suppliers,condition.getRecordId());
			if("500".equals(status)|| StringUtils.isBlank(status)){
				map.put("error", "语音接口调用异常");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		return null;
	}
	
	
	
	/**
	 * 上传待通知信息（本地测试）
	 * <简述> 
	 *
	 * @author Jia Chengxiang
	 * @dateTime 2017-10-16下午4:19:02
	 * @param suppliers
	 * @param recordId
	 * @return
	 */
	public String callVoiceService(List<Supplier> suppliers, String recordId) {
		
		//查询项目信息
		SupplierExtractProjectInfo projectInfo = recordService.selectByPrimaryKey(recordId);
		
		ArrayList<PeopleYytz> arrayList = new ArrayList<>();
		for (Supplier supplier : suppliers) {
			PeopleYytz peopleYytz = new PeopleYytz();
			peopleYytz.setUsername(supplier.getSupplierName());
			peopleYytz.setMobile("017319170452");
			peopleYytz.setContactname(supplier.getArmyBusinessName());
			peopleYytz.setContactmobile("017319170452");
			arrayList.add(peopleYytz);
		}
		
		
		ProjectYytz projectYytz = new ProjectYytz();
		projectYytz.setProvince(projectInfo.getProvinceName()); 
		projectYytz.setAddress(projectInfo.getCityName());
		projectYytz.setSite(projectInfo.getSellSite()); 
		
		projectYytz.setContactnum(projectInfo.getContactNum()); 
		projectYytz.setContactperson(projectInfo.getContactPerson()); 
		projectYytz.getPeoplelist().addAll(arrayList);
		projectYytz.setProjectid(projectInfo.getProjectId());
		projectYytz.setProjectname(projectInfo.getProjectName()); 
		projectYytz.setRecordid(projectInfo.getId()); 
		projectYytz.setReviewdays(0); 
		projectYytz.setSellend(DateUtils.dateToXmlDate(projectInfo.getSellEnd())); 
		projectYytz.setStarttime(DateUtils.dateToXmlDate(projectInfo.getSellBegin()));
		
		Epoint005WebService service = WebServiceUtil.getService();
		
		String putObject = service.putObject(projectYytz, "C");
		System.out.println(putObject);
		return putObject;
	}
	
	
	/**
	 * 上传待通知信息2（项目，供应商信息直接传入）
	 * <简述> 
	 *
	 * @author Jia Chengxiang
	 * @dateTime 2017-10-19下午7:52:59
	 * @param suppliers
	 * @param recordId
	 * @return
	 */
	public String callVoiceService2(List<Supplier> suppliers, SupplierExtractProjectInfo projectInfo) {
		
		ArrayList<PeopleYytz> arrayList = new ArrayList<>();
		for (Supplier supplier : suppliers) {
			PeopleYytz peopleYytz = new PeopleYytz();
			peopleYytz.setUsername(supplier.getSupplierName());
			peopleYytz.setMobile("17319170452");
			peopleYytz.setContactname(supplier.getArmyBusinessName());
			peopleYytz.setContactmobile("17319170452");
			arrayList.add(peopleYytz);
		}
		
		
		ProjectYytz projectYytz = new ProjectYytz();
		projectYytz.setProvince(projectInfo.getProvinceName()); 
		projectYytz.setAddress(projectInfo.getCityName());
		projectYytz.setSite(projectInfo.getSellSite()); 
		
		projectYytz.setContactnum(projectInfo.getContactNum()); 
		projectYytz.setContactperson(projectInfo.getContactPerson()); 
		projectYytz.getPeoplelist().addAll(arrayList);
		projectYytz.setProjectid(projectInfo.getProjectId());
		projectYytz.setProjectname(projectInfo.getProjectName()); 
		projectYytz.setRecordid(projectInfo.getId()); 
		projectYytz.setReviewdays(0); 
		projectYytz.setSellend(DateUtils.dateToXmlDate(projectInfo.getSellEnd())); 
		projectYytz.setStarttime(DateUtils.dateToXmlDate(projectInfo.getSellBegin()));
		
		Epoint005WebService service = WebServiceUtil.getService();
		
		String putObject = service.putObject(projectYytz, "C");
		System.out.println(putObject);
		return putObject;
	}
	
	
	/**
	 * 接收语音通知结果
	 * <简述> 
	 *
	 * @author Jia Chengxiang
	 * @dateTime 2017-10-13上午10:46:24
	 * @return
	 */
	@Override
	public void receiveVoiceResult(String json) {
		
		ProjectVoiceResult projectVoiceResult = null;
		
		//解析json
		if(StringUtils.isNotBlank(json)){
			
			//解析json
			try {
				projectVoiceResult = mapper.readValue(json, ProjectVoiceResult.class);
				
				/*
				 projectVoiceResult = mapper.readValue(json, ProjectVoiceResult.class);
				 System.out.println(projectVoiceResult.getRecordId());
				ProjectVoiceResult parse = (ProjectVoiceResult)JSON.parse(json);*/
				
				
				/*Map<Object, Class<SupplierVoiceResult>> map = new HashMap<Object, Class<SupplierVoiceResult>>();
				map.put("suppliers", SupplierVoiceResult.class);
				projectVoiceResult = (ProjectVoiceResult)JSONObject.toBean(JSONObject.fromObject(json),ProjectVoiceResult.class,map);*/

				//System.out.println(parse.getProjectId());
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			//获取项目信息,查询抽取数量
			SupplierExtractProjectInfo projectInfo = recordService.selectByPrimaryKey(projectVoiceResult.getRecordId());
			SupplierExtractCondition condition = conditionMapper.selectByPrimaryKey(projectInfo.getConditionId());
			
			HashMap<Object, Object> hashMap = new HashMap<>();
			String supplierTypeCode = condition.getSupplierTypeCode().toLowerCase();
			hashMap.put("conditionId", projectInfo.getConditionId());
			hashMap.put("propertyName", supplierTypeCode+"ExtractNum");
			List<String> conditionConTypes = contypeMapper.getByMap(hashMap);
			
			String ExtractNum = null;
			if(conditionConTypes.size()>0){
				ExtractNum = conditionConTypes.get(0);
			}
			
			if(null != projectVoiceResult){
				//获取参加状态,持久化
				List<SupplierVoiceResult> suppliersResult = projectVoiceResult.getSuppliers();
				int count = 0;
				for (SupplierVoiceResult supplier : suppliersResult) {
					if(supplier.getJoin().equals("1")){
						count ++;
					}
				}
				
				//修改供应商参加状态
				resultService.saveOrUpdateVoiceResult(condition, null,suppliersResult,projectInfo.getProjectInto());
				//判断参加人数是否满足，不满足再次获取供应商通知
				int parseInt = Integer.parseInt(ExtractNum);
				if(count<parseInt){
					SupplierConType conType = this.selectconType(parseInt-count,condition.getId());
					Map<String, Object> autoExtract = this.autoExtract(condition, conType,projectInfo.getProjectInto());
				}else{
					//修改项目状态为抽取结束
				}
			}
		}
	}
	
	
	/**
	 * 查询详细抽取条件
	 * <简述> 
	 *
	 * @author Jia Chengxiang
	 * @dateTime 2017-10-16下午3:42:44
	 * @return
	 */
	public SupplierConType selectconType(int extractNum,String conditionId) {
		
		List<Map<String, String>> conTypeList = contypeMapper.getConTypeList(conditionId);
		
		SupplierConType conType = new SupplierConType();
		for (Map<String, String> map : conTypeList) {
			try {
				Class<?> supplierConType = SupplierConType.class;
				supplierConType.getMethod("set"+map.get("property_name")).invoke(conType, "property_value");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return conType;
	}
	
	/**
	 * 外网查询待通知项目集合
	 * <简述> 
	 *
	 * @author Jia Chengxiang
	 * @dateTime 2017-10-19下午7:09:34
	 * @return
	 */
	public void selectAutoExtractProject() {
		
		List<SupplierExtractProjectInfo> projectInfos = recordService.selectAutoExtractProject();
		if(null != projectInfos){
			for (SupplierExtractProjectInfo projectInfo : projectInfos) {
				
				SupplierExtractCondition condition = conditionMapper.getByRid(projectInfo.getId());
				

				HashMap<Object, Object> hashMap = new HashMap<>();
				String supplierTypeCode = condition.getSupplierTypeCode().toLowerCase();
				hashMap.put("conditionId", projectInfo.getConditionId());
				hashMap.put("propertyName", supplierTypeCode+"ExtractNum");
				List<String> conditionConTypes = contypeMapper.getByMap(hashMap);
				
				String ExtractNum = null;
				if(conditionConTypes.size()>0){
					ExtractNum = conditionConTypes.get(0);
				}
				
				SupplierConType conType = selectconType(Integer.parseInt(ExtractNum),condition.getId());
				
				//排除供应商
				conditionService.excludeSupplier(condition);
				
				String typeCode = condition.getSupplierTypeCode();
				try {
					//设置抽取条件
					String code = conditionService.setExtractCondition(typeCode, condition, conType);
					//查询供应商
					List<Supplier> suppliers = supplierExtRelateMapper.autoExtractSupplierList(condition);
					//存储自动抽取结果
					resultService.saveOrUpdateVoiceResult(condition,suppliers,null,projectInfo.getProjectInto());
					
					String status = callVoiceService2(suppliers,projectInfo);
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}


	 /**
	   * 点击自动抽取 将项目信息，抽取条件从内网导出
	   * <简述> 
	   *
	   * @author Jia Chengxiang
	   * @dateTime 2017-10-20下午6:09:19
	   * @return
	   */
	@Override
	public Map<String, Object> exportExtractInfo(
			SupplierExtractCondition condition, SupplierConType conType,
			String projectInto) {
		//查询项目信息
		SupplierExtractProjectInfo projectInfo = recordService.selectByPrimaryKey(condition.getRecordId());
		if(null!=projectInfo){
			 //生成json 并保存
            FileUtils.writeFile(FileUtils.getExporttFile(FileUtils.SUPPLIER_EXTRACT_PROJECT_PATH_FILENAME, 35),JSON.toJSONString(projectInfo));
		}
		
		//条件信息
        FileUtils.writeFile(FileUtils.getExporttFile(FileUtils.SUPPLIER_EXTRACT_PROJECT_PATH_FILENAME, 35),JSON.toJSONString(condition));
		
        //详细条件
        FileUtils.writeFile(FileUtils.getExporttFile(FileUtils.SUPPLIER_EXTRACT_PROJECT_PATH_FILENAME, 35),JSON.toJSONString(condition));
		
        synchRecordService.synchBidding(new Date(), "1", Constant.DATE_SYNCH_SUPPLIER_EXTRACT, Constant.OPER_TYPE_EXPORT, Constant.SUPPLIER_EXTRACT_COMMIT);
		return null;
	}
	
	@Override
	public void  importSupplierExtract(File file) {
	   int num = 0;
	   for (File file2 : file.listFiles()) {
            //抽取项目信息
            if(file2.getName().contains(FileUtils.SUPPLIER_EXTRACT_PROJECT_PATH_FILENAME)){
                List<SupplierExtractProjectInfo> projectList = FileUtils.getBeans(file2, SupplierExtractProjectInfo.class);
                if(projectList != null && projectList.size() > 0){
                    num += projectList.size();
                    for (SupplierExtractProjectInfo projectInfo : projectList) {
                        SupplierExtractProjectInfo projectInfo2 = recordService.selectByPrimaryKey(projectInfo.getId());
                        if(projectInfo2 != null){
                        	recordService.update(projectInfo);
                        }else{
                        	recordService.insertProjectInfo(projectInfo);
                        }
                    }
                }
            }
            // 抽取条件
            if (file2.getName().contains(FileUtils.SUPPLIER_EXTRACT_CONDITION_PATH_FILENAME)) {
                List<SupplierExtractCondition> conditions = FileUtils.getBeans(file2, SupplierExtractCondition.class);
                num += conditions.size();
                for (SupplierExtractCondition condition : conditions) {
                	SupplierExtractCondition extractCondition = conditionMapper.selectByPrimaryKey(condition.getId());
                    if(extractCondition != null){
                    	conditionMapper.updateByPrimaryKeySelective(condition);
                    }else{
                    	conditionMapper.insertSelective(condition);
                    }
                    if(null!=condition.getSupplierConType()){
                    	conditionService.saveContype(condition, condition.getSupplierConType());
                    }
                }
            }
        }
        synchRecordService.synchBidding(new Date(), num+"", Constant.DATE_SYNCH_SUPPLIER_EXTRACT, Constant.OPER_TYPE_IMPORT, Constant.SUPPLIER_EXTRACT_COMMIT_IMPORT);
	}
	
	 /**
     * 供应商抽取结果导入
     */
    @Override
    public void importSupplierExtractResult(File file) {
        int num = 0;
        for (File file2 : file.listFiles()) {
            // 抽取结果信息
            if (file2.getName().contains(FileUtils.SUPPLIER_EXTRACT_RESULT_PATH_FILENAME)) {
                List<SupplierExtractResult> resultList = FileUtils.getBeans(file2, SupplierExtractResult.class);
                num += resultList == null ? 0 : resultList.size();
                for (SupplierExtractResult result : resultList) {
                    SupplierExtractResult selectById = resultService.selectById(result.getId());
                    if(selectById != null){
                    	resultService.updateByPrimaryKeySelective(result);
                    }else{
                    	resultService.insertSelective(result);
                    }
                }
            }
            if (file2.getName().contains(FileUtils.SUPPLIER_EXTRACT_ADV_RESULT_PATH_FILENAME)) {
            	List<SupplierExtractResult> resultList = FileUtils.getBeans(file2, SupplierExtractResult.class);
            	 num += resultList == null ? 0 : resultList.size();
            	for (SupplierExtractResult result : resultList) {
            		SupplierExtractResult selectById = resultService.selectAdvById(result.getId());
            		if(selectById != null){
            			resultService.updateAdvByPrimaryKeySelective(result);
            		}else{
            			resultService.insertAdvSelective(result);
            		}
            	}
            }
        }
        synchRecordService.synchBidding(new Date(), num+"", Constant.DATE_SYNCH_SUPPLIER_EXTRACT_RESULT, Constant.OPER_TYPE_IMPORT, Constant.SUPPLIER_EXTRACT_RESULT_COMMIT_IMPORT);
    }

    /**
     * 供应商抽取结果导出
     */
    @Override
    public void exportSupplierExtractResult(String start, String end, Date synchDate) {
        int sum = 0;
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("start", start);
        hashMap.put("end", end);
        List<SupplierExtractResult> resultList = resultService.selectByUpdateDate(hashMap);
        if (resultList != null && resultList.size() > 0) {
            sum += resultList.size();
            // 供应商抽取结果信息
            FileUtils.writeFile(FileUtils.getExporttFile(FileUtils.SUPPLIER_EXTRACT_RESULT_PATH_FILENAME, 36), JSON.toJSONString(resultList));
        }
        List<SupplierExtRelate> resultList2 = resultService.selectByAdvUpdateDate(hashMap);
        if (resultList2 != null && resultList2.size() > 0) {
        	sum += resultList2.size();
        	// 供应商抽取结果信息
        	FileUtils.writeFile(FileUtils.getExporttFile(FileUtils.SUPPLIER_EXTRACT_ADV_RESULT_PATH_FILENAME, 36), JSON.toJSONString(resultList2));
        }
        synchRecordService.synchBidding(new Date(), sum + "",Constant.DATE_SYNCH_SUPPLIER_EXTRACT_RESULT, Constant.OPER_TYPE_EXPORT,Constant.SUPPLIER_EXTRACT_RESULT_COMMIT);
    }
}
