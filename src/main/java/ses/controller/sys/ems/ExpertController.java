package ses.controller.sys.ems;

import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageInfo;

import bss.model.ppms.Packages;
import bss.model.ppms.Project;
import bss.model.ppms.SaleTender;
import bss.model.ppms.ext.ProjectExt;
import bss.model.prms.PackageExpert;
import bss.model.prms.ReviewProgress;
import bss.service.ppms.PackageService;
import bss.service.ppms.ProjectService;
import bss.service.ppms.SaleTenderService;
import bss.service.prms.PackageExpertService;
import bss.service.prms.ReviewProgressService;
import common.constant.Constant;
import common.constant.StaticVariables;
import ses.model.bms.Area;
import ses.model.bms.Category;
import ses.model.bms.CategoryTree;
import ses.model.bms.DictionaryData;
import ses.model.bms.PreMenu;
import ses.model.bms.Role;
import ses.model.bms.User;
import ses.model.bms.UserPreMenu;
import ses.model.bms.Userrole;
import ses.model.ems.Expert;
import ses.model.ems.ExpertAttachment;
import ses.model.ems.ExpertAudit;
import ses.model.ems.ExpertCategory;
import ses.model.ems.ProjectExtract;
import ses.model.oms.PurchaseDep;
import ses.model.sms.Quote;
import ses.service.bms.AreaServiceI;
import ses.service.bms.CategoryService;
import ses.service.bms.DictionaryDataServiceI;
import ses.service.bms.NoticeDocumentService;
import ses.service.bms.PreMenuServiceI;
import ses.service.bms.RoleServiceI;
import ses.service.bms.UserServiceI;
import ses.service.ems.ExpertAttachmentService;
import ses.service.ems.ExpertAuditService;
import ses.service.ems.ExpertCategoryService;
import ses.service.ems.ExpertService;
import ses.service.ems.ProjectExtractService;
import ses.service.oms.PurchaseOrgnizationServiceI;
import ses.service.sms.SupplierQuoteService;
import ses.util.DictionaryDataUtil;
import ses.util.PropertiesUtil;
import ses.util.WfUtil;
import ses.util.WordUtil;

@Controller
@RequestMapping("/expert")
public class ExpertController {
    @Autowired
    private UserServiceI userService;// 用户管理
    @Autowired
    private ExpertService service;// 专家管理
    @Autowired
    private PurchaseOrgnizationServiceI purchaseOrgnizationService;// 采购机构管理
    @Autowired
    private ExpertAuditService expertAuditService; // 审核信息管理
    @Autowired
    private ExpertCategoryService expertCategoryService;// 专家类别中间表
    @Autowired
    private ExpertAttachmentService attachmentService;// 附件管理
    @Autowired
    private PackageExpertService packageExpertService;// 专家项目包 关联表
    @Autowired
    private PackageService packageService;// 包 service
    @Autowired
    private ProjectService projectService;// 项目service
    @Autowired
    private SaleTenderService saleTenderService;// 供应商查询
    @Autowired
    private ReviewProgressService reviewProgressService;// 进度
    @Autowired
    private DictionaryDataServiceI dictionaryDataServiceI;// TypeId
    @Autowired
    private SupplierQuoteService supplierQuoteService;// 供应商报价
    @Autowired
    private NoticeDocumentService noticeDocumentService;
    @Autowired
    private AreaServiceI areaServiceI;// 地区查询
    @Autowired
    private PreMenuServiceI menuService;// 地区查询
    @Autowired
    private RoleServiceI roleService;// 地区查询
    @Autowired
    private ProjectExtractService projectExtractService;//是否被抽取查询
    @Autowired
    private CategoryService categoryService;//品目

    /**
     * 
     * @Title: toExpert
     * @author lkzx
     * @date 2016年8月31日 下午7:04:16
     * @Description: TODO 跳转到评审专家注册页面
     * @param @return
     * @return String
     */
    @RequestMapping(value = "/toExpert")
    public String toExpert(Model model) {
        // 查询数据字典中的专家来源配置数据
        List<DictionaryData> lyTypeList = DictionaryDataUtil.find(12);
        model.addAttribute("lyTypeList", lyTypeList);        
        return "ses/ems/expert/expert_register";
    }

    /**
     * 
     * @Title: view
     * @author ShaoYangYang
     * @date 2016年9月29日 上午11:03:50
     * @Description: TODO 查看专家信息
     * @param id
     * @param model
     * @param @return
     * @return String
     */
    @RequestMapping("/view")
    public String view(@RequestParam("id") String id, Model model) {
        // 查询出专家
        Expert expert = service.selectByPrimaryKey(id);
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("id", expert.getPurchaseDepId());
        map.put("typeName", "1");
        // 查询出采购机构
        List<PurchaseDep> depList = purchaseOrgnizationService
                .findPurchaseDepList(map);
        if (depList != null && depList.size() > 0) {
            PurchaseDep purchaseDep = depList.get(0);
            model.addAttribute("purchase", purchaseDep);
        }
        // 查询数据字典中的证件类型配置数据
        List<DictionaryData> idTypeList = DictionaryDataUtil.find(9);
        model.addAttribute("idTypeList", idTypeList);
        // 查询数据字典中的政治面貌配置数据
        List<DictionaryData> zzList = DictionaryDataUtil.find(10);
        model.addAttribute("zzList", zzList);
        // 查询数据字典中的最高学历配置数据
        List<DictionaryData> xlList = DictionaryDataUtil.find(11);
        model.addAttribute("xlList", xlList);
        // 查询数据字典中的专家来源配置数据
        List<DictionaryData> lyTypeList = DictionaryDataUtil.find(12);
        model.addAttribute("lyTypeList", lyTypeList);
        // 查询数据字典中的性别配置数据
        List<DictionaryData> sexList = DictionaryDataUtil.find(13);
        model.addAttribute("sexList", sexList);
        // 产品类型数据字典
        List<DictionaryData> spList = DictionaryDataUtil.find(6);
        model.addAttribute("spList", spList);
        // 货物类型数据字典
        List<DictionaryData> hwList = DictionaryDataUtil.find(8);
        model.addAttribute("hwList", hwList);
        // 经济类型数据字典
        List<DictionaryData> jjTypeList = DictionaryDataUtil.find(19);
        model.addAttribute("jjList", jjTypeList);
        // 专家系统key
        Integer expertKey = Constant.EXPERT_SYS_KEY;
        Map<String, Object> typeMap = getTypeId();
        // typrId集合
        model.addAttribute("typeMap", typeMap);
        // 业务id就是专家id
        model.addAttribute("sysId", id);
        // Constant.EXPERT_SYS_VALUE;
        model.addAttribute("expertKey", expertKey);
        model.addAttribute("expert", expert);

        return "ses/ems/expert/view";
    }

    /**
     * 
     * @Title: toRegisterNotice
     * @author lkzx
     * @date 2016年8月31日 下午7:04:16
     * @Description: TODO 跳转到评审专家注册须知页面
     * @param @return
     * @return String
     */
    @RequestMapping(value = "/toRegisterNotice")
    public String toRegisterNotice(Model model) {
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("docType", "专家须知文档");
        String doc = noticeDocumentService.findDocByMap(param);
        model.addAttribute("doc", doc);
        return "ses/ems/expert/register_notice";
    }

    /**
     * 
     * @Title: register
     * @author lkzx
     * @date 2016年8月31日 下午6:36:19
     * @Description: TODO 注册评审专家用户
     * @param expert
     * @param model
     * @return String
     */
    @RequestMapping("/register")
    public String register(User user, HttpSession session, Model model,
            HttpServletRequest request, @RequestParam String token2,
            RedirectAttributes attr, String expertsFrom) {
        Object tokenValue = session.getAttribute("tokenSession");
        if (tokenValue != null && tokenValue.equals(token2)) {
            // 正常提交
            session.removeAttribute("tokenSession");
            // 判断用户名密码是否合法
            String loginName = user.getLoginName();
            String password = user.getPassword();
            String regex = "[`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]";
            Pattern p = Pattern.compile(regex);
            Pattern p2 = Pattern.compile("[\u4e00-\u9fa5]");
            Matcher m = p.matcher(loginName);
            Matcher m2 = p2.matcher(loginName);
            Matcher matcher = p.matcher(password);
            Matcher matcher2 = p2.matcher(password);
            if (loginName.trim().length() < 3 || m.find() || m2.find()) {
                model.addAttribute("message", "用户名不符合规则");
                return "ems/expert/expert_register";
            } else if (password.trim().length() < 6 || matcher.find()
                    || matcher2.find()) {
                model.addAttribute("message", "密码不符合规则");
                return "ems/expert/expert_register";
            }
            user.setId(WfUtil.createUUID());
            request.setAttribute("user", user);
            // 查找用户类型(字段被移除)
            // String userType = DictionaryDataUtil.getId("EXPERT_U");
            // user.setTypeName(userType);
            String expertId = WfUtil.createUUID();
            user.setTypeId(expertId);
            userService.save(user, null);
            Expert expert = new Expert();
            expert.setId(expertId);
            expert.setMobile(user.getMobile());
            expert.setExpertsFrom(expertsFrom);
            service.insertSelective(expert);
            Role role = new Role();
            role.setCode("EXPERT_R");
            List<Role> listRole = roleService.find(role);
            if (listRole != null && listRole.size() > 0) {
                Userrole userrole = new Userrole();
                userrole.setRoleId(listRole.get(0));
                userrole.setUserId(user);
                /** 删除用户之前的菜单权限*/
                UserPreMenu userPreMenu = new UserPreMenu();
                userPreMenu.setUser(user);
                userService.deleteUserMenu(userPreMenu);
                /** 删除用户之前的角色信息*/
                /** 给该用户初始化专家角色 */
                userService.saveRelativity(userrole);
                String[] roleIds = listRole.get(0).getId().split(",");
                List<String> listMenu = menuService.findByRids(roleIds);
                /** 给用户初始化专家菜单权限 */
                for (String menuId : listMenu) {
                    UserPreMenu upm = new UserPreMenu();
                    PreMenu preMenu = menuService.get(menuId);
                    upm.setPreMenu(preMenu);
                    upm.setUser(user);
                    userService.saveUserMenu(upm);
                }
            }
            attr.addAttribute("userId", user.getId());
            return "redirect:toAddBasicInfo.html";
        }
        // 重复提交
        else {
            attr.addAttribute("userId", user.getId());
            return "redirect:toAddBasicInfo.html";
        }
    }

