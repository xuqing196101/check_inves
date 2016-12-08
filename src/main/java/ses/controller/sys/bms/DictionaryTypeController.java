package ses.controller.sys.bms;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.RequestMapping;

import bss.controller.base.BaseController;

import com.github.pagehelper.PageInfo;
import ses.model.bms.DictionaryType;
import ses.service.bms.DictionaryTypeService;

@Controller
@RequestMapping("/dictionaryType")
public class DictionaryTypeController extends BaseController{

    @Autowired
    private DictionaryTypeService dictionaryTypeService;
    
    /**
     * 
     * @Title: list
     * @author Liyi 
     * @date 2016-12-6 下午4:17:30  
     * @Description:获取数据字典类型列表
     * @param:     
     * @return:
     */
    @RequestMapping("/list")
    public String list(Model model, Integer page) {
        List<DictionaryType> ls = dictionaryTypeService.listByPage(page == null ? 1 : page);
        model.addAttribute("list", new PageInfo<DictionaryType>(ls));
        return "ses/bms/dictionaryType/list";
    }

    @RequestMapping("/add")
    public String add(HttpServletRequest request,Model model){
    	return "ses/bms/dictionaryType/add";
    }
    
    @RequestMapping("/save")
    public String save(HttpServletRequest request,@Valid DictionaryType dt,BindingResult result,Model model){
		Boolean flag = true;
		String url = "";
		List<DictionaryType> ls = dictionaryTypeService.findList();
		if (ls!=null && ls.size()>0) {
			Integer codeLong = dt.getCode();
			String nameString = dt.getName();
			for (DictionaryType dictionaryType : ls) {
				if (dictionaryType.getCode()==codeLong) {
					flag = false;
					model.addAttribute("ERR_code", "该类型编号已存在");
				}
			}
			for (DictionaryType dictionaryType : ls) {
				if (dictionaryType.getName().equals(nameString)) {
					flag = false;
					model.addAttribute("ERR_name", "该类型名称已存在");
				}
			}
		}
		if(result.hasErrors()){
			List<FieldError> errors = result.getFieldErrors();
			for(FieldError fieldError:errors){
				model.addAttribute("ERR_"+fieldError.getField(), fieldError.getDefaultMessage());
			}
			flag = false;
		}
		if(flag == false){
			model.addAttribute("dictionaryType", dt);
			url="ses/bms/dictionaryType/add";
		}else{
	    	dictionaryTypeService.insertSelective(dt);
			url="redirect:list.do";
		}		

    	return url;
    }
    
	@RequestMapping("/edit")
	public String edit(Model model,String id){
		model.addAttribute("dictionaryType",dictionaryTypeService.get(id));
    	return "ses/bms/dictionaryType/edit";		
	}
	
	@RequestMapping("/update")
    public String update(HttpServletRequest request,@Valid DictionaryType dt,BindingResult result,Model model){
		Boolean flag = true;
		String url = "";
		List<DictionaryType> ls = dictionaryTypeService.findList();
		if (ls!=null && ls.size()>0) {
			Integer codeLong = dt.getCode();
			String nameString = dt.getName();
			for (DictionaryType dictionaryType : ls) {
				if (dictionaryType.getCode()==codeLong) {
					flag = false;
					model.addAttribute("ERR_code", "该类型编号已存在");
				}
			}
			for (DictionaryType dictionaryType : ls) {
				if (dictionaryType.getName().equals(nameString)) {
					flag = false;
					model.addAttribute("ERR_name", "该类型名称已存在");
				}
			}
		}
		if(result.hasErrors()){
			List<FieldError> errors = result.getFieldErrors();
			for(FieldError fieldError:errors){
				model.addAttribute("ERR_"+fieldError.getField(), fieldError.getDefaultMessage());
			}
			flag = false;
		}
		if(flag == false){
			model.addAttribute("dictionaryType", dt);
			url="ses/bms/dictionaryType/edit";
		}else{
	    	dictionaryTypeService.updateByPrimaryKeySelective(dt);
			url="redirect:list.do";
		}		

    	return url;
	}
	
	@RequestMapping("/delete")
	public String delete(String ids){
		String[] id=ids.split(",");
		for (String str : id) {
			dictionaryTypeService.deleteByPrimaryKey(str);
		}
		return "redirect:list.do";
	}
	
	@RequestMapping("/search")
	public String search(Model model,HttpServletRequest request,DictionaryType dictionaryType,Integer page){
		List<DictionaryType> dictionaryTypes = dictionaryTypeService.search(page==null?1:page,dictionaryType);
		model.addAttribute("list",new PageInfo<DictionaryType>(dictionaryTypes));
		model.addAttribute("dictionaryType",dictionaryType);
		return "ses/bms/dictionaryType/list";
	}
}
