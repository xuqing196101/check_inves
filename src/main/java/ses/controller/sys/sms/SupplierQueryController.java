package ses.controller.sys.sms;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import ses.formbean.ContractBean;
import ses.formbean.QualificationBean;
import ses.model.bms.Area;
import ses.model.bms.Category;
import ses.model.bms.DictionaryData;
import ses.model.bms.Qualification;
import ses.model.bms.User;
import ses.model.sms.Supplier;
import ses.model.sms.SupplierAptitute;
import ses.model.sms.SupplierAudit;
import ses.model.sms.SupplierCertEng;
import ses.model.sms.SupplierCertPro;
import ses.model.sms.SupplierCertSell;
import ses.model.sms.SupplierCertServe;
import ses.model.sms.SupplierDictionaryData;
import ses.model.sms.SupplierEdit;
import ses.model.sms.SupplierFinance;
import ses.model.sms.SupplierItem;
import ses.model.sms.SupplierMatEng;
import ses.model.sms.SupplierMatPro;
import ses.model.sms.SupplierMatSell;
import ses.model.sms.SupplierMatServe;
import ses.model.sms.SupplierRegPerson;
import ses.model.sms.SupplierStockholder;
import ses.model.sms.SupplierTypeRelate;
import ses.service.bms.AreaServiceI;
import ses.service.bms.CategoryService;
import ses.service.bms.DictionaryDataServiceI;
import ses.service.sms.SupplierAuditService;
import ses.service.sms.SupplierEditService;
import ses.service.sms.SupplierItemService;
import ses.service.sms.SupplierLevelService;
import ses.service.sms.SupplierService;
import ses.service.sms.SupplierTypeRelateService;
import ses.util.DictionaryDataUtil;
import ses.util.FtpUtil;
import ses.util.PropUtil;
import bss.formbean.Maps;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageInfo;
import common.constant.Constant;
import common.model.UploadFile;
/**
 * 版权：(C) 版权所有 
 * <简述>
 * <详细描述>供应商查询
 * @author   Song Biaowei
 * @version  
 * @since
 * @see
 */
@Controller
@Scope("prototype")
@RequestMapping("/supplierQuery")
public class SupplierQueryController extends BaseSupplierController {

    /**
     * 定义常量2
     */
    private static final int NUMBER_TWO = 2;
    /**
     * 定义常量3
     */
    private static final int NUMBER_THREE = 3;
    /**
     * 供应商审核服务层
     */
    @Autowired
    private SupplierAuditService supplierAuditService;
    /**
     * 供应商注册服务层
     */
    @Autowired
    private SupplierService supplierService;
    /**
     * 供应商等级服务层
     */
    @Autowired
    private SupplierLevelService supplierLevelService;
    /**
     * 供应商变更服务层
     */
    @Autowired
    private SupplierEditService supplierEditService;
    /**
     * 数据字典服务层
     */
    @Autowired
    private DictionaryDataServiceI dictionaryDataServiceI;
    /**
     * 地区服务层
     */
    @Autowired
    private AreaServiceI areaService;
    
    @Autowired
    private SupplierItemService supplierItemService;
    
    @Autowired
    private CategoryService categoryService;
    
    @Autowired
    private SupplierTypeRelateService supplierTypeRelateService;

    /**
     *〈简述〉供应商查询
     *〈详细描述〉按照各种条件来查询供应商信息
     * @author Song Biaowei
     * @param sup 供应商实体类
     * @param model 模型
     * @param status 状态
     * @param judge 判断是哪个菜单 供应商查询0 入库供应商2 按照品目查询供应商2
     * @param supplierTypeIds 供应商类型ID组成的字符串
     * @param supplierType 供应商类型名称组成的字符串
     * @param categoryNames 品目名称组成的字符串
     * @param categoryIds 品目ID组成的字符串
     * @return String
     */
    @RequestMapping("/highmaps")
    public String highmaps(Supplier sup, Model model, Integer status, Integer judge, String supplierTypeIds
                           , String supplierType, String categoryNames, String categoryIds){
        if (judge != null){
            status = judge;
        }
        if (status != null){
            sup.setStatus(status);
        }
        if (categoryIds != null && !"".equals(categoryIds)){
            List<String> listCategoryIds = Arrays.asList(categoryIds.split(","));
            sup.setItem(listCategoryIds);
        }
        if (supplierTypeIds != null && !"".equals(supplierTypeIds)) {
            List<String> listSupplierTypeIds = Arrays.asList(supplierTypeIds.split(","));
            sup.setItemType(listSupplierTypeIds);
        }
        List<Supplier>  listSupplier = supplierAuditService.querySupplierbytypeAndCategoryIds(sup, null);
        //开始循环 判断地址是否
        Map<String, Integer> map = supplierEditService.getMap();
        Integer maxCount = 0;
        for (Supplier supplier:listSupplier) {
            for (Map.Entry<String, Integer> entry:map.entrySet()) {   
                if (supplier.getName() != null && !"".equals(supplier.getName())) {
                    if (supplier.getName().indexOf(entry.getKey()) != -1){
                        map.put((String) entry.getKey(), (Integer) map.get(entry.getKey()) + 1);
                        if (maxCount < map.get(entry.getKey())) {
                            maxCount = map.get(entry.getKey());
                        }
                        break;
                    }
                }
            }
        }
        if (maxCount == 0) {
            maxCount =2500;
        }
        List<Maps> listMap = new LinkedList<Maps>();
        for (Map.Entry<String, Integer> entry:map.entrySet()) {   
            Maps mp = new Maps();
            mp.setValue(new BigDecimal(entry.getValue()));
            mp.setName(entry.getKey());
            listMap.add(mp);
        }   
        String json = JSON.toJSONString(listMap);
        model.addAttribute("data", json);
        model.addAttribute("maxCount", maxCount);
        model.addAttribute("supplier", sup);
        model.addAttribute("categoryNames", categoryNames);
        model.addAttribute("supplierType", supplierType);
        model.addAttribute("supplierTypeIds", supplierTypeIds);
        model.addAttribute("categoryIds", categoryIds);
        if (judge != null && judge == NUMBER_THREE) {
            return "ses/sms/supplier_query/all_ruku_supplier";
        } else {
            return "ses/sms/supplier_query/all_supplier";
        }
    }

