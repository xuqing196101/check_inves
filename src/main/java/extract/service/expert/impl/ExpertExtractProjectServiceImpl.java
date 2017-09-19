package extract.service.expert.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import ses.dao.bms.AreaMapper;
import ses.dao.bms.DictionaryDataMapper;
import ses.dao.oms.OrgnizationMapper;
import ses.model.bms.Area;
import ses.model.bms.DictionaryData;
import ses.util.DictionaryDataUtil;
import ses.util.PropertiesUtil;

import com.github.pagehelper.PageHelper;

import extract.dao.common.ExtractUserMapper;
import extract.dao.common.SuperviseMapper;
import extract.dao.expert.ExpertExtractProjectMapper;
import extract.model.expert.ExpertExtractProject;
import extract.model.supplier.SupplierExtractCondition;
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
    private ExtractUserMapper userMapper;
    @Autowired
    private SuperviseMapper superviseMapper;
	
    /**
     * 保存信息
     */
    @Override
    public int save(ExpertExtractProject expertExtractProject) {
        expertExtractProject.setCreatedAt(new Date());
        expertExtractProject.setUpdatedAt(new Date());
        expertExtractProject.setIsDeleted((short) 0);
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
			}
		}
		return list;
	}


	@Override
	public ResponseEntity<byte[]> printRecord(String id,
			HttpServletRequest request, HttpServletResponse response) {
		//根据记录id 查询项目信息不同供应商类别打印两个记录表
		Map<String, Object> info = selectExtractInfo(id);
		
		//expertExtractConditionService.selectByProjectId(id);
		
		
		return null;
	}


	private Map<String, Object> selectExtractInfo(String id) {
		ExpertExtractProject projectInfo = expertExtractProjectMapper.selectByPrimaryKey(id);
		//日期格式化
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日");
		
		String projectCode = projectInfo.getProjectType();
		Map<String, Object> map = new HashMap<>();
		
		//采购机构
		map.put("ProcurementDep",orgnizationMapper.findOrgByPrimaryKey(projectInfo.getProcurementDepId()).getName());
		
		//抽取地点
		map.put("construction", projectInfo.getExtractAddress());
		
		//抽取时间
		map.put("extractTime", simpleDateFormat.format(projectInfo.getCreatedAt()));
		
		//项目编号
		map.put("projectCode", projectInfo.getCode());
		
		//抽取方式
		map.put("extractTheWay", projectInfo.getIsAuto()==0?"自动抽取":"人工抽取");
		
		//项目名称
		map.put("projectName", projectInfo.getProjectName());
		
		//供应商地域
		//map.put("areaName", condition.getAreaName()==0?"全国":"");
		
		
		//人员信息
		map.put("extractUsers",  userMapper.getlistByRid(projectInfo.getId()));
		map.put("supervises",  superviseMapper.getlistByRid(projectInfo.getId()));
				
		
		return null;
	}
	
}
