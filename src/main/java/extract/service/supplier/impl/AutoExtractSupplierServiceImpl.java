package extract.service.supplier.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ses.model.sms.Supplier;
import ses.model.sms.SupplierExtRelate;
import ses.util.MobileUtils;
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
import extract.model.supplier.ExtractConditionRelation;
import extract.model.supplier.ProjectVoiceResult;
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
public class AutoExtractSupplierServiceImpl implements AutoExtractSupplierService {

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
	
	/**
	 * 自动抽取()
	 */
	@Override
	public Map<String, Object> autoExtract(SupplierExtractCondition condition,
			String projectInto) {
		
		HashMap<String, Object> map = new HashMap<>();
		
		
		//排除供应商
		conditionService.excludeSupplier(condition);
		
		String typeCode = condition.getSupplierTypeCode();
		try {
			//设置抽取条件
			conditionService.setExtractCondition2(condition, typeCode);
			//查询当前应抽取人数
			if(null == condition.getExtractNum()){
				map.put("error","extractNumError");
				return map;
			}
			//查询供应商
			List<Supplier> suppliers = supplierExtRelateMapper.autoExtractSupplierList(condition);
			if(suppliers.size()>0){
				
				//存储自动抽取结果
				resultService.saveOrUpdateVoiceResult(condition,suppliers,null,projectInto);
				//查询项目信息
				SupplierExtractProjectInfo projectInfo = recordService.selectByPrimaryKey(condition.getRecordId());
				
				//调用语音接口
				String status = callVoiceService2(suppliers,projectInfo);
				
				if("500".equals(status)|| StringUtils.isBlank(status)){
					map.put("error", "语音接口调用异常");
				}
			}else{
				//修改项目状态为不满足条件
				SupplierExtractProjectInfo projectInfo = new SupplierExtractProjectInfo();
				projectInfo.setStatus((short)1);
				projectInfo.setId(condition.getRecordId());
				recordService.update(projectInfo);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
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
	public String callVoiceService2(List<Supplier> suppliers, SupplierExtractProjectInfo projectInfo) throws Exception {
		
		ArrayList<PeopleYytz> arrayList = new ArrayList<>();
		
		for (Supplier supplier : suppliers) {
			PeopleYytz peopleYytz = new PeopleYytz();
			peopleYytz.setUsername(supplier.getSupplierName());
			peopleYytz.setMobile(MobileUtils.getMobile(supplier.getArmyBuinessTelephone()));
			peopleYytz.setContactname(supplier.getArmyBusinessName());
			peopleYytz.setContactmobile(MobileUtils.getMobile(supplier.getArmyBuinessTelephone()));
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
		projectYytz.setSpecexpnum(projectInfo.getExtractNum());
		
		Epoint005WebService service = WebServiceUtil.getService();
		
		String putObject = service.putObject(projectYytz, "C");
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
	public String receiveVoiceResult(String json) {
		
		ProjectVoiceResult projectVoiceResult = null;
		//解析json
		try {
			Map<Object, Class<SupplierVoiceResult>> cMap = new HashMap<Object, Class<SupplierVoiceResult>>();
		    cMap.put("supplierResult", SupplierVoiceResult.class);
			json = json.indexOf("＂") != -1 ? json.replace("＂", "\"") : json;
			projectVoiceResult = (ProjectVoiceResult)JSONObject.toBean(JSONObject.fromObject(json),ProjectVoiceResult.class,cMap);
		} catch (Exception e) {
			e.printStackTrace();
			return "error of json transform";
		}
		
		//获取项目信息,查询抽取数量
		SupplierExtractProjectInfo projectInfo = recordService.selectByPrimaryKey(projectVoiceResult.getRecordeId());
		SupplierExtractCondition condition = conditionMapper.selectByPrimaryKey(projectInfo.getConditionId());
		
		HashMap<Object, Object> hashMap = new HashMap<>();
		hashMap.put("conditionId", projectInfo.getConditionId());
		hashMap.put("propertyName","currentExtractNum");
		
		//查询当前项目要抽取的人数
		List<String> currentExtractNum = contypeMapper.getByMap(hashMap);
		
		String ExtractNum = null;
		if(currentExtractNum.size()>0){
			ExtractNum = currentExtractNum.get(0);
		}
		
		if(null != projectVoiceResult){
			//获取参加状态,持久化
			List<SupplierVoiceResult> suppliersResult = projectVoiceResult.getSupplierResult();
			Short count = 0;
			for (SupplierVoiceResult supplier : suppliersResult) {
				if(supplier.getJoin().equals("1")){
					count ++;
				}
				//去除手机号前面的0
				supplier.setSupplierId(MobileUtils.reMobile(supplier.getSupplierId()));
			}
			
			//修改供应商参加状态
			resultService.saveOrUpdateVoiceResult(condition, null,suppliersResult,projectInfo.getProjectInto());
			//判断参加人数是否满足，不满足再次获取供应商通知
			Short parseShort = Short.parseShort(ExtractNum);
			if(count<parseShort){
				//再次抽取的数量
				Short supplement = (short) (parseShort-count);
				condition.setExtractNum((short) (parseShort-count));
				//将需要再次抽取的数量写入进数据库，便于下次进行判断
				hashMap.put("propertyValue", supplement.toString());
				contypeMapper.updateByMap(hashMap);
				condition = this.selectconType(condition);
				this.autoExtract(condition,projectInfo.getProjectInto());
			}else{
				//修改项目状态为抽取结束
				projectInfo.setStatus((short)1);
				recordService.update(projectInfo);
			}
		}
		return "service error";
	}
	
	
	/**
	 * 查询详细抽取条件
	 * <简述> 
	 *
	 * @author Jia Chengxiang
	 * @dateTime 2017-10-16下午3:42:44
	 * @return
	 */
	public SupplierExtractCondition selectconType(SupplierExtractCondition condition) {
		
		List<ExtractConditionRelation> conTypeList = contypeMapper.getConTypeList(condition.getId());
		
		//SupplierConType conType = new SupplierConType();
		Class<?> conditionClass = condition.getClass();
		for (ExtractConditionRelation map : conTypeList) {
			
			try {
				
				//首字母大写
				String string = map.getPropertyName();
				char[] charArray = string.toCharArray();
				charArray[0] -= 32;
				String valueOf = String.valueOf(charArray);
				Object invoke = conditionClass.getMethod("get"+valueOf).invoke(condition);
				if(null == invoke){
					conditionClass.getMethod("set"+valueOf,String.class).invoke(condition, map.getPropertyValue());
				}else{
					conditionClass.getMethod("set"+valueOf,String.class).invoke(condition, invoke+","+map.getPropertyValue());
				}
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return condition;
	}

	
	/**
	 * 外网查询待通知项目集合
	 * <简述> 
	 *
	 * @author Jia Chengxiang
	 * @dateTime 2017-10-19下午7:09:34
	 * @return
	 */
	@Override
	public void selectAutoExtractProject(Date start,Date end) {
		SupplierExtractProjectInfo p = new SupplierExtractProjectInfo();
		p.setExtractTheWay((short)0);
		p.setStartTime(DateUtils.dateToString(DateUtils.getTodayZeroTime()));
		p.setEndTime(common.utils.DateUtils.getCurrentTime());
		p.setStatus((short)2);
		List<SupplierExtractProjectInfo> projectInfos = recordService.selectRecordForExport(p);
		if(null != projectInfos){
			for (SupplierExtractProjectInfo projectInfo : projectInfos) {
				
				SupplierExtractCondition condition = conditionMapper.getByRid(projectInfo.getId());
				

				HashMap<Object, Object> hashMap = new HashMap<>();
				hashMap.put("conditionId", projectInfo.getConditionId());
				hashMap.put("propertyName", "currentExtractNum");
				List<String> conditionConTypes = contypeMapper.getByMap(hashMap);
				
				String ExtractNum = null;
				if(conditionConTypes.size()>0){
					ExtractNum = conditionConTypes.get(0);
				}
				
				condition.setExtractNum(Short.parseShort(ExtractNum));
				condition = selectconType(condition);
				
				//排除供应商
				conditionService.excludeSupplier(condition);
				
				String typeCode = condition.getSupplierTypeCode();
				try {
					//设置抽取条件
					conditionService.setExtractCondition2(condition,typeCode);
					//查询供应商
					List<Supplier> suppliers = supplierExtRelateMapper.autoExtractSupplierList(condition);
					if(suppliers.size()>0){
						//存储自动抽取结果
						resultService.saveOrUpdateVoiceResult(condition,suppliers,null,projectInfo.getProjectInto());
						projectInfo.setExtractNum(condition.getExtractNum());
						callVoiceService2(suppliers,projectInfo);
					}else{
						//修改项目状态为不满足条件
						projectInfo.setStatus((short)1);
						recordService.update(projectInfo);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}


	/**
	 * 前台点击自动抽取后，将当前抽取条件导出
	 */
	@Override
	public String exportExtractInfo(SupplierExtractCondition condition,String projectInto) {
		ArrayList<SupplierExtractProjectInfo> projectInfos = new ArrayList<>();
		ArrayList<SupplierExtractCondition> conditions = new ArrayList<>();
		conditions.add(condition);
		//查询项目信息
		SupplierExtractProjectInfo projectInfo = recordService.selectByPrimaryKey(condition.getRecordId());
		projectInfos.add(projectInfo);
		if(null!=projectInfo){
			projectInfo.setStatus((short)2);
			projectInfo.setExtractTheWay((short)0);
			 //生成json 并保存
            FileUtils.writeFile(FileUtils.getExporttFile(FileUtils.SUPPLIER_EXTRACT_PROJECT_PATH_FILENAME, 35),JSON.toJSONString(projectInfos));
		}
		
		//条件信息
        FileUtils.writeFile(FileUtils.getExporttFile(FileUtils.SUPPLIER_EXTRACT_CONDITION_PATH_FILENAME, 35),JSON.toJSONString(conditions));
		
        //详细条件
        //FileUtils.writeFile(FileUtils.getExporttFile(FileUtils.SUPPLIER_EXTRACT_CONTYPE_PATH_FILENAME, 35),JSON.toJSONString(condition.getSupplierConType()));
		
        synchRecordService.synchBidding(new Date(), "1", Constant.DATE_SYNCH_SUPPLIER_EXTRACT_INFO, Constant.OPER_TYPE_EXPORT, Constant.SUPPLIER_EXTRACT_COMMIT);
		return "OK";
	}
	
	/**
	 * 外网导入抽取信息抽取条件
	 */
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
                    	conditionMapper.updateConditionByPrimaryKeySelective(condition);
                    }else{
                    	conditionMapper.insertSelective(condition);
                    }
                    conditionService.saveContype2(condition);
                }
            }
        }
        synchRecordService.synchBidding(new Date(), num+"", Constant.DATE_SYNCH_SUPPLIER_EXTRACT_INFO, Constant.OPER_TYPE_IMPORT, Constant.SUPPLIER_EXTRACT_COMMIT_IMPORT);
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
        synchRecordService.synchBidding(synchDate, sum + "",Constant.DATE_SYNCH_SUPPLIER_EXTRACT_RESULT, Constant.OPER_TYPE_EXPORT,Constant.SUPPLIER_EXTRACT_RESULT_COMMIT);
    }
    
	 /**
	   * 抽取记录导出（项目信息）
	   * <简述> 
	   *
	   * @author Jia Chengxiang
	   * @dateTime 2017-10-20下午6:09:19
	   * @return
	   */
	@Override
	public Map<String, Object> exportExtractProjectInfo(String start, String end, Date synchDate) {
		//查询项目信息
		SupplierExtractProjectInfo projectInfo = new SupplierExtractProjectInfo();
		projectInfo.setExtractTheWay((short)0);
		projectInfo.setStartTime(start);
		projectInfo.setEndTime(end);
		projectInfo.setStatus((short)1);
		List<SupplierExtractProjectInfo> projectInfos  = recordService.selectRecordForExport(projectInfo);
		if(projectInfos.size()>0){
			//生成json 并保存
			FileUtils.writeFile(FileUtils.getExporttFile(FileUtils.SUPPLIER_EXTRACT_PROJECT_PATH_FILENAME, 35),JSON.toJSONString(projectInfos));
		}
		synchRecordService.synchBidding(synchDate, projectInfos.size()+"", Constant.DATE_SYNCH_SUPPLIER_EXTRACT_INFO, Constant.OPER_TYPE_EXPORT, Constant.SUPPLIER_EXTRACT_COMMIT);
		return null;
	}
	
}
