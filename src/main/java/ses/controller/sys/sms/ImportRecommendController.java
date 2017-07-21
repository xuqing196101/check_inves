package ses.controller.sys.sms;

import java.io.IOException;
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

import ses.model.bms.DictionaryData;
import ses.model.bms.Role;
import ses.model.bms.User;
import ses.model.bms.Userrole;
import ses.model.oms.Orgnization;
import ses.model.sms.ImportRecommend;
import ses.service.bms.DictionaryDataServiceI;
import ses.service.bms.PreMenuServiceI;
import ses.service.bms.RoleServiceI;
import ses.service.bms.UserServiceI;
import ses.service.sms.ImportRecommendService;

import com.github.pagehelper.PageInfo;
import common.annotation.CurrentUser;
import common.constant.Constant;

/**
 * 版权：(C) 版权所有 
 * <简述>进口代理商控制层
 * <详细描述>进口代理商在支撑系统是以个登记功能，保存的同时需要给用户名和密码存到用户表中
 * @author   Song Biaowei
 * @version  
 * @since
 * @see
 */
@Controller
@Scope("prototype")
@RequestMapping("/importRecommend")
public class ImportRecommendController extends BaseSupplierController {
    /**
     * 定义常量2
     */
    private static final int NUMBER_TWO = 2;
    /**
     * 定义常量3
     */
    private static final int NUMBER_THREE = 3;
    /**
     * 定义常量5
     */
    private static final int NUMBER_FIVE = 5;
    /**
     * 进口代理商service层
     */
    @Autowired
    private ImportRecommendService importRecommendService;
    /**
     * 用户表service层
     */
    @Autowired
    private UserServiceI userService;
    /**
     * 字典表服务层
     */
    @Autowired
    private DictionaryDataServiceI dictionaryDataServiceI;

    /**
     * 角色服务层
     */
    @Autowired
    private RoleServiceI roleService;
    
    /**
     * 菜单服务层
     */
    @Autowired
    private PreMenuServiceI menuService;
    
    /**
     *〈简述〉进口代理商登记列表
     *〈详细描述〉
     * @author Song Biaowei
     * @param ir 进口代理商实体
     * @param request request
     * @param page 当前页
     * @param model 模型
     * @return String
     */
    @RequestMapping("list")
    public String registerStart(ImportRecommend ir, HttpServletRequest request, Integer page, Model model,@CurrentUser User user){
    	//权限验证 登陆状态 角色只能是资源服务中心
    	if(null!=user && "4".equals(user.getTypeName())){
	        List<ImportRecommend> irList = importRecommendService.selectByRecommend(ir, page == null ? 1 : page);
	        request.setAttribute("irList", new PageInfo<>(irList));
	        request.setAttribute("ir", ir);
    	}else{
    		model.addAttribute("error", "权限不足");
    	}
        return "ses/sms/import_recommend/list";
    }

    /**
     *〈简述〉打开新增页面
     *〈详细描述〉
     * @author Song Biaowei
     * @return String
     */
    @RequestMapping("/add")
    public String add(){
        return "ses/sms/import_recommend/add";
    }

