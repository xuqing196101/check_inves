package extract.service.expert.impl;

import java.net.URLEncoder;
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
import org.springframework.stereotype.Service;

import ses.dao.bms.AreaMapper;
import ses.dao.bms.CategoryMapper;
import ses.dao.bms.DictionaryDataMapper;
import ses.dao.bms.EngCategoryMapper;
import ses.dao.oms.OrgnizationMapper;
import ses.model.bms.Area;
import ses.model.bms.Category;
import ses.model.bms.DictionaryData;
import ses.model.bms.User;
import ses.model.oms.Orgnization;
import ses.service.ems.ExpertService;
import ses.util.DictionaryDataUtil;
import ses.util.PropertiesUtil;
import ses.util.WordUtil;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;

import extract.dao.common.ExtractUserMapper;
import extract.dao.common.SuperviseMapper;
import extract.dao.expert.ExpertExtractConditionMapper;
import extract.dao.expert.ExpertExtractProjectMapper;
import extract.dao.expert.ExpertExtractResultMapper;
import extract.dao.expert.ExpertExtractTypeInfoMapper;
import extract.dao.expert.ExtractCategoryMapper;
import extract.model.expert.ExpertExtractCondition;
import extract.model.expert.ExpertExtractProject;
import extract.model.expert.ExpertExtractResult;
import extract.model.expert.ExpertExtractTypeInfo;
import extract.service.expert.ExpertExtractConditionService;
import extract.service.expert.ExpertExtractProjectService;

/**
 * 
 * Description: 专家抽取项目信息管理
 * 
 * @author zhang shubin
 * @version 2016-9-7
 * @since JDK1.7
 */
@Service("expertExtractProjectService")
public class ExpertExtractProjectServiceImpl implements ExpertExtractProjectService {

    //专家抽取项目信息
    @Autowired
    private ExpertExtractProjectMapper expertExtractProjectMapper;

    //数据字典
    @Autowired
    private DictionaryDataMapper dictionaryDataMapper;
    
    //地区
    @Autowired
    private AreaMapper areaMapper;
    
    //条件
    @Autowired
    private ExpertExtractConditionService expertExtractConditionService;

    //机构
    @Autowired
    private OrgnizationMapper orgnizationMapper;
    
    @Autowired
    private ExpertService service;
    
    @Autowired
    private CategoryMapper categoryMapper;
    
    @Autowired
    private EngCategoryMapper engCategoryMapper;
    /**
     * 人员信息
     */
    @Autowired
    private ExtractUserMapper userMapper;
    @Autowired
    private SuperviseMapper superviseMapper;
    
    //抽取人员
    @Autowired
    private ExtractUserMapper extractUserMapper;
    
    //抽取条件
    @Autowired
    private ExpertExtractConditionMapper expertExtractConditionMapper;
    
    
    @Autowired
    private ExpertExtractTypeInfoMapper typeInfoMapper;
    
    
    @Autowired
    private ExpertExtractResultMapper resultMapper;
    
    @Autowired
    private ExtractCategoryMapper extractCategoryMapper;
    
    /**
     * 保存信息
     */
    @Override
    public int save(ExpertExtractProject expertExtractProject,User user) {
        expertExtractProject.setCreatedAt(new Date());
        expertExtractProject.setUpdatedAt(new Date());
        expertExtractProject.setIsDeleted((short) 0);
        expertExtractProject.setStatus("1");
        if(user != null){
            expertExtractProject.setProcurementDepId(user.getOrg().getId());
        }
        return expertExtractProjectMapper.insertSelective(expertExtractProject);
    }


