package ses.controller.sys.sms;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import ses.model.bms.Area;
import ses.model.bms.Todos;
import ses.model.bms.User;
import ses.model.sms.Supplier;
import ses.model.sms.SupplierDictionaryData;
import ses.model.sms.SupplierEdit;
import ses.model.sms.SupplierReason;
import ses.service.bms.AreaServiceI;
import ses.service.bms.DictionaryDataServiceI;
import ses.service.bms.TodosService;
import ses.service.sms.SupplierAudReasonService;
import ses.service.sms.SupplierAuditService;
import ses.service.sms.SupplierEditService;
import ses.service.sms.SupplierService;
import ses.util.PropUtil;
import ses.util.ValidateUtils;

import com.alibaba.fastjson.JSONArray;
import com.github.pagehelper.PageInfo;
import common.constant.Constant;
import common.model.UpdateHistory;
import common.model.UploadFile;
import common.service.UpdateHistoryService;
import common.service.UploadService;

/**
 * 版权：(C) 版权所有 
 * <简述>供应商基本信息变更控制层
 * <详细描述>供应商基本信息变更控制层
 * @author   Song Biaowei
 * @version  
 * @since
 * @see
 */
@Controller
@Scope("prototype")
@RequestMapping("/supplier_edit")
public class SupplierEditController extends BaseSupplierController {
    /**
     * 定义常量2
     */
    private static final int NUMBER_TWO = 2;
    /**
     * 定义常量4
     */    
    private static final int NUMBER_FOUR = 4;
    
    /**
     * 供应商信息修改服务层
     */
    @Autowired
    private SupplierEditService supplierEditService;
    
    /**
     * 文件上传服务层
     */    
    @Autowired
    private UploadService uploadService;
    
    /**
     * 发送待办服务层    
     */
    @Autowired
    private TodosService todosService;
    
    /**
     * 供应商信息修改后审核原因服务层
     */
    @Autowired
    private SupplierAudReasonService supplierAudReasonService; 
    
    /**
     * 供应商审核服务层
     */
    @Autowired
    private SupplierAuditService supplierAuditService;
    
    /**
     * 数据字典服务层 
     */
    @Autowired
    private DictionaryDataServiceI dictionaryDataServiceI;
    
    /**
     *供应商注册服务层 
     */
    @Autowired
    private SupplierService supplierService;
    
    /**
     *地区服务层 
     */
    @Autowired
    private AreaServiceI areaService;
    
    
    @Autowired
    private UpdateHistoryService updateHistoryService;

    /**
     *〈简述〉供应商修改记录列表
     *〈详细描述〉供应商修改记录列表
     * @author Song Biaowei
     * @param se 条件查询封装实体
     * @param request request
     * @param page 当前页数
     * @param model 模型
     * @return String
     */
    @RequestMapping(value = "list")
    public String registerStart(SupplierEdit se, HttpServletRequest request, Integer page, Model model){
        User user1 = (User) request.getSession().getAttribute("loginUser");
        //session过期转到登陆页面
        if(user1==null)
        	return "redirect:../index/sign.html";
        se.setRecordId(user1.getTypeId());
        String supplier_id=user1.getTypeId();
        //获取供应商修改历史
        List<UpdateHistory> list= updateHistoryService.queryByUpdateId(supplier_id);
        //转对象
        
        
     //   JSONArray json1 = JSONArray.fromObject(json);
  	 // List<PurchaseRequired> list = (List<PurchaseRequired>)JSONArray.toCollection(json1, PurchaseRequired.class);
        
        List<SupplierEdit> seList = supplierEditService.findAll(se, page == null ? 1 : page);
        request.setAttribute("seList" , new PageInfo<>(seList));
        model.addAttribute("id" , user1.getTypeId());
        return "ses/sms/supplier_apply_edit/list";
    }
    
