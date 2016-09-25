/**
 * 
 */
package ses.controller.sys.sms;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageInfo;

import ses.model.bms.Area;
import ses.model.bms.User;
import ses.model.sms.Supplier;
import ses.model.sms.SupplierCondition;
import ses.model.sms.SupplierExtRelate;
import ses.model.sms.SupplierExtracts;
import ses.model.sms.SupplierType;
import ses.service.bms.AreaServiceI;
import ses.service.bms.UserServiceI;
import ses.service.sms.SupplierAuditService;
import ses.service.sms.SupplierExtRelateService;
import ses.service.sms.SupplierExtractsService;
import ses.service.sms.SupplierTypeService;

/**
 * @Description:供应商抽取记录
 *	 
 * @author Wang Wenshuai
 * @date 2016年9月18日下午3:29:12
 * @since  JDK 1.7
 */
@Controller
@Scope("prototype")
@RequestMapping("/SupplierExtracts")
public class SupplierExtractsController {
	@Autowired
	private SupplierAuditService supplierAuditServlice;
	@Autowired
	private SupplierTypeService supplierTypeService;
	@Autowired
	private SupplierExtractsService supplierExtractsService;
	@Autowired
	private SupplierExtRelateService extRelateService;
	@Autowired
	private AreaServiceI areaService;	
	@Autowired
	private UserServiceI userService;
	/**
	 * @Description:分页获取记录集合
	 *
	 * @author Wang Wenshuai
	 * @version 2016年9月21日 下午4:04:35  
	 * @param @param extracts
	 * @param @return      
	 * @return String
	 */
	@RequestMapping("/listSupplierExtracts")
	public String listSupplierExtracts(Model model, SupplierExtracts extracts){

		List<SupplierExtracts> extractslist = supplierExtractsService.listExtracts(new SupplierExtracts());
		model.addAttribute("extractslist",extractslist);

		return "ses/sms/supplier_extracts/recordlist";
	}

	/**
	 * @Description:展示单条记录
	 *
	 * @author Wang Wenshuai
	 * @version 2016年9月21日 下午4:44:49  
	 * @param @return      
	 * @return String
	 */
	@RequestMapping("/showSupplierExtracts")
	public String showSupplierExtracts(Model model,String id){
		List<SupplierExtracts> listExtracts = supplierExtractsService.listExtracts(new SupplierExtracts(id));
		if(listExtracts != null && listExtracts.size() !=0){
			SupplierExtracts extracts = listExtracts.get(0);
			model.addAttribute("extracts",extracts);
			model.addAttribute("extRelate",extracts.getSupplierExtRelate());
			model.addAttribute("Superintendentuser",extracts.getSuperintendentuser());
			model.addAttribute("peopleuser", extracts.getExtractsPeopleUser());
			SupplierCondition condition=JSON.parseObject(extracts.getExtractingConditions(),SupplierCondition.class);
			model.addAttribute("condition",condition);
		}

		return "ses/sms/supplier_extracts/show_info";
	}

	/**
	 * @Description:抽取条件设置
	 *
	 * @author Wang Wenshuai
	 * @date 2016年9月18日 下午3:55:23  
	 * @param @return      
	 * @return String
	 */
	@RequestMapping("/conditions")
	public String conditions(Model model,String area,String id){
		List<Area> listArea = areaService.findTreeByPid(area==null?"1":area);
		List<SupplierType> listType=supplierTypeService.findSupplierType();
		model.addAttribute("listArea", listArea);
		model.addAttribute("listType", listType);
		model.addAttribute("id",id);
		return "ses/sms/supplier_extracts/manual_extraction";
	}
	/**
	 * @Description: 获取市
	 *
	 * @author Wang Wenshuai
	 * @date 2016年9月18日 下午4:16:35  
	 * @param @return      
	 * @return String
	 */
	@ResponseBody
	@RequestMapping("/city")
	public Object city(Model model,String area){

		List<Area> listArea = areaService.findTreeByPid(area==null?"1":area);

		return listArea;
	}

