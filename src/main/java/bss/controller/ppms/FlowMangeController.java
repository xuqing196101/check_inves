package bss.controller.ppms;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import ses.model.bms.User;
import ses.util.DictionaryDataUtil;

import com.github.pagehelper.PageInfo;

import bss.model.ppms.FlowDefine;
import bss.service.ppms.FlowMangeService;

/**
 * 版权：(C) 版权所有 
 * <简述>项目实施流程环节管理
 * <详细描述>
 * @author   Ye MaoLin
 * @version  
 * @since
 * @see
 */
@Controller
@RequestMapping("/flow")
public class FlowMangeController {
    
    @Autowired
    private FlowMangeService flowMangeService;
    
    /**
     *〈简述〉列表查询
     *〈详细描述〉
     * @author Ye MaoLin
     * @param model
     * @param page 页码
     * @param fd 实体封装对象
     * @param typeCode 采购方式编码
     * @return 当前采购方式编码对应list集合
     * @throws Exception
     */
    @RequestMapping("/list")
    public String list(Model model, Integer page, FlowDefine fd, String typeCode) {
        if (typeCode != null && !"".equals(typeCode)) {
            fd.setPurchaseTypeId(DictionaryDataUtil.getId(typeCode));
        } 
        List<FlowDefine> ls = flowMangeService.listByPage(fd, page == null ? 1 : page);
        model.addAttribute("list", new PageInfo<FlowDefine>(ls));
        model.addAttribute("fd", fd);
        return "bss/ppms/flow/list";
    }
    
    /**
     *〈简述〉弹出新增页面
     *〈详细描述〉
     * @author Ye MaoLin
     * @param request
     * @param typeId 采购方式编码id
     * @param model
     * @return 页面
     * @throws Exception
     */
    @RequestMapping("/add")
    public String add(HttpServletRequest request, String typeId, Model model) throws Exception{
        model.addAttribute("typeId", typeId);
        return "bss/ppms/flow/add";
    }
    
    /**
     *〈简述〉ajax保存数据
     *〈详细描述〉
     * @author Ye MaoLin
     * @param fd 实体封装对象
     * @param response 
     * @throws IOException
     */
    @RequestMapping("/save")
    @ResponseBody
    public void save(FlowDefine fd, HttpServletResponse response) throws IOException{
       try {
            String msg = "";
            int count = 0;
            if ("".equals(fd.getName()) || fd.getName() == null) {
                msg += "请填写流程名称";
                count ++;
            } 
            if (fd.getStep() == null) {
                if (count > 0) {
                    msg += "和流程步骤";
                } else {
                    msg += "请填写流程步骤";
                }
                count ++;
            } 
            if (fd.getCode() == null || "".equals(fd.getCode())) {
                if (count > 0) {
                    msg += "、流程编码";
                } else {
                    msg += "请填写流程编码";
                }
                count ++;
            }
            if (count > 0) {
                response.setContentType("text/html;charset=utf-8");
                response.getWriter().print(
                        "{\"success\": " + false + ", \"msg\": \"" + msg
                                + "\"}");
            }
            if (count == 0) {
                fd.setCreatedAt(new Date());
                fd.setUpdatedAt(new Date());
                fd.setIsDeleted(0);
                flowMangeService.save(fd);
                msg += "添加成功";
                response.setContentType("text/html;charset=utf-8");
                response.getWriter()
                        .print("{\"success\": " + true + ", \"msg\": \"" + msg
                                + "\"}");
            }
            response.getWriter().flush();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException();
        } finally{
            response.getWriter().close();
        }
    }
    
    /**
     *〈简述〉弹出编辑页面
     *〈详细描述〉
     * @author Ye MaoLin
     * @param fd 实体封装对象
     * @param page
     * @param model
     * @return
     * @throws Exception
     */
    @RequestMapping("/edit")
    public String edit(FlowDefine fd, Integer page, Model model) throws Exception{
        List<FlowDefine> ls = flowMangeService.find(fd);
        if (ls.size() > 0){
            model.addAttribute("fd", ls.get(0));
            model.addAttribute("currpage",page);
            return "bss/ppms/flow/edit";
        } else {
            throw new Exception("访问失败");
        }
    }
    
    /**
     *〈简述〉更新数据
     *〈详细描述〉
     * @author Ye MaoLin
     * @param fd 实体封装对象
     * @param response
     * @throws IOException
     */
    @RequestMapping("/update")
    public void update(FlowDefine fd, HttpServletResponse response) throws IOException{
        try {
            String msg = "";
            int count = 0;
            if ("".equals(fd.getName()) || fd.getName() == null) {
                msg += "请填写流程名称";
                count ++;
            } 
            if (fd.getStep() == null) {
                if (count > 0) {
                    msg += "和流程步骤";
                } else {
                    msg += "请填写流程步骤";
                }
                count ++;
            } 
            if (count > 0) {
                response.setContentType("text/html;charset=utf-8");
                response.getWriter().print(
                        "{\"success\": " + false + ", \"msg\": \"" + msg
                                + "\"}");
            }
            if (count == 0) {
                fd.setUpdatedAt(new Date());
                flowMangeService.update(fd);
                msg += "更新成功";
                response.setContentType("text/html;charset=utf-8");
                response.getWriter()
                        .print("{\"success\": " + true + ", \"msg\": \"" + msg
                                + "\"}");
            }
            response.getWriter().flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally{
            response.getWriter().close();
        }
    }
    
    /**
     *〈简述〉逻辑删除
     *〈详细描述〉
     * @author Ye MaoLin
     * @param ids 需要被删除的数据id字符串拼接
     * @return 列表页面
     * @throws Exception
     */
    @RequestMapping("/deleteSoft")
    public String deleteSoft(String ids, String purchaseTypeId) throws Exception{
        String[] idArr = ids.split(",");
        for (String id : idArr) {
            FlowDefine fd = new FlowDefine();
            fd.setId(id);
            List<FlowDefine> ls = flowMangeService.find(fd);
            if (ls != null && ls.size() > 0){
                ls.get(0).setIsDeleted(1);
                flowMangeService.update(ls.get(0));
            } else {
                throw new Exception("获取失败");
            }
        }
        return "redirect:list.html?purchaseTypeId="+purchaseTypeId;
    }
    
}