    /**
     *〈简述〉保存进口代理商
     *〈详细描述〉保存进口代理商并且要存入到用户表中去
     * @author Song Biaowei
     * @param ir 进口代理商实体类
     * @param result 校验
     * @param request request
     * @param model 模型
     * @return String
     * @throws IOException 异常处理
     */
    @RequestMapping("/save")
    public String save(@Valid ImportRecommend ir, BindingResult result, HttpServletRequest request, Model model) throws IOException{
        if (result.hasErrors()) {
            List<FieldError> errors = result.getFieldErrors();
            for (FieldError fieldError:errors) {
                model.addAttribute("ERR_" + fieldError.getField(), fieldError.getDefaultMessage());
            }
            if (ir.getAddress().equals("-请选择-")) {
                model.addAttribute("ERR_address", "企业地址不能为空");
            }
            if (ir.getLoginName() != null) {
                List<User> users = userService.findByLoginName(ir.getLoginName());
                if (users.size() > 0){
                    model.addAttribute("ERR_loginName", "用户名已存在");
                }
            }
            model.addAttribute("ir", ir);
            return "ses/sms/import_recommend/add";
        }
        if (ir.getLoginName() != null){
            List<User> users = userService.findByLoginName(ir.getLoginName());
            if (users.size() > 0){
                model.addAttribute("ERR_loginName", "用户名已存在");
                model.addAttribute("ir", ir);
                return "ses/sms/import_recommend/add";
            }
        }
        if (ir.getAddress().equals("-请选择-")){
            model.addAttribute("ir", ir);
            model.addAttribute("ERR_address", "企业地址不能为空");
            return "ses/sms/import_recommend/add";
        }
        User user1 = (User) request.getSession().getAttribute("loginUser");
        ir.setCreatedAt(new Date());
        ir.setCreator(user1.getId());
        if (ir.getType() == 1){
            ir.setStatus((short) NUMBER_THREE);
        } else {
            ir.setStatus((short) 0);
        }
        importRecommendService.register(ir);
        //存到user表里面
        User user = new User();
        user.setLoginName(ir.getLoginName());
        user.setCreatedAt(new Date());
        user.setIsDeleted(0);
        user.setPassword(ir.getPassword());
        user.setTypeId(ir.getId());
        user.setAddress(ir.getAddress());
        user.setTypeId(ir.getId());
        //采购管理部门的id
        Orgnization org = new Orgnization();
        if (user1.getOrg() != null ){
            org.setId(user1.getOrg().getId());
        }
        user.setOrg(org);
        userService.save(user, null);
        Role role = new Role();
        role.setCode("IMPORT_AGENT_R");
        List<Role> listRole = roleService.find(role);
        if (listRole != null && listRole.size() > 0) {
            Userrole userrole = new Userrole();
            userrole.setRoleId(listRole.get(0));
            userrole.setUserId(user);
            /**给该用户初始化进口代理商角色*/
            userService.saveRelativity(userrole);
           //String[] roleIds = listRole.get(0).getId().split(",");
           //List<String> listMenu = menuService.findByRids(roleIds);
            /**给用户初始化进口代理商菜单权限*/
            /*for (String menuId : listMenu) {
                UserPreMenu upm = new UserPreMenu();
                PreMenu preMenu = menuService.get(menuId);
                upm.setPreMenu(preMenu);
                upm.setUser(user);
                userService.saveUserMenu(upm);
            }*/
        }
        return "redirect:list.html";
    }

    /**
     *〈简述〉修改进口代理商信息
     *〈详细描述〉
     * @author Song Biaowei
     * @param id 进口代理是id
     * @param model 模型
     * @return String
     */
    @RequestMapping("/edit")
    public String edit(String id, Model model){
        ImportRecommend ir = importRecommendService.findById(id);
        model.addAttribute("ir", ir);
        return "ses/sms/import_recommend/edit";
    }

    /**
     *〈简述〉更新进口代理商数据
     *〈详细描述〉
     * @author Song Biaowei
     * @param ir 进口代理商实体类
     * @param result 校验
     * @param model 模型
     * @param request request
     * @return String
     * @throws IOException 异常
     */
    @RequestMapping("/update")
    public String update(@Valid ImportRecommend ir, BindingResult result, Model model, HttpServletRequest request) throws IOException{
        if (result.hasErrors()){
            List<FieldError> errors = result.getFieldErrors();
            for (FieldError fieldError : errors) {
                model.addAttribute("ERR_" + fieldError.getField(), fieldError.getDefaultMessage());
            }
            if (ir.getAddress().equals("-请选择-")) {
                return "ses/sms/import_recommend/add";
            }
            model.addAttribute("ir", ir);
            return "ses/sms/import_recommend/edit";
        }
        if (ir.getAddress().equals("-请选择-")) {
            model.addAttribute("ir", ir);
            model.addAttribute("ERR_address", "企业地址不能为空");
            return "ses/sms/import_recommend/edit";
        }
        ir.setUpdatedAt(new Date());
        importRecommendService.update(ir);
        return "redirect:list.html";
    }

