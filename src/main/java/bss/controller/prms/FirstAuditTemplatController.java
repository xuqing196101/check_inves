package bss.controller.prms;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.github.pagehelper.PageInfo;

import bss.controller.base.BaseController;
import bss.model.prms.FirstAuditTemitem;
import bss.model.prms.FirstAuditTemplat;
import bss.service.prms.FirstAuditTemitemService;
import bss.service.prms.FirstAuditTemplatService;
import ses.model.bms.DictionaryData;
import ses.util.DictionaryDataUtil;

@Controller
@RequestMapping("/auditTemplat")
public class FirstAuditTemplatController extends BaseController{

	@Autowired
	private FirstAuditTemplatService service;
	
	@Autowired
	private FirstAuditTemitemService temService;
	
	/**
	 * 
	  * @Title: list
	  * @author ShaoYangYang
	  * @date 2016年10月12日 上午10:17:21  
	  * @Description: TODO 列表
	  * @param @return      
	  * @return String
	 */
	@RequestMapping("/list")
	public String list(String name, String kind, Integer page, Model model){
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("name", name);
		map.put("kind", kind);
		List<FirstAuditTemplat> list = service.selectAll(map,page==null?1:page);
		List<DictionaryData> kinds = DictionaryDataUtil.find(20);
		model.addAttribute("list", new PageInfo<>(list));
		model.addAttribute("kinds", kinds);
		model.addAttribute("kind", kind);
		model.addAttribute("name", name);
		return "bss/prms/templat/list";
	}
	
	/**
	 * 
	  * @Title: toAdd
	  * @author ShaoYangYang
	  * @date 2016年10月11日 下午4:03:10  
	  * @Description: TODO 跳转到新增页面
	  * @param @return      
	  * @return String
	 */
	@RequestMapping("/toAdd")
	public String toAdd(Model model){
	  List<DictionaryData> kinds = DictionaryDataUtil.find(20);
    model.addAttribute("kinds", kinds);
		return "bss/prms/templat/add_templat";
	}
	/**
	 * 
	  * @Title: add
	  * @author ShaoYangYang
	  * @date 2016年10月11日 下午4:03:35  
	  * @Description: TODO 新增
	  * @param @param templat
	  * @param @return      
	  * @return String
	 */
	@RequestMapping("/add")
	public String add(@Valid FirstAuditTemplat templat, BindingResult result, Model model){
	  if(result.hasErrors()){
      List<DictionaryData> kinds = DictionaryDataUtil.find(20);
      model.addAttribute("kinds", kinds);
      model.addAttribute("templat", templat);
      return "bss/prms/templat/add_templat";
    }
		service.save(templat);
		return "redirect:list.html";
	}
	/**
	 * 
	  * @Title: edit
	  * @author ShaoYangYang
	  * @date 2016年10月12日 下午1:51:00  
	  * @Description: TODO 修改
	  * @param @param templat
	  * @param @return      
	  * @return String
	 */
	@RequestMapping("/edit")
	public String edit(@Valid FirstAuditTemplat templat, BindingResult result, Model model){
	  if(result.hasErrors()){
	    List<DictionaryData> kinds = DictionaryDataUtil.find(20);
	    model.addAttribute("kinds", kinds);
	    model.addAttribute("templat", templat);
	    return "bss/prms/templat/edit";
	  }
		service.update(templat);
		return "redirect:list.html";
	}
	/**
	 * 
	  * @Title: toEdit
	  * @author ShaoYangYang
	  * @date 2016年10月12日 下午1:53:26  
	  * @Description: TODO 去往修改页面
	  * @param @param id
	  * @param @param model
	  * @param @return      
	  * @return String
	 */
	@RequestMapping("/toEdit")
	public String toEdit(String id , Model model){
		FirstAuditTemplat templat = service.getById(id);
		List<DictionaryData> kinds = DictionaryDataUtil.find(20);
    model.addAttribute("kinds", kinds);
		model.addAttribute("templat", templat);
		return "bss/prms/templat/edit";
	}
	
	/**
	 *〈简述〉编辑模板的评审项
	 *〈详细描述〉
	 * @author Ye MaoLin
	 * @param templatKind 模板类型id
	 * @return
	 */
	@RequestMapping("/editItem")
	public String editItem(String templetKind, Model model, String templetId){
	  DictionaryData kind = DictionaryDataUtil.findById(templetKind);
	  List<FirstAuditTemitem> items = temService.selectByTemplatId(templetId);
	  if (kind != null && kind.getCode().equals("REVIEW_QC")) {
	    List<DictionaryData> dds = DictionaryDataUtil.find(22);
	    //符合性审查项
	    FirstAuditTemitem record = new FirstAuditTemitem();
	    record.setKind(DictionaryDataUtil.getId("COMPLIANCE"));
	    record.setTemplatId(templetId);
	    List<FirstAuditTemitem> items1 = temService.find(record);
	    //资格性审查项
      FirstAuditTemitem record2 = new FirstAuditTemitem();
      record2.setKind(DictionaryDataUtil.getId("QUALIFICATION"));
      record2.setTemplatId(templetId);
      List<FirstAuditTemitem> items2 = temService.find(record2);
	    //符合性资格性审查项类型
	    model.addAttribute("dds", dds);
	    model.addAttribute("kind", kind);
	    model.addAttribute("items1", items1);
	    model.addAttribute("items2", items2);
	    model.addAttribute("templetKind", templetKind);
	    model.addAttribute("templetId", templetId);
	    //符合性资格性审查项编辑
	    return "bss/prms/templat/qc_item_templet";
    } 
	  if (kind != null && kind.getCode().equals("REVIEW_ET")) {
	    List<DictionaryData> dds = DictionaryDataUtil.find(23);
      //经济技术审查项类型
      model.addAttribute("dds", dds);
      model.addAttribute("kind", kind);
      model.addAttribute("items", items);
      model.addAttribute("templetKind", templetKind);
      model.addAttribute("templetId", templetId);
      //经济技术审查项编辑
      return "bss/prms/templat/et_item_templet";
    } 
	  return null;
	}
	