    /**
     *〈简述〉点击地图进入某一个省
     *〈详细描述〉
     * @author Song Biaowei
     * @param judge 判断是哪个菜单 供应商查询0 入库供应商2 按照品目查询供应商2
     * @param sup 供应商实体类
     * @param page 当前页
     * @param model 模型
     * @param supplierTypeIds 供应商类型ID组成的字符串
     * @param supplierType 供应商类型名称组成的字符串
     * @param categoryNames 品目名称组成的字符串
     * @param categoryIds 品目ID组成的字符串
     * @return String
     * @throws UnsupportedEncodingException 异常处理
     */
    @RequestMapping("/findSupplierByPriovince")
    public String findSupplierByPriovince(Integer judge, Supplier sup, Integer page, Model model, String supplierTypeIds, String supplierType
                                          , String categoryNames, String categoryIds) throws UnsupportedEncodingException{
        if (judge != null) {
            sup.setStatus(judge);
        }
        model.addAttribute("address", sup.getAddress());
        String address = supplierEditService.getProvince(sup.getAddress());
        if ("".equals(address)) {
            String addressName = URLDecoder.decode(sup.getAddress(), "UTF-8");
            if (addressName.length() > NUMBER_TWO) {
                sup.setAddress(addressName.substring(0, NUMBER_THREE).replace(",", ""));
                model.addAttribute("address", sup.getAddress());
            } else {
                sup.setAddress(addressName.substring(0, NUMBER_TWO).replace(",", ""));
                model.addAttribute("address", sup.getAddress());
            }
        } else {
            sup.setAddress(address);
        }
        if (categoryIds != null && !"".equals(categoryIds)) {
            List<String> listCategoryIds = Arrays.asList(categoryIds.split(","));
            sup.setItem(listCategoryIds);
        }
        if (supplierTypeIds != null && !"".equals(supplierTypeIds)) {
            List<String> listSupplierTypeIds = Arrays.asList(supplierTypeIds.split(","));
            sup.setItemType(listSupplierTypeIds);
        }
        List<Supplier>  listSupplier = supplierAuditService.querySupplierbytypeAndCategoryIds(sup, page == null ? 1 : page);
        this.getSupplierType(listSupplier);
        model.addAttribute("listSupplier", new PageInfo<>(listSupplier));
        model.addAttribute("supplier", sup);
        model.addAttribute("categoryNames", categoryNames);
        model.addAttribute("supplierType", supplierType);
        model.addAttribute("supplierTypeIds", supplierTypeIds);
        model.addAttribute("categoryIds", categoryIds);
        //等于3说明是入库供应商
        if (judge != null && judge == NUMBER_THREE) {
            return "ses/sms/supplier_query/select_ruku_supplier_by_province";
        } else {
            if (sup.getStatus() != null && sup.getStatus() == NUMBER_THREE && sup.getCount() != 0) {
                return "ses/sms/supplier_query/select_ruku_supplier_by_province";
            } else {
                return "ses/sms/supplier_query/select_supplier_by_province";
            }
        }
    }

    /**
     *〈简述〉按照品目查询供应商
     *〈详细描述〉
     * @author Song Biaowei
     * @param sup 实体类
     * @param page 当前页
     * @param categoryIds 品目id组成的字符串
     * @param model 模型
     * @return String
     */
    @RequestMapping("/selectByCategory")
    public String selectByCategory(Supplier sup, Integer page, String categoryIds, Model model) {
        if (categoryIds != null && !"".equals(categoryIds)) {
            List<String> listCategoryIds = Arrays.asList(categoryIds.split(","));
            sup.setItem(listCategoryIds);
        }
        List<Supplier>  listSupplier = supplierAuditService.querySupplierbytypeAndCategoryIds(sup, page == null ? 1 : page);
        getSupplierType(listSupplier);
        model.addAttribute("listSupplier", new PageInfo<>(listSupplier));
        model.addAttribute("supplier", sup);
        model.addAttribute("categoryIds", categoryIds);
       /* Category category = new Category();
        List<String> list=new ArrayList<String>();
        if (categoryIds != null) {
            list=Arrays.asList(categoryIds.split(",")); 
        }
        List<SupplierTypeTree> listSupplierTypeTrees = categoryService.queryCategory(category,list ,1);
        String json = JSON.toJSONStringWithDateFormat(listSupplierTypeTrees, "yyyy-MM-dd HH:mm:ss");
        json = json.replaceAll("parent", "isParent").replaceAll("isParentId", "parentId");
        model.addAttribute("json", json);*/
        return "ses/sms/supplier_query/select_by_category";
    }

