/**
 * 
 */
package ses.controller.sys.sms;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.github.pagehelper.PageHelper;

import ses.dao.sms.SupplierExtRelateMapper;
import ses.dao.sms.SupplierExtUserMapper;
import ses.dao.sms.SupplierExtractsMapper;
import ses.model.bms.Area;
import ses.model.bms.User;
import ses.model.ems.ExtConTypeArray;
import ses.model.sms.SupplierConType;
import ses.model.sms.SupplierCondition;
import ses.model.sms.SupplierExtUser;
import ses.model.sms.SupplierExtracts;
import ses.service.bms.AreaServiceI;
import ses.service.sms.SupplierConTypeService;
import ses.service.sms.SupplierConditionService;

/**
 * @Description:查询条件控制
 *	 
 * @author Wang Wenshuai
 * @version 2016年9月28日上午10:58:03
 * @since  JDK 1.7
 */
@Controller
@Scope("prototype")
@RequestMapping("/SupplierCondition")
public class SupplierConditionController {
	@Autowired
	private SupplierConditionService conditionService;
	@Autowired 
	private SupplierConTypeService conTypeService;
	@Autowired
	private AreaServiceI areaService;
	@Autowired
	private SupplierExtRelateMapper extRelateMapper; //关联表
	@Autowired
	private SupplierExtractsMapper supplierExtractsMapper;//记录
	@Autowired
	private SupplierExtUserMapper userServicl;
	/**
	 * @Description:保存查询条件
	 *
	 * @author Wang Wenshuai
	 * @version 2016年9月28日 上午10:56:45  
	 * @param @return      
	 * @return String 
	 */
	@RequestMapping("/saveSupplierCondition")
	public String saveSupplierCondition(SupplierCondition condition,ExtConTypeArray extConTypeArray,String[] sids,HttpServletRequest sq){
		if(condition.getId()!=null&&!"".equals(condition.getId())){
			conditionService.update(condition);	
			//删除关联数据重新添加
			conTypeService.delete(condition.getId());
		}else{
			//插入信息
			conditionService.insert(condition);
			//给专家记录表set信息并且插入到记录表
			SupplierExtracts record=new SupplierExtracts();
			record.setProjectId(condition.getProjectId());
			PageHelper.startPage(1, 1);
			List<SupplierExtracts> list = supplierExtractsMapper.list(record);
			if(list==null||list.size()==0){
				SupplierExtracts expExtractRecord=new SupplierExtracts();
				expExtractRecord.setProjectId(condition.getProjectId());
				User user=(User) sq.getSession().getAttribute("loginUser");
				expExtractRecord.setExtractsPeople(user.getId());
				expExtractRecord.setExtractTheWay((short)1);
				expExtractRecord.setExtractionSites(condition.getAddress());
				supplierExtractsMapper.insertSelective(expExtractRecord);
			}
		}
		SupplierConType conType=null;
		if(extConTypeArray!=null&&extConTypeArray.getExtCategoryId()!=null){
			for (int i = 0; i < extConTypeArray.getExtCategoryId().length; i++) {
				conType=new SupplierConType();
				conType.setCategoryId(extConTypeArray.getExtCategoryId()[i]);
				conType.setSupplieCount(Integer.parseInt(extConTypeArray.getExtCount()[i]));
				conType.setSupplieQualification(extConTypeArray.getExtQualifications()[i]);
				conType.setSupplieTypeId(new Short(extConTypeArray.getExpertsTypeId()[i]));
				conType.setCategoryName(extConTypeArray.getExtCategoryName()[i]);
				conType.setConditionId(condition.getId());
				conType.setIsMulticondition(new Short(extConTypeArray.getIsSatisfy()[i]));
				//如果有id就修改没有就新增
				conTypeService.insert(conType);	
			}
		}
		
		//监督人员
		if(sids!=null&&sids.length!=0){
			SupplierExtUser record=null;
			userServicl.deleteProjectId(condition.getProjectId());
			for (String id : sids) {
				if(!"".equals(id)){
					record=new SupplierExtUser();
					record.setProjectId(condition.getProjectId());
					record.setUserId(id);
					userServicl.insertSelective(record);
				}
			}
		}
		return "redirect:/SupplierExtracts/Extraction.do?id="+condition.getProjectId();
	}

	/**
	 * @Description:查询单个
	 *
	 * @author Wang Wenshuai
	 * @version 2016年9月30日 下午1:59:22  
	 * @param @return      
	 * @return String
	 */
	@RequestMapping("/showSupplierCondition")
	public String showSupplierCondition(SupplierCondition condition,Model model,String cId){
		List<Area> listArea = areaService.findTreeByPid("1",null);
		model.addAttribute("listArea", listArea);

		List<SupplierCondition> list = conditionService.list(condition);
		if(list!=null&&list.size()!=0){
			model.addAttribute("ExpExtCondition", list.get(0));
			model.addAttribute("projectId", list.get(0).getProjectId());
		}
		return "ses/sms/supplier_extracts/add_condition";
	}

	/**
	 * @Description:修改
	 *
	 * @author Wang Wenshuai
	 * @version 2016年9月30日 下午1:47:48  
	 * @param @return      
	 * @return String
	 */
	@RequestMapping("/updateCondition")
	public String updateCondition(){

		return null;
	}
	/**
	 * @Description:删除
	 *
	 * @author Wang Wenshuai
	 * @version 2016年9月30日 下午3:09:44  
	 * @param @param delids
	 * @param @return      
	 * @return Object
	 */

	@RequestMapping("/dels")	
	public String dels(@RequestParam(value="delids",required=false)String delids){
		String[] id=delids.split(",");
		for (String str : id) {
			conTypeService.delete(str);
		}
		return "sccuess";
	}
}
