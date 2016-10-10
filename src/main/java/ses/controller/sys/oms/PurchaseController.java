package ses.controller.sys.oms;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.aspectj.weaver.NewMemberClassTypeMunger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import ses.model.bms.User;
import ses.model.ems.ExpertBlackList;
import ses.model.oms.Orgnization;
import ses.model.oms.PurchaseDep;
import ses.model.oms.PurchaseInfo;
import ses.model.oms.PurchaseOrg;
import ses.model.oms.util.AjaxJsonData;
import ses.model.oms.util.CommUtils;
import ses.model.oms.util.CommonConstant;
import ses.service.bms.UserServiceI;
import ses.service.oms.PurchaseServiceI;
import ses.util.PropUtil;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sun.org.apache.bcel.internal.generic.NEW;

/**
 * 
 * @Title: PurchaseController
 * @Description: 
 * @author: Tian Kunfeng
 * @date: 2016-9-20上午11:46:15
 */
@Controller
@Scope("prototype")
@RequestMapping("/purchase")
public class PurchaseController {
	@Autowired
	private PurchaseServiceI purchaseServiceI;
	@Autowired
	private UserServiceI userServiceI;
	private AjaxJsonData jsonData = new AjaxJsonData();
	@RequestMapping("list")
	public String list(Model model,@ModelAttribute PurchaseInfo purchaseInfo,Integer page){
		//每页显示十条
		PageHelper.startPage(page == null ? 1 : page,CommonConstant.PAGE_SIZE);
		HashMap<String, Object> map = new HashMap<String, Object>();
		if(purchaseInfo.getRelName()!=null&&!purchaseInfo.getRelName().equals("")){
			map.put("relName", purchaseInfo.getRelName());
		}
		if(purchaseInfo.getPurchaseDepName()!=null&&!purchaseInfo.getPurchaseDepName().equals("")){
			map.put("purchaseDepName", purchaseInfo.getPurchaseDepName());
		}
		List<PurchaseInfo> purchaseList = purchaseServiceI.findPurchaseList(map);
		model.addAttribute("purchaseList",purchaseList);

		//分页标签
		model.addAttribute("list",new PageInfo<PurchaseInfo>(purchaseList));
		/*String pagesales = CommUtils.getTranslation(page,"purchase/list.do");
		model.addAttribute("pagesql", pagesales);*/
		model.addAttribute("purchaseInfo", purchaseInfo);
		return "ses/oms/purchase/list";
	}
	@RequestMapping("add")
	public String add() {
		return "ses/oms/purchase/add";
	}
	