    /**
     *〈简述〉供应商基本信息
     *〈详细描述〉
     * @author Song Biaowei
     * @param request request
     * @param isRuku 和person一起 判断返回这三个页面（供应商查询、入库供应商查询、品目查询供应商）中的一个
     * @param supplier 供应商实体类
     * @param supplierId 供应商id
     * @param person 和isRuku一起判断返回这三个页面（供应商查询、入库供应商查询、品目查询供应商）中的一个
     * @param model 模型
     * @return String
     */
    @RequestMapping("/essential")
    public String essentialInformation(HttpServletRequest request, Integer isRuku, Supplier supplier, String supplierId, Integer person, Model model) {
        User user = (User) request.getSession().getAttribute("loginUser");
        Integer ps = (Integer) request.getSession().getAttribute("ps");
        if (user.getTypeId() != null && ps != null) {
            person = ps;
        }
        if (user.getTypeId() != null && person != null) {
            request.getSession().setAttribute("ps", person);
            supplierId = user.getTypeId();
        }
        supplier = supplierAuditService.supplierById(supplierId);
        try {
            String provinceName = "";
            String cityName = "";
            Area area = areaService.listById(supplier.getAddress());
            if (area != null) {
                cityName = area.getName();
                Area area1 = areaService.listById(area.getParentId());
                if (area1 != null) {
                    provinceName = area1.getName();
                }
            }
            supplier.setAddress(provinceName + cityName);
        } catch (Exception e) {
            e.printStackTrace();
        }
        getSupplierType(supplier);
        request.getSession().setAttribute("supplierDictionaryData", dictionaryDataServiceI.getSupplierDictionary());
        request.getSession().setAttribute("sysKey", Constant.SUPPLIER_SYS_KEY);
        model.addAttribute("suppliers", supplier);
        //将状态是否入库isRuku存入session里面
        Integer irk = (Integer) request.getSession().getAttribute("irk");
        //第一次进来的时候有值,session为null。
        if (irk == null && isRuku != null) {
            request.getSession().setAttribute("irk", isRuku);
        } else if (irk != null && isRuku == null) {
            isRuku = (Integer) request.getSession().getAttribute("irk");
        } else if (irk != null && isRuku != null) {
            Integer isRuku1 = (Integer) request.getSession().getAttribute("irk");
            if (!isRuku.equals(isRuku1)) {
                request.getSession().removeAttribute("irk");
                request.getSession().setAttribute("irk", isRuku);
            }
        }
        if (isRuku != null && isRuku == 1) {
            model.addAttribute("status", supplier.getStatus());
        }
        if (isRuku != null && isRuku == NUMBER_TWO) {
            model.addAttribute("category", 1);
        }
        model.addAttribute("person", person);
        return "ses/sms/supplier_query/supplierInfo/essential";
    }

    /**
     *〈简述〉供应商财务信息
     *〈详细描述〉
     * @author Song Biaowei
     * @param request request
     * @param supplierFinance 供应商财务实体类
     * @param supplier 供应商基本信息实体类
     * @return String
     */
    @RequestMapping("/financial")
    public String financialInformation(HttpServletRequest request, SupplierFinance supplierFinance, Supplier supplier) {
        String supplierId = supplierFinance.getSupplierId();
        //勾选的供应商类型
        String supplierTypeName = supplierAuditService.findSupplierTypeNameBySupplierId(supplierId);
        request.setAttribute("supplierTypeNames", supplierTypeName);
        request.setAttribute("supplierId", supplierId);
        
        //文件
        if(supplierId!=null){
            List<SupplierFinance> supplierFinance1 = supplierService.get(supplierId).getListSupplierFinances();
            request.setAttribute("financial", supplierFinance1);
        }
        
        request.setAttribute("supplierDictionaryData", dictionaryDataServiceI.getSupplierDictionary());
        request.setAttribute("sysKey",  Constant.SUPPLIER_SYS_KEY);
        request.setAttribute("supplierId", supplierId);
        supplier.setId(supplierId);
        getSupplierType(supplier);
        request.setAttribute("suppliers", supplier);
        return "ses/sms/supplier_query/supplierInfo/financial";
    }
    
    /**
     *〈简述〉股东信息
     *〈详细描述〉
     * @author Song Biaowei
     * @param request request
     * @param supplierStockholder 股东信息实体类
     * @return String
     */
    @RequestMapping("/shareholder")
    public String shareholderInformation(HttpServletRequest request, SupplierStockholder supplierStockholder) {
        String supplierId = supplierStockholder.getSupplierId();
        List<SupplierStockholder> list = supplierAuditService.ShareholderBySupplierId(supplierId);
        request.setAttribute("supplierId", supplierId);
        request.setAttribute("shareholder", list);
        Supplier supplier = new Supplier();
        supplier.setId(supplierId);
        getSupplierType(supplier);
        request.setAttribute("suppliers", supplier);
        return "ses/sms/supplier_query/supplierInfo/shareholder";
    }