	/**
	 * 
	  * @Title: toAddFirstAudit
	  * @author ShaoYangYang
	  * @date 2016年10月12日 下午2:08:24  
	  * @Description: TODO 去往添加初审项定义页面
	  * @param @return      
	  * @return String
	 */
	@RequestMapping("toAddFirstAudit")
	public String toAddFirstAudit(String id,Model model){
		FirstAuditTemplat templat = service.getById(id);
		List<FirstAuditTemitem> list = temService.selectByTemplatId(id);
		model.addAttribute("templat", templat);
		model.addAttribute("list", list);
		return "bss/prms/templat/add_first_audit";
	}
	/**
	 * 
	  * @Title: saveFirstAudit
	  * @author ShaoYangYang
	  * @date 2016年10月12日 下午2:52:48  
	  * @Description: TODO 保存初审项定义
	  * @param @return      
	  * @return String
	 */
	@RequestMapping("saveFirstAudit")
	public String saveFirstAudit(FirstAuditTemitem temitem ,RedirectAttributes attr){
		temService.save(temitem);
		attr.addAttribute("id", temitem.getTemplatId());
		return "redirect:toAddFirstAudit.html";
	}
	/**
	 * 
	  * @Title: toEditFirstAudit
	  * @author ShaoYangYang
	  * @date 2016年10月12日 下午5:32:58  
	  * @Description: TODO 去往修改初审项窗口
	  * @param @param id
	  * @param @param model
	  * @param @return      
	  * @return String
	 */
	@RequestMapping("toEditFirstAudit")
	public String toEditFirstAudit(String id,Model model){
		FirstAuditTemitem temitem = temService.getById(id);
		model.addAttribute("temitem", temitem);
		return  "bss/prms/templat/edit_first_audit";
	}
	/**
	 * 
	  * @Title: editFirstAudit
	  * @author ShaoYangYang
	  * @date 2016年10月12日 下午3:01:11  
	  * @Description: TODO 修改初审项定义
	  * @param @param temitem
	  * @param @param attr
	  * @param @return      
	  * @return String
	 */
	@RequestMapping("editFirstAudit")
	public String editFirstAudit(FirstAuditTemitem temitem,RedirectAttributes attr){
		temService.update(temitem);
		attr.addAttribute("id", temitem.getTemplatId());
		return "redirect:toAddFirstAudit.html";
	}
	/**
	 * 
	  * @Title: delete
	  * @author ShaoYangYang
	  * @date 2016年10月12日 上午11:19:13  
	  * @Description: TODO 删除初审项定义
	  * @param       
	  * @return void
	 */
	@RequestMapping("deleteFirstAudit")
	@ResponseBody
	public void deleteFirstAudit(String ids){
		String[] id = ids.split(",");
		for (int i = 0; i < id.length; i++) {
			temService.deleteById(id[i]);
		}
	}
	/**
	 * 
	  * @Title: delete
	  * @author ShaoYangYang
	  * @date 2016年10月12日 上午11:19:13  
	  * @Description: TODO 删除模板
	  * @param       
	  * @return void
	 */
	@RequestMapping("delete")
	public String delete(String ids){
		String[] id = ids.split(",");
		//循环删除选中的数据
		for (String string : id) {
			service.deleteById(string);
		}
		return "redirect:list.html";
	}
	
	/**
	 *〈简述〉保存符合性评审项
	 *〈详细描述〉
	 * @author Ye MaoLin
	 * @param response
	 * @param auditTemitem
	 * @throws IOException
	 */
	@RequestMapping("/saveItem")
	public void saveItem(HttpServletResponse response, FirstAuditTemitem auditTemitem) throws IOException{
	  try {
      int count = 0;
      String msg = "";
      if (auditTemitem.getName() == null || "".equals(auditTemitem.getName())) {
        msg += "请输入评审项名称";
        count ++;
      }
      if (auditTemitem.getPosition()== null) {
        if (count > 0) {
          msg += "、序号";
        } else {
          msg += "请输入排序号";
        }
        count ++;
      }
      if (auditTemitem.getContent()== null || "".equals(auditTemitem.getContent())) {
        if (count > 0) {
          msg += "和评审内容";
        } else {
          msg += "请输入评审内容";
        }
        count ++;
      }
      if (count > 0) {
        response.setContentType("text/html;charset=utf-8");
        response.getWriter()
                .print("{\"success\": " + false + ", \"msg\": \"" + msg+ "\"}");
      }
      if (count == 0) {
        msg += "添加成功";
        temService.save(auditTemitem);
        response.setContentType("text/html;charset=utf-8");
        response.getWriter()
                .print("{\"success\": " + true + ", \"msg\": \"" + msg+ "\"}");
        
      }
	    response.getWriter().flush();
    } catch (Exception e) {
        e.printStackTrace();
    } finally{
        response.getWriter().close();
    }
	}
}