	@RequestMapping(value="create",method= RequestMethod.POST)
	public String create(@ModelAttribute PurchaseInfo purchaseInfo,HttpServletRequest request){
		User currUser=(User) request.getSession().getAttribute("loginUser");
		purchaseServiceI.savePurchase(purchaseInfo);
		User user = new User();
		user.setTypeId(purchaseInfo.getId());
		user.setRelName(purchaseInfo.getRelName());
		user.setGender(purchaseInfo.getGender());
		user.setMobile(purchaseInfo.getMobile());
		user.setAddress(purchaseInfo.getAddress());
		user.setTelephone(purchaseInfo.getTelephone());
		Orgnization org = new Orgnization();
		if(purchaseInfo.getPurchaseDepId()!=null && !purchaseInfo.getPurchaseDepId().equals("")){
			org.setId(purchaseInfo.getPurchaseDepId());
			user.setOrg(org);
		}
		userServiceI.save(user,currUser);
		
		
		return "redirect:list.do";
	}
	@RequestMapping(value="createAjax",method= RequestMethod.POST)
	@ResponseBody
	public AjaxJsonData createAjax(@ModelAttribute PurchaseInfo purchaseInfo,HttpServletRequest request){
		User currUser=(User) request.getSession().getAttribute("loginUser");
		purchaseServiceI.savePurchase(purchaseInfo);
		User user = new User();
		user.setTypeId(purchaseInfo.getId());
		user.setRelName(purchaseInfo.getRelName());
		user.setGender(purchaseInfo.getGender());
		user.setMobile(purchaseInfo.getMobile());
		user.setAddress(purchaseInfo.getAddress());
		user.setTelephone(purchaseInfo.getTelephone());
		Orgnization org = new Orgnization();
		if(purchaseInfo.getPurchaseDepId()!=null && !purchaseInfo.getPurchaseDepId().equals("")){
			org.setId(purchaseInfo.getPurchaseDepId());
			user.setOrg(org);
		}
		userServiceI.save(user,currUser);
		jsonData.setMessage("保存成功!");
		return jsonData ;
	}
	@RequestMapping("edit")
	public String edit(@ModelAttribute PurchaseInfo purchaseInfo,Model model) {
		HashMap<String,Object> map = new HashMap<String,Object>();
		map.put("id", purchaseInfo.getId());
		List<PurchaseInfo> oList = purchaseServiceI.findPurchaseList(map);
		if(oList!=null && oList.size()>0){
			model.addAttribute("purchaseInfo", oList.get(0));
		}
		return "ses/oms/purchase/edit";
	}
	@RequestMapping(value="update",method= RequestMethod.POST)
	public String update(@ModelAttribute PurchaseInfo purchaseInfo,HttpServletRequest request) throws IOException{
		//User currUser=(User) request.getSession().getAttribute("loginUser");
		User currUser=(User) request.getSession().getAttribute("loginUser");
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("id", purchaseInfo.getId());
		PurchaseInfo p = purchaseServiceI.findPurchaseList(map).get(0);
		this.setUploadFile(request, purchaseInfo);
		purchaseServiceI.updatePurchase(purchaseInfo);
		User user = new User();
		if(p!=null){
			//user.setTypeId(purchaseInfo.getId());
			Orgnization org = new Orgnization();
			if(p.getPurchaseDepId()!=null && !p.getPurchaseDepId().equals("")){
				org.setId(p.getPurchaseDepId());
				user.setOrg(org);
			}
			user.setId(p.getUserId());
			user.setRelName(purchaseInfo.getRelName());
			user.setGender(purchaseInfo.getGender());
			user.setMobile(purchaseInfo.getMobile());
			user.setAddress(purchaseInfo.getAddress());
			user.setTelephone(purchaseInfo.getTelephone());
			user.setDuites(purchaseInfo.getDuites());
			user.setUpdatedAt(new Date());
			userServiceI.update(user);
		}
		return "redirect:list.do";
	}
	@RequestMapping(value="updateAjax",method= RequestMethod.POST)
	@ResponseBody
	public AjaxJsonData updateAjax(@ModelAttribute PurchaseInfo purchaseInfo,HttpServletRequest request) throws IOException{
		User currUser=(User) request.getSession().getAttribute("loginUser");
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("id", purchaseInfo.getId());
		PurchaseInfo p = purchaseServiceI.findPurchaseList(map).get(0);
		this.setUploadFile(request, purchaseInfo);
		purchaseServiceI.updatePurchase(purchaseInfo);
		User user = new User();
		if(p!=null){
			//user.setTypeId(purchaseInfo.getId());
			Orgnization org = new Orgnization();
			if(p.getPurchaseDepId()!=null && !p.getPurchaseDepId().equals("")){
				org.setId(p.getPurchaseDepId());
				user.setOrg(org);
			}
			user.setId(p.getUserId());
			user.setRelName(purchaseInfo.getRelName());
			user.setGender(purchaseInfo.getGender());
			user.setMobile(purchaseInfo.getMobile());
			user.setAddress(purchaseInfo.getAddress());
			user.setTelephone(purchaseInfo.getTelephone());
			user.setDuites(purchaseInfo.getDuites());
			user.setUpdatedAt(new Date());
			userServiceI.update(user);
		}
		jsonData.setMessage("更新成功!");
		return jsonData ;
	}
	@RequestMapping(value = "delajax")
	@ResponseBody    
	public AjaxJsonData delajax(Model model,HttpServletRequest request,@ModelAttribute PurchaseInfo purchaseInfo,HttpSession session,HttpServletResponse response) {
		//UserEntity user = (UserEntity) session.getAttribute(SessionStringPool.LOGIN_USER);
		String ids = request.getParameter("ids");
		ArrayList<String> list = new ArrayList<String>();
		String[] idStrings = null;
		if(ids!=null && !ids.equals("")){
			idStrings = ids.split(",");
		}
		
		for(int i=0;i<idStrings.length;i++){
			list.add(idStrings[i]);
		}
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("list", list);
		//purchaseServiceI.delPurchaseByMap(map);//物理删除
		delUserByPurchaseIds(ids);
		AjaxJsonData json = new AjaxJsonData();
		json.setSuccess(true);
		json.setMessage("删除成功");
		/*if(orgnization.getIsDeleted()!=null && orgnization.getIsDeleted().equals(1)){
			json.setMessage("更新成功");
		}*/
		return json;
	}
	public AjaxJsonData getJsonData() {
		return jsonData;
	}
	public void setJsonData(AjaxJsonData jsonData) {
		this.jsonData = jsonData;
	}
	public void delUserByPurchaseIds(String ids){
		if(ids!=null && !ids.equals("")){
			HashMap<String, Object> map = new HashMap<String, Object>();
			User u = new User();
			PurchaseInfo p =new PurchaseInfo();
			String[] idStrings = ids.split(",");
			for(int i=0;i<idStrings.length;i++){
				map.put("id", idStrings[i]);
				PurchaseInfo purchaseInfo = purchaseServiceI.findPurchaseList(map).get(0);
				u.setId(purchaseInfo.getUserId());
				u.setIsDeleted(1);
				userServiceI.update(u);
				
				
				p.setId(idStrings[i]);
				p.setIsDeleted(1);
				purchaseServiceI.updatePurchase(p);
				
			}
			
		}
	}
	public void setUploadFile(HttpServletRequest request, PurchaseInfo purchaseInfo) throws IOException {
		CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());
		// 检查form中是否有enctype="multipart/form-data"
		if (multipartResolver.isMultipart(request)) {
			// 将request变成多部分request
			MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
			// 获取multiRequest 中所有的文件名
			Iterator<String> its = multiRequest.getFileNames();
			String getRootPath= request.getSession().getServletContext().getRealPath("/").split("\\\\")[0] + "/" + PropUtil.getProperty("file.upload.path.purchase");
			while (its.hasNext()) {
				String str = its.next();
				MultipartFile file = multiRequest.getFile(str);
				if (file != null && file.getSize() > 0) {
					String path = getRootPath + file.getOriginalFilename();
					file.transferTo(new File(path));
					if (str.equals("quaCertFile")) {
						purchaseInfo.setQuaCert(path);
					} 
				}
			}
		}
	}
}