    /**
     *〈简述〉物资生产型专业信息  
     *〈详细描述〉
     * @author Song Biaowei
     * @param request request
     * @param supplierMatPro 物资生产型专业信息实体类
     * @return String
     */
    @RequestMapping("/materialProduction")
    public String materialProduction(HttpServletRequest request, SupplierMatPro supplierMatPro) {
        String supplierId = supplierMatPro.getSupplierId();
        List<SupplierCertPro> materialProduction = supplierAuditService.findBySupplierId(supplierId);
        supplierMatPro = supplierService.get(supplierId).getSupplierMatPro();
        request.setAttribute("supplierId", supplierId);
        request.setAttribute("materialProduction", materialProduction);
        request.setAttribute("supplierMatPros", supplierMatPro);
        Supplier supplier = new Supplier();
        supplier.setId(supplierId);
        getSupplierType(supplier);
        request.setAttribute("suppliers", supplier);
        return "ses/sms/supplier_query/supplierInfo/material_production";
    }

    /**
     *〈简述〉物资销售专业信息
     *〈详细描述〉
     * @author Song Biaowei
     * @param request request
     * @param supplierMatSell 物资销售专业信息实体类
     * @return String
     */
    @RequestMapping("/materialSales")
    public String materialSales(HttpServletRequest request, SupplierMatSell supplierMatSell){
        String supplierId = supplierMatSell.getSupplierId();
        //资质资格证书
        List<SupplierCertSell> supplierCertSell = supplierAuditService.findCertSellBySupplierId(supplierId);
        //供应商组织机构和人员
        supplierMatSell = supplierService.get(supplierId).getSupplierMatSell();
        request.setAttribute("supplierCertSell", supplierCertSell);
        request.setAttribute("supplierMatSells", supplierMatSell);
        request.setAttribute("supplierId", supplierId);
        Supplier supplier = new Supplier();
        supplier.setId(supplierId);
        getSupplierType(supplier);
        request.setAttribute("suppliers", supplier);
        return "ses/sms/supplier_query/supplierInfo/material_sales";
    }

    /**
     *〈简述〉物资销售专业信息
     *〈详细描述〉
     * @author Song Biaowei
     * @param request request
     * @param supplierMatEng 物资销售专业信息
     * @return String
     */
    @RequestMapping("/engineering")
    public String engineeringInformation(HttpServletRequest request, SupplierMatEng supplierMatEng){
        String supplierId = supplierMatEng.getSupplierId();
        if (supplierId != null) {
            //资质资格证书信息
            List<SupplierCertEng> supplierCertEng = supplierAuditService.findCertEngBySupplierId(supplierId);
            request.setAttribute("supplierCertEng", supplierCertEng);
            //资质资格信息
            List<SupplierAptitute> supplierAptitute = supplierAuditService.findAptituteBySupplierId(supplierId);
            request.setAttribute("supplierAptitutes", supplierAptitute);
            //组织结构
            supplierMatEng = supplierAuditService.findMatEngBySupplierId(supplierId);
            request.setAttribute("supplierMatEngs", supplierMatEng);
            //注册人人员
            List<SupplierRegPerson> listSupplierRegPersons = supplierService.get(supplierId).getSupplierMatEng().getListSupplierRegPersons();
            request.setAttribute("listRegPerson", listSupplierRegPersons);
        }
        request.setAttribute("supplierId", supplierId);
        Supplier supplier = new Supplier();
        supplier.setId(supplierId);
        getSupplierType(supplier);
        request.setAttribute("suppliers", supplier);
        return "ses/sms/supplier_query/supplierInfo/engineering";
    }

    /**
     *〈简述〉服务专业信息
     *〈详细描述〉
     * @author Song Biaowei
     * @param request request 
     * @param supplierMatSe 服务专业信息
     * @return String
     */
    @RequestMapping("/serviceInformation")
    public String serviceInformation(HttpServletRequest request, SupplierMatServe supplierMatSe){
        String supplierId = supplierMatSe.getSupplierId();
        //资质证书信息
        List<SupplierCertServe> supplierCertSe = supplierAuditService.findCertSeBySupplierId(supplierId);
        //组织结构和人员
        supplierMatSe = supplierAuditService.findMatSeBySupplierId(supplierId);
        request.setAttribute("supplierCertSes", supplierCertSe);
        request.setAttribute("supplierMatSes", supplierMatSe);
        request.setAttribute("supplierId", supplierId);
        Supplier supplier = new Supplier();
        supplier.setId(supplierId);
        getSupplierType(supplier);
        request.setAttribute("suppliers", supplier);
        return "ses/sms/supplier_query/supplierInfo/service_information";
    }

    /**
     *〈简述〉产品信息
     *〈详细描述〉
     * @author Song Biaowei
     * @param request request
     * @param supplierAudit 实体类
     * @param supplier 供应商基本信息实体类
     * @return String
     */
    @RequestMapping("product")
    public String productInformation(HttpServletRequest request, SupplierAudit supplierAudit, Supplier supplier){
        String supplierId = supplierAudit.getSupplierId();
        request.setAttribute("supplierId", supplierId);
       /* if (supplierId != null) {
            List<SupplierItem> listItem = supplierService.get(supplierId).getListSupplierItems();
            request.setAttribute("listItem", listItem);
        }*/
        String supplierTypeName = supplierAuditService.findSupplierTypeNameBySupplierId(supplierId);
        request.setAttribute("supplierTypeNames", supplierTypeName);
        supplier = supplierService.get(supplierId);
        supplier.setId(supplierId);
        getSupplierType(supplier);
        request.setAttribute("suppliers", supplier);
        return "ses/sms/supplier_query/supplierInfo/product";
    }