	/**
	 * @Description:返回结果
	 *
	 * @author Wang Wenshuai
	 * @date 2016年9月19日 下午2:31:46  
	 * @param @param model
	 * @param @return      
	 * @return String
	 */
	@ResponseBody
	@RequestMapping("/resultSupplier")
	public Object resultSupplier(Model model,String id){
		//		修改状态
		String ids[]=id.split(",");
		extRelateService.updateOperating(new SupplierExtRelate(ids[0],new Short(ids[2])));
		//查询数据
		List<SupplierExtracts> extractslist = supplierExtractsService.listExtracts(new SupplierExtracts(ids[1]));
		//存放已操作
		List<SupplierExtRelate> extRelateListYes=new ArrayList<SupplierExtRelate>();
		//未操作
		List<SupplierExtRelate> extRelateListNo=new ArrayList<SupplierExtRelate>();
		for (SupplierExtRelate supplierExtRelate :  extractslist.get(0).getSupplierExtRelate()) {
			if(supplierExtRelate.getOperatingType()!=null&&(supplierExtRelate.getOperatingType()==1||supplierExtRelate.getOperatingType()==2||supplierExtRelate.getOperatingType()==3)){
				extRelateListYes.add(supplierExtRelate);
			}else{
				extRelateListNo.add(supplierExtRelate);
			}
		}
		SupplierCondition condition=JSON.parseObject(extractslist.get(0).getExtractingConditions(),SupplierCondition.class);
		if(extRelateListYes.size()>=condition.getCount()){
			return "sccuess";
		}else{
			if(extRelateListNo.size()!=0){
				extRelateListYes.add(extRelateListNo.get(0));
			}
			return extRelateListYes;
		}
	}	

	/**
	 * @Description:跳转到结果界面
	 *
	 * @author Wang Wenshuai
	 * @date 2016年9月20日 上午10:21:51  
	 * @param @param model
	 * @param @param rq
	 * @param @return      
	 * @return String
	 */
	@RequestMapping("/JumpResultSupplier")
	public String JumpResultSupplier(Model model,HttpServletRequest rq,Supplier supplier,SupplierCondition condition,String eid,String sids){
		Object attribute = rq.getSession().getAttribute("loginUser");
		condition.setPeopleId(attribute==null?"2521F623FA2F4399875433678F622F2D":attribute.toString());
		//插入抽取记录表
		String id= supplierExtractsService.insert(supplier,condition,eid,sids);
		//查询数据
		List<SupplierExtracts> sextractslist = supplierExtractsService.listExtracts(new SupplierExtracts(id));
		if(sextractslist!=null&&sextractslist.size()!=0){
			model.addAttribute("sextractslist",sextractslist.get(0));
			model.addAttribute("condition", condition);
			//存放已操作
			List<SupplierExtRelate> extRelateListYes=new ArrayList<SupplierExtRelate>();
			//未操作
			List<SupplierExtRelate> extRelateListNo=new ArrayList<SupplierExtRelate>();
			for (SupplierExtRelate supplierExtRelate :  sextractslist.get(0).getSupplierExtRelate()) {
				if(supplierExtRelate.getOperatingType()!=null&&(supplierExtRelate.getOperatingType()==1||supplierExtRelate.getOperatingType()==2||supplierExtRelate.getOperatingType()==3)){
					extRelateListYes.add(supplierExtRelate);
				}else{
					extRelateListNo.add(supplierExtRelate);
				}
			}
			extRelateListYes.add(extRelateListNo.get(0));
			model.addAttribute("extRelateListYes",extRelateListYes);
			extRelateListNo.remove(0);
			model.addAttribute("extRelateListNo", extRelateListNo);
		}
		return "ses/sms/supplier_extracts/list";
	}

	/**
	 * @Description:展示品目信息
	 *
	 * @author Wang Wenshuai
	 * @version 2016年9月23日 下午2:00:22  
	 * @param @return      
	 * @return String
	 */
	@RequestMapping("/showproduct")
	public String showproduct(){
		return "ses/sms/supplier_extracts/product";
	}
	
	/**
	 * @Description:显示监督人员
	 *
	 * @author Wang Wenshuai
	 * @version 2016年9月25日 09:49:56 
	 * @param @return      
	 * @return String
	 */
	@RequestMapping("/showSupervise")
	public String showSupervise(Model model, Integer page){
			List<User> users = userService
					.selectUser(null, page == null ? 1 : page);
			model.addAttribute("list", new PageInfo<User>(users));
			return "ses/sms/supplier_extracts/supervise_list";
	}
}