    /**
     * 
     * @Title: toAddBasicInfo
     * @author ShaoYangYang
     * @date 2016年11月23日 下午7:27:29
     * @Description: TODO
     * @param userId
     * @param request
     * @param response
     * @param model
     * @return String
     */

    @RequestMapping("/toAddBasicInfo")
    public String toAddBasicInfo(@RequestParam("userId") String userId,
            HttpServletRequest request, HttpServletResponse response,
            Model model) {
        model.addAttribute("userId", userId);
        User user = userService.getUserById(userId);
        String typeId = user.getTypeId();
        // 生成专家id
        String expertId = "";
        int flag = 0;
        // 暂存 或退回后重新填写
        Expert expert = service.selectByPrimaryKey(typeId);
        model.addAttribute("expert", expert);
        String stepNumber;
        if ("".equals(expert.getStepNumber()) || expert.getStepNumber() == null) {
            stepNumber = "one";
        } else {
            stepNumber = expert.getStepNumber();
        }
        if (expert != null)
            expertId = expert.getId();
        // 判断已提交 未审核的数据 跳转到查看页面
        if (expert != null && expert.getIsSubmit().equals("1")
                && expert.getStatus().equals("0")) {
            // 已提交未审核数据
            flag = 1;
        }
        Map<String, Object> errorMap = service.Validate(expert, 3, null);
        expert.setExpertsFrom(dictionaryDataServiceI.getDictionaryData(expert.getExpertsFrom()).getCode());
        model.addAttribute("expert", expert);
        model.addAttribute("errorMap", errorMap);
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("typeName", "1");
        List<PurchaseDep> purchaseDepList = purchaseOrgnizationService
                .findPurchaseDepList(map);
        // 专家系统key
        Integer expertKey = Constant.EXPERT_SYS_KEY;
        // 获取各个附件类型id集合
        Map<String, Object> typeMap = getTypeId();
        // 判断是否有合同书和申请表的附件
        String att = isAttachment(expertId, typeMap);
        // 查询数据字典中的证件类型配置数据
        List<DictionaryData> idTypeList = DictionaryDataUtil.find(9);
        model.addAttribute("idTypeList", idTypeList);
        // 查询数据字典中的政治面貌配置数据
        List<DictionaryData> zzList = DictionaryDataUtil.find(10);
        model.addAttribute("zzList", zzList);
        // 查询数据字典中的最高学历配置数据
        List<DictionaryData> xlList = DictionaryDataUtil.find(11);
        model.addAttribute("xlList", xlList);
        // 查询数据字典中的专家来源配置数据
        List<DictionaryData> lyTypeList = DictionaryDataUtil.find(12);
        model.addAttribute("lyTypeList", lyTypeList);
        // 如果是外网用户则不可以选择专家来源为军队
        //String ipAddress = request.getRemoteAddr();
        //int type = IpAddressUtil.validateIpAddress(ipAddress);
        PropertiesUtil config = new PropertiesUtil("config.properties");
        String type = config.getString("ipAddressType");
        // 如果是外网用户,则删除军队这个选项
        if ("1".equals(type)) {
            for (int i = 0; i < lyTypeList.size(); i++) {
                // 循环判断如果是军队则remove
                if ("军队".equals(lyTypeList.get(i).getName())) {
                    lyTypeList.remove(i);
                }
            }
        }
        // 查询数据字典中的性别配置数据
        List<DictionaryData> sexList = DictionaryDataUtil.find(13);
        model.addAttribute("sexList", sexList);
        // 产品类型数据字典
        List<DictionaryData> spList = DictionaryDataUtil.find(6);
        model.addAttribute("spList", spList);
        // 货物类型数据字典
        List<DictionaryData> hwList = DictionaryDataUtil.find(8);
        model.addAttribute("hwList", hwList);
        // 经济类型数据字典
        List<DictionaryData> jjTypeList = DictionaryDataUtil.find(19);
        model.addAttribute("jjList", jjTypeList);
        // 学位类型数据字典
        List<DictionaryData> xwTypeList = DictionaryDataUtil.find(21);
        model.addAttribute("xwList", xwTypeList);
        model.addAttribute("att", att);
        // typrId集合
        model.addAttribute("typeMap", typeMap);

        model.addAttribute("sysId", expertId);
        // Constant.EXPERT_SYS_VALUE;
        model.addAttribute("expertKey", expertKey);
        model.addAttribute("purchase", purchaseDepList);
        model.addAttribute("user", user);
        if ("six".equals(stepNumber)) {
            showCategory(expert, model);
        }
        if ("3".equals(expert.getStatus())) {
            // 如果状态为退回修改则查询没通过的字段 
            ExpertAudit expertAudit = new ExpertAudit();
            expertAudit.setExpertId(expertId);
            expertAudit.setSuggestType(stepNumber);
            List<ExpertAudit> auditList = expertAuditService.selectFailByExpertId(expertAudit);
            // 所有的不通过字段的名字
            StringBuffer errorField = new StringBuffer();
            for (ExpertAudit audit : auditList) {
                errorField.append(audit.getAuditField() + ",");
            }
            model.addAttribute("errorField", errorField);
        }
        return "ses/ems/expert/basic_info_"+stepNumber;
    }

    /**
     *〈简述〉
     * 专家注册新加步骤:产品目录
     *〈详细描述〉
     * @author WangHuijie
     * @return
     */
    public void showCategory(Expert expert, Model model){
        List<DictionaryData> allCategoryList = new ArrayList<DictionaryData>();
        // 获取专家类别
        List<String> allTypeId = new ArrayList<String>();
        for (String id : expert.getExpertsTypeId().split(",")) {
            allTypeId.add(id);
        }
        a:for (int i = 0; i < allTypeId.size(); i++ ) {
            DictionaryData dictionaryData = dictionaryDataServiceI.getDictionaryData(allTypeId.get(i));
            if (dictionaryData != null && dictionaryData.getName().contains("经济")) {
                allTypeId.remove(i);
                continue a;
            };
            allCategoryList.add(dictionaryData);
        }
        model.addAttribute("allCategoryList", allCategoryList);
    }
    
    /**
     *〈简述〉
     * 查询不通过理由
     *〈详细描述〉
     * @author WangHuijie
     * @param expertId
     * @param auditField
     * @return
     */
    @RequestMapping(value = "/findAuditReason",produces="application/json;charset=UTF-8")
    @ResponseBody
    public String findErrorReason(ExpertAudit expertAudit){
        List<ExpertAudit> audit = expertAuditService.selectFailByExpertId(expertAudit);
        return JSON.toJSONString(audit.get(0));
    }
    