    /**
     *〈简述〉变更
     *〈详细描述〉增加一条变更记录
     * @author Song Biaowei
     * @param request request
     * @param id 供应商id
     * @param model 模型
     * @return String
     */
    @RequestMapping(value = "add")
    public String register(HttpServletRequest request, String id, Model model){
        Supplier supplier = supplierAuditService.supplierById(id);
        supplier.setAddress(getAddressName(supplier.getAddress()));
        request.getSession().setAttribute("supplierDictionaryData", dictionaryDataServiceI.getSupplierDictionary());
        request.getSession().setAttribute("sysKey", Constant.SUPPLIER_SYS_KEY);
        model.addAttribute("suppliers", supplier);
        return "ses/sms/supplier_apply_edit/add";
    }

    /**
     *〈简述〉保存
     *〈详细描述〉保存变更数据
     * @author Song Biaowei
     * @param se 实体
     * @param request request
     * @param model 模型
     * @param procurementDepId 采购机构ID
     * @return String
     * @throws IOException 异常处理
     */
    @RequestMapping(value = "save")
    public String registerEnd(SupplierEdit se, String procurementDepId, HttpServletRequest request, Model model) throws IOException{
        User user1 = (User) request.getSession().getAttribute("loginUser");
        if (validateBasicInfo(request, model, se)){
            se.setRecordId(se.getId());
            se.setId(null);
            se.setCreateDate(new Timestamp(new Date().getTime()));
            se.setStatus((short) 0);
            supplierEditService.insertSelective(se);
            Todos todo = new Todos();
            todo.setSenderId(user1.getId());
            todo.setOrgId(procurementDepId);
            todo.setPowerId(PropUtil.getProperty("gysedit"));
            todo.setUndoType((short) 1);
            todo.setName("供应商变更审核");
            todo.setIsDeleted((short) 0);
            todo.setCreatedAt(new Date());
            todo.setUrl("supplier_edit/audit.html?id=" + se.getId());
            todosService.insert(todo);
            return "redirect:list.html";
        }
        Supplier supplier = supplierAuditService.supplierById(se.getId());
        supplier.setAddress(getAddressName(supplier.getAddress()));
        request.getSession().setAttribute("supplierDictionaryData", dictionaryDataServiceI.getSupplierDictionary());
        request.getSession().setAttribute("sysKey", Constant.SUPPLIER_SYS_KEY);
        model.addAttribute("suppliers", se);
        return "ses/sms/supplier_apply_edit/add";
    }

    /**
     *〈简述〉审核修改信息
     *〈详细描述〉审核修改信息
     * @author Song Biaowei
     * @param id 修改记录id
     * @param model 模型
     * @param req request
     * @return String
     */
    @RequestMapping(value = "audit")
    public String aduit(String id, Model model, HttpServletRequest req){
        //修改后的
        SupplierEdit se = supplierEditService.selectByPrimaryKey(id);
        //修改前的
        Supplier supplier = supplierAuditService.supplierById(se.getRecordId());
        supplier.setAddress(getAddressName(supplier.getAddress()));
        Supplier result = supplierEditService.getResult(se, supplier);
        req.getSession().setAttribute("supplierDictionaryData", dictionaryDataServiceI.getSupplierDictionary());
        req.getSession().setAttribute("sysKey", Constant.SUPPLIER_SYS_KEY);
        req.getSession().setAttribute("supplierId_edit", se.getId());
        req.getSession().setAttribute("result", result);
        model.addAttribute("currSupplier", se);
        return "ses/sms/supplier_apply_edit/audit";
    }

    /**
     *〈简述〉审核完成
     *〈详细描述〉审核通过或者退回
     * @author Song Biaowei
     * @param id 审核记录id
     * @param auditStatus 审核通过或者退回状态
     * @param model 模型
     * @param req request
     * @return String
     */
    @RequestMapping(value = "auditEnd")
    public String auditEnd(String id, Short auditStatus, Model model, HttpServletRequest req){
        SupplierEdit se = new SupplierEdit();
        se.setId(id);
        se.setStatus(auditStatus);
        if (auditStatus == 1){
            this.copyToSupplier(se.getId());
            se.setCreateDate(new Timestamp(new Date().getTime()));
            supplierEditService.updateByPrimaryKey(se);
        } else {
            supplierEditService.updateByPrimaryKey(se);
        }
        todosService.updateIsFinish("supplier_edit/audit.html?id=" + id);
        return "redirect:/login/home.html";
    }

