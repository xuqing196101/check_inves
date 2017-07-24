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

import common.annotation.CurrentUser;
import ses.model.bms.DictionaryType;
import ses.model.bms.User;
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
    public String list(@CurrentUser User user,Model model, Integer page) {
    	//声明标识是否是资源服务中心
        String authType = null;
        if(null != user && "4".equals(user.getTypeName())){
            //判断是否 是资源服务中心 
            authType = "4";
            List<DictionaryType> ls = dictionaryTypeService.listByPage(page == null ? 1 : page);
            model.addAttribute("list", new PageInfo<DictionaryType>(ls));
            model.addAttribute("authType", authType);
        }
        return "ses/bms/dictionaryType/list";
    }

    @RequestMapping("/add")
    public String add(@CurrentUser User user,HttpServletRequest request,Model model){
    	//判断是否 是资源服务中心 
    	if(null != user && "4".equals(user.getTypeName())){
    		return "ses/bms/dictionaryType/add";
    	}
    	return "";
    }
    
    @RequestMapping("/save")
    public String save(@CurrentUser User user,HttpServletRequest request,@Valid DictionaryType dt,BindingResult result,Model model){
    	//判断是否 是资源服务中心 
    	if(null != user && "4".equals(user.getTypeName())){
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
    	return "";
    }
    
	@RequestMapping("/edit")
	public String edit(@CurrentUser User user,Model model,String id){
		//判断是否 是资源服务中心 
    	if(null != user && "4".equals(user.getTypeName())){
    		model.addAttribute("dictionaryType",dictionaryTypeService.get(id));
    		return "ses/bms/dictionaryType/edit";
    	}
    	return "";
	}
	
	@RequestMapping("/update")
    public String update(@CurrentUser User user,HttpServletRequest request,@Valid DictionaryType dt,BindingResult result,Model model){
		//判断是否 是资源服务中心 
    	if(null != user && "4".equals(user.getTypeName())){
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
    	return "";
	}
	
	@RequestMapping("/delete")
	public String delete(@CurrentUser User user,String ids){
		//判断是否 是资源服务中心 
    	if(null != user && "4".equals(user.getTypeName())){
			String[] id=ids.split(",");
			for (String str : id) {
				dictionaryTypeService.deleteByPrimaryKey(str);
			}
			return "redirect:list.do";
    	}
    	return "";
	}
	
	@RequestMapping("/search")
	public String search(@CurrentUser User user,Model model,HttpServletRequest request,DictionaryType dictionaryType,Integer page){
		//声明标识是否是资源服务中心
        String authType = null;
        if(null != user && "4".equals(user.getTypeName())){
            //判断是否 是资源服务中心 
            authType = "4";
			List<DictionaryType> dictionaryTypes = dictionaryTypeService.search(page==null?1:page,dictionaryType);
			model.addAttribute("list",new PageInfo<DictionaryType>(dictionaryTypes));
			model.addAttribute("dictionaryType",dictionaryType);
			model.addAttribute("authType",authType);
        }
		return "ses/bms/dictionaryType/list";
	}
}
