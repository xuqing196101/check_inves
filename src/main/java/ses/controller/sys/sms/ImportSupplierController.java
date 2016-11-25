package ses.controller.sys.sms;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.RequestMapping;

import ses.model.bms.Todos;
import ses.model.bms.User;
import ses.model.sms.ImportSupplierWithBLOBs;
import ses.service.bms.TodosService;
import ses.service.sms.ImportSupplierService;
import ses.util.PropUtil;
import ses.util.ValidateUtils;

import com.github.pagehelper.PageInfo;

/**
 * 版权：(C) 版权所有 
 * <简述>进口供应商控制层
 * <详细描述>
 * @author   Song Biaowei
 * @version  
 * @since
 * @see
 */
@Controller
@Scope("prototype")
@RequestMapping("/importSupplier")
public class ImportSupplierController {
    /**
     * 进口供应商服务层
     */
    @Autowired
    private ImportSupplierService importSupplierService;
    /**
     * 待办任务服务层
     */
    @Autowired
    private TodosService todosService;

    /**
     *〈简述〉进口供应商列表
     *〈详细描述〉
     * @author Song Biaowei
     * @param is 进口供应商实体
     * @param supName 名称查询条件 
     * @param supType 类型查询条件
     * @param request request
     * @param page 当前页
     * @param model 模型
     * @return String
     */
    @RequestMapping("list")
    public String registerStart(ImportSupplierWithBLOBs is, String supName, String supType, HttpServletRequest request, Integer page, Model model){
        if (supName != null && !supName.equals("")) {
            is.setName(supName);
        }
        if (supType != null && !supType.equals("")) {
            is.setSupplierType(supType);
        }
        List<ImportSupplierWithBLOBs> isList = importSupplierService.selectByFsInfo(is, page == null ? 1 : page);
        request.setAttribute("isList", new PageInfo<>(isList));
        model.addAttribute("name", is.getName());
        model.addAttribute("supplierType", is.getSupplierType());
        return "ses/sms/import_supplier/list";
    }
    
    /**
     *〈简述〉修改进口供应商信息
     *〈详细描述〉
     * @author Song Biaowei
     * @param is 进口供应商实体
     * @param model 模型
     * @param request request
     * @return String
     */
    @RequestMapping("edit")
    public String edit(ImportSupplierWithBLOBs is, Model model, HttpServletRequest request){
        ImportSupplierWithBLOBs importSupplierWithBLOBs = importSupplierService.selectByPrimaryKey(is);
        model.addAttribute("is", importSupplierWithBLOBs);
        return "ses/sms/import_supplier/edit";
    }
    
    /**
     *〈简述〉进口供应商审核页面
     *〈详细描述〉
     * @author Song Biaowei
     * @param is 进口供应商实体
     * @param model 模型
     * @param request request
     * @return String
     */
    @RequestMapping(value = "auditShow")
    public String auditShow(ImportSupplierWithBLOBs is, Model model, HttpServletRequest request){
        ImportSupplierWithBLOBs importSupplierWithBLOBs = importSupplierService.selectByPrimaryKey(is);
        model.addAttribute("is", importSupplierWithBLOBs);
        return "ses/sms/import_supplier/audit";
    }

    /**
     *〈简述〉进口供应商审核完成通过或者退回
     *〈详细描述〉
     * @author Song Biaowei
     * @param is 进口供应商实体
     * @param model 模型
     * @param request request
     * @return String
     */
    @RequestMapping(value = "audit")
    public String audit(ImportSupplierWithBLOBs is, Model model, HttpServletRequest request){
        ImportSupplierWithBLOBs importSupplierWithBLOBs = importSupplierService.selectByPrimaryKey(is);
        model.addAttribute("is", importSupplierWithBLOBs);
        importSupplierService.updateRegisterInfo(is);
        if (is.getStatus() != 0){
            todosService.updateIsFinish("importSupplier/auditShow.html?id=" + is.getId());
        }
        return "redirect:/login/home.html";
    }
    
    /**
     *〈简述〉供应商详细信息展示
     *〈详细描述〉
     * @author Song Biaowei
     * @param is 实体类
     * @param model 模型
     * @param request request
     * @return String
     */
    @RequestMapping("show")
    public String show(ImportSupplierWithBLOBs is, Model model, HttpServletRequest request){
        ImportSupplierWithBLOBs importSupplierWithBLOBs = importSupplierService.selectByPrimaryKey(is);
        model.addAttribute("is", importSupplierWithBLOBs);
        return "ses/sms/import_supplier/show";
    }
    
    /**
     *〈简述〉软删除
     *〈详细描述〉
     * @author Song Biaowei
     * @param ids 主键字符串，为了批量删除
     * @return String
     */
    @RequestMapping("delete")
    public String delete(String ids) {
        String[] id = ids.split(",");
        for (String str : id) {
            importSupplierService.delete(str);
        }
        return "redirect:list.html";
    }
    