    /**
     *〈简述〉诚信信息
     *〈详细描述〉
     * @author Song Biaowei
     * @param model 模型
     * @param supplier 实体类
     * @param supplierId 供应商id
     * @param page 当前页
     * @return String
     */
    @RequestMapping(value = "list")
    public String list(Model model, Supplier supplier, String supplierId, Integer page) {
        supplier.setId(supplierId);
        List<Supplier> listSuppliers = supplierLevelService.findSupplier(supplier, page == null ? 1 : page);
        model.addAttribute("listSuppliers", new PageInfo<Supplier>(listSuppliers));
        model.addAttribute("supplierName", supplier.getSupplierName());
        model.addAttribute("supplierId", supplier.getId());
        supplier.setId(supplierId);
        getSupplierType(supplier);
        model.addAttribute("suppliers", supplier);
        return "ses/sms/supplier_query/supplierInfo/cheng_xin";
    }

    /**
     *〈简述〉品目信息
     *〈详细描述〉
     * @author Song Biaowei
     * @param supplierId 供应商id
     * @param model 模型
     * @return String
     */
    @RequestMapping("/item")
    public String item(String supplierId, Model model, HttpServletRequest request) {
        //勾选的供应商类型
        String supplierTypeName = supplierAuditService.findSupplierTypeNameBySupplierId(supplierId);
        request.setAttribute("supplierTypeNames", supplierTypeName);
        request.setAttribute("supplierId", supplierId);
        
        Supplier supplier = supplierService.get(supplierId);
        request.setAttribute("currSupplier", supplier);
        
        //上一步
        String lastUrl = null;
        if(supplierTypeName.contains("服务")){
            lastUrl = request.getContextPath() + "/supplierAudit/serviceInformation.html";
        }else if(supplierTypeName.contains("工程") && lastUrl == null){
            lastUrl = request.getContextPath() + "/supplierAudit/engineering.html";
        }else if(supplierTypeName.contains("销售") && lastUrl == null){
            lastUrl = request.getContextPath() + "/supplierAudit/materialSales.html";
        }else if(supplierTypeName.contains("生产") && lastUrl == null){
            lastUrl = request.getContextPath() + "/supplierAudit/materialProduction.html";
        }else{
            lastUrl = request.getContextPath() + "/supplierAudit/shareholder.html";
        }
        request.setAttribute("lastUrl", lastUrl);
        model.addAttribute("id", supplierId);
        Supplier supplier1 = new Supplier();
        supplier.setId(supplierId);
        getSupplierType(supplier);
        model.addAttribute("suppliers", supplier1);
        return "ses/sms/supplier_query/supplierInfo/item";
    }

    /**
     *〈简述〉修改历史记录
     *〈详细描述〉
     * @author Song Biaowei
     * @param supplierId 供应商id
     * @param model 模型
     * @return String
     */
    @RequestMapping("/showUpdateHistory")
    public String showUpdateHistory(String supplierId, Model model) {
        SupplierEdit se = new SupplierEdit();
        se.setRecordId(supplierId);
        List<SupplierEdit> listEdit = supplierEditService.getAllRecord(se);
        List<String> list = supplierEditService.getList(listEdit);
        model.addAttribute("list", list);
        Supplier supplier = new Supplier();
        supplier.setId(supplierId);
        getSupplierType(supplier);
        model.addAttribute("suppliers", supplier);
        return "ses/sms/supplier_query/supplierInfo/show_update_history";
    }

    /**
     *〈简述〉获取供应商类型
     *〈详细描述〉
     * @author Song Biaowei
     * @param request request
     * @param response response
     * @param fileName 文件名称
     */
    @RequestMapping("/downLoadFile")
    public void download(HttpServletRequest request, HttpServletResponse response, String fileName) {
        if ("".equals(fileName)) {
            super.alert(request, response, "无附件下载 !", true);
            return;
        }
        String stashPath = super.getStashPath(request);
        FtpUtil.startDownFile(stashPath, PropUtil.getProperty("file.upload.path.supplier"), fileName);
        FtpUtil.closeFtp();
        if (fileName != null && !"".equals(fileName)) {
            super.download(request, response, fileName);
        } else {
            super.alert(request, response, "无附件下载 !", true);
        }
        super.removeStash(request, fileName);
    }

    /**
     *〈简述〉获取供应商类型
     *〈详细描述〉
     * @author Song Biaowei
     * @param listSupplier 供应商基本信息集合
     */
    public void getSupplierType(List<Supplier> listSupplier){
        for (Supplier sup : listSupplier) {
            String supplierTypes = supplierService.selectSupplierTypes(sup);
            sup.setSupplierType(supplierTypes);
            DictionaryData dd = DictionaryDataUtil.findById(sup.getBusinessType());
            String business = null;
            if (dd != null) {
                business = dd.getName();
            }
            sup.setBusinessType(business);
        }
    }

