package bss.controller.ppms;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;

import common.constant.Constant;
import common.constant.StaticVariables;

import ses.model.bms.DictionaryData;
import ses.model.sms.Quote;
import ses.service.sms.SupplierQuoteService;
import ses.util.DictionaryDataUtil;

import bss.controller.base.BaseController;
import bss.model.ppms.AdvancedDetail;
import bss.model.ppms.AdvancedPackages;
import bss.model.ppms.AdvancedProject;
import bss.model.ppms.SupplierCheckPass;
import bss.model.ppms.theSubject;
import bss.service.ppms.AduitQuotaService;
import bss.service.ppms.AdvancedDetailService;
import bss.service.ppms.AdvancedPackageService;
import bss.service.ppms.AdvancedProjectService;
import bss.service.ppms.FlowMangeService;
import bss.service.ppms.SupplierCheckPassService;
import bss.service.ppms.theSubjectService;

@Controller
@Scope("prototype")
@RequestMapping("/adWinningSupplier")
public class AdWinningSupplierController extends BaseController {
    
    /** SCCUESS */
    private static final String SUCCESS = "SCCUESS";
    /** ERROR */
    private static final String ERROR = "ERROR";
    
    @Autowired
    private SupplierCheckPassService checkPassService;
    
    @Autowired
    private AdvancedPackageService packageService;
    
    @Autowired
    private SupplierQuoteService supplierQuoteService;

    @Autowired
    private AdvancedProjectService projectService;
    
    @Autowired
    private FlowMangeService flowMangeService;
    
    @Autowired
    private theSubjectService theSubjectService;
    
    @Autowired
    private AdvancedDetailService detailService;
    
    /**
     * 
     *〈确认中标供应商页面〉
     *〈详细描述〉
     * @author FengTian
     * @param model
     * @param projectId
     * @param flowDefineId
     * @return
     */
    @RequestMapping("/selectSupplier")
    public String selectWinningSupplier(Model model, String projectId, String flowDefineId){
        AdvancedProject project = projectService.selectById(projectId);
        if(project != null){
            String purchaseType = DictionaryDataUtil.getId("DYLY");
            if(!purchaseType.equals(project.getPurchaseType())){
                List<AdvancedPackages> packages = packageService.listSupplierCheckPass(project.getId());
                if(packages != null && !packages.isEmpty()){
                    model.addAttribute("packList", packages);
                }
            } else {
                List<AdvancedPackages> packages = packageService.notSupplierCheckPass(project.getId());
                if(packages != null && !packages.isEmpty()){
                    model.addAttribute("packages", packages);
                    //获取已有中标供应商的包组
                    String[] packcount = checkPassService.selectWonBid(project.getId());
                    if (packages.size() != packcount.length){
                        model.addAttribute("error", ERROR);
                    }
                }
            }
        }
        model.addAttribute("projectId", projectId);
        model.addAttribute("flowDefineId", flowDefineId);
        return "bss/ppms/advanced_project/winning_supplier/list";
    }
    