    /**
     * 根据项目类型加载专家类别
     */
    @Override
    public List<DictionaryData> loadExpertKind(String id) {
        DictionaryData dictionaryData = DictionaryDataUtil.findById(id);
        String code = "";
        if(dictionaryData != null){
            code = dictionaryData.getCode();
        }
        List<DictionaryData> expertKindList = new ArrayList<>();
        if(code.endsWith("GOODS")){
            //物资    物资服务经济
            DictionaryData project = DictionaryDataUtil.get("GOODS");
            project.setName(project.getName() + "技术");
            expertKindList.add(project);
            expertKindList.add(DictionaryDataUtil.get("GOODS_SERVER"));
        }else if(code.endsWith("PROJECT")){
            //工程技术    工程经济
            DictionaryData project = DictionaryDataUtil.get("PROJECT");
            project.setName(project.getName() + "技术");
            expertKindList.add(project);
            expertKindList.add(DictionaryDataUtil.get("GOODS_PROJECT"));
        }else if(code.endsWith("SERVICE")){
            //服务
            DictionaryData project = DictionaryDataUtil.get("SERVICE");
            project.setName(project.getName() + "技术");
            expertKindList.add(project);
        }
        return expertKindList;
    }


    /**
     * 条件查询所有
     */
    @Override
    public List<ExpertExtractProject> findAll(Map<String, Object> map ,ExpertExtractProject extractProject) {
        PropertiesUtil config = new PropertiesUtil("config.properties");
        PageHelper.startPage((int)map.get("page"),Integer.parseInt(config.getString("pageSize")));
        if(extractProject != null){
            map.put("projectName", extractProject.getProjectName());
            map.put("code", extractProject.getCode());
            map.put("purchaseWay", extractProject.getPurchaseWay());
        }
        List<ExpertExtractProject> list = expertExtractProjectMapper.findAll(map);
        if(list != null && list.size() > 0){
            for (ExpertExtractProject expertExtractProject : list) {
                //项目类型
                String projectType = expertExtractProject.getProjectType();
                if(projectType != null){
                    expertExtractProject.setProjectType(DictionaryDataUtil.findById(projectType) == null ? "" : DictionaryDataUtil.findById(projectType).getName());
                }
                //采购方式
                String purchaseWay = expertExtractProject.getPurchaseWay();
                if(purchaseWay != null){
                    expertExtractProject.setPurchaseWay(DictionaryDataUtil.findById(purchaseWay) == null ? "" : DictionaryDataUtil.findById(purchaseWay).getName());
                    if(expertExtractProject.getPurchaseWay().equals("询价采购")){
                        expertExtractProject.setPurchaseWay("询价");
                    }
                }
                //评审地点
                String reviewProvince = expertExtractProject.getReviewProvince();
                String reviewAddress = expertExtractProject.getReviewAddress();
                if(reviewProvince != null && reviewAddress != null){
                    Area area1 = areaMapper.selectById(reviewProvince);
                    Area area2 = areaMapper.selectById(reviewAddress);
                    if(area1 != null && area2 != null){
                        expertExtractProject.setReviewAddress(area1.getName() + "/" + area2.getName());
                    }
                }
                //抽取人员
                List<String> userList = extractUserMapper.getUnameByprojectId(expertExtractProject.getId());
                StringBuffer sb = new StringBuffer();
                if(userList != null && userList.size() > 0){
                    for (int i = 0; i < userList.size(); i++) {
                        if(i == 0){
                            sb.append(userList.get(i));
                        }else{
                            sb.append(",");
                            sb.append(userList.get(i));
                        }
                    }
                }
                expertExtractProject.setExtractPerson(sb.toString());
            }
        }
        return list;
    }