    /**
     *〈简述〉查看内容
     *〈详细描述〉查看变更记录内容
     * @author Song Biaowei
     * @param id 变更记录id
     * @param model 模型
     * @param request request
     * @return String
     */
    @RequestMapping(value = "view")
    public String view(String id, Model model, HttpServletRequest request){
        SupplierEdit se = supplierEditService.selectByPrimaryKey(id);
        model.addAttribute("suppliers", se);
        request.getSession().setAttribute("supplierDictionaryData", dictionaryDataServiceI.getSupplierDictionary());
        request.getSession().setAttribute("sysKey", Constant.SUPPLIER_SYS_KEY);
        return "ses/sms/supplier_apply_edit/view";
    }

    /**
     *〈简述〉查看审核不通过理由
     *〈详细描述〉查看审核不通过理由，只有退回的状态下才会显示
     * @author Song Biaowei
     * @param sr 审核原因实体
     * @param request request
     * @param page 当前页
     * @param model 模型
     * @return String
     */
    @RequestMapping(value = "reasonList")
    public String reasonList(SupplierReason sr, HttpServletRequest request, Integer page, Model model){
        List<SupplierReason> srList = supplierAudReasonService.findAll(sr);
        model.addAttribute("srList", srList);
        return "ses/sms/supplier_apply_edit/audits";
    }

    /**
     *〈简述〉审核不通过信息的时候通过ajax保存到数据库
     *〈详细描述〉审核不通过信息的时候通过ajax保存到数据库
     * @author Song Biaowei
     * @param sr 审核原因实体
     * @param request request
     * @throws IOException 异常处理
     */
    @RequestMapping(value = "saveReason")
    @ResponseBody
    public void saveReason(SupplierReason sr, HttpServletRequest request) throws IOException{
        supplierAudReasonService.insertSelective(sr);
    }
    
    /**
     *〈简述〉赋值
     *〈详细描述〉审核成功就给变更表的值赋值给supplier实体并且更新supplier
     * @author Song Biaowei
     * @param seId 变更记录id
     */
    public void copyToSupplier(String seId){
        SupplierEdit supplierEdit = supplierEditService.selectByPrimaryKey(seId);
        Supplier supplier = supplierAuditService.supplierById(supplierEdit.getRecordId());
        //第一次保存的时候要给原始数据保存一条，用来后面做对比
        SupplierEdit supplierEdit1 = supplierEditService.setToSupplierEdit(supplier);
        //开始改变供应商的值
        Supplier supplier1 = supplierEditService.setToSupplier(supplierEdit);
        Area area = new Area();
        area.setName(supplier1.getAddress().split(",")[1]);
        List<Area> listArea = areaService.listByArea(area);
        supplier1.setAddress(listArea.get(0).getId());
        supplier1.setId(supplier.getId());
        SupplierEdit supplierEdit2 = new SupplierEdit();
        supplierEdit2.setRecordId(supplier.getId());
        supplierEdit2.setStatus((short) NUMBER_FOUR);
        //如果是第一次审核通过的时候要给原始数据保存下来
        if (supplierEditService.getAllbySupplierId(supplierEdit2).size() == 0){
            String provinceName = "";
            String cityName = "";
            Area areaSe = areaService.listById(supplierEdit1.getAddress());
            if (areaSe != null) {
                cityName = areaSe.getName();
                Area area1 = areaService.listById(areaSe.getParentId());
                if (area1 != null) {
                    provinceName = area1.getName();
                }
            }
            supplierEdit1.setAddress(provinceName + cityName);
            supplierEdit1.setStatus((short) NUMBER_FOUR);
            supplierEdit1.setCreateDate(new Timestamp(new Date().getTime()));
            supplierEditService.insertSelective(supplierEdit1);
        }
        supplierService.perfectBasic(supplier1);
    }
    