    /**
     * 
     *〈获取包下所有供应商信息〉
     *〈详细描述〉
     * @author FengTian
     * @param model
     * @param packageId
     * @param flowDefineId
     * @param projectId
     * @param sq
     * @param view
     * @return
     */
    @RequestMapping("/confirmSupplier")
    public String confirmSupplier(Model model, String packageId, String flowDefineId, String projectId, HttpServletRequest request){
        SupplierCheckPass checkPass = new SupplierCheckPass();
        checkPass.setPackageId(packageId);
        List<SupplierCheckPass> listSupplierCheckPass = checkPassService.listCheckPass(checkPass);
        if(listSupplierCheckPass != null && !listSupplierCheckPass.isEmpty()){
            for (SupplierCheckPass supplierCheckPass : listSupplierCheckPass) {
                //查询报价历史记录
                if(supplierCheckPass != null && supplierCheckPass.getSupplier() != null ){
                    Quote quote = new Quote();
                    quote.setPackageId(packageId);
                    quote.setSupplierId(supplierCheckPass.getSupplier().getId());
                    List<Quote> quoteList = supplierQuoteService.selectQuoteHistoryList(quote);
                    supplierCheckPass.getSupplier().setListQuote(quoteList);
                }
            }
            model.addAttribute("supplierCheckPass", listSupplierCheckPass);
        }
        //获取已有中标供应商的包组
        String[] packcount = checkPassService.selectWonBid(projectId);
        List<AdvancedPackages> packList = packageService.listSupplierCheckPass(projectId);
        if (packList.size() != packcount.length){
            model.addAttribute("error", ERROR);
        }
        flowMangeService.flowExe(request, flowDefineId, projectId, 2);
        model.addAttribute("flowDefineId", flowDefineId);
        model.addAttribute("projectId", projectId);
        model.addAttribute("packageId", packageId);
        return "bss/ppms/advanced_project/winning_supplier/supplier_check";
    }
    
    /**
     * 
     *〈简述〉判断占比数量是否为小数
     *〈详细描述〉
     * @author FengTian
     * @param ids checkpassId的一个字符串组
     * @param priceRatios 传过来的占比的一个字符串组
     * @return 路径---确认供应商页面
     */
    @ResponseBody
    @RequestMapping("/changeRatioByCheckpassId")
    public String changeRatioByCheckpassId(String ids, String priceRatios){
        if(StringUtils.isNotBlank(ids) && StringUtils.isNotBlank(priceRatios)){
            Boolean flag = checkPassService.checkpassId(ids, priceRatios);
            if(flag){
                return SUCCESS;
            } else {
                return ERROR;
            }
        }
        return StaticVariables.FAILED;
    }

    /**
     * 
     *〈获取供应商〉
     *〈详细描述〉
     * @author FengTian
     * @param model
     * @param passquote
     * @param supplierIds
     * @param packageId
     * @param ids
     * @param priceRatios
     * @param flowDefineId
     * @param projectId
     * @return
     */
    @RequestMapping("/packageSupplier")
    public String selectpackage(HttpServletRequest request, Model model, String passquote, String supplierIds, String packageId, String ids, String priceRatios, String flowDefineId,String projectId){
        if(StringUtils.isNotBlank(priceRatios) && StringUtils.isNotBlank(ids) && StringUtils.isNotBlank(packageId)){
            checkPassService.changeSupplierWonTheBidding(ids,priceRatios);
        }
        if(StringUtils.isNotBlank(supplierIds)){
            List<SupplierCheckPass> checkPassSupplier = checkPassService.checkPassSupplier(supplierIds, packageId);
            if(checkPassSupplier != null && !checkPassSupplier.isEmpty()){
                model.addAttribute("supplierCheckPass", checkPassSupplier);
            }
        }
        //查询报价历史记录
        Quote quote = new Quote();
        quote.setPackageId(packageId);
        List<Quote> quoteList = supplierQuoteService.selectQuoteHistoryList(quote);
        if (quoteList != null && !quoteList.isEmpty()) {
            if (quoteList.get(0).getQuotePrice() == null || quoteList.get(0).getQuotePrice().equals(new BigDecimal(0))){
                model.addAttribute("quote", 0);//提示唱总价
            }else if(quoteList.get(0).getQuotePrice() != null&&!quoteList.get(0).getQuotePrice().equals(new BigDecimal(0))){
                model.addAttribute("quote", 1);//提示唱明细
            }
        }
        List<theSubject> detailList = theSubjectService.selectByPackagesId(packageId);
        model.addAttribute("detailList", detailList);
        model.addAttribute("flowDefineId", flowDefineId);
        model.addAttribute("projectId", projectId);
        model.addAttribute("packageId", packageId);
        model.addAttribute("supplierIds", supplierIds);
        flowMangeService.flowExe(request, flowDefineId, projectId, 2);
        return "bss/ppms/advanced_project/winning_supplier/supplier_list";
    }
    
