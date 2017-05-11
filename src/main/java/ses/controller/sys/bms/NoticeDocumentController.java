/**
 * 
 */
package ses.controller.sys.bms;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.RequestMapping;

import com.github.pagehelper.PageInfo;
import common.annotation.CurrentUser;

import ses.model.bms.DictionaryData;
import ses.model.bms.NoticeDocument;
import ses.model.bms.User;
import ses.service.bms.NoticeDocumentService;
import ses.util.DictionaryDataUtil;

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
	public String getAll(@CurrentUser User user,Model model,Integer page){
		String authType=null;
		if(user!= null){
			//判断是否 是资源服务中心 
			if("4".equals(user.getTypeName())){
				authType=user.getTypeName();
	    initDocType(model);
		List<NoticeDocument> noticeDocuments = noticeDocumentService.getAll(page==null?1:page);
		model.addAttribute("list",new PageInfo<NoticeDocument>(noticeDocuments));
			}
		}
		model.addAttribute("authType", authType);
		
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
	public String add(@CurrentUser User user,Model model){
		String authType=null;
		if(user!= null){
			//判断是否 是资源服务中心 
			if("4".equals(user.getTypeName())){
				authType=user.getTypeName();
	            initDocType(model);
			 }
		 }
		model.addAttribute("authType",authType);
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
	public String save(@CurrentUser User user,@Valid NoticeDocument noticeDocument,BindingResult result,Model model){
		Boolean flag = true;
		String url = "";
		
		initDocType(model);
		if(!StringUtils.isNotBlank(noticeDocument.getDocType())){
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
			String authType=null;
			if(user!= null){
				//判断是否 是资源服务中心 
				if("4".equals(user.getTypeName())){
					authType=user.getTypeName();
					model.addAttribute("authType", authType);
			noticeDocumentService.save(noticeDocument);
				}
			}
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
	public String edit(@CurrentUser User user,Model model,String id){
		String authType=null;
		if(user!= null){
			//判断是否 是资源服务中心 
			if("4".equals(user.getTypeName())){
				authType=user.getTypeName();
	    initDocType(model);
		model.addAttribute("noticeDocument",noticeDocumentService.get(id));
			}
		}
			model.addAttribute("authType", authType);
		return "ses/bms/noticeDocument/edit";
	}
	
	/**
	 * 
	 *〈简述〉初始化类型
	 *〈详细描述〉
	 * @author myc
	 * @param model
	 */
	private void initDocType(Model model){
	    List<DictionaryData> list = DictionaryDataUtil.find(28);
        model.addAttribute("noticeType", list);
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
	public String update(@CurrentUser User user,@Valid NoticeDocument noticeDocument,BindingResult result,Model model){
		Boolean flag = true;
		String url = "";
		initDocType(model);
		if(!StringUtils.isNotBlank(noticeDocument.getDocType())){
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
			url="ses/bms/noticeDocument/edit";
		}else{
			String authType=null;
			if(user!= null){
				//判断是否 是资源服务中心 
				if("4".equals(user.getTypeName())){
					authType=user.getTypeName();
					model.addAttribute("authType", authType);
			noticeDocumentService.update(noticeDocument);
				}
				}
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
	public String delete(@CurrentUser User user,String ids){
		if(user!= null){
			//判断是否 是资源服务中心 
			if("4".equals(user.getTypeName())){
		String[] id=ids.split(",");
		for (String str : id) {
			noticeDocumentService.delete(str);
		}
			}
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
	public String view(@CurrentUser User user,Model model,String id){
		String authType=null;
		if(user!= null){
			//判断是否 是资源服务中心 
			if("4".equals(user.getTypeName())){
				authType=user.getTypeName();
				model.addAttribute("authType", authType);
	    initDocType(model);
		model.addAttribute("noticeDocument",noticeDocumentService.get(id));
			}
		 }
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
	    initDocType(model);
		List<NoticeDocument> noticeDocuments = noticeDocumentService.search(page==null?1:page,noticeDocument);
		model.addAttribute("list",new PageInfo<NoticeDocument>(noticeDocuments));
		model.addAttribute("noticeDocument",noticeDocument);
		return "ses/bms/noticeDocument/list";
	}
}
