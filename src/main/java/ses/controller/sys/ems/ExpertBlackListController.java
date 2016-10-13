package ses.controller.sys.ems;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import ses.model.ems.Expert;
import ses.model.ems.ExpertBlackList;
import ses.service.ems.ExpertBlackListService;
import ses.util.PropUtil;

import com.github.pagehelper.PageInfo;

/**
 * <p>Title:ExpertBlackListController </p>
 * <p>Description: 专家黑名单控制器</p>
 * @author Xu Qing
 * @date 2016-9-8下午2:51:05
 */
@Controller
@RequestMapping("/expert")
public class ExpertBlackListController {
	@Autowired
	private ExpertBlackListService service;
	/**
	 * @Title: add
	 * @author Xu Qing
	 * @date 2016-9-8 下午5:13:31  
	 * @Description: 添加页面 
	 * @param @param expertBlackList
	 * @param @return      
	 * @return String
	 */
	@RequestMapping("/addBlacklist")
	public String add(ExpertBlackList expertBlackList,Model model){
		//所有专家
		/*List<Expert> expertList = service.findExpertList();
		model.addAttribute("expertList", expertList);*/
		
		//选择的专家
		String relName = expertBlackList.getRelName();
		model.addAttribute("relName", relName);
		return "ses/ems/expertBlackList/add";
	}
	/**
	 * @Title: save
	 * @author Xu Qing
	 * @date 2016-9-8 下午5:13:53  
	 * @Description: 保存信息 
	 * @param @param expertBlackList
	 * @param @return      
	 * @return String
	 * @throws IOException 
	 */
	@RequestMapping("/saveBlacklist")
	public String save(HttpServletRequest request,ExpertBlackList expertBlackList) throws IOException{
		expertBlackList.setCreatedAt(new Date());
		//保存文件
		this.setExpertBlackListUpload(request, expertBlackList);
		service.insert(expertBlackList);
		return "redirect:blacklist.html";
	}
	/**
	 * @Title: fnidList
	 * @author Xu Qing
	 * @date 2016-9-8 下午5:14:12  
	 * @Description: 列表页 ,可条件查询
	 * @param @return      
	 * @return String
	 */
	@RequestMapping("/blacklist")
	public String fnidAll(HttpServletRequest request,Model model,Integer page,ExpertBlackList expert){
		List<ExpertBlackList> expertList = service.findAll(expert,page==null?1:page);
		//所有专家
		List<Expert> expertName = service.findExpertList();
		model.addAttribute("expertName", expertName);
		request.setAttribute("result", new PageInfo<>(expertList));
		model.addAttribute("expertList", expertList);
		return "ses/ems/expertBlackList/list";
	}
	/**
	 * @Title: edit
	 * @author Xu Qing
	 * @date 2016-9-9 下午1:52:05  
	 * @Description: 修改页面 
	 * @param @return      
	 * @return String
	 */
	@RequestMapping("/editBlacklist")
	public String edit(HttpServletRequest request, Model model){
		String id = request.getParameter("id");
		ExpertBlackList expertBlackList = service.findById(id);
		/*//所有专家
		List<Expert> expertList = service.findExpertList();
		model.addAttribute("expertList", expertList);*/
		//选择的专家
		String relName = expertBlackList.getRelName();
		model.addAttribute("relName", relName);
		model.addAttribute("expert", expertBlackList);
		return "ses/ems/expertBlackList/edit";
	}
	/**
	 * @Title: update
	 * @author Xu Qing
	 * @date 2016-9-9 下午3:12:04  
	 * @Description: 更新数据 
	 * @param @param expertBlackList
	 * @param @return      
	 * @return String
	 * @throws IOException 
	 */
	@RequestMapping("/updateBlacklist")
	public String update(HttpServletRequest request,ExpertBlackList expertBlackList) throws IOException{
		expertBlackList.setCreatedAt(new Date());
		//保存文件
		this.setExpertBlackListUpload(request, expertBlackList);
		service.update(expertBlackList);
		return "redirect:blacklist.html";
	}
	/**
	 * @Title: delete
	 * @author Xu Qing
	 * @date 2016-9-9 下午4:54:50  
	 * @Description: 根据id批量删除信息 
	 * @param @param id
	 * @param @return      
	 * @return String
	 */
	@RequestMapping("/deleteBlacklist")
	public String delete(HttpServletRequest request){
		String[] ids = request.getParameter("ids").split(",");
		for(int i = 0;i<ids.length;i++){
			service.delete(ids[i]);
		}
		return "redirect:blacklist.html";
	}
	
	/**
	 * @Title: setExpertBlackListUpload
	 * @author Xu Qing
	 * @date 2016-9-29 下午2:03:31  
	 * @Description: 文件上传
	 * @param @param request
	 * @param @param expertBlackList
	 * @param @throws IOException      
	 * @return void
	 */
	public void setExpertBlackListUpload(HttpServletRequest request, ExpertBlackList expertBlackList) throws IOException {
		CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());
		// 检查form中是否有enctype="multipart/form-data"
		if (multipartResolver.isMultipart(request)) {
			// 将request变成多部分request
			MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
			// 获取multiRequest 中所有的文件名
			Iterator<String> its = multiRequest.getFileNames();
			String getRootPath= request.getSession().getServletContext().getRealPath("/").split("\\\\")[0] + "/" + PropUtil.getProperty("file.upload.path.expertBlackList");
			while (its.hasNext()) {
				String str = its.next();
				MultipartFile file = multiRequest.getFile(str);
				if (file != null && file.getSize() > 0) {
					String path = getRootPath + file.getOriginalFilename();
					file.transferTo(new File(path));
					if (str.equals("attachmentCertFile")) {
						expertBlackList.setAttachmentCert(path);
					} 
				}
			}
		}
	}
	
	/**
	 * @Title: findExpertAll
	 * @author Xu Qing
	 * @date 2016-10-12 下午7:51:17  
	 * @Description: 查询专家 ,可条件查询
	 * @param @param expert
	 * @param @param page
	 * @param @param model
	 * @param @return      
	 * @return String
	 */
	@RequestMapping(value = "expert_list")
	public String findExpertAll(HttpServletRequest request,Expert expert,Integer page,Model model) {
		List<Expert> expertList = service.findExpertAll(expert,page==null?1:page);
		request.setAttribute("result", new PageInfo<>(expertList));
		model.addAttribute("expertAll", expertList);
		
		//回显
		String relName = expert.getRelName();
		model.addAttribute("relName", relName);
		return "ses/ems/expertBlackList/dialog_expert";
	}
	
	
	@InitBinder
	public void initBinder(ServletRequestDataBinder binder) {
		binder.registerCustomEditor(Date.class, new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd"), true));
	}

}