    /**
     * 
     *〈查看〉
     *〈详细描述〉
     * @author Administrator
     * @param request
     * @param flowDefineId
     * @param projectId
     * @param packageId
     * @param supplierId
     * @return
     */
    @RequestMapping("/viewPackageSupplier")
    public String viewPackageSupplier(Model model, HttpServletRequest request, String flowDefineId, String projectId, String packageId, String supplierIds){
        SupplierCheckPass checkPass = new SupplierCheckPass();
        checkPass.setPackageId(packageId);
        checkPass.setIsWonBid((short)1);
        List<SupplierCheckPass> listCheck = checkPassService.listCheckPassBD(checkPass);
        if(listCheck != null && !listCheck.isEmpty()){
            String[] rat = ratio(listCheck.size());
            for (int i = 0; i < listCheck.size(); i++ ) {
                if (listCheck.get(i).getIsWonBid() == 1 && listCheck.get(i).getWonPrice() == null && listCheck.get(i).getPriceRatio() ==null ){
                  Double  price = (Double.parseDouble(rat[i])/100)*Double.parseDouble(listCheck.get(0).getTotalPrice().toString());
                  SupplierCheckPass supplierCheckPass = listCheck.get(i);
                  supplierCheckPass.setWonPrice(new BigDecimal(price).setScale(2, BigDecimal.ROUND_HALF_UP));
                  supplierCheckPass.setPriceRatio(rat[i]);
                  checkPassService.update(supplierCheckPass); 
                }
            }
        }
        
        if(StringUtils.isNotBlank(supplierIds)){
            List<SupplierCheckPass> checkPassSupplier = checkPassService.checkPassSupplier(supplierIds, packageId);
            if(checkPassSupplier != null && !checkPassSupplier.isEmpty()){
                model.addAttribute("supplierCheckPass", checkPassSupplier);
            }
        }
      //查询报价历史记录
        Quote quote = new Quote();
        quote.setPackageId(packageId);
        List<Quote> quoteList = supplierQuoteService.selectQuoteHistoryList(quote);
        if (quoteList != null && !quoteList.isEmpty()) {
            if (quoteList.get(0).getQuotePrice() == null || quoteList.get(0).getQuotePrice().equals(new BigDecimal(0))){
                model.addAttribute("quote", 0);//提示唱总价
            }else if(quoteList.get(0).getQuotePrice() != null&&!quoteList.get(0).getQuotePrice().equals(new BigDecimal(0))){
                model.addAttribute("quote", 1);//提示唱明细
            }
        }
        flowMangeService.flowExe(request, flowDefineId, projectId, 1);
        model.addAttribute("projectId", projectId);
        model.addAttribute("flowDefineId", flowDefineId);
        model.addAttribute("packageId", packageId);
        model.addAttribute("supplierIds", supplierIds);
        return "bss/ppms/advanced_project/winning_supplier/view_supplier";
    }
    
    /**
     * 
     *〈简述〉供应商上传凭证
     *〈详细描述〉
     * @author Wang Wenshuai
     * @param model
     * @param packageId
     * @param flowDefineId
     * @return
     */
    @RequestMapping("/supplierUpload")
    public String supplierUpload(Model model,String projectId, String packageId, String flowDefineId, String checkPassId){
      //凭证上传
      model.addAttribute("supplierVoucher", DictionaryDataUtil.getId("CHECK_PASS_SUPPLIER_BGYJ"));

      //招标系统key
      Integer tenderKey = Constant.TENDER_SYS_KEY;
      model.addAttribute("packageId", packageId);
      model.addAttribute("tenderKey", tenderKey);
      model.addAttribute("projectId", projectId);
      model.addAttribute("flowDefineId", flowDefineId);
      model.addAttribute("checkPassId", checkPassId);
      return "bss/ppms/advanced_project/winning_supplier/supplierUpload";
    }
    