    @Override
    public ResponseEntity<byte[]> printRecord(String id,HttpServletRequest request, HttpServletResponse response) throws Exception {
		// 根据记录id 查询项目信息不同供应商类别打印两个记录表
		Map<String, Object> info = selectExtractInfo(id);
		// 文件存储地址
		String filePath = request.getSession().getServletContext().getRealPath("/WEB-INF/upload_file/");
		// 文件名称
		String name;
		if ("PROJECT".equals(info.get("projectTypeCode"))) {
			name = new String(("军队供应商抽取记录表(工程类).doc").getBytes("UTF-8"),"UTF-8");
		} else {
			name = new String(("军队供应商抽取记录表(物资服务类).doc").getBytes("UTF-8"),"UTF-8");
		}
		// Supplier supplier = JSON.parseObject(supplierJson, Supplier.class);
		/** 创建word文件 **/
		String fileName;
		if ("PROJECT".equals(info.get("projectTypeCode"))) {
			fileName = WordUtil.createWord(info, "extractExpertEng.ftl", name,request);
		} else {
			fileName = WordUtil.createWord(info, "extractExpert.ftl", name,request);
		}

		// String fileName = WordUtil.createWord(supplier, "test2.ftl", name, request);
		// 下载后的文件名
		String downFileName;
		if ("PROJECT".equals(info.get("projectTypeCode"))) {
			downFileName = "军队采购评审专家抽取记录表(工程类).doc";
		} else {
			downFileName = "军队供采购评审专家取记录表(物资服务类).doc";
		}

		if (request.getHeader("User-Agent").toUpperCase().indexOf("MSIE") > 0) {
			// 解决IE下文件名乱码
			downFileName = URLEncoder.encode(downFileName, "UTF-8");
		} else {
			// 解决非IE下文件名乱码
			downFileName = new String(downFileName.getBytes("UTF-8"),"ISO8859-1");
		}
		return service.downloadFile(fileName, filePath, downFileName);
    }


