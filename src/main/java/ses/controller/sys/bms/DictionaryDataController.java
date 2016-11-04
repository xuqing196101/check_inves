package ses.controller.sys.bms;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;

import bss.controller.base.BaseController;

import com.github.pagehelper.PageInfo;

import ses.model.bms.DictionaryData;
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
    public String add(HttpServletRequest request, Integer kind, Model model){
        model.addAttribute("kind", kind);
        return "ses/bms/dictionaryData/add";
    }
    
    @RequestMapping("/save")
    public String save(@Valid DictionaryData dd, BindingResult result, HttpServletRequest request, Model model){
        
        if (result.hasErrors()) {
            model.addAttribute("dd", dd);
            return "ses/bms/dictionaryData/add";
        }
        
        DictionaryData temp1 = new DictionaryData();
        temp1.setCode(dd.getCode());
        List<DictionaryData> dds = dictionaryDataService.find(temp1);
        if(dds.size() > 0){
            model.addAttribute("dd", dd);
            model.addAttribute("exist", "编码已存在");
            return "ses/bms/dictionaryData/add";
        }
        
        dd.setCreatedAt(new Date());
        dd.setUpdatedAt(new Date());
        dd.setIsDeleted(0);
        dictionaryDataService.save(dd);
        return "redirect:list.html?kind="+dd.getKind();
    }
    
    @RequestMapping("/edit")
    public String edit(DictionaryData dd, Integer page, Model model) throws Exception{
        List<DictionaryData> ls = dictionaryDataService.find(dd);
        if (ls.size() > 0){
            model.addAttribute("dd", ls.get(0));
            model.addAttribute("currpage",page);
            return "ses/bms/dictionaryData/edit";
        } else {
            throw new Exception("访问失败");
        }
    }
    
    @RequestMapping("/update")
    public String update(@Valid DictionaryData dd, BindingResult result, Model model, Integer currpage){
        if(result.hasErrors()){
            model.addAttribute("dd", dd);
            model.addAttribute("currpage",currpage);
            return "ses/bms/dictionaryData/edit";
        }
        
        List<DictionaryData> dds = dictionaryDataService.findRepeat(dd);
        if(dds.size() > 0){
            model.addAttribute("dd", dd);
            model.addAttribute("currpage",currpage);
            model.addAttribute("exist", "编码已存在");
            return "ses/bms/dictionaryData/edit";
        }
        
        dd.setUpdatedAt(new Date());
        dictionaryDataService.update(dd);
        return "redirect:list.html?kind="+dd.getKind()+"&page="+currpage;
    }
    
    @RequestMapping("/deleteSoft")
    public String deleteSoft(String ids) throws Exception{
        String[] idArr = ids.split(",");
        Integer kind = 1;
        for (String id : idArr) {
            DictionaryData dd = new DictionaryData();
            dd.setId(id);
            List<DictionaryData> ls = dictionaryDataService.find(dd);
            if (ls.size() > 0){
                dd.setIsDeleted(1);
                dictionaryDataService.update(dd);
                kind = dd.getKind();
            } else {
                throw new Exception("获取失败");
            }
        }
        return "redirect:list.html?kind="+kind;
    }
    
}
