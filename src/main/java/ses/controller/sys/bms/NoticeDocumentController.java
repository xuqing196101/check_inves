/**
 * 
 */
package ses.controller.sys.bms;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.RequestMapping;

import com.github.pagehelper.PageInfo;

import ses.model.bms.NoticeDocument;
import ses.model.bms.Templet;
import ses.service.bms.NoticeDocumentService;

/**
 * @Title:NoticeDocumentController 
 * @Description: 须知文档管理控制类
 * @author Liyi
 * @date 2016-10-18下午4:21:00
 *
 */
@Controller
@Scope("prototype")
@RequestMapping("/noticeDocument")
public class NoticeDocumentController {
	@Resource
	private NoticeDocumentService noticeDocumentService;
	
	/**
	 * 
	 * @Title: getAll
	 * @author Liyi 
	 * @date 2016-10-18 下午4:42:48  
	 * @Description:获取须知文档列表
	 * @param:     
	 * @return:
	 */
	@RequestMapping("/getAll")
	public String getAll(Model model,Integer page){
		List<NoticeDocument> noticeDocuments = noticeDocumentService.getAll(page==null?1:page);
		model.addAttribute("list",new PageInfo<NoticeDocument>(noticeDocuments));
		return "ses/bms/noticeDocument/list";
	}
	
	/**
	 * 
	 * @Title: add
	 * @author Liyi 
	 * @date 2016-10-18 下午4:52:10  
	 * @Description:跳转至新增页面
	 * @param:     
	 * @return:
	 */
	@RequestMapping("/add")
	public String add(HttpServletRequest request,Model model){	
		return "ses/bms/noticeDocument/add";
	}
	
	/**
	 * 
	 * @Title: save
	 * @author Liyi 
	 * @date 2016-10-18 下午4:54:02  
	 * @Description:保存新增信息
	 * @param:     
	 * @return:
	 */
	@RequestMapping("/save")
	public String save(HttpServletRequest request,@Valid NoticeDocument noticeDocument,BindingResult result,Model model){
		Boolean flag = true;
		String url = "";
		if(noticeDocument.getDocType().equals("-请选择-")){
			flag = false;
			model.addAttribute("ERR_docType", "请选择须知文档类型");
		}
		if(result.hasErrors()){
			List<FieldError> errors = result.getFieldErrors();
			for(FieldError fieldError:errors){
				model.addAttribute("ERR_"+fieldError.getField(), fieldError.getDefaultMessage());
			}
			flag = false;
		}
		if(flag == false){
			model.addAttribute("noticeDocument", noticeDocument);
			url="ses/bms/noticeDocument/add";
		}else{	
			noticeDocumentService.save(noticeDocument);
			url="redirect:getAll.do";
		}
		return url;
	}
	
	/**
	 * 
	 * @Title: edit
	 * @author Liyi 
	 * @date 2016-10-18 下午4:57:06  
	 * @Description:跳转修改编辑页面
	 * @param:     
	 * @return:
	 */
	@RequestMapping("/edit")
	public String edit(Model model,String id){
		model.addAttribute("noticeDocument",noticeDocumentService.get(id));
		return "ses/bms/noticeDocument/edit";
	}
	
	/**
	 * 
	 * @Title: update
	 * @author Liyi 
	 * @date 2016-10-18 下午4:58:37  
	 * @Description:更新修改信息
	 * @param:     
	 * @return:
	 */
	@RequestMapping("/update")
	public String update(HttpServletRequest request,@Valid NoticeDocument noticeDocument,BindingResult result,Model model){
		Boolean flag = true;
		String url = "";
		if(noticeDocument.getDocType().equals("-请选择-")){
			flag = false;
			model.addAttribute("ERR_docType", "请选择模板类型");
		}
		if(result.hasErrors()){
			List<FieldError> errors = result.getFieldErrors();
			for(FieldError fieldError:errors){
				model.addAttribute("ERR_"+fieldError.getField(), fieldError.getDefaultMessage());
			}
			flag = false;
		}
		if(flag == false){
			model.addAttribute("noticeDocument", noticeDocument);
			url="ses/bms/noticeDocument/edit";
		}else{
			noticeDocumentService.update(noticeDocument);
			url="redirect:getAll.do";
		}
		return url;
	}
	
	/**
	 * 
	 * @Title: delete
	 * @author Liyi 
	 * @date 2016-10-18 下午5:06:23  
	 * @Description:批量删除
	 * @param:     
	 * @return:
	 */
	@RequestMapping("/delete")
	public String delete(String ids){
		String[] id=ids.split(",");
		for (String str : id) {
			noticeDocumentService.delete(str);
		}
		return "redirect:getAll.do";
	}
	
	/**
	 * 
	 * @Title: view
	 * @author Liyi 
	 * @date 2016-10-18 下午5:07:16  
	 * @Description:跳转查看页面
	 * @param:     
	 * @return:
	 */
	@RequestMapping("/view")
	public String view(Model model,String id){
		model.addAttribute("noticeDocument",noticeDocumentService.get(id));
		return "ses/bms/noticeDocument/view";
	}
	
	/**
	 * 
	 * @Title: search
	 * @author Liyi 
	 * @date 2016-10-18 下午5:07:37  
	 * @Description:条件查询
	 * @param:     
	 * @return:
	 */
	@RequestMapping("/search")
	public String search(Model model,HttpServletRequest request,NoticeDocument noticeDocument,Integer page){
		List<NoticeDocument> noticeDocuments = noticeDocumentService.search(page==null?1:page,noticeDocument);
		model.addAttribute("list",new PageInfo<NoticeDocument>(noticeDocuments));
		model.addAttribute("noticeDocument",noticeDocument);
		return "ses/bms/templet/list";
	}
}