    /**
     *〈简述〉获取供应商类型
     *〈详细描述〉
     * @author Song Biaowei
     * @param supplier 供应商基本信息
     */
    public void getSupplierType(Supplier supplier){
        String supplierTypes = supplierService.selectSupplierTypes(supplier);
        supplier.setSupplierType(supplierTypes);
        DictionaryData dd = DictionaryDataUtil.findById(supplier.getBusinessType());
        String business = null;
        if (dd != null) {
            business = dd.getName();
        }
        supplier.setBusinessType(business);
    }
    
    /**
     * @Title: aptitude
     * @author XuQing 
     * @date 2016-12-28 上午11:12:26  
     * @Description:资质文件维护
     * @param @return      
     * @return String
     */
    @RequestMapping(value = "aptitude")
    public String aptitude(Model model, String supplierId) {
        String supplierTypeIds= supplierTypeRelateService.findBySupplier(supplierId);
        
        //勾选的供应商类型
        String supplierTypeName = supplierAuditService.findSupplierTypeNameBySupplierId(supplierId);
        model.addAttribute("supplierTypeNames", supplierTypeName);

        //查询所有的三级品目生产
        List<Category> list2 = getSupplier(supplierId,supplierTypeIds);
        removeSame(list2);
        
        //根据品目id查询所有的证书信息
        List<QualificationBean> list3 = supplierService.queryCategoyrId(list2, 2);
     
        //查询所有的三级品目销售
        List<Category> listSlae = getSale(supplierId,supplierTypeIds);
        removeSame(listSlae);
        
        //根据品目id查询所有的证书信息
        List<QualificationBean> saleQua= supplierService.queryCategoyrId(listSlae, 3);
        
        //查询所有的三级目录工程
        List<Category> listProject = getSale(supplierId,supplierTypeIds);
        removeSame(listProject);
        
        //根据品目id查询所有的工证书
        List<QualificationBean> projectQua= supplierService.queryCategoyrId(listProject, 1);
       
        //查询所有的三级品目服务
        List<Category> listService = getSale(supplierId,supplierTypeIds);
        removeSame(listService);
        
        //根据品目id查询所有的服务证书信息
        List<QualificationBean> serviceQua= supplierService.queryCategoyrId(listService, 1);
       
        //生产证书
        List<Qualification> qaList=new ArrayList<Qualification>();
        List<Qualification> saleList=new ArrayList<Qualification>();
        List<Qualification> projectList=new ArrayList<Qualification>();
        List<Qualification> serviceList=new ArrayList<Qualification>();
           
        if(list3!=null&&list3.size()>0){
            for(QualificationBean qb:list3){
                qaList.addAll(qb.getList());
            }
        }
        
        //销售
        if(saleQua!=null&&saleQua.size()>0){
            for(QualificationBean qb:saleQua){
                saleList.addAll(qb.getList());
            }
        } 
        
        //工程
        if(projectQua!=null&&projectQua.size()>0){
            for(QualificationBean qb:projectQua){
                projectList.addAll(qb.getList());
            }
        } 
        
        //服务
        if(serviceQua!=null&&serviceQua.size()>0){
            for(QualificationBean qb:serviceQua){
                serviceList.addAll(qb.getList());
            }
        } 
       
        //生产
        StringBuffer sbUp=new StringBuffer("");
        StringBuffer sbShow=new StringBuffer("");
        int len=qaList.size()+1;
        for(int i=1;i<len;i++){
            sbUp.append("pUp"+i+",");
            sbShow.append("pShow"+i+",");
         
        }
           
        //销售
        int slaelen=saleList.size()+1;
        for(int i=1;i<slaelen;i++){
            sbUp.append("saleUp"+i+",");
            sbShow.append("saleShow"+i+",");
        }
        if(projectList!=null&&projectList.size()>0){
            int projectlen=projectList.size()+1;
            for(int i=1;i<projectlen;i++){
                sbUp.append("projectUp"+i+",");
                sbShow.append("projectShow"+i+",");
            } 
        }
        
       if(serviceList!=null&&serviceList.size()>0){
           int serverlen=serviceList.size()+1;
           for(int i=1;i<serverlen;i++){
               sbUp.append("serverUp"+i+",");
               sbShow.append("serverShow"+i+",");
           } 
       }

       model.addAttribute("saleUp", sbUp);
       model.addAttribute("saleShow", sbShow);
       model.addAttribute("cateList",  list3);
       model.addAttribute("saleQua", saleQua);
       model.addAttribute("projectQua", projectQua);
       model.addAttribute("supplierId", supplierId);
       model.addAttribute("typeId", DictionaryDataUtil.getId("SUPPLIER_APTITUD"));
       model.addAttribute("sysKey", Constant.SUPPLIER_SYS_KEY);
       return "ses/sms/supplier_query/supplierInfo/aptitude";
    }
    
