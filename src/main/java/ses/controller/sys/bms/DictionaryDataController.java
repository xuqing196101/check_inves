package ses.controller.sys.bms;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import net.sf.json.JSONArray;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import bss.controller.base.BaseController;

import com.alibaba.druid.stat.TableStat.Mode;
import com.github.pagehelper.PageInfo;
import com.sun.org.apache.xpath.internal.operations.Mod;

import ses.model.bms.DictionaryData;
import ses.model.bms.User;
import ses.model.oms.Orgnization;
import ses.model.oms.util.Ztree;
import ses.service.bms.DictionaryDataServiceI;

@Controller
@RequestMapping("/dictionaryData")
public class DictionaryDataController extends BaseController{

    @Autowired
    private DictionaryDataServiceI dictionaryDataService;
    
    @RequestMapping("/list")
    public String list(Model model, Integer page, DictionaryData dd) {
        List<DictionaryData> ls = dictionaryDataService.listByPage(dd, page == null ? 1 : page);
        model.addAttribute("list", new PageInfo<DictionaryData>(ls));
        model.addAttribute("dd", dd);
        return "ses/bms/dictionaryData/list";
    }
    
    @RequestMapping("/add")
    public String add(HttpServletRequest request){
        return "ses/bms/dictionaryData/add";
    }
    
    @RequestMapping("/save")
    public String save(@Valid DictionaryData dd, BindingResult result, String pName, String pId, HttpServletRequest request, Model model){
        
        if (result.hasErrors()) {
            model.addAttribute("dd", dd);
            model.addAttribute("pName", pName);
            model.addAttribute("pId", pId);
            return "ses/bms/dictionaryData/add";
        }
        
        DictionaryData temp1 = new DictionaryData();
        temp1.setCode(dd.getCode());
        List<DictionaryData> dds = dictionaryDataService.find(temp1);
        if(dds.size() > 0){
            model.addAttribute("dd", dd);
            model.addAttribute("pName", pName);
            model.addAttribute("pId", pId);
            model.addAttribute("exist", "编码已存在");
            return "ses/bms/dictionaryData/add";
        }
        
        DictionaryData parent = null; 
        if (!"".equals(pId) && pId != null && !"".equals(pName) && pName != null){
            DictionaryData temp = new DictionaryData();
            temp.setId(pId);
            temp.setName(pName);
            List<DictionaryData> ls = dictionaryDataService.find(temp);
            if(ls != null &&  ls.size() > 0){
                parent = ls.get(0);
                dd.setIsRoot(false);
            }
        } else {
            dd.setIsRoot(true);
        }
        dd.setParent(parent);
        dd.setCreatedAt(new Date());
        dd.setIsDeleted(0);
        dictionaryDataService.save(dd);
        return "redirect:list.html";
    }
    
    @RequestMapping("/edit")
    public String edit(DictionaryData dd, Integer page, Model model) throws Exception{
        List<DictionaryData> ls = dictionaryDataService.find(dd);
        if (ls.size() > 0){
            model.addAttribute("dd", ls.get(0));
            model.addAttribute("currpage",page);
            DictionaryData parent = ls.get(0).getParent();
            if(parent != null){
                model.addAttribute("pName", parent.getName());
                model.addAttribute("pId", parent.getId());
            }
            return "ses/bms/dictionaryData/edit";
        } else {
            throw new Exception("访问失败");
        }
    }
    
    @RequestMapping("/update")
    public String update(@Valid DictionaryData dd, BindingResult result, String pName, String pId,  Model model, Integer currpage){
        if(result.hasErrors()){
            model.addAttribute("dd", dd);
            model.addAttribute("currpage",currpage);
            model.addAttribute("pName", pName);
            model.addAttribute("pId", pId);
            return "ses/bms/dictionaryData/edit";
        }
        
        DictionaryData temp1 = new DictionaryData();
        temp1.setCode(dd.getCode());
        List<DictionaryData> dds = dictionaryDataService.find(temp1);
        if(dds.size() > 0){
            model.addAttribute("dd", dd);
            model.addAttribute("currpage",currpage);
            model.addAttribute("pName", pName);
            model.addAttribute("pId", pId);
            model.addAttribute("exist", "编码已存在");
            return "ses/bms/dictionaryData/edit";
        }
        
        DictionaryData parent = null; 
        if (!"".equals(pId) && pId != null && !"".equals(pName) && pName != null){
            DictionaryData temp = new DictionaryData();
            temp.setId(pId);
            temp.setName(pName);
            List<DictionaryData> ls = dictionaryDataService.find(temp);
            if(ls != null &&  ls.size() > 0){
                parent = ls.get(0);
                dd.setIsRoot(false);
            }
        } else {
            dd.setIsRoot(true);
        }
        dd.setParent(parent);
        dd.setUpdatedAt(new Date());
        dictionaryDataService.update(dd);
        return "redirect:list.html?page="+currpage;
    }
    
    @RequestMapping("/deleteSoft")
    public String deleteSoft(String ids) throws Exception{
        String[] idArr = ids.split(",");
        for (String id : idArr) {
            DictionaryData dd = new DictionaryData();
            dd.setId(id);
            List<DictionaryData> ls = dictionaryDataService.find(dd);
            if (ls.size() > 0){
                dd.setIsDeleted(1);
                dictionaryDataService.update(dd);
            } else {
                throw new Exception("获取失败");
            }
        }
        return "redirect:list.html";
    }
    
    @RequestMapping(value = "getPTree",produces={"application/json;charset=UTF-8"})
    @ResponseBody    
    public String getPTree(HttpServletRequest request, HttpSession session, String id){
        DictionaryData parent = null; 
        if(id != null && !"".equals(id) ){
            DictionaryData temp = new DictionaryData();
            temp.setId(id);
            List<DictionaryData> ls = dictionaryDataService.find(temp);
            if(ls != null &&  ls.size() > 0){
                parent = ls.get(0).getParent();
            }
        }
        DictionaryData temp2 = new DictionaryData();
        temp2.setIsRoot(true);
        List<DictionaryData> ls = dictionaryDataService.find(temp2);
        List<Ztree> treeList = new ArrayList<Ztree>();  
        for (DictionaryData dd : ls){
            Ztree z = new Ztree();
            z.setId(dd.getId());
            z.setName(dd.getName());
            z.setpId(dd.getParent() == null ? "0":dd.getParent().getId());
            if (parent != null){
                if (dd.getId().equals(parent.getId())){
                    z.setChecked(true);
                }
            }
            treeList.add(z);
        }
        JSONArray jObject = JSONArray.fromObject(treeList);
        return jObject.toString();
    }
}