    /**
     *〈简述〉金扣供应商更新
     *〈详细描述〉
     * @author Song Biaowei
     * @param is 实体类
     * @param result 校验
     * @param model 模型
     * @param request request
     * @return Stirng
     * @throws IOException 异常处理
     */
    @RequestMapping("update")
    public String update(@Valid ImportSupplierWithBLOBs is, BindingResult result, Model model, HttpServletRequest request) throws IOException{
        if (result.hasErrors()) {
            List<FieldError> errors = result.getFieldErrors();
            for (FieldError fieldError : errors) {
                model.addAttribute("ERR_" + fieldError.getField(), fieldError.getDefaultMessage());
            }
            model.addAttribute("is", is);
            if (!ValidateUtils.Zipcode(is.getPostCode() + "")) {
                model.addAttribute("ERR_postCode", "请输入正确的邮编");
            }
            if (!ValidateUtils.Mobile(is.getTelephone() + "")) {
                model.addAttribute("ERR_telephone", "请输入正确的手机号码");
            }
            if ("-请选择-".equals(is.getAddress())) {
                model.addAttribute("is", is);
                model.addAttribute("ERR_address", "请输入地址");
                return "ses/sms/import_supplier/edit";
            }
            return "ses/sms/import_supplier/edit";
        }
        if ("-请选择-".equals(is.getAddress())) {
            model.addAttribute("is", is);
            model.addAttribute("ERR_address", "请输入地址");
            return "ses/sms/import_supplier/edit";
        }

        is.setUpdatedAt(new Timestamp(new Date().getTime()));
        importSupplierService.updateRegisterInfo(is);
        return "redirect:list.html";
    }
    
    /**
     *〈简述〉供应商登记
     *〈详细描述〉
     * @author Song Biaowei
     * @param is 实体类
     * @param request request
     * @param model 模型
     * @return String
     */
    @RequestMapping("register")
    public String register(ImportSupplierWithBLOBs is, HttpServletRequest request, Model model){
        //保存基本信息返回 id作为外键保存到user用户表里面去
        return "ses/sms/import_supplier/register";
    }
    
    /**
     *〈简述〉供应商保存
     *〈详细描述〉
     * @author Song Biaowei
     * @param is 实体类
     * @param result 校验
     * @param request request
     * @param model 模型
     * @return String
     * @throws IOException 异常处理
     */
    @RequestMapping("registerEnd")
    public String registerEnd(@Valid ImportSupplierWithBLOBs is, BindingResult result, HttpServletRequest request, Model model) throws IOException{
        if (result.hasErrors()){
            List<FieldError> errors = result.getFieldErrors();
            for (FieldError fieldError:errors){
                model.addAttribute("ERR_" + fieldError.getField(), fieldError.getDefaultMessage());
            }
            model.addAttribute("is", is);
            if (!ValidateUtils.Zipcode(is.getPostCode() + "")){
                model.addAttribute("ERR_postCode", "请输入正确的邮编");
            }
            if (!ValidateUtils.Mobile(is.getTelephone() + "")){
                model.addAttribute("ERR_telephone", "请输入正确的手机号码");
            }
            if ("-请选择-".equals(is.getAddress())){
                model.addAttribute("is", is);
                model.addAttribute("ERR_address", "请输入地址");
                return "ses/sms/import_supplier/edit";
            }
            return "ses/sms/import_supplier/register";
        }
        if ("-请选择-".equals(is.getAddress())){
            model.addAttribute("is", is);
            model.addAttribute("ERR_address", "请输入地址");
            return "ses/sms/import_supplier/register";
        }
        
        is.setStatus((short) 0);
        is.setCreatedAt(new Timestamp(new Date().getTime()));
        User user1 = (User) request.getSession().getAttribute("loginUser");
        is.setCreatorId(user1.getId());
        is.setOrgId(user1.getOrg().getId());
        importSupplierService.register(is);
        Todos todo = new Todos();
        //自己的id
        todo.setSenderId(user1.getId());
        //代办人id
        todo.setOrgId(user1.getOrg().getId());
        //权限Id
        todo.setPowerId(PropUtil.getProperty("gysdb"));
        //待办类型 供应商
        todo.setUndoType((short) 1);
        //标题
        todo.setName("进口供应商审核");
        //逻辑删除 0未删除 1已删除
        todo.setIsDeleted((short) 0);
        todo.setCreatedAt(new Date());
        todo.setUrl("importSupplier/auditShow.html?id=" + is.getId());
        todosService.insert(todo);
        return "redirect:list.html";
    }
}
