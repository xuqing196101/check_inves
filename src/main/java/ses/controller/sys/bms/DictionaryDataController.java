package ses.controller.sys.bms;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import net.sf.json.JSONSerializer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;

import ses.controller.sys.sms.BaseSupplierController;
import ses.model.bms.DictionaryData;
import ses.model.bms.DictionaryType;
import ses.model.bms.User;
import ses.service.bms.DictionaryDataServiceI;
import ses.service.bms.DictionaryTypeService;

import com.github.pagehelper.PageInfo;

import common.annotation.CurrentUser;

@Controller
@RequestMapping("/dictionaryData")
public class DictionaryDataController extends BaseSupplierController{

    @Autowired
    private DictionaryDataServiceI dictionaryDataService;
    
    @Autowired
    private DictionaryTypeService dictionaryTypeService;
    
    @RequestMapping("/list")
    public String list(Model model, Integer page, DictionaryData dd) {
        List<DictionaryData> ls = dictionaryDataService.listByPage(dd, page == null ? 1 : page);
        model.addAttribute("list", new PageInfo<DictionaryData>(ls));
        model.addAttribute("dd", dd);
        return "ses/bms/dictionaryData/list";
    }
    
    @RequestMapping("/add")
    public String add(HttpServletRequest request, Integer kind, Model model){
        model.addAttribute("kind", kind);
        return "ses/bms/dictionaryData/add";
    }
    
    @RequestMapping("/save")
    public String save(@Valid DictionaryData dd, BindingResult result, HttpServletRequest request, Model model){
        
        if (result.hasErrors()) {
            model.addAttribute("kind", dd.getKind());
            model.addAttribute("dd", dd);
            return "ses/bms/dictionaryData/add";
        }
        
        DictionaryData temp1 = new DictionaryData();
        temp1.setCode(dd.getCode());
        List<DictionaryData> dds = dictionaryDataService.find(temp1);
        if(dds.size() > 0){
            model.addAttribute("dd", dd);
            model.addAttribute("kind", dd.getKind());
            model.addAttribute("exist", "编码已存在");
            return "ses/bms/dictionaryData/add";
        }
        
        dd.setCreatedAt(new Date());
        dd.setUpdatedAt(new Date());
        dd.setIsDeleted(0);
        dictionaryDataService.save(dd);
        return "redirect:dictionaryDataList.html?kind="+dd.getKind();
    }
    
    @RequestMapping("/edit")
    public String edit(DictionaryData dd, Integer page, Model model) throws Exception{
        List<DictionaryData> ls = dictionaryDataService.find(dd);
        if (ls.size() > 0){
            model.addAttribute("dd", ls.get(0));
            return "ses/bms/dictionaryData/edit";
        } else {
            throw new Exception("访问失败");
        }
    }
    
    @RequestMapping("/update")
    public String update(@Valid DictionaryData dd, BindingResult result, Model model, HttpServletRequest request){
        if(result.hasErrors()){
            model.addAttribute("dd", dd);
            return "ses/bms/dictionaryData/edit";
        }
        
        List<DictionaryData> dds = dictionaryDataService.findRepeat(dd);
        if(dds.size() > 0){
            model.addAttribute("dd", dd);
            model.addAttribute("exist", "编码已存在");
            return "ses/bms/dictionaryData/edit";
        }
        
        dd.setUpdatedAt(new Date());
        dictionaryDataService.update(dd);
        return "redirect:dictionaryDataList.html?kind="+dd.getKind();
    }
    
    @RequestMapping("/deleteSoft")
    public String deleteSoft(String ids) throws Exception{
        String[] idArr = ids.split(",");
        Integer kind = 1;
        for (String id : idArr) {
            DictionaryData dd = new DictionaryData();
            dd.setId(id);
            List<DictionaryData> ls = dictionaryDataService.find(dd);
            if (ls != null && ls.size() > 0){
                ls.get(0).setIsDeleted(1);
                dictionaryDataService.update(ls.get(0));
                kind = ls.get(0).getKind();
            } else {
                throw new Exception("获取失败");
            }
        }
        return "redirect:dictionaryDataList.html?kind="+kind;
    }
    
    @RequestMapping("/dictionaryDataList")
    public String dictionaryDataList(@CurrentUser User user,Model model,String kind,Integer page) {
    	//声明标识是否是资源服务中心
        String authType = null;
        if(null != user && "4".equals(user.getTypeName())){
            //判断是否 是资源服务中心 
            authType = "4";
	        List<DictionaryType> ls = dictionaryTypeService.findList();
	        model.addAttribute("list", ls);
	        model.addAttribute("kind", kind);
	        model.addAttribute("authType", authType);
        }
        return "ses/bms/dictionaryData/dictionaryDataList";
    }
    
    @RequestMapping("/showList")
    public void showList(@CurrentUser User user,HttpServletResponse response,DictionaryData dd,Integer page) throws Exception{
    	//声明标识是否是资源服务中心
        if(null != user && "4".equals(user.getTypeName())){
	    	List<DictionaryData> ls = dictionaryDataService.listByPage(dd, page == null ? 1 : page);
	    	HashMap<String, Object> map = new HashMap<>();
	    	map.put("pageInfo", new PageInfo<DictionaryData>(ls));
	    	map.put("list", ls);
	    	map.put("kind", dd.getKind());
	    	super.writeJson(response, JSONSerializer.toJSON(map));
        }
    }
}