    private Map<String, Object> selectExtractInfo(String id) {
        ExpertExtractProject projectInfo = expertExtractProjectMapper.selectByPrimaryKey(id);
        //日期格式化
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日");
        String temp = "";
        //条件
        HashMap<Object, Object> cmap = new HashMap<>();
        cmap.put("recordId", id);
        ExpertExtractCondition condition = expertExtractConditionService.getByMap(cmap);
        
        Map<String, Object> map = new HashMap<>();
        
        //采购机构
        if(StringUtils.isNotBlank(projectInfo.getProcurementDepId())){
            Orgnization findOrgByPrimaryKey = orgnizationMapper.findOrgByPrimaryKey(projectInfo.getProcurementDepId());
            if(null!=findOrgByPrimaryKey);
            map.put("ProcurementDep",findOrgByPrimaryKey.getName());
        }
        
        //抽取地点
        map.put("construction", projectInfo.getExtractAddress());
        
        //抽取时间
        map.put("extractTime", simpleDateFormat.format(projectInfo.getCreatedAt()));
        
        //项目编号
        map.put("projectCode", projectInfo.getCode());
        
        //抽取方式
        map.put("extractTheWay", projectInfo.getIsAuto()==0?"人工抽取":"自动抽取");
        
        //项目名称
        map.put("projectName", projectInfo.getProjectName());
        
        //供应商地域
        if(condition.getAreaName().equals("0")){
            map.put("areaName", "全国");
        }else{
            String[] split = condition.getAreaName().split(",");
            temp = "";
            for (String str : split) {
                temp += areaMapper.selectById(str).getName()+",";
            }
            map.put("areaName", temp.substring(0,temp.lastIndexOf(",")));
        }
        
        //map.put("areaName", condition.getAreaName()==0?"全国":"");
        
        //人员信息
        map.put("extractUsers",  userMapper.getlistByRid(projectInfo.getId()));
        map.put("supervises",  superviseMapper.getlistByRid(projectInfo.getId()));

        DictionaryData typeData = DictionaryDataUtil.findById(projectInfo.getProjectType());
        String typeCode = typeData==null?"":typeData.getCode();
        map.put("projectTypeCode", typeCode);

        //抽取条件
        //总人数
        map.put("anum", condition.getExtractNum());

        List<ExpertExtractTypeInfo> extractTypeInfo = typeInfoMapper.selectByTypeInfo(new ExpertExtractTypeInfo(condition.getId(),null));
        if(null !=extractTypeInfo){
            for (ExpertExtractTypeInfo e : extractTypeInfo) {
                if(StringUtils.isNotBlank(e.getExpertTypeCode())){
                    String expertTypeCode = e.getExpertTypeCode();
                    if(expertTypeCode.indexOf("_") != -1){
                        //经济专家人数
                        map.put("gnum", "经济专家："+e.getCountPerson()+"人");
                        
                        //职称
                        map.put("tzc",StringUtils.isBlank(e.getTechnicalTitle())?"经济专家：不限":"经济专家："+e.getTechnicalTitle());
                        //产品类别
                        List<String> clist = extractCategoryMapper.selByConditionId(e.getConditionId(),DictionaryDataUtil.getId(expertTypeCode));
                        temp = "";
                        if(clist != null && clist.size() > 0){
                            for (String cid : clist) {
                                Category cate = categoryMapper.findById(cid);
                                if(null!=cate){
                                    temp += cate.getName()+",";
                                }
                            }
                            temp = temp.substring(0,temp.lastIndexOf(","));
                        }else{
                            temp = "不限类别";
                        }
                        map.put("jjcategory","经济："+temp );
                        //执业资格
                        String professional = e.getEngQualification() == null ? "不限" : e.getEngQualification();
                        map.put("jjprofessional","经济专家："+professional );
                    }else{
                        //技术专家
                        map.put("tnum", "技术专家："+e.getCountPerson()+"人、");
                        
                        //职称
                        map.put("gzc",StringUtils.isBlank(e.getTechnicalTitle())?"技术专家：不限、":"技术专家："+e.getTechnicalTitle()+"、");
                        
                        //产品类别
                        List<String> clist = extractCategoryMapper.selByConditionId(e.getConditionId(),DictionaryDataUtil.getId(expertTypeCode));
                        temp = "";
                        if(clist != null && clist.size() > 0){
                            for (String cid : clist) {
                                Category cate = categoryMapper.findById(cid);
                                if(null!=cate){
                                    temp += cate.getName()+",";
                                }
                            }
                            temp = temp.substring(0,temp.lastIndexOf(","));
                        }else{
                            temp = "不限类别";
                        }
                        map.put("jscategory","技术："+temp+"、" );
                        //执业资格
                        String professional = e.getEngQualification() == null ? "不限" : e.getEngQualification();
                        map.put("jsprofessional","技术专家："+professional+"、" );
                    }
                }
            }
            
        }

        //结果
        List<ExpertExtractResult> resultList = resultMapper.getResultListByrecordId(id);
        for (ExpertExtractResult expertExtractResult : resultList) {
        	if(expertExtractResult.getExpertCode() != null){
        		String typeName = DictionaryDataUtil.get(expertExtractResult.getExpertCode()) == null ? "" : DictionaryDataUtil.get(expertExtractResult.getExpertCode()).getName();
        		expertExtractResult.setExpertCode(typeName);
        	}
		}
        List<ExpertExtractResult> backList = resultMapper.getBackExpertListByrecordId(id);
        for (ExpertExtractResult expertExtractResult : backList) {
        	if(expertExtractResult.getExpertCode() != null){
        		String typeName = DictionaryDataUtil.get(expertExtractResult.getExpertCode()) == null ? "" : DictionaryDataUtil.get(expertExtractResult.getExpertCode()).getName();
        		expertExtractResult.setExpertCode(typeName);
        	}
		}
        map.put("result", resultList);
        map.put("back", backList);
        return map;
    }


    @Override
    public String vaProjectCode(String code) {
        Map<String, Object> map = new HashMap<String, Object>();
        int v = expertExtractProjectMapper.vaProjectCode(code);
        if(v > 0){
            //已经存在
            map.put("status", "no");
        }else{
            map.put("status", "yes");
        }
        return JSON.toJSONString(map);
    }
    
    /**
     * 修改项目抽取状态
     */
    @Override
    public int updataStatus(String projectId) {
        Map<String, Object> map = new HashMap<>();
        map.put("projectId", projectId);
        map.put("status", "2");
        int v = expertExtractProjectMapper.updataStatus(map);
        return v;
    }

    /**
     * 根据主键查询
     */
    @Override
    public ExpertExtractProject selectByPrimaryKey(String id) {
        return expertExtractProjectMapper.selectByPrimaryKey(id);
    }
}