    /**
     * @Title: contract
     * @author XuQing 
     * @date 2016-12-28 上午11:12:33  
     * @Description:品目合同
     * @param @return      
     * @return String
     */
    @RequestMapping(value = "contract")
    public String contract(Model model, String supplierId) {
        List<SupplierTypeRelate> typeIds= supplierTypeRelateService.queryBySupplier(supplierId);

        String supplierTypeIds = "";
        for(SupplierTypeRelate s : typeIds){
            supplierTypeIds += s.getSupplierTypeId()+ ",";
        }
        //勾选的供应商类型
        String supplierTypeName = supplierAuditService.findSupplierTypeNameBySupplierId(supplierId);
        model.addAttribute("supplierTypeNames", supplierTypeName);
        List<ContractBean> contract = new LinkedList<ContractBean>();
         
         List<ContractBean> saleBean=new LinkedList<ContractBean>();
         
         List<ContractBean> projectBean=new LinkedList<ContractBean>();
         List<ContractBean> serverBean=new LinkedList<ContractBean>();
         
         //合同
         String id1 = DictionaryDataUtil.getId("CATEGORY_ONE_YEAR");
         String id2 = DictionaryDataUtil.getId("CATEGORY_TWO_YEAR");
         String id3 = DictionaryDataUtil.getId("CATEGORY_THREE_YEAR");
         //账单
         String id4 = DictionaryDataUtil.getId("CTAEGORY_ONE_BIL");
         String id5 = DictionaryDataUtil.getId("CTAEGORY_TWO_BIL");      
         String id6 = DictionaryDataUtil.getId("CATEGORY_THREE_BIL");
         int count=0;
         StringBuffer sbUp=new StringBuffer("");
         StringBuffer sbShow=new StringBuffer("");
         String[] strs = supplierTypeIds.split(",");
         List<Category> product=new ArrayList<Category>();
         List<Category> sale=new ArrayList<Category>();
         List<Category> project=new ArrayList<Category>();
         List<Category> server=new ArrayList<Category>();
          for(String type:strs){
              if(type.equals("PRODUCT")){
                  List<Category> list = supplierItemService.getCategory(supplierId,"PRODUCT");
                  removeSame(list);
                  product.addAll(list);
              }
              if(type.equals("SALES")){
                  List<Category> list = supplierItemService.getCategory(supplierId,"SALES");
                  removeSame(list);
                  sale.addAll(list);
              }
              if(type.equals("PROJECT")){
                  List<Category> list = supplierItemService.getCategory(supplierId,"PROJECT");
                  removeSame(list);
                  project.addAll(list);
              }
              if(type.equals("SERVICE")){
                  List<Category> list = supplierItemService.getCategory(supplierId,"SERVICE");
                  removeSame(list);
                  server.addAll(list);
              }
          }
        
         for(Category ca:product){
             ContractBean con=new ContractBean();
             con.setId(ca.getId());
             con.setName(ca.getName());
             
             
             sbUp.append("pUp"+count+",");
             sbShow.append("pShow"+count+",");
             con.setOneContract(id1);
             count++;
             
             
             sbUp.append("pUp"+count+",");
             sbShow.append("pShow"+count+",");
             con.setTwoContract(id2);
             count++;
             
             
             sbUp.append("pUp"+count+",");
             sbShow.append("pShow"+count+",");
             con.setThreeContract(id3);
             count++;
             
             
             sbUp.append("pUp"+count+",");
             sbShow.append("pShow"+count+",");
             con.setOneBil(id4);
             count++;
             
             
             sbUp.append("pUp"+count+",");
             sbShow.append("pShow"+count+",");
             con.setTwoBil(id5);
             count++;
             
             
             sbUp.append("pUp"+count+",");
             sbShow.append("pShow"+count+",");
             con.setTwoBil(id6);
             count++;
     
             contract.add(con);
         }
         
         int sales=0;
         for(Category ca:sale){
             ContractBean con=new ContractBean();
             con.setId(ca.getId());
             con.setName(ca.getName());
             
             
             sbUp.append("saleUp"+sales+",");
             sbShow.append("saleShow"+sales+",");
             con.setOneContract(id1);
             sales++;
             
             
             sbUp.append("saleUp"+sales+",");
             sbShow.append("saleShow"+sales+",");
             con.setTwoContract(id2);
             sales++;
             
             
             sbUp.append("saleUp"+sales+",");
             sbShow.append("saleShow"+sales+",");
             con.setThreeContract(id3);
             sales++;
             
             
             sbUp.append("saleUp"+sales+",");
             sbShow.append("saleShow"+sales+",");
             con.setOneBil(id4);
             sales++;
             
             
             sbUp.append("saleUp"+sales+",");
             sbShow.append("saleShow"+sales+",");
             con.setTwoBil(id5);
             sales++;
             
             
             sbUp.append("saleUp"+sales+",");
             sbShow.append("saleShow"+sales+",");
             con.setTwoBil(id6);
             sales++;
     
             saleBean.add(con);
         }
         
         int projects=0;
         for(Category ca:project){
             ContractBean con=new ContractBean();
             con.setId(ca.getId());
             con.setName(ca.getName());
             
             
             sbUp.append("projectUp"+projects+",");
             sbShow.append("projectShow"+projects+",");
             con.setOneContract(id1);
             projects++;
             
             
             sbUp.append("projectUp"+projects+",");
             sbShow.append("projectShow"+projects+",");
             con.setTwoContract(id2);
             projects++;
             
             
             sbUp.append("projectUp"+projects+",");
             sbShow.append("projectShow"+projects+",");
             con.setThreeContract(id3);
             projects++;
             
             
             sbUp.append("projectUp"+projects+",");
             sbShow.append("projectShow"+projects+",");
             con.setOneBil(id4);
             projects++;
             
             
             sbUp.append("projectUp"+projects+",");
             sbShow.append("projectShow"+projects+",");
             con.setTwoBil(id5);
             projects++;
             
             
             sbUp.append("projectUp"+projects+",");
             sbShow.append("projectShow"+projects+",");
             con.setTwoBil(id6);
             projects++;
     
             projectBean.add(con);
         }
         
         int servers=0;
         for(Category ca:server){
             ContractBean con=new ContractBean();
             con.setId(ca.getId());
             con.setName(ca.getName());
             
             
             sbUp.append("serUp"+servers+",");
             sbShow.append("serpShow"+servers+",");
             con.setOneContract(id1);
             servers++;
             
             
             sbUp.append("serUp"+servers+",");
             sbShow.append("serpShow"+servers+",");
             con.setTwoContract(id2);
             servers++;
             
             
             sbUp.append("serUp"+servers+",");
             sbShow.append("serpShow"+servers+",");
             con.setThreeContract(id3);
             servers++;
             
             
             sbUp.append("serUp"+servers+",");
             sbShow.append("serpShow"+servers+",");
             con.setOneBil(id4);
             servers++;
             
             
             sbUp.append("serUp"+servers+",");
             sbShow.append("serpShow"+servers+",");
             con.setTwoBil(id5);
             servers++;
             
             
             sbUp.append("serUp"+servers+",");
             sbShow.append("serpShow"+servers+",");
             con.setTwoBil(id6);
             servers++;
     
             serverBean.add(con);
         }
         
         
         model.addAttribute("serverBean", serverBean);
         model.addAttribute("projectBean", projectBean);
         model.addAttribute("saleBean", saleBean); 
         model.addAttribute("contract", contract);  
         model.addAttribute("sbUp", sbUp);
         model.addAttribute("sbShow", sbShow);
         List<Integer> years = supplierService.getThressYear();
         model.addAttribute("years", years);
         model.addAttribute("supplierTypeIds", supplierTypeIds);
         model.addAttribute("supplierId", supplierId);
         model.addAttribute("sysKey", Constant.SUPPLIER_SYS_KEY);
        return "ses/sms/supplier_query/supplierInfo/contract";
    }
    
