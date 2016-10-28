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

import com.alibaba.druid.stat.TableStat.Mode;
import com.github.pagehelper.PageInfo;
import com.sun.org.apache.xpath.internal.operations.Mod;

import ses.model.bms.AttachmentType;
import ses.service.bms.AttachmentTypeServiceI;

@Controller
@RequestMapping("/attachmentType")
public class AttachmentTypeController extends BaseController{

    @Autowired
    private AttachmentTypeServiceI attachmentTypeService;
    
    @RequestMapping("/list")
    public String list(Model model, Integer page, AttachmentType attachmentType) {
        List<AttachmentType> ls = attachmentTypeService.listByPage(attachmentType, page == null ? 1 : page);
        model.addAttribute("list", new PageInfo<AttachmentType>(ls));
        model.addAttribute("at", attachmentType);
        return "ses/bms/attachmentType/list";
    }
    
    @RequestMapping("/add")
    public String add(HttpServletRequest request){
        return "ses/bms/attachmentType/add";
    }
    
    @RequestMapping("/save")
    public String save(@Valid AttachmentType at, BindingResult result, HttpServletRequest request, Model model){
        if (result.hasErrors()) {
            model.addAttribute("at", at);
            return "ses/bms/attachmentType/add";
        }
        at.setCreatedAt(new Date());
        at.setIsDeleted(0);
        attachmentTypeService.save(at);
        return "redirect:list.html";
    }
    
    @RequestMapping("/edit")
    public String edit(AttachmentType at, Integer page, Model model) throws Exception{
        List<AttachmentType> ls = attachmentTypeService.find(at);
        if (ls.size() > 0){
            model.addAttribute("at", ls.get(0));
            model.addAttribute("currpage",page);
            return "ses/bms/attachmentType/edit";
        } else {
            throw new Exception("访问失败");
        }
    }
    
    @RequestMapping("/update")
    public String update(@Valid AttachmentType at, BindingResult result, Model model, Integer currpage){
        if(result.hasErrors()){
            model.addAttribute("at", at);
            return "ses/bms/attachmentType/edit";
        }
        at.setUpdatedAt(new Date());
        attachmentTypeService.update(at);
        return "redirect:list.html?page="+currpage;
    }
    
    @RequestMapping("/deleteSoft")
    public String deleteSoft(String ids) throws Exception{
        String[] idArr = ids.split(",");
        for (String id : idArr) {
            AttachmentType at = new AttachmentType();
            at.setId(id);
            List<AttachmentType> ls = attachmentTypeService.find(at);
            if (ls.size() > 0){
                at.setIsDeleted(1);
                attachmentTypeService.update(at);
            } else {
                throw new Exception("获取失败");
            }
        }
        return "redirect:list.html";
    }
    
}