    /**
     *〈简述〉软删除进口代理商
     *〈详细描述〉
     * @author Song Biaowei
     * @param ids 进口代理商id
     * @return String
     */
    @RequestMapping("/delete_soft")
    public String delete(String ids) {
        String[] id = ids.split(",");
        for (String str : id) {
            ImportRecommend ir = importRecommendService.findById(str);
            ir.setStatus((short) NUMBER_FIVE);
            importRecommendService.update(ir);
        }
        return "redirect:list.html";
    }
    
    /**
     *〈简述〉展示进口代理商信息
     *〈详细描述〉
     * @author Song Biaowei
     * @param id 主键
     * @param model 模型
     * @return String
     */
    @RequestMapping("/show")
    public String show(String id, Model model){
        ImportRecommend ir = importRecommendService.findById(id);
        model.addAttribute("ir", ir);
        return "ses/sms/import_recommend/show";
    }

    /**
     *〈简述〉暂停正式代理商
     *〈详细描述〉
     * @author Song Biaowei
     * @param id 主键
     * @param model 模型
     * @return String
     */
    @RequestMapping("/zanting")
    public String zanting(String id, Model model){
        ImportRecommend ir = importRecommendService.findById(id);
        if (ir.getType() == 1) {
            ir.setStatus((short) NUMBER_TWO);
        }
        importRecommendService.update(ir);
        model.addAttribute("ir", ir);
        return "redirect:list.html";
    }

    /**
     *〈简述〉启用正式代理商
     *〈详细描述〉
     * @author Song Biaowei
     * @param id 主键
     * @param model 模型
     * @return String
     */
    @RequestMapping("/qiyong")
    public String qiyong(String id, Model model){
        ImportRecommend ir = importRecommendService.findById(id);
        if (ir.getType() == 1) {
            ir.setStatus((short) NUMBER_THREE);
        }
        importRecommendService.update(ir);
        model.addAttribute("ir", ir);
        return "redirect:list.html";
    }

    /**
     *〈简述〉激活临时代理商
     *〈详细描述〉
     * @author Song Biaowei
     * @param request request
     * @param id 逐渐
     * @param model 模型
     * @return String
     * @throws IOException 异常处理
     */
    @RequestMapping("/jihuo_add")
    public String jihuo(HttpServletRequest request, String id, Model model) throws IOException{
        model.addAttribute("id", id);
        DictionaryData dd = new DictionaryData();
        dd.setCode("IMPORT_RECOMMEND");
        List<DictionaryData > list = dictionaryDataServiceI.find(dd);
        request.getSession().setAttribute("sysKey", Constant.SUPPLIER_SYS_KEY);
        if (list.size() > 0){
            model.addAttribute("typeId", list.get(0).getId());
        }
        return "ses/sms/import_recommend/jihuo";
    }

    /**
     *〈简述〉保存激活文件
     *〈详细描述〉
     * @author Song Biaowei
     * @param ir 进口代理商实体
     * @param id 主键
     * @param model 模型
     * @param request request
     * @return String
     * @throws IOException 异常处理
     */
    @RequestMapping("/jihuo_save")
    public String jihuoSave(ImportRecommend ir, String id, Model model, HttpServletRequest request) throws IOException{
        ImportRecommend ir1 = importRecommendService.findById(id);
        ir1.setUseCount((long) 1);
        ir1.setAttachment(ir.getAttachment());
        ir1.setStatus((short) 1);
        importRecommendService.update(ir1);
        model.addAttribute("ir", ir);
        return "redirect:list.html";
    }
}