    /**
     * 
     *〈查看明细〉
     *〈详细描述〉
     * @author FengTian
     * @param model
     * @param packageId
     * @return
     */
    @RequestMapping("/openDetail")
    public String openDetail(Model model, String packageId){
        if(StringUtils.isNotBlank(packageId)){
            AdvancedPackages packages = packageService.selectById(packageId);
            if(packages != null){
                HashMap<String, Object> map = new HashMap<>();
                map.put("packageId", packages.getId());
                List<AdvancedDetail> list = detailService.selectByAll(map);
                if(list != null && !list.isEmpty()){
                    model.addAttribute("list", list);
                }
                model.addAttribute("packageName", packages.getName());
            }
        }
        return "bss/ppms/advanced_project/winning_supplier/package_list";
    }
    
    /**
     * 
     *〈录入标的〉
     *〈详细描述〉
     * @author FengTian
     * @param projectId
     * @param packageId
     * @param model
     * @param supplierId
     * @param passId
     * @return
     */
    @RequestMapping("/inputList")
    public String inputList(String projectId, String packageId, String supplierIds, Model model, String supplierId, String passId){
        if(StringUtils.isNotBlank(packageId)){
            List<SupplierCheckPass> listCheckPass = checkPassService.listCheckPassOrderRanking(packageId);
            if(listCheckPass != null && !listCheckPass.isEmpty()){
                HashMap<String, Object> hashMap=new HashMap<String, Object>();
                hashMap.put("supplierId", listCheckPass.get(0).getSupplierId());
                hashMap.put("packageId", packageId);
                List<theSubject> theSubjects = theSubjectService.selectBysupplierIdAndPackagesId(hashMap);
                HashMap<String,Object> map = new HashMap<>();
                map.put("packageId", packageId);
                List<AdvancedDetail> selectByAll = detailService.selectByAll(map);
                //获取明细
                for (AdvancedDetail details : selectByAll) {
                    if(theSubjects != null && !theSubjects.isEmpty()){
                        for (theSubject subject : theSubjects) {
                            if(StringUtils.isNotBlank(subject.getDetailId())){
                                if(subject.getDetailId().equals(details.getId())){
                                    details.setBudget(subject.getUnitPrice());
                                }
                            }
                        }
                    }else {
                        details.setBudget(null);
                    }
                    model.addAttribute("detailList", selectByAll);
                }
            }
        }
        if(StringUtils.isNotBlank(passId)){
            SupplierCheckPass checkPass = checkPassService.findByPrimaryKey(passId);
            if(checkPass != null){
                model.addAttribute("pass", checkPass);
            }
        }
        model.addAttribute("packageId", packageId);
        model.addAttribute("projectId", projectId);
        model.addAttribute("supplierId", supplierId);
        model.addAttribute("supplierIds", supplierIds);
        return "bss/ppms/advanced_project/winning_supplier/add_list";
    }
    
    /**
     * 
     *〈执行完成〉
     *〈详细描述〉
     * @author FengTian
     * @param projectId
     * @param flowDefineId
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping("/executeFinish")
    public String executeFinish(String  projectId, String flowDefineId,HttpServletRequest request){
        //获取已有中标供应商的包组
        String[] packList = checkPassService.selectWonBid(projectId);
        //查看项目下有多少包
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("projectId", projectId);
        List<AdvancedPackages> list = packageService.selectByAll(map);
        if(list.size() != packList.length){
            return JSON.toJSONString(ERROR);
        } else {
            
        }
        return JSON.toJSONString(SUCCESS);
    }
    
    private String[] ratio(Integer key) {
        String[] str = null;
        switch (key) {
            case 1:
                str = new String[]{"100"};
                break;
            case 2:
                str = new String[]{"70","30"};
                break;
            case 3:
                str = new String[]{"50","30","20"};
                break;
            case 4:
                str = new String[]{"40","30","20","10"};
                break;
            default:
                break;
        }
        return str;
    }
    
}
