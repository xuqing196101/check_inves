package ses.controller.sys.sms;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import ses.model.bms.User;
import ses.model.sms.ImportRecommend;
import ses.service.bms.UserServiceI;
import ses.service.sms.ImportRecommendService;
import ses.util.PropUtil;

import com.github.pagehelper.PageInfo;

/**
 * @Title: ImportRecommendController
 * @Description: 进口代理商的控制层
 * @author: Song Biaowei
 * @date: 2016-10-4上午11:14:24
 */

@Controller
@Scope("prototype")
@RequestMapping("/importRecommend")
public class ImportRecommendController {
	@Autowired
	private ImportRecommendService importRecommendService;
	@Autowired
	private UserServiceI userService;
	
	/**
	 * @Title: registerStart
	 * @author Song Biaowei
	 * @date 2016-10-4 上午11:15:31  
	 * @Description: 进口代理商列表 
	 * @param @param ir
	 * @param @param request
	 * @param @param page
	 * @param @param model
	 * @param @return      
	 * @return String
	 */
	@RequestMapping("list")
	public String registerStart(ImportRecommend ir,HttpServletRequest request,Integer page,Model model){
		List<ImportRecommend> irList=importRecommendService.selectByRecommend(ir,page==null?1:page);
		request.setAttribute("irList", new PageInfo<>(irList));
		request.setAttribute("ir", ir);
		return "ses/sms/import_recommend/list";
	}
	
	/**
	 * @Title: add
	 * @author Song Biaowei
	 * @date 2016-10-4 下午2:17:31  
	 * @Description: 打开增加页面
	 * @param @return      
	 * @return String
	 */
	@RequestMapping("add")
	public String add(){
		return "ses/sms/import_recommend/add";
	}
	
	/**
	 * @Title: save
	 * @author Song Biaowei
	 * @date 2016-10-4 上午11:40:52  
	 * @Description: 进口代理商保存 
	 * @param @param ir
	 * @param @param model
	 * @param @param request
	 * @param @return
	 * @param @throws IOException      
	 * @return String
	 */
	@RequestMapping("save")
	public String save(ImportRecommend ir,Model model,HttpServletRequest request) throws IOException{
		User user1=(User) request.getSession().getAttribute("loginUser");
		ir.setCreatedAt(new Date());
		ir.setCreator(user1.getRelName());
		importRecommendService.register(ir);
		//存到user表里面
		User user=new User();
		user.setLoginName(ir.getLoginName());
		user.setPassword(ir.getPassword());
		user.setTypeId(ir.getId());
		user.setTypeName(7);
		userService.save(user, null);
		return "redirect:list.html";
	}
	
	/**
	 * @Title: edit
	 * @author Song Biaowei
	 * @date 2016-10-4 上午11:49:23  
	 * @Description: 进口代理商修改 
	 * @param @param id
	 * @param @param model
	 * @param @return      
	 * @return String
	 */
	@RequestMapping("edit")
	public String edit(String id,Model model){
		ImportRecommend ir=importRecommendService.findById(id);
		model.addAttribute("ir", ir);
		return "ses/sms/import_recommend/edit";
	}
	
	/**
	 * @Title: update
	 * @author Song Biaowei
	 * @date 2016-10-4 上午11:49:42  
	 * @Description: 进口代理商更新 
	 * @param @param ir
	 * @param @param model
	 * @param @param request
	 * @param @return
	 * @param @throws IOException      
	 * @return String
	 */
	@RequestMapping("update")
	public String update(ImportRecommend ir,Model model,HttpServletRequest request) throws IOException{
		ir.setUpdatedAt(new Date());
		importRecommendService.update(ir);
		return "redirect:list.html";
	}
	
	/**
	 * @Title: delete_soft
	 * @author Song Biaowei
	 * @date 2016-10-4 上午11:49:54  
	 * @Description: 软删除 
	 * @param @param ids
	 * @param @return      
	 * @return String
	 */
	@RequestMapping("delete_soft")
	public String delete_soft(String ids) {
		String[] id = ids.split(",");
		for (String str : id) {
			ImportRecommend ir=importRecommendService.findById(str);
			ir.setStatus((short)2);
			importRecommendService.update(ir);
		}
		return "redirect:list.html";
	}
	/**
	 * @Title: show
	 * @author Song Biaowei
	 * @date 2016-10-4 下午3:42:40  
	 * @Description: 查看进口代理商信息
	 * @param @param id
	 * @param @param model
	 * @param @return      
	 * @return String
	 */
	@RequestMapping("show")
	public String show(String id,Model model){
		ImportRecommend ir=importRecommendService.findById(id);
		model.addAttribute("ir", ir);
		return "ses/sms/import_recommend/show";
	}
	
	/**
	 * @Title: zanting
	 * @author Song Biaowei
	 * @date 2016-10-4 下午3:59:19  
	 * @Description: 暂停进口代理商，暂停后不能使用
	 * @param @param id
	 * @param @param model
	 * @param @return      
	 * @return String
	 */
	@RequestMapping("zanting")
	public String zanting(String id,Model model){
		ImportRecommend ir=importRecommendService.findById(id);
		ir.setStatus((short)3);
		importRecommendService.update(ir);
		model.addAttribute("ir", ir);
		return "redirect:list.html";
	}
	
	/**
	 * @Title: jihuo
	 * @author Song Biaowei
	 * @date 2016-10-4 下午4:09:49  
	 * @Description: 打开激活页面，上传附件，并且填写使用次数
	 * @param @throws IOException      
	 * @return String
	 */
	@RequestMapping("jihuo_add")
	public String jihuo(String id,Model model) throws IOException{
		model.addAttribute("id", id);
		return "ses/sms/import_recommend/jihuo";
	}
	
	/**
	 * @Title: jihuo
	 * @author Song Biaowei
	 * @date 2016-10-4 下午4:01:43  
	 * @Description: TODO 
	 * @param @param ir
	 * @param @param id
	 * @param @param model
	 * @param @return      
	 * @return String
	 * @throws IOException 
	 */
	@RequestMapping("jihuo_save")
	public String jihuoSave(ImportRecommend ir,String id,Model model,HttpServletRequest request) throws IOException{
		this.setSupplierUpload(request,ir);
		ImportRecommend ir1=importRecommendService.findById(id);
		ir1.setAttachment(ir.getAttachment());
		ir1.setUseCount(ir.getUseCount());
		ir1.setStatus((short)4);
		importRecommendService.update(ir1);
		model.addAttribute("ir", ir);
		return "redirect:list.html";
	}
	
	
	public void setSupplierUpload(HttpServletRequest request, ImportRecommend ir) throws IOException {
		CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());
		// 检查form中是否有enctype="multipart/form-data"
		if (multipartResolver.isMultipart(request)) {
			// 将request变成多部分request
			MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
			// 获取multiRequest 中所有的文件名
			Iterator<String> its = multiRequest.getFileNames();
			while (its.hasNext()) {
				String str = its.next();
				MultipartFile file = multiRequest.getFile(str);
				if (file != null && file.getSize() > 0) {
					String path = getRootPath(request) + file.getOriginalFilename();
					file.transferTo(new File(path));
					if (str.equals("attachment")) {
						ir.setAttachment(path);
					}
				}
			}
		}
	}
	public static String getRootPath(HttpServletRequest request) {
		return request.getSession().getServletContext().getRealPath("/").split("\\\\")[0] + "/" + PropUtil.getProperty("file.upload.path.supplier");
	}
}