    /**
     * @Title: removeSame
     * @author XuQing 
     * @date 2017-1-4 下午7:23:33  
     * @Description:去重
     * @param @param list      
     * @return void
     */
   public void removeSame(List<Category> list) {
       for (int i = 0; i < list.size() - 1; i++) {
           for (int j = list.size() - 1; j > i; j--) {
               if (list.get(j).getId().equals(list.get(i).getId())) {
                   list.remove(j);
               }
           }
       }
    }
 //生产
   public List<Category> getSupplier(String supplierId,String code){
       List<Category> categoryList=new ArrayList<Category>();
       String[] types = code.split(",");
       for(String s:types){
           String   categoryId="";
              if (s != null ) {
                  if(s.equals("PRODUCT")){
                      categoryId = DictionaryDataUtil.getId("GOODS");
                      List<SupplierItem> category = supplierItemService.getCategory(supplierId, categoryId,s);
                      for(SupplierItem c:category){
                        Category cate= categoryService.selectByPrimaryKey(c.getCategoryId());
                        cate.setParentId(c.getId());
                        categoryList.add(cate);
                        }
                    }
              }
       }
         return  categoryList;
   }
   
   //销售
   public List<Category>  getSale(String supplierId,String code){
           List<Category> categoryList=new ArrayList<Category>();
           
           String[] types = code.split(",");
           for(String s:types){
               String   categoryId="";
                  if (s != null ) {
                      if(s.equals("SALES")){
                          categoryId = DictionaryDataUtil.getId("GOODS");
                          List<SupplierItem> category = supplierItemService.getCategory(supplierId, categoryId,s);
                          for(SupplierItem c:category){
                            Category cate= categoryService.selectByPrimaryKey(c.getCategoryId());
                            cate.setParentId(c.getId());
                            categoryList.add(cate);
                           }
                     }
                 }
           }
           
           return categoryList;
       }
       
       
       //工程
       public List<Category>  getProject(String supplierId,String code){
           List<Category> categoryList=new ArrayList<Category>();
           
           String[] types = code.split(",");
           for(String s:types){
               String   categoryId="";
                  if (s != null ) {
                      if(s.equals("PROJECT") ){
                          categoryId = DictionaryDataUtil.getId("PROJECT");
                          List<SupplierItem> category = supplierItemService.getCategory(supplierId, categoryId,s);
                         for(SupplierItem c:category){
                            Category cate= categoryService.selectByPrimaryKey(c.getCategoryId());
                            cate.setParentId(c.getId());
                            categoryList.add(cate);
                    
                           }
                    }
               }
           
           }
           
           return categoryList;
       }
       
       //服务
       public List<Category>  getServer(String supplierId,String code){
           List<Category> categoryList=new ArrayList<Category>();
           
           String[] types = code.split(",");
           for(String s:types){
               String   categoryId="";
                  if (s != null ) {
                      if(s.equals("SERVICE")){
                      List<SupplierItem> category = supplierItemService.getCategory(supplierId, categoryId,s);
                        for(SupplierItem c:category){
                            Category cate= categoryService.selectByPrimaryKey(c.getCategoryId());
                            cate.setParentId(c.getId());
                            categoryList.add(cate);
                           }
                      }
                }
           }
           return categoryList;
       }
}