    @RequestMapping(value = "getCategory", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String getCategory(String expertId, String id){
        List<CategoryTree> allCategories = new ArrayList<CategoryTree>();
        DictionaryData parent = dictionaryDataServiceI.getDictionaryData(id);    
        CategoryTree ct = new CategoryTree();
        ct.setName(parent.getName());
        ct.setId(parent.getId());
        ct.setIsParent("true");
        // 判断是否被选中
        /*List<ExpertCategory> allCategory = expertCategoryService.getListByExpertId(expertId);
        for (ExpertCategory expertCategory : allCategory) {
            String parentId = categoryService.selectByPrimaryKey(expertCategory.getCategoryId()).getParentId();
            if (parentId != null && parentId.equals(ct.getId())) {
                ct.setChecked(true);
            }
        }
        allCategories.add(ct);*/
        allCategories.add(ct);
        // 递归查询出所有节点
        List<Category> categoryTree = getCategoryTree(ct.getId());
        // 遍历所有节点添加到list中
        for (Category c : categoryTree) {
            List<Category> list1 = categoryService.findTreeByPid(c.getId());
            CategoryTree ct1 = new CategoryTree();
            ct1.setName(c.getName());
            ct1.setParentId(c.getParentId());
            ct1.setId(c.getId());
            
            ExpertCategory ec = expertCategoryService.getExpertCategory(expertId, c.getId());
            if (ec != null){
                ct1.setChecked(true);
            }
            // 设置是否为父级
            if (!list1.isEmpty()) {
                ct1.setIsParent("true");
            } else {
                ct1.setIsParent("false");
            }
            // 设置是否回显
            /*for (ExpertCategory category : allCategory) {
                if (category.getCategoryId() != null) {
                    if (category.getCategoryId().equals(c.getId())) {
                        ct1.setChecked(true);
                    }
                }
            }*/
            allCategories.add(ct1);
        }
        return JSON.toJSONString(allCategories);
    }
        /*if(id == null && categoryIds != null) {
            DictionaryData type = dictionaryDataServiceI.getDictionaryData(categoryIds);
            CategoryTree ct = new CategoryTree();
            ct.setName(type.getName().substring(0, type.getName().length() - 2));
            ct.setId(type.getId());
            ct.setIsParent("true");
            // 判断是否被选中
            List<ExpertCategory> allCategory = expertCategoryService.getListByExpertId(expertId);
            for (ExpertCategory expertCategory : allCategory) {
                String parentId = categoryService.selectByPrimaryKey(expertCategory.getCategoryId()).getParentId();
                if (parentId != null && parentId.equals(ct.getId())) {
                    ct.setChecked(true);
                }
            }
            allList.add(ct);
        } else {
            List<ExpertCategory> expertCategory = expertCategoryService.getListByExpertId(expertId);
            List<Category> list = categoryService.findTreeByPid(id);
            for (Category c : list) {
                List<Category> list1 = categoryService.findTreeByPid(c.getId());
                CategoryTree ct1 = new CategoryTree();
                ct1.setName(c.getName());
                ct1.setId(c.getId());
                // 设置是否为父级
                if (!list1.isEmpty()) {
                    ct1.setIsParent("true");
                } else {
                    ct1.setIsParent("false");
                }
                // 设置是否回显
                for (ExpertCategory category : expertCategory) {
                    if (category.getCategoryId() != null) {
                        if (category.getCategoryId().equals(c.getId())) {
                            ct1.setChecked(true);
                        }
                    }
                }
                allList.add(ct1);
            }*/
    
    /**
     *〈简述〉
     * 递归查询所有Tree节点
     *〈详细描述〉
     * @author WangHuijie
     * @param id
     * @return
     */
    public List<Category> getCategoryTree(String id){
        List<Category> childList = new ArrayList<Category>();
        List<Category> list = categoryService.findTreeByStatus(id,StaticVariables.CATEGORY_PUBLISH_STATUS);
        childList.addAll(list);
        for (Category cate : list) {
            childList.addAll(getCategoryTree(cate.getId()));
        }
        return childList;
    }
    
    @RequestMapping(value = "getAllCategory", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String getAllCategory(String expertId){
        Expert expert = service.selectByPrimaryKey(expertId);
        List<DictionaryData> allCategoryList = new ArrayList<DictionaryData>();
        List<String> allTypeId = new ArrayList<String>();
        for (String id : expert.getExpertsTypeId().split(",")) {
            allTypeId.add(id);
        }
        a:for (int i = 0; i < allTypeId.size(); i++ ) {
            DictionaryData dictionaryData = dictionaryDataServiceI.getDictionaryData(allTypeId.get(i));
            if (dictionaryData.getName().contains("经济")) {
                allTypeId.remove(i);
                continue a;
            };
            allCategoryList.add(dictionaryData);
        }
        return JSON.toJSONString(allCategoryList);
    }
    
    /**
     *〈简述〉
     * 判断提交审核后有没有超过45天以及查询初审机构信息
     *〈详细描述〉
     * @author WangHuijie
     * @param expertId
     * @return
     * @throws Exception 
     */
    @RequestMapping(value = "validateAuditTime", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String validateAuditTime(String userId) throws Exception{
        HashMap<String, Object> allInfo = new HashMap<String, Object>();
        // 根据userId查询出Expert
        Expert expert = service.selectByPrimaryKey(userService.getUserById(userId).getTypeId());   
        Date submitDate = expert.getUpdatedAt();
        allInfo.put("submitDate", new SimpleDateFormat("yyyy年MM月dd日").format(submitDate));
        // 判断有没有超过45天
        String isok;
        int betweenDays = service.daysBetween(submitDate);
        if (betweenDays > 45) {
            isok = "0";
        } else {
            isok = "1";
        }
        allInfo.put("isok", isok);
        // 查询初审机构信息
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("id", expert.getPurchaseDepId());
        map.put("typeName", "1");
        List<PurchaseDep> depList = purchaseOrgnizationService.findPurchaseDepList(map);
        if (depList != null && depList.size() > 0) {
            PurchaseDep purchaseDep = depList.get(0);
            allInfo.put("contact", purchaseDep.getContact() == null ? "暂无" : purchaseDep.getContact());
            allInfo.put("contactTelephone", purchaseDep.getContactTelephone() == null ? "暂无" : purchaseDep.getContactTelephone());
        }
        return JSON.toJSONString(allInfo);
    }
    
    
    @RequestMapping(value = "showJiGou", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String showJiGou(String pId, String zId) {
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("typeName", "1");
        map.put("provinceId", pId);
        map.put("cityId", zId);
        List<PurchaseDep> purchaseDepList = purchaseOrgnizationService
                .findPurchaseDepList(map);
        return JSON.toJSONString(purchaseDepList);
    }
    
    /**
     * 
     * @Title: isAttachment
     * @author ShaoYangYang
     * @date 2016年11月9日 下午3:02:58
     * @Description: TODO 判断是否有合同书和申请表的附件
     * @param expertId
     * @param typeMap
     * @param @return
     * @return String
     */
    private String isAttachment(String expertId, Map<String, Object> typeMap) {
        Map<String, Object> mapAttachment = new HashMap<>();
        mapAttachment.put("isDeleted", 0);
        mapAttachment.put("businessId", expertId);
        mapAttachment.put("typeId", typeMap.get("EXPERT_CONTRACT_TYPEID"));
        List<ExpertAttachment> attList = attachmentService
                .selectListByMap(mapAttachment);
        Map<String, Object> mapAttachment2 = new HashMap<>();
        mapAttachment2.put("isDeleted", 0);
        if (StringUtils.isEmpty(expertId)) {
            return "2";
        }
        mapAttachment2.put("businessId", expertId);
        mapAttachment2.put("typeId", typeMap.get("EXPERT_APPLICATION_TYPEID"));
        List<ExpertAttachment> attList2 = attachmentService
                .selectListByMap(mapAttachment2);
        if ((attList != null && attList.size() > 0)
                || (attList2 != null && attList2.size() > 0)) {
            // 有附件为1
            return "1";
        } else {
            // 没有附件为2
            return "2";
        }

    }

    /**
     * 
     * @Title: getTypeId
     * @author ShaoYangYang
     * @date 2016年11月9日 下午2:32:38
     * @Description: TODO 封装附件类型
     * @param @return
     * @return Map<String,Object>
     */
    private Map<String, Object> getTypeId() {
        DictionaryData dd = new DictionaryData();
        Map<String, Object> typeMap = new HashMap<>();
        for (int i = 0; i < 8; i++) {
            if (i == 0) {
                // 军官证件
                dd.setCode("EXPERT_IDNUMBER");
                List<DictionaryData> find = dictionaryDataServiceI.find(dd);
                typeMap.put("EXPERT_IDNUMBER_TYPEID", find.get(0).getId());
            }
            if (i == 1) {
                // 职称证书
                dd.setCode("EXPERT_TITLE");
                List<DictionaryData> find = dictionaryDataServiceI.find(dd);
                typeMap.put("EXPERT_TITLE_TYPEID", find.get(0).getId());
            }
            if (i == 2) {
                // 申请表
                dd.setCode("EXPERT_APPLICATION");
                List<DictionaryData> find = dictionaryDataServiceI.find(dd);
                typeMap.put("EXPERT_APPLICATION_TYPEID", find.get(0).getId());
            }
            if (i == 3) {
                // 学历证书
                dd.setCode("EXPERT_ACADEMIC");
                List<DictionaryData> find = dictionaryDataServiceI.find(dd);
                typeMap.put("EXPERT_ACADEMIC_TYPEID", find.get(0).getId());
            }
            if (i == 4) {
                // 学位证书
                dd.setCode("EXPERT_DEGREE");
                List<DictionaryData> find = dictionaryDataServiceI.find(dd);
                typeMap.put("EXPERT_DEGREE_TYPEID", find.get(0).getId());
            }
            if (i == 5) {
                // 个人照片
                dd.setCode("EXPERT_PHOTO");
                List<DictionaryData> find = dictionaryDataServiceI.find(dd);
                typeMap.put("EXPERT_PHOTO_TYPEID", find.get(0).getId());
            }
            if (i == 6) {
                // 合同书
                dd.setCode("EXPERT_CONTRACT");
                List<DictionaryData> find = dictionaryDataServiceI.find(dd);
                typeMap.put("EXPERT_CONTRACT_TYPEID", find.get(0).getId());
            }
            if (i == 7) {
                // 军官证件
                dd.setCode("EXPERT_IDCARDNUMBER");
                List<DictionaryData> find = dictionaryDataServiceI.find(dd);
                typeMap.put("EXPERT_IDCARDNUMBER_TYPEID", find.get(0).getId());
            }
        }
        return typeMap;
    }

    /**
     * 
     * @Title: toEditBasicInfo
     * @author lkzx
     * @date 2016年9月1日 上午11:12:55
     * @Description: TODO 跳转到修改个人信息
     * @param model
     * @param @return
     * @return String
     */
    @RequestMapping("/toEditBasicInfo")
    public String toEditBasicInfo(@RequestParam("id") String id,
            HttpServletRequest request, HttpServletResponse response,
            Model model) {
        Expert expert = service.selectByPrimaryKey(id);
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("typeName", "1");
        List<PurchaseDep> depList = purchaseOrgnizationService
                .findPurchaseDepList(null);
        if (depList != null && depList.size() > 0) {
            PurchaseDep purchaseDep = depList.get(0);
            model.addAttribute("purchase", purchaseDep);
        }
        // 查询数据字典中的证件类型配置数据
        List<DictionaryData> idTypeList = DictionaryDataUtil.find(9);
        model.addAttribute("idTypeList", idTypeList);
        // 查询数据字典中的政治面貌配置数据
        List<DictionaryData> zzList = DictionaryDataUtil.find(10);
        model.addAttribute("zzList", zzList);
        // 查询数据字典中的最高学历配置数据
        List<DictionaryData> xlList = DictionaryDataUtil.find(11);
        model.addAttribute("xlList", xlList);
        // 查询数据字典中的最高学位配置数据
        List<DictionaryData> xwList = DictionaryDataUtil.find(21);
        model.addAttribute("xwList", xwList);
        // 查询数据字典中的专家来源配置数据
        List<DictionaryData> lyTypeList = DictionaryDataUtil.find(12);
        model.addAttribute("lyTypeList", lyTypeList);
        // 查询数据字典中的性别配置数据
        List<DictionaryData> sexList = DictionaryDataUtil.find(13);
        model.addAttribute("sexList", sexList);
        // 产品类型数据字典
        List<DictionaryData> spList = DictionaryDataUtil.find(6);
        model.addAttribute("spList", spList);
        // 货物类型数据字典
        List<DictionaryData> hwList = DictionaryDataUtil.find(8);
        model.addAttribute("hwList", hwList);
        // 经济类型数据字典
        List<DictionaryData> jjTypeList = DictionaryDataUtil.find(19);
        model.addAttribute("jjList", jjTypeList);

        // 专家系统key
        Integer expertKey = Constant.EXPERT_SYS_KEY;
        Map<String, Object> typeMap = getTypeId();
        // typrId集合
        model.addAttribute("typeMap", typeMap);
        // 业务id就是专家id
        model.addAttribute("sysId", id);
        // Constant.EXPERT_SYS_VALUE;
        model.addAttribute("expertKey", expertKey);
        expert.setExpertsFrom(dictionaryDataServiceI.getDictionaryData(expert.getExpertsFrom()).getCode());
        model.addAttribute("expert", expert);
        return "ses/ems/expert/edit_basic_info";
    }

    /**
     * 
     * @Title: toBasicInfo
     * @author lkzx
     * @date 2016年9月1日 上午11:12:55
     * @Description: TODO 跳转到审核页面
     * @param model
     * @return String
     */
    @RequestMapping("/toShenHe")
    public String toShenHe(@RequestParam("id") String id,
            HttpServletRequest request, HttpServletResponse response,
            Model model) {
        Expert expert = service.selectByPrimaryKey(id);
        // 查询出采购机构
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("id", expert.getPurchaseDepId());
        map.put("typeName", "1");
        List<PurchaseDep> depList = purchaseOrgnizationService
                .findPurchaseDepList(map);
        if (depList != null && depList.size() > 0) {
            PurchaseDep purchaseDep = depList.get(0);
            model.addAttribute("purchase", purchaseDep);
        }
        // 查询数据字典中的证件类型配置数据
        List<DictionaryData> idTypeList = DictionaryDataUtil.find(9);
        model.addAttribute("idTypeList", idTypeList);
        // 查询数据字典中的政治面貌配置数据
        List<DictionaryData> zzList = DictionaryDataUtil.find(10);
        model.addAttribute("zzList", zzList);
        // 查询数据字典中的最高学历配置数据
        List<DictionaryData> xlList = DictionaryDataUtil.find(11);
        model.addAttribute("xlList", xlList);
        // 查询数据字典中的专家来源配置数据
        List<DictionaryData> lyTypeList = DictionaryDataUtil.find(12);
        model.addAttribute("lyTypeList", lyTypeList);
        // 查询数据字典中的性别配置数据
        List<DictionaryData> sexList = DictionaryDataUtil.find(13);
        model.addAttribute("sexList", sexList);
        // 产品类型数据字典
        List<DictionaryData> spList = DictionaryDataUtil.find(6);
        model.addAttribute("spList", spList);
        // 货物类型数据字典
        List<DictionaryData> hwList = DictionaryDataUtil.find(8);
        model.addAttribute("hwList", hwList);
        // 经济类型数据字典
        List<DictionaryData> jjTypeList = DictionaryDataUtil.find(19);
        model.addAttribute("jjList", jjTypeList);
        // 专家系统key
        Integer expertKey = Constant.EXPERT_SYS_KEY;
        Map<String, Object> typeMap = getTypeId();
        // typrId集合
        model.addAttribute("typeMap", typeMap);
        // 业务id就是专家id
        model.addAttribute("sysId", id);
        // Constant.EXPERT_SYS_VALUE;
        model.addAttribute("expertKey", expertKey);
        request.setAttribute("expert", expert);
        return "ses/ems/expert/audit";
    }
    
    @RequestMapping("/toSecondAudit")
    public String toSecondAudit(@RequestParam("id") String id,
            HttpServletRequest request, HttpServletResponse response,
            Model model) {
        Expert expert = service.selectByPrimaryKey(id);
        // 查询出采购机构
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("id", expert.getPurchaseDepId());
        map.put("typeName", "1");
        List<PurchaseDep> depList = purchaseOrgnizationService
                .findPurchaseDepList(map);
        if (depList != null && depList.size() > 0) {
            PurchaseDep purchaseDep = depList.get(0);
            model.addAttribute("purchase", purchaseDep);
        }
        // 查询数据字典中的证件类型配置数据
        List<DictionaryData> idTypeList = DictionaryDataUtil.find(9);
        model.addAttribute("idTypeList", idTypeList);
        // 查询数据字典中的政治面貌配置数据
        List<DictionaryData> zzList = DictionaryDataUtil.find(10);
        model.addAttribute("zzList", zzList);
        // 查询数据字典中的最高学历配置数据
        List<DictionaryData> xlList = DictionaryDataUtil.find(11);
        model.addAttribute("xlList", xlList);
        // 查询数据字典中的专家来源配置数据
        List<DictionaryData> lyTypeList = DictionaryDataUtil.find(12);
        model.addAttribute("lyTypeList", lyTypeList);
        // 查询数据字典中的性别配置数据
        List<DictionaryData> sexList = DictionaryDataUtil.find(13);
        model.addAttribute("sexList", sexList);
        // 产品类型数据字典
        List<DictionaryData> spList = DictionaryDataUtil.find(6);
        model.addAttribute("spList", spList);
        // 货物类型数据字典
        List<DictionaryData> hwList = DictionaryDataUtil.find(8);
        model.addAttribute("hwList", hwList);
        // 经济类型数据字典
        List<DictionaryData> jjTypeList = DictionaryDataUtil.find(19);
        model.addAttribute("jjList", jjTypeList);
        // 专家系统key
        Integer expertKey = Constant.EXPERT_SYS_KEY;
        Map<String, Object> typeMap = getTypeId();
        // typrId集合
        model.addAttribute("typeMap", typeMap);
        // 业务id就是专家id
        model.addAttribute("sysId", id);
        // Constant.EXPERT_SYS_VALUE;
        model.addAttribute("expertKey", expertKey);
        request.setAttribute("expert", expert);
        return "ses/ems/expert/second_audit";
    }

    /**
     * 
     * @Title: shenhe
     * @author lkzx
     * @date 2016年9月5日 下午2:12:19
     * @Description: TODO 执行审核专家信息
     * @param @return
     * @return String
     */
    @RequestMapping("/shenhe")
    public String shenhe(@RequestParam("isPass") String isPass, Expert expert,
            @RequestParam("remark") String remark, HttpSession session) {
        // 当前登录用户
        User user = (User) session.getAttribute("loginUser");
        // 去除临时专家角色,根据状态去判断登录的跳转路径
        /*User expertUser = userService.findByTypeId(expert.getId());
        if ("1".equals(isPass)) {
            Role role = new Role();
            role.setCode("EXPERT_R");
            List<Role> listRole = roleService.find(role);
            if (listRole != null && listRole.size() > 0) {
                Userrole userrole = new Userrole();
                userrole.setRoleId(listRole.get(0));
                userrole.setUserId(expertUser);
                *//** 给该用户初始化进口代理商角色 *//*
                userService.saveRelativity(userrole);
                String[] roleIds = listRole.get(0).getId().split(",");
                List<String> listMenu = menuService.findByRids(roleIds);
                *//** 给用户初始化进口代理商菜单权限 *//*
                for (String menuId : listMenu) {
                    UserPreMenu upm = new UserPreMenu();
                    PreMenu preMenu = menuService.get(menuId);
                    upm.setPreMenu(preMenu);
                    upm.setUser(expertUser);
                    userService.saveUserMenu(upm);
                }
            }
        }*/
        // 专家状态修改
        expert.setStatus(isPass);
        // 审核时初始化专家诚信积分
        expert.setHonestyScore(0);
        // 审核信息增加
        expertAuditService.auditExpert(expert, remark, user);
        // 执行修改
        service.updateByPrimaryKeySelective(expert);
        return "redirect:findAllExpert.html";
    }

    @RequestMapping("/secondAudit")
    public String secondAudit(@RequestParam("isPass") String isPass, Expert expert,
             HttpSession session) {
        // 专家状态修改
        expert.setStatus(isPass);
        // 执行修改
        service.updateByPrimaryKeySelective(expert);
        return "redirect:secondAuditExpert.html";
    }
    
    /**
     * 
     * @Title: toEditBasicInfo
     * @author lkzx
     * @date 2016年9月1日 上午11:14:38
     * @Description: TODO 后台 跳到修改个人信息页面
     * @param @return
     * @return String
     * @throws IOException
     */
    @RequestMapping("/toPersonInfo")
    public String toPersonInfo(Model model, HttpSession session,
            HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        User user = (User) session.getAttribute("loginUser");
        // 判断用户的类型为专家类型
        if (user != null) {
            String typeId = user.getTypeId();
            if (typeId != null && StringUtils.isNotEmpty(typeId)) {
                Expert expert = service.selectByPrimaryKey(typeId);
                HashMap<String, Object> map = new HashMap<String, Object>();
                if (expert != null) {
                    String purchaseDepId = expert.getPurchaseDepId();
                    if (purchaseDepId != null
                            && StringUtils.isNotEmpty(purchaseDepId)) {
                        map.put("id", purchaseDepId);
                        map.put("typeName", "1");
                        // 采购机构
                        List<PurchaseDep> depList = purchaseOrgnizationService
                                .findPurchaseDepList(map);
                        if (depList != null && depList.size() > 0) {
                            PurchaseDep purchaseDep = depList.get(0);
                            model.addAttribute("purchase", purchaseDep);
                        }
                    }
                    // 查询数据字典中的证件类型配置数据
                    List<DictionaryData> idTypeList = DictionaryDataUtil
                            .find(9);
                    model.addAttribute("idTypeList", idTypeList);
                    // 查询数据字典中的政治面貌配置数据
                    List<DictionaryData> zzList = DictionaryDataUtil.find(10);
                    model.addAttribute("zzList", zzList);
                    // 查询数据字典中的最高学历配置数据
                    List<DictionaryData> xlList = DictionaryDataUtil.find(11);
                    model.addAttribute("xlList", xlList);
                    // 查询数据字典中的专家来源配置数据
                    List<DictionaryData> lyTypeList = DictionaryDataUtil
                            .find(12);
                    model.addAttribute("lyTypeList", lyTypeList);
                    // 查询数据字典中的性别配置数据
                    List<DictionaryData> sexList = DictionaryDataUtil.find(13);
                    model.addAttribute("sexList", sexList);
                    // 产品类型数据字典
                    List<DictionaryData> spList = DictionaryDataUtil.find(6);
                    model.addAttribute("spList", spList);
                    // 货物类型数据字典
                    List<DictionaryData> hwList = DictionaryDataUtil.find(8);
                    model.addAttribute("hwList", hwList);
                    // 经济类型数据字典
                    List<DictionaryData> jjTypeList = DictionaryDataUtil.find(19);
                    model.addAttribute("jjList", jjTypeList);
                    // 专家系统key
                    Integer expertKey = Constant.EXPERT_SYS_KEY;
                    Map<String, Object> typeMap = getTypeId();
                    // typrId集合
                    model.addAttribute("typeMap", typeMap);
                    // 业务id就是专家id
                    model.addAttribute("sysId", expert.getId());
                    // Constant.EXPERT_SYS_VALUE;
                    model.addAttribute("expertKey", expertKey);
                    model.addAttribute("expert", expert);
                }
            }
        }
        return "ses/ems/expert/person_info";
    }

    /**
     * 
     * @Title: editBasicInfo
     * @author lkzx
     * @date 2016年9月1日 上午11:14:38
     * @Description: TODO 修改个人信息
     * @param @return
     * @return String
     * @throws IOException
     */
    @RequestMapping("/editBasicInfo")
    public String editBasicInfo(Expert expert, Model model,
            HttpSession session, @RequestParam("token2") String token2,
            HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        User user = (User) session.getAttribute("loginUser");
        // 修改个人信息
        service.editBasicInfo(expert, user);
        return "redirect:toPersonInfo.html";
    }

    /**
     * 
     * @Title: edit
     * @author lkzx
     * @date 2016年9月1日 上午11:14:38
     * @Description: TODO 修改个人全部信息
     * @param @return
     * @return String
     * @throws IOException
     */
    @RequestMapping("/edit")
    public String edit(Expert expert, Model model, HttpSession session,
            @RequestParam("token2") String token2, HttpServletRequest request,
            HttpServletResponse response) throws IOException {
        Object tokenValue = session.getAttribute("tokenSession");
        if (tokenValue != null && tokenValue.equals(token2)) {
            // 正常提交
            session.removeAttribute("tokenSession");
            // 修改状态为已提交
            expert.setIsSubmit("1");
            // 修改时间
            expert.setUpdatedAt(new Date());
            service.updateByPrimaryKeySelective(expert);
            return "redirect:findAllExpert.html";
        } else {
            // 重复提交 这里未做重复提醒，只是不重复修改
            return "redirect:findAllExpert.html";
        }
    }

    /**
     * 
     * @Title: add
     * @author lkzx
     * @date 2016年9月1日 上午11:14:38
     * @Description: TODO 新增个人信息
     * @param @return
     * @return String
     * @throws IOException
     */
    @RequestMapping("/add")
    public String add(String categoryId, String sysId, Expert expert,
            String userId, Model model, RedirectAttributes attr,
            HttpSession session, String token2, HttpServletRequest request,
            HttpServletResponse response) {
        try {
            Object tokenValue = session.getAttribute("tokenSession");
            String expertId = sysId;
            if (tokenValue != null && tokenValue.equals(token2)) {
                // 正常提交
                session.removeAttribute("tokenSession");
                User user = (User) session.getAttribute("loginUser");
                // 用户信息处理
                service.userManager(user, userId, expert, expertId);
                // 调用service逻辑代码 实现提交
                Map<String, Object> map = service.saveOrUpdate(expert,
                        expertId, categoryId, null, userId);
                if (map != null && !map.isEmpty()) {
                    attr.addAttribute("userId", userId);
                    return "redirect:toAddBasicInfo.html";
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            // 未做异常处理
        }
        attr.addAttribute("userId", userId);
        return "redirect:toAddBasicInfo.html";
    }
    
    /**
   * 
   * @Title: add
   * @author lkzx
   * @date 2016年9月1日 上午11:14:38
   * @Description: TODO 新增个人信息
   * @param @return
   * @return String
   * @throws IOException
   */
  @ResponseBody
  @RequestMapping("/add1")
  public void add1(String categoryId, String sysId, Expert expert,
      String userId, Model model, RedirectAttributes attr,
      HttpSession session, String token2, HttpServletRequest request,
      HttpServletResponse response, String gitFlag) {
    try {
        String expertId = sysId;
        // 正常提交
        User user = (User) session.getAttribute("loginUser");
        // 用户信息处理
        service.userManager(user, userId, expert, expertId);
        // 调用service逻辑代码 实现提交
        service.saveOrUpdate(expert, expertId, categoryId, gitFlag, userId);
        expert.setIsDo("0");
        //已提交
        expert.setIsSubmit("1");
        Expert temp = service.selectByPrimaryKey(expertId);
        if ("3".equals(temp.getStatus())) {
            //未审核
            expert.setStatus("0");
        }
        //修改时间
        expert.setUpdatedAt(new Date());
        service.updateByPrimaryKeySelective(expert);
    } catch (Exception e) {
      e.printStackTrace();
      // 未做异常处理
    }
    attr.addAttribute("userId", userId);
    //return "redirect:toAddBasicInfo.html";
  }

    /**
     * 
     * @Title: zanCun
     * @author ShaoYangYang
     * @date 2016年11月8日 上午10:40:42
     * @Description: TODO ajax暂存逻辑
     * @param @param sysId
     * @param @param expert
     * @param @param categoryId
     * @param @param userId
     * @param @param model
     * @param @param session
     * @return void
     */
    @RequestMapping("zanCun")
    @ResponseBody
    public Expert zanCun(String sysId, Expert expert, String categoryId,
            String userId, Model model, HttpSession session) {
        try {
            // 预定义id
            String expertId = sysId;
            User user = (User) session.getAttribute("loginUser");
            // 用户信息处理
            service.userManager(user, userId, expert, expertId);
            // 调用service逻辑 实现暂存
            StringBuffer categories = new StringBuffer();
            List<ExpertCategory> allList = expertCategoryService.getListByExpertId(expert.getId());
            for (ExpertCategory expertCategory : allList) {
                Category category = categoryService.selectByPrimaryKey(expertCategory.getCategoryId());
                categories.append(category == null ? "" : category.getName());
                categories.append("、");
            }
            if (!"".equals(categories.toString())) {
                String productCategories = categories.substring(0, categories.length() - 1);
                expert.setProductCategories(productCategories);
            }
            service.zanCunInsert(expert, expertId, categoryId);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return expert;
    }

    /**
     * 
     * @Title: getCategoryByExpertId
     * @author ShaoYangYang
     * @date 2016年9月28日 下午5:14:00
     * @Description: TODO 根据专家id查询该专家关联的品目code
     * @param @param id
     * @param @return
     * @return List<ExpertCategory>
     */
    @RequestMapping("/getCategoryByExpertId")
    @ResponseBody
    public String getCategoryByExpertId(String expertId) {
        List<ExpertCategory> list = expertCategoryService.getListByExpertId(expertId);
        List<String> categoryList = new ArrayList<String>();
        for (ExpertCategory expertCategory : list) {
            categoryList.add(expertCategory.getCategoryId());
        }
        return JSON.toJSONString(categoryList);
    }

    /**
     * 
     * @Title: getCategoryByExpertId
     * @author ShaoYangYang
     * @date 2016年9月28日 下午5:14:00
     * @Description: TODO 根据专家id查询该专家的采购机构id
     * @param @param id
     * @param @return
     * @return List<ExpertCategory>
     */
    @RequestMapping("/getPurDepIdByExpertId")
    @ResponseBody
    public String getPurDepIdByExpertId(@RequestParam("expertId") String id) {
        Expert expert = service.selectByPrimaryKey(id);
        if (expert != null) {
            String purDepId = expert.getPurchaseDepId();
            return purDepId;
        }
        return null;
    }

    /**
     * 
     * @Title: deleteAll
     * @author ShaoYangYang
     * @date 2016年9月8日 下午3:53:36
     * @Description: TODO 软删除
     * @param
     * @return void
     */
    @RequestMapping("/deleteAll")
    public String deleteAll(@RequestParam("ids") String ids) {
        String[] id = ids.split(",");
        // 循环删除选中的数据
        for (String string : id) {
            Expert expert = service.selectByPrimaryKey(string);
            if (expert != null) {
                expert.setIsDelete((short) 1);
                service.updateByPrimaryKeySelective(expert);
            }
        }
        return "redirect:findAllExpert.html";
    }

    /**
     * 
     * @Title: findAllExpert
     * @author lkzx
     * @date 2016年9月2日 下午5:44:37
     * @Description: TODO 查询所有专家 可以条件查询
     * @param @return
     * @return String
     */
    @RequestMapping("/findAllExpert")
    public String findAllExpert(Expert expert, Integer page,
            HttpServletRequest request, HttpServletResponse response) {
        List<Expert> allExpert = service.selectAllExpert(page == null ? 0
                : page, expert);
        for (Expert exp : allExpert) {
            DictionaryData dictionaryData = dictionaryDataServiceI
                .getDictionaryData(exp.getGender());
            exp.setGender(dictionaryData == null ? "" : dictionaryData.getName());
            StringBuffer expertType = new StringBuffer();
            if (exp.getExpertsTypeId() != null) {
                for (String typeId : exp.getExpertsTypeId().split(",")) {
                    DictionaryData data = dictionaryDataServiceI.getDictionaryData(typeId);
                    if (6 == data.getKind()) {
                        expertType.append(data.getName() + "技术、");
                    } else {
                        expertType.append(data.getName() + "、");
                    }
                }
                String expertsType = expertType.toString().substring(0, expertType.length() - 1);
                exp.setExpertsTypeId(expertsType);
            } else {
                exp.setExpertsTypeId("");
            }
        }
        // 查询数据字典中的专家来源配置数据
        List<DictionaryData> lyTypeList = DictionaryDataUtil.find(12);
        request.setAttribute("lyTypeList", lyTypeList);
        // 查询数据字典中的专家类别数据
        List<DictionaryData> jsTypeList = DictionaryDataUtil.find(6);
        for (DictionaryData data : jsTypeList) {
            data.setName(data.getName() + "技术");
        }
        List<DictionaryData> jjTypeList = DictionaryDataUtil.find(19);
        jsTypeList.addAll(jjTypeList);
        request.setAttribute("expTypeList", jsTypeList);
        request.setAttribute("result", new PageInfo<Expert>(allExpert));
        request.setAttribute("expert", expert);
        return "ses/ems/expert/list";
    }
    
    /**
     *〈简述〉
     * 专家复审列表展示
     *〈详细描述〉
     * @author WangHuijie
     * @param expert
     * @param page
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/secondAuditExpert")
    public String secondAuditExpert(Expert expert, Integer page,
            HttpServletRequest request, HttpServletResponse response) {
        List<Expert> allExpert = service.selectAllExpert(page == null ? 0
                : page, expert);
        ProjectExtract projectExtract = new ProjectExtract();
        a:for (Expert exp : allExpert) {
            // 判断是否被抽取
            projectExtract.setExpertId(exp.getId());
            projectExtract.setReason("1");
            List<ProjectExtract> list = projectExtractService.list(projectExtract);
            if (list.isEmpty()) {
                allExpert.remove(exp);
                continue a;
            }
            DictionaryData dictionaryData = dictionaryDataServiceI
                .getDictionaryData(exp.getGender());
            exp.setGender(dictionaryData == null ? "" : dictionaryData.getName());
        }
        // 查询数据字典中的专家来源配置数据
        List<DictionaryData> lyTypeList = DictionaryDataUtil.find(12);
        request.setAttribute("lyTypeList", lyTypeList);
        request.setAttribute("result", new PageInfo<Expert>(allExpert));
        request.setAttribute("expert", expert);
        return "ses/ems/expert/second_audit_list";
    }

    /**
     * 
     * @Title: findAllExpertShenHe
     * @author lkzx
     * @date 2016年9月2日 下午5:44:37
     * @Description: TODO 查询所有审核状态的专家 可以条件查询
     * @param @return
     * @return String
     */
    @RequestMapping("/findAllExpertShenHe")
    public String findAllExpertShenHe(Expert expert, Integer page,
            HttpServletRequest request, HttpServletResponse response) {
        List<Expert> allExpert = service.selectAllExpert(page == null ? 1
                : page, expert);
        request.setAttribute("result", new PageInfo<Expert>(allExpert));
        request.setAttribute("expert", expert);
        return "ses/ems/expert/audit_list";
    }

    /**
     * 
     * @Title: toShenHeExpert
     * @author lkzx
     * @date 2016年9月2日 下午5:44:37
     * @Description: TODO 跳转到未审核专家
     * @param @return
     * @return String
     */
    @RequestMapping("/toShenHeExpert")
    public String toShenHeExpert(Expert expert, Integer page,
            HttpServletRequest request, HttpServletResponse response) {
        expert.setStatus("0");
        List<Expert> allExpert = service.selectAllExpert(page == null ? 1
                : page, expert);
        request.setAttribute("result", new PageInfo<Expert>(allExpert));
        request.setAttribute("expert", expert);
        return "ses/ems/expert/audit_list";
    }

    /**
     * 
     * @Title: findAllLoginName
     * @author ShaoYangYang
     * @date 2016年9月14日 下午6:13:09
     * @Description: TODO 用户名唯一判断
     * @param @param loginName
     * @param @param model
     * @param @return
     * @return List<User>
     */
    @RequestMapping("/findAllLoginName")
    @ResponseBody
    public String findAllLoginName(@RequestParam("loginName") String loginName,
            Model model) {
        List<User> userList = userService.findByLoginName(loginName);
        if (userList != null && userList.size() > 0) {
            return "1";
        }
        return "2";
    }

    /**
     * 
     * @Title: findAttachment
     * @author ShaoYangYang
     * @date 2016年11月8日 下午5:35:46
     * @Description: TODO 根据附件类型id 和业务id 查询附件
     * @param @param sysId
     * @param @param typeId
     * @param @return
     * @return List<ExpertAttachment>
     */
    @RequestMapping(value = "findAttachment", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String findAttachment(String sysId) {

        Map<String, Object> map = new HashMap<>();
        map.put("businessId", sysId);
        map.put("isDeleted", "0");
        List<ExpertAttachment> list = attachmentService.selectListByMap(map);
        return JSON.toJSONString(list);
    }
    /**
     *〈简述〉项目评审 只显示项目
     *〈详细描述〉
     * @author Song Biaowei
     * @param model 模型
     * @param session session
     * @return String
     */
    @RequestMapping(value = "toProjectList")
    public String projectList(Model model, HttpSession session) {
        try {
            User user = (User) session.getAttribute("loginUser");
            // 判断用户的类型为专家类型
            if (user != null) {
                // 获取专家id
                String typeId = user.getTypeId();
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("expertId", typeId);
                map.put("isGather", "0");
                // 查询出关联表中的项目id和包id
                List<PackageExpert> packageExpertList = packageExpertService.selectList(map);
                HashMap<String, Object> hashMap;
                // 该专家的所有包集合
                List<Packages> packageList = new ArrayList<Packages>();
                for (PackageExpert packageExpert : packageExpertList) {
                    // 包id
                    String string = packageExpert.getPackageId();
                    hashMap = new HashMap<String, Object>();
                    hashMap.put("id", string);
                    List<Packages> packages = packageService.findPackageById(hashMap);
                    if (packages != null && packages.size() > 0) {
                        packageList.add(packages.get(0));
                    }
                }
                Set<String> strList = new HashSet<String>();
                for (PackageExpert packageExpert : packageExpertList) {
                    strList.add(packageExpert.getProjectId());
                }
                if (packageList != null && packageList.size() > 0) {
                    List<ProjectExt> projectExtList = new ArrayList<ProjectExt>();
                    ProjectExt projectExt;
                    for (String projectId : strList) {
                        projectExt = new ProjectExt();
                        Project project = projectService.selectById(projectId);
                        PropertyUtils.copyProperties(projectExt, project);
                        projectExtList.add(projectExt);
                    }
                    model.addAttribute("projectExtList", projectExtList);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "bss/prms/audit/list_project";
    }

    /**
     * 
     * @Title: projectList
     * @author ShaoYangYang
     * @date 2016年10月22日 上午10:28:43
     * @Description: TODO 去项目评审列表页面
     * @param @return
     * @return String
     */
    @RequestMapping("projectList")
    public String toProjectList(Model model, HttpSession session, String projectId) {
        try {
            User user = (User) session.getAttribute("loginUser");
            // 判断用户的类型为专家类型
            if (user != null) {
                // 获取专家id
                String typeId = user.getTypeId();
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("expertId", typeId);
                // map.put("isAudit", 0);
                map.put("isGather", 0);
                // 查询出关联表中的项目id和包id
                List<PackageExpert> pel = packageExpertService.selectList(map);
                List<PackageExpert> packageExpertList = new ArrayList<PackageExpert>();
                for (PackageExpert packageExpert : pel) {
                    //if (packageExpert.getProjectId().equals(projectId)){
                    packageExpertList.add(packageExpert);
                    //}
                }
                HashMap<String, Object> hashMap;
                // 该专家的所有包集合
                List<Packages> packageList = new ArrayList<Packages>();
                for (PackageExpert packageExpert : packageExpertList) {
                    // 包id
                    String string = packageExpert.getPackageId();
                    hashMap = new HashMap<String, Object>();
                    hashMap.put("id", string);
                    List<Packages> packages = packageService.findPackageById(hashMap);
                    if (packages != null && packages.size() > 0) {
                        packageList.add(packages.get(0));
                    }
                }
                // 循环包集合 根据包中的项目id 查询出项目集合
                if (packageList != null && packageList.size() > 0) {
                    List<ProjectExt> projectExtList = new ArrayList<ProjectExt>();
                    ProjectExt projectExt;
                    for (Packages packages : packageList) {
                        projectExt = new ProjectExt();
                        Project project = projectService.selectById(packages.getProjectId());
                        PropertyUtils.copyProperties(projectExt, project);
                        projectExt.setPackageId(packages.getId());
                        projectExt.setPackageName(packages.getName());
                        //进度
                        Map<String, Object> map2 = new HashMap<String, Object>();
                        //map2.put("projectId", projectId);
                        map2.put("packageId", packages.getId());
                        //查询该包有没有评审进度数据
                        List<ReviewProgress> rplist = reviewProgressService.selectByMap(map2);
                        if (rplist == null || rplist.size() <= 0) {
                            ReviewProgress reviewProgress = new ReviewProgress();
                            reviewProgress.setAuditStatus("0");
                            reviewProgress.setFirstAuditProgress(0.00);
                            reviewProgress.setPackageId(packages.getId());
                            reviewProgress.setPackageName(packages.getName());
                            reviewProgress.setProjectId(projectId);
                            reviewProgress.setScoreProgress(0.00);
                            reviewProgress.setTotalProgress(0.00);
                            projectExt.setReviewProgress(reviewProgress);
                        } else {
                            projectExt.setReviewProgress(rplist.get(0));
                        }
                        projectExtList.add(projectExt);
                        model.addAttribute("projectExtList", projectExtList);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "bss/prms/audit/list";
    }
    
    /**
     *〈简述〉
     * 判断是否通过了符合性审查
     *〈详细描述〉
     * 1代表通过,0没通过
     * @author WangHuijie
     * @param projectId
     * @param packageId
     * @return
     */
    @ResponseBody
    @RequestMapping("/validateIsGrade")
    public String validateIsGrade(String projectId, String packageId){
        // 0代表为通过符合性审查
        String isok = "0";
        Map<String, Object> mapSearch = new HashMap<String, Object>();
        mapSearch.put("projectId", projectId);
        mapSearch.put("packageId", packageId);
        List<PackageExpert> list = packageExpertService.selectList(mapSearch);
        if (list.isEmpty()) {
            PackageExpert packageExpert = list.get(0);
            if ("1".equals(packageExpert.getIsAudit()) && !"1".equals(packageExpert.getIsGrade())) {
                // 如果通过则改为1
                isok = "1";
            }
        }
        return isok;
    }
    
    /**
     * 
     * @Title: toFirstAudit
     * @author ShaoYangYang
     * @date 2016年10月22日 下午3:50:09
     * @Description: TODO 去往项目初审 供应商详情页
     * @param @return
     * @return String
     */
    @RequestMapping("toFirstAudit")
    public String toFirstAudit(String projectId, String packageId, Model model,
            HttpSession session) {
        // 是否已评审
        User user = (User) session.getAttribute("loginUser");
        String expertId = user.getTypeId();
        Map<String, Object> map = new HashMap<>();
        map.put("expertId", expertId);
        map.put("packageId", packageId);
        map.put("projectId", projectId);
        List<PackageExpert> packageExpertList = packageExpertService
                .selectList(map);
        if (packageExpertList != null && packageExpertList.size() > 0) {
            model.addAttribute("packageExpert", packageExpertList.get(0));
        }
        // 供应商信息
        List<SaleTender> supplierList = saleTenderService.list(new SaleTender(
                projectId), 0);
        model.addAttribute("supplierList", supplierList);
        model.addAttribute("projectId", projectId);
        model.addAttribute("packageId", packageId);
        
        return "bss/prms/audit/suppplier_list";
    }

    /**
     * 
     * @Title: saveProgress
     * @author ShaoYangYang
     * @date 2016年10月27日 下午2:17:47
     * @Description: TODO 保存评分信息 更新评分进度
     * @param @return
     * @return String
     */
    @RequestMapping("saveGrade")
    public String saveGrade(String projectId, String packageId,
            HttpSession session, RedirectAttributes attr) {
        User user = (User) session.getAttribute("loginUser");
        String expertId = user.getTypeId();
        reviewProgressService.saveGrade(projectId, packageId, expertId);
        attr.addAttribute("projectId", projectId);
        attr.addAttribute("packageId", packageId);
        return "redirect:toFirstAudit.html";
    }

    /**
     * 
     * @Title: supplierQuote
     * @author ShaoYangYang
     * @date 2016年11月11日 下午2:46:47
     * @Description: TODO 查看供应商报价
     * @param packageId
     * @param supplierId
     * @param @return
     * @return String
     */
    @RequestMapping("supplierQuote")
    public String supplierQuote(String packageId, String supplierId, Model model) {
        Quote quote = new Quote();
        quote.setPackageId(packageId);
        quote.setSupplierId(supplierId);
        List<Quote> historyList = supplierQuoteService
                .selectQuoteHistoryList(quote);
        if (historyList != null && historyList.size() > 0) {
            long create = historyList.get(0).getCreatedAt().getTime();
            for (Quote quote2 : historyList) {
                if (quote2.getCreatedAt().getTime() > create) {
                    create = quote2.getCreatedAt().getTime();
                }
            }
            Date date = new Date(create);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            String formatDate = sdf.format(date);
            Timestamp timestamp = Timestamp.valueOf(formatDate);
            quote.setCreatedAt(timestamp);
            List<Quote> historyList2 = supplierQuoteService
                    .selectQuoteHistoryList(quote);
            model.addAttribute("historyList", historyList2);
        }
        return "bss/prms/audit/list";
    }

    /**
     * 
     * @Title: saveProgress
     * @author ShaoYangYang
     * @date 2016年10月27日 下午2:17:47
     * @Description: TODO 保存审核信息
     * @param @return
     * @return String
     */
    @RequestMapping("saveProgress")
    public String saveProgress(String projectId, String packageId,
            HttpSession session, RedirectAttributes attr) {
        User user = (User) session.getAttribute("loginUser");
        String expertId = user.getTypeId();
        // 更新进度 保存审核信息
        reviewProgressService.saveProgress(projectId, packageId, expertId);
        attr.addAttribute("projectId", projectId);
        attr.addAttribute("packageId", packageId);
        return "redirect:toFirstAudit.html";
    }

    /**
     * 
     * @Title: download
     * @author ShaoYangYang
     * @date 2016年9月7日 下午6:53:12
     * @Description: TODO 下载申请表
     * @param @param expert
     * @param @param request
     * @param @throws Exception
     * @return ResponseEntity<byte[]>
     */
    @RequestMapping("download")
    public ResponseEntity<byte[]> download(String id,
            HttpServletRequest request) throws Exception {
        // 根据编号查询专家信息
        Expert expert = service.selectByPrimaryKey(id);
        // 文件存储地址
        String filePath = request.getSession().getServletContext()
                .getRealPath("/WEB-INF/upload_file/");
        // 文件名称
        String fileName = createWordMethod(expert, request);
        // 下载后的文件名
        String downFileName = new String("军队评标专家申请表.doc".getBytes("UTF-8"),
                "iso-8859-1");// 为了解决中文名称乱码问题
        return service.downloadFile(fileName, filePath, downFileName);
    }
    
    /**
     *〈简述〉
     * 下载专家承诺书
     *〈详细描述〉
     * @author WangHuijie
     * @param id
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping("downloadBook")
    public ResponseEntity<byte[]> downloadBook(String id,
            HttpServletRequest request) throws Exception {
        // 文件存储地址
        String filePath = request.getSession().getServletContext()
                .getRealPath("/WEB-INF/upload_file/");
        // 文件名称
        String name = new String(("军队评标专家承诺书.doc").getBytes("UTF-8"),
                "UTF-8");
        /** 生成word 返回文件名 */
        String fileName = WordUtil.createWord(null, "expertBook.ftl",
                name, request);
        // 下载后的文件名
        String downFileName = new String("军队评标专家承诺书.doc".getBytes("UTF-8"),
                "iso-8859-1");// 为了解决中文名称乱码问题
        return service.downloadFile(fileName, filePath, downFileName);
    }
    
    /**
     *〈简述〉
     * 下载专家注册须知
     *〈详细描述〉
     * @author WangHuijie
     * @param id
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping("/downNotice")
    public ResponseEntity<byte[]> downNotice(String id,
            HttpServletRequest request) throws Exception {
        // 文件存储地址
        String filePath = request.getSession().getServletContext()
                .getRealPath("/WEB-INF/upload_file/");
        // 文件名称
        String name = new String(("评审专家申请人注册须知.doc").getBytes("UTF-8"),
                "UTF-8");
        /** 生成word 返回文件名 */
        String fileName = WordUtil.createWord(null, "expertNotice.ftl",
                name, request);
        // 下载后的文件名
        String downFileName = new String("评审专家申请人注册须知.doc".getBytes("UTF-8"),
                "iso-8859-1");// 为了解决中文名称乱码问题
        return service.downloadFile(fileName, filePath, downFileName);
    }
    
    /**
     * 
     * 
     * @Title: createWordMethod
     * @author: lkzx
     * @date: 2016-9-7 下午3:25:38
     * @Description: TODO 生成word文件提供下载
     * @param: @param expert
     * @return: String
     * @throws Exception
     */
    private String createWordMethod(Expert expert, HttpServletRequest request) throws Exception {
        /** 用于组装word页面需要的数据 */
        Map<String, Object> dataMap = new HashMap<String, Object>();
        dataMap.put("relName", expert.getRelName() == null ? "" : expert.getRelName());
        String sex = expert.getGender();
        DictionaryData gender = dictionaryDataServiceI.getDictionaryData(sex);
        dataMap.put("gender", gender == null ? "" : gender.getName());
        dataMap.put("birthday",
                expert.getBirthday() == null ? "" : new SimpleDateFormat(
                        "yyyy-MM-dd").format(expert.getBirthday()));
        String faceId = expert.getPoliticsStatus();
        DictionaryData politicsStatus = dictionaryDataServiceI.getDictionaryData(faceId);
        dataMap.put("politicsStatus", politicsStatus == null ? "" : politicsStatus.getName());
        dataMap.put("nation", expert.getNation() == null ? "" : expert.getNation());
        dataMap.put("healthState", expert.getHealthState() == null ? "" : expert.getHealthState());
        dataMap.put("workUnit", expert.getWorkUnit() == null ? "" : expert.getWorkUnit());
        dataMap.put("coverNote", expert.getCoverNote() == null ? "" : expert.getCoverNote());
        dataMap.put("unitAddress", expert.getUnitAddress() == null ? "" : expert.getUnitAddress());
        dataMap.put("postCode", expert.getPostCode() == null ? "" : expert.getPostCode());
        dataMap.put("atDuty", expert.getAtDuty() == null ? "" : expert.getAtDuty());
        dataMap.put("idCardNumber", expert.getIdCardNumber() == null ? "" : expert.getIdCardNumber());
        DictionaryData idType = dictionaryDataServiceI.getDictionaryData(expert.getIdType());
        if (idType != null) {
            dataMap.put("idType", idType.getName() == null ? "" : idType.getName());
        } else {
            dataMap.put("idType", "");
        }
        dataMap.put("idNumber", expert.getIdNumber() == null ? "" : expert.getIdNumber());
        dataMap.put("major", expert.getMajor() == null ? "" : expert.getMajor());
        dataMap.put("timeStartWork", expert.getTimeStartWork() == null ? "" : new SimpleDateFormat("yyyy-MM").format(expert.getTimeStartWork()));
        DictionaryData expertsForm = dictionaryDataServiceI.getDictionaryData(expert.getExpertsFrom());
        if (expertsForm != null) {
            dataMap.put("expertsFrom", expertsForm.getName() == null ? "" : expertsForm.getName());
        } else {
            dataMap.put("expertsFrom", "");
        }
        dataMap.put(
                "professTechTitles",
                expert.getProfessTechTitles() == null ? "" : expert
                        .getProfessTechTitles());
        dataMap.put("makeTechDate", expert.getMakeTechDate() == null ? "" : new SimpleDateFormat("yyyy-MM").format(expert.getMakeTechDate()));
        StringBuffer expertType = new StringBuffer();
        for (String typeId : expert.getExpertsTypeId().split(",")) {
            expertType.append(dictionaryDataServiceI.getDictionaryData(typeId).getName() + "、");
        }
        String expertsType = expertType.toString().substring(0, expertType.length() - 1);
        dataMap.put("expertsTypeId", expertsType);
        dataMap.put("graduateSchool", expert.getGraduateSchool() == null ? "" : expert.getGraduateSchool());
        String hightEducationId = expert.getHightEducation();
        DictionaryData hightEducation = dictionaryDataServiceI.getDictionaryData(hightEducationId);
        dataMap.put("hightEducation", hightEducation == null ? "" : hightEducation.getName());
        DictionaryData degree = dictionaryDataServiceI.getDictionaryData(expert.getDegree());
        if (degree != null) {
            dataMap.put("degree", degree.getName() == null ? "" : degree.getName());
        } else {
            dataMap.put("degree", "");
        }
        dataMap.put("mobile", expert.getMobile() == null ? "" : expert.getMobile());
        dataMap.put("telephone", expert.getTelephone() == null ? "" : expert.getTelephone());
        dataMap.put("fax", expert.getFax() == null ? "" : expert.getFax());
        dataMap.put("email", expert.getEmail() == null ? "" : expert.getEmail());
        StringBuffer categories = new StringBuffer();
        List<ExpertCategory> allList = expertCategoryService.getListByExpertId(expert.getId());
        for (ExpertCategory expertCategory : allList) {
            categories.append(categoryService.selectByPrimaryKey(expertCategory.getCategoryId()).getName());
            categories.append("、");
        }
        String productCategories = categories.substring(0, categories.length() - 1);
        dataMap.put("productCategories", productCategories);
        dataMap.put("jobExperiences", expert.getJobExperiences() == null ? "" : expert.getJobExperiences());
        dataMap.put("academicAchievement", expert.getAcademicAchievement() == null ? "" : expert.getAcademicAchievement());
        dataMap.put("reviewSituation", expert.getReviewSituation() == null ? "" : expert.getReviewSituation());
        dataMap.put("avoidanceSituation", expert.getAvoidanceSituation() == null ? "" : expert.getAvoidanceSituation());
        // 文件名称
        String fileName = new String(("军队评标专家申请表.doc").getBytes("UTF-8"),
                "UTF-8");
        /** 生成word 返回文件名 */
        String newFileName = WordUtil.createWord(dataMap, "expert.ftl",
                fileName, request);
        return newFileName;
    }

    /**
     *〈简述〉
     * 根据机构编号查询所在的省市
     *〈详细描述〉
     * @author Wanghuijie
     * @param purDepId
     * @return
     */
      @ResponseBody
      @RequestMapping(value = "getPIdandCIdByPurDepId")
      public String getPIdandCIdByPurDepId(String purDepId) {
            if (purDepId != null && !"".equals(purDepId)) {
                  Map<String, String> purchaseDep = purchaseOrgnizationService.findPIDandCIDByOrgId(purDepId);
                  return JSON.toJSONString(purchaseDep);
            }
            return null;
      }
      /**
       *〈简述〉
       * 专家注册页面的手机号唯一性验证
       *〈详细描述〉
       * @author WangHuijie
       * @param phone 
       * @return
       */
      @ResponseBody
      @RequestMapping("/validatePhone")
      public String findAllPhone(String phone) {
          List<Expert> list = service.validatePhone(phone);
          if (list.isEmpty()) {
              return "0";
          } else {
              return "1";
          }
      }
      
      @ResponseBody
      @RequestMapping("/validateAge")
      public String validateAge(String birthday) {
          String isok = "0";
          String year = birthday.substring(0, 4);
          String now = new SimpleDateFormat("yyyy").format(new Date());
          if (Integer.parseInt(now) - Integer.parseInt(year) >= 70) {
              isok = "1";
          }
          return isok;
      }
      /**
     *〈简述〉
     * 专家注册页面的身份证号唯一性验证
     *〈详细描述〉
     * @author WangHuijie
     * @param phone
     * @return
     */
    @ResponseBody
    @RequestMapping("/validateIdNumber")
    public String validateIdNumber(String idNumber, String expertId) {
        List<Expert> list = service.validateIdNumber(idNumber);
        if (list.isEmpty()) {
            return "0";
        } else {
            if (list.size() == 1 && expertId.equals(list.get(0).getId())) {
                return "0";
            } else {
                return "1";
            }
        }
    }
    /**
     *〈简述〉
     * 注册时点击下一步,将表中的STRP_NUMBER进行同步
     *〈详细描述〉
     * @author WangHuijie
     * @param expertId
     * @param stepNumber
     */
    @ResponseBody
    @RequestMapping("/updateStepNumber")
    public void updateStepNumber(String expertId, String stepNumber) {
        service.updateStepNumber(expertId, stepNumber);
    }
    /**
     *〈简述〉
     * 为专家注册第四部准备数据
     *〈详细描述〉
     * 将表中的数据查出,在数据字典中查询
     * @author WangHuijie
     * @param expertId
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/initData", produces = "application/json;charset=UTF-8")
    public String initData(String expertId){
        Expert expert = service.selectByPrimaryKey(expertId);
        expert.setGender(dictionaryDataServiceI.getDictionaryData(expert.getGender()).getName());
        // 政治面貌
        expert.setPoliticsStatus(dictionaryDataServiceI.getDictionaryData(expert.getPoliticsStatus()).getName());
        String address = expert.getAddress();
        Area area = areaServiceI.listById(address);
        // 市
        String cityName = area.getName();
        // 省
        String provinceName = areaServiceI.listById(area.getParentId()).getName();
        expert.setAddress(provinceName.concat(cityName));
        // 最高学历
        expert.setHightEducation(dictionaryDataServiceI.getDictionaryData(expert.getHightEducation()).getName());
        // 最高学位
        expert.setDegree(dictionaryDataServiceI.getDictionaryData(expert.getDegree()).getName());
        // 军队人员身份证件类型
        expert.setIdType(dictionaryDataServiceI.getDictionaryData(expert.getIdType()).getName());
        // 专家来源
        expert.setExpertsFrom(dictionaryDataServiceI.getDictionaryData(expert.getExpertsFrom()).getName());
        // 专家类别
        StringBuffer expertType = new StringBuffer();
        for (String typeId : expert.getExpertsTypeId().split(",")) {
            DictionaryData type = dictionaryDataServiceI.getDictionaryData(typeId);
            if (type.getKind().intValue() == 6) {
                type.setName(type.getName() + "技术");
            }
            expertType.append(type.getName() + "、");
        }
        String expertsType = expertType.toString().substring(0, expertType.length() - 1);
        expert.setExpertsTypeId(expertsType);
        return JSON.toJSONString(expert);
    }
}