    /**
     *〈简述〉根据字典表获取地址名称
     *〈详细描述〉根据字典表获取地址名称
     * @author Song Biaowei
     * @param str 变更记录id
     * @return String
     */
    public String getAddressName(String str){
        String provinceName = "";
        String cityName = "";
        Area area = areaService.listById(str);
        if (area != null){
            cityName = area.getName();
            Area area1 = areaService.listById(area.getParentId());
            if (area1 != null){
                provinceName = area1.getName();
            }
        }
        return provinceName + "," + cityName;
    }
    
    /**
     *〈简述〉校验
     *〈详细描述〉供应商变更校验
     * @author Song Biaowei
     * @param se 供应商信息实体
     * @param request request
     * @param model 模型
     * @return boolean
     */
    public boolean validateBasicInfo(HttpServletRequest request, Model model, SupplierEdit se) {
        int count = 0;
        if (se.getSupplierName() == null || !se.getSupplierName().trim().matches("^.{1,80}$")) {
            model.addAttribute("err_msg_supplierName", "不能为空!");
            count++;
        }
        if (se.getWebsite() == null || !ValidateUtils.Url(se.getWebsite())) {
            model.addAttribute("err_msg_website", "格式错误 !");
            count++;
        }
        if (se.getFoundDate() == null) {
            model.addAttribute("err_msg_foundDate", "不能为空 !");
            count++;
        }
        if (se.getAddress() == null) {
            model.addAttribute("err_msg_address", "不能为空!");
            count++;
        }
        if (se.getBankName() == null || !se.getBankName().trim().matches("^.{1,80}$")) {
            model.addAttribute("err_msg_bankName", "不能为空 !");
            count++;
        }
        if (se.getBankAccount() == null || !se.getBankAccount().matches("^\\d{16}||\\d{19}$")) {
            model.addAttribute("err_msg_bankAccount", "格式不正确 !");
            count++;
        }
        if (se.getPostCode() == null || !ValidateUtils.Zipcode(se.getPostCode())) {
            model.addAttribute("err_msg_postCode", "格式不正确 !");
            count++;
        }
        if (se.getLegalName() == null) {
            model.addAttribute("err_legalName", "不能为空 !");
            count++;
        }
        if (se.getLegalIdCard() == null) {
            model.addAttribute("err_legalCard", "不能为空 !");
            count++;
        }
        if (se.getLegalIdCard() != null && !se.getLegalIdCard().matches("^(\\d{15}$|^\\d{18}$|^\\d{17}(\\d|X|x))$")) {
            model.addAttribute("err_legalCard", "身份证号码格式不正确 !");
            count++;
        }
        if (se.getLegalMobile() == null) {
            model.addAttribute("err_legalMobile", "不能为空 !");
            count++;
        }

        if (se.getLegalTelephone() == null || !se.getLegalTelephone().matches("^1[0-9]{10}$")) {
            model.addAttribute("err_legalPhone", "格式不正确 !");
            count++;
        }
        if (se.getContactName() == null) {
            model.addAttribute("err_conName", "不能为空 !");
            count++;
        }
        
        if (se.getContactFax() == null) {
            model.addAttribute("err_fax", "格式不正确 !");
            count++;
        }
        
        if (se.getContactMobile() == null) {
            model.addAttribute("err_catMobile", "格式不正确 !");
            count++;
        }
        if (se.getContactTelephone() == null || !se.getContactTelephone().matches("^1[0-9]{10}$")){
            model.addAttribute("err_catTelphone", "格式不正确 !");
            count++;
        }
        if (se.getContactEmail() == null
            || !se.getContactEmail().matches("^([a-zA-Z0-9]+[_|\\_|\\.]?)*[a-zA-Z0-9]+@([a-zA-Z0-9]+[_|\\_|\\.]?)*[a-zA-Z0-9]+\\.[a-zA-Z]{2,3}$")){
            model.addAttribute("err_catEmail", "格式不正确 !");
            count++;
        }
        if (se.getContactAddress() == null) {
            model.addAttribute("err_conAddress", "不能为空!");
            count++;
        }
        if (se.getCreditCode() == null) {
            model.addAttribute("err_creditCide", "不能为空!");
            count++;
        }
        
        if (se.getRegistAuthority() == null) {
            model.addAttribute("err_reAuthoy", "不能为空 !");
            count++;
        }
        if (se.getRegistFund() == null) {
            model.addAttribute("err_fund", "不能为空 !");
            count++;
        }
        if (se.getRegistFund() != null && !se.getRegistFund().toString().matches("^[1-9]\\d*.\\d*|0.\\d*[1-9]\\d*$")){
            model.addAttribute("err_fund", "资金不能小于0或者是格式不正确 !");
            count++;
        }
        if (se.getBusinessStartDate() == null) {
            model.addAttribute("err_sDate", "营业开始时间不能为空 !");
            count++;
        }
        if (se.getBusinessEndDate() == null) {
            model.addAttribute("err_eDate", "营业截至时间不能为空 !");
            count++;
        }
        if (se.getBusinessAddress() == null) {
            model.addAttribute("err_bAddress", "经营地址不能为空!");
            count++;
        }
        if (se.getBusinessPostCode() == null) {
            model.addAttribute("err_bCode", "不能为空!");
            count++;
        }
        if (se != null && se.getBusinessPostCode() != null && !ValidateUtils.Zipcode(se.getBusinessPostCode().toString())){
            model.addAttribute("err_bCode", "邮编格式不正确!");
            count++;
        }
        SupplierDictionaryData supplierDictionary = dictionaryDataServiceI.getSupplierDictionary();
        //* 近三个月完税凭证
        List<UploadFile> tlist = uploadService.getFilesOther(se.getId(), supplierDictionary.getSupplierTaxCert(), Constant.SUPPLIER_SYS_KEY.toString());
        if (tlist != null && tlist.size() <= 0){
            count++;
            model.addAttribute("err_taxCert", "请上传文件!");
        }
        //* 近三年银行基本账户年末对账单
        List<UploadFile> blist = uploadService.getFilesOther(se.getId(), supplierDictionary.getSupplierBillCert(), Constant.SUPPLIER_SYS_KEY.toString());
        if (blist != null && blist.size() <= 0) {
            count++;
            model.addAttribute("err_bil", "请上传文件!");
        }
        //近三个月缴纳社会保险金凭证
        List<UploadFile> slist = uploadService.getFilesOther(se.getId(), supplierDictionary.getSupplierSecurityCert(), Constant.SUPPLIER_SYS_KEY.toString());
        if (slist != null && slist.size() <= 0) {
            count++;
            model.addAttribute("err_security", "请上传文件!");
        }
        //近三年内无重大违法记录声明
        List<UploadFile> bearlist = uploadService.getFilesOther(se.getId(), supplierDictionary.getSupplierBearchCert(), Constant.SUPPLIER_SYS_KEY.toString());
        if (bearlist != null && bearlist.size() <= 0){
            count++;
            model.addAttribute("err_bearch", "请上传文件!");
        }
        
        //供应商执照
        List<UploadFile> list = uploadService.getFilesOther(se.getId(), supplierDictionary.getSupplierBusinessCert(), Constant.SUPPLIER_SYS_KEY.toString());
        if (list != null && list.size() <= 0) {
            count++;
            model.addAttribute("err_business", "请上传文件!");
        }
        if (count > 0) {
            return false;
        }
        return true;
    }
}
